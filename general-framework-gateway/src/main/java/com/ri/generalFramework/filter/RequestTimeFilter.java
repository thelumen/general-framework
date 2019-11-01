package com.ri.generalFramework.filter;

import com.ri.generalFramework.filterFactory.RequestTimeGatewayFilterFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

import static com.ri.generalFramework.util.GatewayFilterUtil.*;

public class RequestTimeFilter implements GatewayFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(RequestTimeFilter.class);
    private static final Log log = LogFactory.getLog(GatewayFilter.class);

    private RequestTimeGatewayFilterFactory.Config config;

    public RequestTimeFilter(RequestTimeGatewayFilterFactory.Config config) {
        this.config = config;
    }

    private static final String REQUEST_TIME_BEGIN = "requestTimeBegin";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(REQUEST_TIME_BEGIN, System.currentTimeMillis());
        ServerHttpRequest oldRequest = exchange.getRequest();
        String contentType = oldRequest.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
        URI uri = oldRequest.getURI();
        HttpMethod method = oldRequest.getMethod();
        String traceID = UUID.randomUUID().toString().replaceAll("-", "");
        // 定义新的消息头
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(oldRequest.getHeaders());
        // 添加消息头
        headers.set("TraceId", traceID);
        // 设置CONTENT_TYPE
        if (StringUtils.isNotBlank(contentType)) {
            headers.set(HttpHeaders.CONTENT_TYPE, contentType);
        }
        //post请求时，如果是文件上传之类的请求，不修改请求消息体
        if (method == HttpMethod.POST && (MediaType.APPLICATION_FORM_URLENCODED_VALUE.equalsIgnoreCase(contentType)
                || MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(contentType))) {
            //从请求里获取Post请求体
            String bodyStr = resolveBodyFromRequest(oldRequest);
            // 这种处理方式，必须保证post请求时，原始post表单必须有数据过来，不然会报错
            if (StringUtils.isBlank(bodyStr)) {
                logger.error("请求异常：{} POST请求必须传递参数", oldRequest.getURI().getRawPath());
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.BAD_REQUEST);
                return response.setComplete();
            }
            //下面的将请求体再次封装写回到request里，传到下一级，否则，由于请求体已被消费，后续的服务将取不到值
            URI newUri = UriComponentsBuilder.fromUri(uri).build(true).toUri();
            ServerHttpRequest request = oldRequest.mutate().uri(newUri).build();
            DataBuffer bodyDataBuffer = stringBuffer(bodyStr);
            Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);

            // 由于修改了传递参数，需要重新设置CONTENT_LENGTH，长度是字节长度，不是字符串长度
            int length = bodyStr.getBytes().length;
            headers.remove(HttpHeaders.CONTENT_LENGTH);
            headers.setContentLength(length);

            // 由于post的body只能订阅一次，由于上面代码中已经订阅过一次body。所以要再次封装请求到request才行，不然会报错请求已经订阅过
            request = reBuildRequest(request, headers.getContentLength(), bodyFlux);
            //封装request，传给下一级
            request.mutate().header(HttpHeaders.CONTENT_LENGTH, Integer.toString(bodyStr.length()));
            return chain.filter(exchange.mutate().request(request).build()).then(Mono.fromRunnable(getRunnable(exchange)));
        } else if (method == HttpMethod.GET) {
            headers.remove(HttpHeaders.CONTENT_LENGTH);
            headers.setContentLength(0);
            URI newUri = UriComponentsBuilder.fromUri(uri).build(true).toUri();
            ServerHttpRequest request = oldRequest.mutate().uri(newUri).build();
            request = reBuildRequest(request, headers.getContentLength());
            return chain.filter(exchange.mutate().request(request).build()).then(Mono.fromRunnable(getRunnable(exchange)));
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 返回执行结构
     *
     * @param exchange ServerWebExchange
     * @return Runnable
     */
    private Runnable getRunnable(ServerWebExchange exchange) {
        return () -> {
            Long startTime = exchange.getAttribute(REQUEST_TIME_BEGIN);
            if (startTime != null) {
                StringBuilder sb = new StringBuilder(exchange.getRequest().getURI().getRawPath())
                        .append(": ")
                        .append(System.currentTimeMillis() - startTime)
                        .append("ms");
                if (config.isWithParams()) {
                    sb.append(" params:").append(exchange.getRequest().getQueryParams());
                }
                log.info(sb.toString());
            }
        };
    }

}
