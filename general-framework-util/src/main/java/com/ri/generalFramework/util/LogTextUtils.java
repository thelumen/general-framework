package com.ri.generalFramework.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

public class LogTextUtils {
    //Logger实例
    private Log logError;
    private Log logInfo;

    //将Log类封装为单例模式
    private static LogTextUtils logTextUtils = new LogTextUtils();

    //构造函数，用于初始化Logger配置需要的属性
    private LogTextUtils() {

        //获得当前目录路径
        //String filePath=this.getClass().getResource("/").getPath()+"log4j.properties";
        //System.out.println(filePath);
        //logger所需的配置文件路径
        //PropertyConfigurator.configure(filePath);
        //获得日志类logger的实例
        //logger = Logger.getLogger(LogTextUtils.class);
        logInfo = LogFactory.getLog("myInfoLog");
        logError = LogFactory.getLog("myErrorLog");
    }

    public static LogTextUtils getLogger() {
        return logTextUtils;
    }

    public void writeError(String message) {
        logError.error(message);
    }

    public void writeInfo(String message) {
        Object obj = TraceIdUtils.getTraceId();
        if (null != obj) {
            logInfo.info(obj + " " + message);
        } else {
            logInfo.info(message);
        }

    }

    public void writeWarn(String message) {
        logInfo.warn(message);
    }

    public void writeDebug(String message) {
        logInfo.debug(message);
    }

    public void writeError(Throwable ex, String[] items) {
        StringBuilder stringBuffer = new StringBuilder();
        if (items != null) {
            for (String string : items) {
                stringBuffer.append(string);
            }
        }
        Object obj = TraceIdUtils.getTraceId();
        if (null != obj) {
            obj = "\rtraceId: " + obj;
            stringBuffer.append(obj);
        }
        stringBuffer.append("\r错误信息:");
        //存在错误信息为空的情况
        if (StringUtils.hasLength(ex.getMessage())) {
            //解决elk展示问题，但是日志文件则看着很乱
            stringBuffer.append(ex.getMessage().replaceAll("\n", ""));
        } else {
            if (null != ex.getCause())
                ex = ex.getCause();
            if (null != ex && !StringUtils.isEmpty(ex.getMessage())) {
                stringBuffer.append(ex.getMessage().replaceAll("\n", ""));
            }
        }
        stringBuffer.append("\r错误对象:");
        if (null != ex) {
            stringBuffer.append(ex.toString());
        }
        stringBuffer.append("\r错误轨迹:");

        if (null != ex) {
            StackTraceElement[] messages = ex.getStackTrace();
            for (StackTraceElement message : messages) {
                stringBuffer.append(message.toString());
                stringBuffer.append("\r");
            }
        }

        logError.error(stringBuffer.toString());
        if (ex != null) {
            ex.printStackTrace();
        }
        //System.out.println(stringBuffer.toString());
    }

}
