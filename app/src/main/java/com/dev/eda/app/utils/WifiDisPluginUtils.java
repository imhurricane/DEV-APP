package com.dev.eda.app.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


import com.didi.virtualapk.PluginManager;

import java.io.File;

public class WifiDisPluginUtils {

    public static final String PLUGIN_APK_NAME = "plugin_demo.apk";

    public static final String PLUGIN_SD_PATH = Environment.getExternalStorageDirectory()+"";
    /**
     * 插件文件路径
     **/
    public static final String PLUGIN_FILE_PATH = PLUGIN_SD_PATH + File.separator + PLUGIN_APK_NAME;

    /**
     * 插件apk包名
     **/
    private static final String PLUGIN_PACKAGE_NAME = "com.dev.wifidistanc";
    /**
     * 插件apk入口activity
     **/
    private static final String PLUGIN_LAUNCHER_ACTIVITY = "com.dev.wifidistanc.PluginMainActivity";

    /**
     * 运行插件中activity
     **/
    public static void showPluginActivity(Context context) {
        if (PluginManager.getInstance(context).getLoadedPlugin(PLUGIN_PACKAGE_NAME) == null) {
            Log.e("showPluginActivity","插件未加载,请尝试重启APP");
            Toast.makeText(context, "插件未加载,请尝试重启APP", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.setClassName(PLUGIN_PACKAGE_NAME, PLUGIN_LAUNCHER_ACTIVITY);
        context.startActivity(intent);
    }
}
