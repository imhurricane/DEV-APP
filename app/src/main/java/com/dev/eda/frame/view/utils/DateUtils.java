package com.dev.eda.frame.view.utils;

import android.annotation.SuppressLint;

import com.dev.eda.app.utils.DateTool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static final int formatStr_yyyyMMddHHmmss = 1;
    public static final int formatStr_yyyyMMddHHmm = 2;
    public static final int formatStr_yyyyMMddHH = 3;
    public static final int formatStr_yyyyMMdd = 4;
    public static final int formatStr_yyyyMM = 5;
    public static final int formatStr_yyyy = 6;
    public static final int formatStr_MMdd = 7;
    public static final int formatStr_HHmm = 8;
    public static final int formatStr_HHmmss = 9;

    @SuppressLint("SimpleDateFormat")
    public static String getTime(Date date, int formatStr) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        switch (formatStr) {
            case formatStr_yyyyMMddHHmmss:
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                break;
            case formatStr_yyyyMMddHHmm:
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                break;
            case formatStr_yyyyMMddHH:
                format = new SimpleDateFormat("yyyy-MM-dd HH");
                break;
            case formatStr_yyyyMMdd:
                format = new SimpleDateFormat("yyyy-MM-dd");
                break;
            case formatStr_yyyyMM:
                format = new SimpleDateFormat("yyyy-MM");
                break;
            case formatStr_yyyy:
                format = new SimpleDateFormat("yyyy");
                break;
            case formatStr_MMdd:
                format = new SimpleDateFormat("MM-dd");
                break;
            case formatStr_HHmm:
                format = new SimpleDateFormat("HH:mm");
                break;
            case formatStr_HHmmss:
                format = new SimpleDateFormat("HH:mm:ss");
                break;
            default:
                break;
        }
        return format.format(date);
    }

    public static boolean[] getTimePickerType(int format) {
        boolean[] type;
        switch (format) {
            case formatStr_yyyyMMddHHmm:
                type = new boolean[]{true, true, true, true, true, false};
                break;
            case formatStr_yyyyMMddHH:
                type = new boolean[]{true, true, true, true, false, false};
                break;
            case formatStr_yyyyMMdd:
                type = new boolean[]{true, true, true, false, false, false};
                break;
            case formatStr_yyyyMM:
                type = new boolean[]{true, true, false, false, false, false};
                break;
            case formatStr_yyyy:
                type = new boolean[]{true, false, false, false, false, false};
                break;
            case formatStr_MMdd:
                type = new boolean[]{false, true, true, false, false, false};
                break;
            case formatStr_HHmm:
                type = new boolean[]{false, false, false, true, true, false};
                break;
            case formatStr_HHmmss:
                type = new boolean[]{false, false, false, true, true, true};
                break;
            default:
                type = new boolean[]{true, true, true, true, true, true};
                break;
        }
        return type;
    }


    public static String getFormStr(String dateStr, int formatStr) throws ParseException {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = format.parse(dateStr);
        switch (formatStr) {
            case formatStr_yyyyMMddHHmmss:
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                break;
            case formatStr_yyyyMMddHHmm:
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                break;
            case formatStr_yyyyMMddHH:
                format = new SimpleDateFormat("yyyy-MM-dd HH");
                break;
            case formatStr_yyyyMMdd:
                format = new SimpleDateFormat("yyyy-MM-dd");
                break;
            case formatStr_yyyyMM:
                format = new SimpleDateFormat("yyyy-MM");
                break;
            case formatStr_yyyy:
                format = new SimpleDateFormat("yyyy");
                break;
            case formatStr_MMdd:
                format = new SimpleDateFormat("MM-dd");
                break;
            case formatStr_HHmm:
                format = new SimpleDateFormat("HH:mm");
                break;
            case formatStr_HHmmss:
                format = new SimpleDateFormat("HH:mm:ss");
                break;
            default:
                break;
        }
        return format.format(parse);
    }
}