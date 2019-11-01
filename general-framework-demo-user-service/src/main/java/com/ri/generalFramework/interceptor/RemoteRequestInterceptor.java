package com.ri.generalFramework.interceptor;

import com.ri.generalFramework.util.TraceIdUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 远程调用的拦截器
 */
public class RemoteRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {

        TraceIdUtils.remoteTransferTraceId(requestTemplate);
    }
}
