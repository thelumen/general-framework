package com.ri.generalFramework.filter;

import com.netflix.zuul.FilterProcessor;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.ri.generalFramework.util.LogTextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 路由包括3阶段, 正常处理过程：preRoute-->route-->postRoute
 * 其中preRoute 和route异常时，会被捕获直接进入postRoute;
 * 会经过postRoute的过滤器(SendErrorFilter extends ZuulFilter）
 * 但是postRoute阶段的异常，是没有处理的过滤器的。以此需要针对postRoute阶段的异常进行单独处理
 * Created by Administrator on 2019/5/23.
 */
public class DidiFilterProcessor extends FilterProcessor {
    @Override
    public Object processZuulFilter(ZuulFilter filter) throws ZuulException {
        try {
            return super.processZuulFilter(filter);
        } catch (Exception e) {
            RequestContext ctx = RequestContext.getCurrentContext();
            HttpServletRequest request = ctx.getRequest();
            ctx.set("failed.exception", e);
            ctx.set("failed.filter", filter);
            ctx.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ctx.set("error.exception", e.getCause());
            LogTextUtils logger = LogTextUtils.getLogger();
            logger.writeError(e, new String[]{String.format("\rDidiFilterProcessor error %s request to %s", request.getMethod(), request.getRequestURL().toString())});

            return null;
        }
    }
}
