/*
 * Commons-Utils
 * Copyright (c) 2017.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 */

package com.ykun.commons.utils.commons;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @ClassName: DateUtils
 * @Description: 日期工具类
 */

public class DateUtils {

    public static final String YYYY_MM = "yyyy-MM";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH:00:00";

    public static final String YYYY_MM_DD_EN = "yyyy/MM/dd";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 增加日期中某类型的某数值。如增加日期
     *
     * @param date     日期
     * @param dateType 类型
     * @param amount   数值
     * @return 计算后日期
     */
    private static Date addInteger(Date date, int dateType, int amount) {
        Date myDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(dateType, amount);
            myDate = calendar.getTime();
        }
        return myDate;
    }

    /**
     * 增加年
     *
     * @param date
     * @param yearAmount
     * @return Date
     */
    public static Date addYear(Date date, int yearAmount) {
        return addInteger(date, Calendar.YEAR, yearAmount);
    }

    /**
     * 增加月
     *
     * @param date
     * @param monthAmount
     * @return Date
     */
    public static Date addMonth(Date date, int monthAmount) {
        return addInteger(date, Calendar.MONTH, monthAmount);
    }

    /**
     * 增加天数
     *
     * @param date
     * @param dayAmount
     * @return Date
     */
    public static Date addDay(Date date, int dayAmount) {
        return addInteger(date, Calendar.DATE, dayAmount);
    }

    /**
     * 增加小时
     *
     * @param date
     * @param hourAmount
     * @return Date
     */
    public static Date addHour(Date date, int hourAmount) {
        return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
    }

    /**
     * 增加分钟
     *
     * @param date
     * @param minuteAmount
     * @return Date
     */
    public static Date addMinute(Date date, int minuteAmount) {
        return addInteger(date, Calendar.MINUTE, minuteAmount);
    }

    /**
     * 增加秒
     *
     * @param date
     * @param secondAmount
     * @return Date
     */
    public static Date addSecond(Date date, int secondAmount) {
        return addInteger(date, Calendar.SECOND, secondAmount);
    }

    /**
     * @param date
     * @param pattern
     * @return
     * @throws
     * @Description:将Date对象转换成字符串格式，时区为零时区
     */
    public static String dateToString(Date date, String pattern) {
        return dateToString(date, pattern, TimeZone.getDefault());
    }

    /**
     * @param date
     * @return
     * @throws
     * @Description:将Date对象转换成字符串格式。默认yyyy-MM-dd HH:mm:ss格式，时区为零时区
     */
    public static String dateToString(Date date) {
        return dateToString(date, YYYY_MM_DD_HH_MM_SS, TimeZone.getDefault());
    }

    /**
     * @param date     Date对象
     * @param pattern  字符串的格式
     * @param timezone 时区
     * @return
     * @throws
     * @Description:将Date对象转换成字符串格式
     */
    public static String dateToString(Date date, String pattern, TimeZone timezone) {
        SimpleDateFormat dateFormat = null;
        if (null == pattern || "".equals(pattern)) {
            dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        } else {
            dateFormat = new SimpleDateFormat(pattern);
        }
        dateFormat.setTimeZone(timezone);
        return dateFormat.format(date);
    }

    /**
     * @param date
     * @param pattern
     * @param timezone
     * @return
     * @throws
     * @Description:将日期字符串，按照pattern格式，某个时区解析为Date对象
     */
    public static Date stringToDate(String date, String pattern, String timezone) {
        Date newDate = null;
        try {
            SimpleDateFormat dateFormat = null;
            if (null == pattern || "".equals(pattern)) {
                dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
            } else {
                dateFormat = new SimpleDateFormat(pattern);
            }
            dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
            newDate = dateFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDate;
    }

    /**
     * @param date
     * @return
     * @throws
     * @Description:将日期字符串，按照pattern格式，零时区解析为Date对象
     */
    public static Date stringToDate(String date, String pattern) {
        return stringToDate(date, pattern, "GMT");
    }

    /**
     * @param date
     * @return
     * @throws
     * @Description:将日期字符串，按照yyyy-MM-dd HH:mm:ss格式，零时区解析为Date对象
     */
    public static Date stringToDate(String date) {
        return stringToDate(date, YYYY_MM_DD_HH_MM_SS, "GMT");
    }

    /**
     * 将日期转换为基于GMT时区的目标时区日期
     *
     * @param date     日期
     * @param timezone 目标时区
     * @return Date
     */
    public static Date getDateBaseGMT(Date date, String timezone) {
        TimeZone srcZone = TimeZone.getTimeZone("GMT");
        TimeZone toZone = TimeZone.getTimeZone(timezone);
        Long targetTime = date.getTime() - srcZone.getRawOffset() + toZone.getRawOffset();
        return new Date(targetTime);
    }

    /**
     * 将日期转换为基于GMT时区的目标时区日期
     *
     * @param date     日期
     * @param timezone 目标时区
     * @return String 格式：yyyy-MM-dd HH:mm:ss
     */
    public static String getStringBaseGMT(Date date, String timezone) {
        return dateToString(getDateBaseGMT(date, timezone));
    }

    /**
     * 检查两个日期是否为同一天
     *
     * @param preDate  第一个日期
     * @param postDate 第二个日期
     * @return true:同一天|false:不是同一天
     */
    public static boolean isSameDay(Date preDate, Date postDate) {
        return dateToString(preDate, YYYY_MM_DD).equals(dateToString(postDate, YYYY_MM_DD));
    }

    public static Date getNowHourDate(Date date) {
        String now = dateToString(date, YYYY_MM_DD_HH);
        return stringToDate(now);
    }

    /**
     * @param timezone 时区
     * @param dateTime 日期时间
     * @return
     * @throws ParseException
     * @throws
     * @Description:将一个字符串日期时间，按照某个时区转成时间戳（秒）
     */
    public static long getTimestamp(String timezone, String dateTime, String pattern) throws ParseException {
        SimpleDateFormat sdf = null;
        if (null == pattern || "".equals(pattern)) {
            sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        } else {
            sdf = new SimpleDateFormat(pattern);
        }
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
        long b = sdf.parse(dateTime).getTime() / 1000;
        return b;
    }

}
