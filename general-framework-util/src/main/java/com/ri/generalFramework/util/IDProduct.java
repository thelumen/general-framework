package com.ri.generalFramework.util;

import java.util.UUID;

public class IDProduct {
    /**
     * 获得一个UUID
     *
     * @return String IDProduct
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        //去掉“-”符号
        return uuid.replaceAll("-", "");
    }
}
