package com.dev.eda.app.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import java.io.File;
import java.util.Locale;

import static android.content.Context.TELEPHONY_SERVICE;

public class DeviceUtils {

    private DeviceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断设备是否root
     *
     * @return the boolean{@code true}: 是<br>{@code false}: 否
     */
    public static boolean isDeviceRoot() {

        String su = "su";
        String[] locations = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/", "/system/bin/failsafe/",
                "/data/local/xbin/", "/data/local/bin/", "/data/local/"};
        for (String location : locations) {
            if (new File(location + su).exists()) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取设备系统版本号
     *
     * @return 设备系统版本号
     */
    public static int getSDKVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }


    /**
     * 获取设备AndroidID
     *
     * @param context 上下文
     * @return AndroidID
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取终端型号
     */
    public static String getTem_Model() {
        String tem_no = android.os.Build.MODEL;
        return tem_no;
    }

    /**
     * 获取终端品牌
     */
    public static String getTem_Release() {
        String tem_title = android.os.Build.VERSION.RELEASE;
        return tem_title;
    }

    /**
     * 获取当前手机系统语言。
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 关机
     * <p>需要root权限或者系统权限 {@code <android:sharedUserId="android.uid.system"/>}</p>
     */
    public static void shutdown(Context context) {
        ShellUtils.exeCmd("reboot -p", true);
        Intent intent = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
        intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 重启
     * <p>需要root权限或者系统权限 {@code <android:sharedUserId="android.uid.system"/>}</p>
     */
    public static void reboot(Context context) {
        ShellUtils.exeCmd("reboot", true);
        Intent intent = new Intent(Intent.ACTION_REBOOT);
        intent.putExtra("nowait", 1);
        intent.putExtra("interval", 1);
        intent.putExtra("window", 0);
        context.sendBroadcast(intent);
    }



    /**
     * 重启
     * <p>需系统权限 {@code <android:sharedUserId="android.uid.system"/>}</p>
     * @param reason 传递给内核来请求特殊的引导模式，如"recovery"
     */
    public static void reboot(Context context,String reason) {
        PowerManager mPowerManager = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        try {
            mPowerManager.reboot(reason);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    /**
     * 重启到recovery
     * <p>需要root权限</p>
     */
    public static void reboot2Recovery() {
        ShellUtils.exeCmd("reboot recovery", true);
    }


    /**
     * 重启到bootloader
     * <p>需要root权限</p>
     */
    public static void reboot2Bootloader() {
        ShellUtils.exeCmd("reboot bootloader", true);
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     * @return 手机IMEI
     */
    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (tm != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            return tm.getDeviceId();
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getImei(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (tm != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            return tm.getImei();
        }
        return null;
    }

}
