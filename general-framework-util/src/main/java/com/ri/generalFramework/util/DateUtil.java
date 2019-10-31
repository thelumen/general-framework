package com.ri.generalFramework.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * 获取距当前时间某个时长的时间
     *
     * @param field java.util.Calendar 单位
     * @param amount 偏移值
     * @return
     */
    public static Date getDateFromNow(int field, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * 获取距传入时间某个时长的时间
     *
     * @param date 基准时间
     * @param field java.util.Calendar 偏移单位
     * @param amount 偏移值
     * @return
     */
    public static Date getDateFrom(Date date, int field, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }
}
