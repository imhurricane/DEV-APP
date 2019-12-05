package com.dev.eda.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTool {


    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date addDate(int hours)
    {
        Date nowTime = new Date();
        Date date = new Date(nowTime.getTime() + hours*60*60*1000);
        return date;
    }

    public static Date subDate(int hours)
    {
        Date nowTime = new Date();
        Date date = new Date(nowTime.getTime() - hours*60*60*1000);
        return date;
    }

    public static String addDateString(int hours)
    {
        Date nowTime = new Date();
        Date date = new Date(nowTime.getTime() + hours*60*60*1000);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }

    public static String subDateString(int hours)
    {
        Date nowTime = new Date();
        Date date = new Date(nowTime.getTime() - hours*60*60*1000);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }

    public static String getCurrentDateString(String pattern)
    {
        Date nowTime = new Date();
        //yyyy-MM-dd HH:mm:ss
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(nowTime);
    }
}
