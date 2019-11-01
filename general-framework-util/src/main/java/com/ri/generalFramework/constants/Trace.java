package com.ri.generalFramework.constants;

public enum Trace {
    TraceID("TraceId"),
    DataID("DataId")
    ;

    private String traceId;

    Trace(String traceId) {
        this.traceId = traceId;
    }
}
