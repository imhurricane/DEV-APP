package com.dev.eda.app.utils;

public class HttpRequestUrl {

    public static String BaseURI = "http://192.168.1.170:7001/";

    private static String BaseURL = BaseURI + "appservlet/?requestdir=app.http&requesttype=";
    private static String BaseMenuURL = BaseURI + "appservlet/?requestdir=app.base.http&requesttype=";

    public static String UpdateAppUrl = BaseURI + "apk/app-release.apk";

    public static String LoginAppUrl = BaseURL + "LoginAppServlet";

    public static String LoginAppUrl1 = BaseURI + "appservlet/?requesttype=login";

    public static String CheckPluginApkVersionUrl = BaseURL + "CheckPluginApkVersion";

    public static String DownloadPluginApkUrl = BaseURI + "apk/";

    public static String LoadingMenuUrl = BaseMenuURL + "MainMenuServlet";

    public static String LoadingMenuItemUrl = BaseMenuURL + "MenuItemServlet";

    public static String LoadingMenuItemDetailUrl = BaseMenuURL + "MenuItemDetailServlet";

    public static String LoadingMenuItemDetailSaveUrl = BaseMenuURL + "MainMenuItemDetailSaveServlet";

    public static String LoadingMenuItemDetailSaveUrl1 = BaseURI + "appservlet/?requesttype=commonbutton&datatype=save";


}
