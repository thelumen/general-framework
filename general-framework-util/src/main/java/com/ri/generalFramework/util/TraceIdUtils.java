package com.ri.generalFramework.util;

import feign.RequestTemplate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

import static com.ri.generalFramework.constants.Trace.DataID;
import static com.ri.generalFramework.constants.Trace.TraceID;

/**
 * 全链路日志跟踪工具类
 */
public final class TraceIdUtils {

    private static final Log log = LogFactory.getLog(TraceIdUtils.class);

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
            requestTemplate.header(TraceID.getID(), obj.toString());

        if (null != obj2)
            requestTemplate.header(DataID.getID(), obj2.toString());
    }

    /***
     * 接受被远程调用使用
     */
    public static void transferTraceId(HttpServletRequest request) {
        // 兼容服务之间传递使用header传递数据-start
        Object obj = request.getHeader(TraceID.getID());//跟踪ID
        Object obj2 = request.getHeader(DataID.getID());//跟踪ID
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
    public static Object removeTraceId() {
        Object obj = getTraceId();
        if (null != obj) {
            threadLocal_1.remove();
            log.info("traceId removed: " + obj);
        }

        Object obj2 = getDataId();
        if (null != obj2) {
            threadLocal_2.remove();
            log.info("dataId removed: " + obj2);
        }

        return obj;
    }
}
