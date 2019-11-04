package com.ri.generalFramework.constants;

public enum Trace {
    TraceID("TraceId"),
    DataID("DataId")
    ;

    private String ID;

    Trace(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }
}
