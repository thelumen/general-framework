package com.ri.generalFramework.filter;

import com.ri.generalFramework.filterFactory.RequestTimeGatewayFilterFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class RequestTimeFilter implements GatewayFilter, Ordered {

    private RequestTimeGatewayFilterFactory.Config config;

    public RequestTimeFilter(RequestTimeGatewayFilterFactory.Config config) {
        this.config = config;
    }

    private static final Log log = LogFactory.getLog(GatewayFilter.class);
    private static final String REQUEST_TIME_BEGIN = "requestTimeBegin";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        exchange.getAttributes().put(REQUEST_TIME_BEGIN, System.currentTimeMillis());
        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
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
                })
        );
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
