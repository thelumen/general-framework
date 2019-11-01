package com.ri.generalFramework.filter;

import brave.Tracer;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component
public class AccessUserNameFilter extends ZuulFilter {

    @Autowired
    Tracer tracer;

    /**
     * 设置过滤器类型、
     *
     * @return FilterConstants
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 设置过滤器优先级
     * 数字越小优先级越高
     *
     * @return 优先级
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 设置过滤器是否生效
     *
     * @return boolean
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        System.out.println(String.format("%s AccessUserNameFilter request to %s", request.getMethod(), request.getRequestURL().toString()));
        // 请求参数写入监控链
        request.getParameterMap().forEach((k, v) -> tracer.currentSpan().tag(k, getStringFromArray(v)));

        String username = request.getParameter("name");// 获取请求的参数
        if (null != username && !"".equals(username)) {// 如果请求的参数不为空，且值为chhliu时，则通过
            tracer.currentSpan().tag("name", username);
            ctx.setSendZuulResponse(true);// 对该请求进行路由
            ctx.setResponseStatusCode(200);
            return null;
        } else {
            tracer.currentSpan().tag("name", "is null");
            ctx.setSendZuulResponse(false);// 过滤该请求，不对其进行路由
            ctx.setResponseStatusCode(401);// 返回错误码
            ctx.setResponseBody("{\"result\":\"username is not correct!\"}");// 返回错误内容
            return null;
        }
    }

    /**
     * 格式化字符串数组
     *
     * @param s 字符串数组
     * @return 格式化字符串
     */
    private String getStringFromArray(String[] s) {
        int l = s.length;
        return l == 0 ? null : l == 1 ? s[0] : Arrays.toString(s);
    }
}
