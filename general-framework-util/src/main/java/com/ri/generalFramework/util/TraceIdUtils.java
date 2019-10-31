package com.ri.generalFramework.util;

import feign.RequestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 全链路日志跟踪工具类
 */
public final class TraceIdUtils {
    private static ThreadLocal<Object> threadLocal_1 = new ThreadLocal<>();
    private static ThreadLocal<Object> threadLocal_2 = new ThreadLocal<>();

    private TraceIdUtils() {
    }

    /***
     * 开启记录跟踪日志
     */
    public static void createTraceId() {
        Object obj = getTraceId();
        if (null == obj) {
            obj = UUID.randomUUID().toString().replaceAll("-", "");
            threadLocal_1.set(obj);
        }
    }

    /***
     * 开启记录跟踪数据权限ID传递日志
     */
    public static void createDataId(String dataId) {
        Object obj = getDataId();
        if (null == obj) {
            threadLocal_2.set(dataId);
        }
    }

    /***
     * 调用远程服务时使用
     * @param requestTemplate
     */
    public static void remoteTransferTraceId(RequestTemplate requestTemplate) {
        Object obj = getTraceId();
        Object obj2 = getDataId();
        if (null != obj)
            requestTemplate.header("trace_id", obj.toString());

        if (null != obj2)
            requestTemplate.header("data_id", obj2.toString());
    }

    /***
     * 接受被远程调用使用
     */
    public static void transferTraceId(HttpServletRequest request) {
        // 兼容服务之间传递使用header传递数据-start
        Object obj = request.getHeader("trace_id");//跟踪ID
        Object obj2 = request.getHeader("data_id");//跟踪ID
        if (null != obj)
            threadLocal_1.set(obj);
        if (null != obj2)
            threadLocal_2.set(obj2);
    }

    public static Object getTraceId() {
        return threadLocal_1.get();
    }

    public static Object getDataId() {
        return threadLocal_2.get();
    }

    /***
     * 记住要调用删除，反之线程的内存溢出
     */
    public static void removeTraceId() {
        LogTextUtils logTextUtils = LogTextUtils.getLogger();
        Object obj = getTraceId();
        if (null != obj) {
            threadLocal_1.remove();
            logTextUtils.writeInfo("traceid removed: " + obj);
        }

        Object obj2 = getDataId();
        if (null != obj2) {
            threadLocal_2.remove();
            logTextUtils.writeInfo("dataid removed: " + obj2);
        }


    }
}
