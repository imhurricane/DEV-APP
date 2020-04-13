package com.dev.eda.app.utils;

public class HttpRequestUrl {

    public static String BaseURI = "http://192.168.1.170:7001";

    private static String BaseURL = BaseURI + "/appservlet/?requestdir=app.http&requesttype=";

    public static String UpdateAppUrl = BaseURI + "/apk/app-release.apk";

    public static String LoginAppUrl = BaseURL + "loginApp";

    public static String CheckPluginApkVersionUrl = BaseURL + "CheckPluginApkVersion";

    public static String DownloadPluginApkUrl = BaseURI + "/apk/";


}
