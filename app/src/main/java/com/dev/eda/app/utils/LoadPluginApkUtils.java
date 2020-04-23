package com.dev.eda.app.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dev.eda.app.LocalStorage.LocalStorage;
import com.dev.eda.app.http.callback.JsonCallback;
import com.dev.eda.frame.home.model.PluginModel;
import com.didi.virtualapk.PluginManager;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.v3.MessageDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;

public class LoadPluginApkUtils {

    public static final String WIFIDIS_PLUGIN_APK_NAME = "plugin_demo.apk";
    public static final String WIFIDIS_PLUGIN_VERSION_KEY = "plugin_demo";
    public static final String WIFIDIS_PLUGIN_SD_PATH = Environment.getExternalStorageDirectory() + "";
    public static final String WIFIDIS_PLUGIN_PACKAGE_NAME = "com.dev.wifidistanc";
    public static final String WIFIDIS_PLUGIN_LAUNCHER_ACTIVITY = "com.dev.wifidistanc.PluginMainActivity";

    public static final String TOUR_PLUGIN_APK_NAME = "plugin_tour.apk";
    public static final String TOUR_PLUGIN_VERSION_KEY = "plugin_tour";
    public static final String TOUR_PLUGIN_SD_PATH = Environment.getExternalStorageDirectory() + "";
    public static final String TOUR_PLUGIN_PACKAGE_NAME = "com.dev.tour.intelcheckbash";
    public static final String TOUR_PLUGIN_LAUNCHER_ACTIVITY = "com.dev.tour.intelcheckbash.TourMainActivity";

    /**
     * 运行插件中activity
     **/
    public static void showPluginActivity(Context context, String packageName, String activityName) {

        try {
            if (PluginManager.getInstance(context).getLoadedPlugin(packageName) == null) {
                Toast.makeText(context, "插件未加载,请尝试重启APP", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent();
            intent.setClassName(packageName, activityName);
            context.startActivity(intent);

        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }

    }

    /**
     * 加载插件
     **/
    public static void loadPlugin(Context context, String pluginFilePath) {
        PluginManager pluginManager = PluginManager.getInstance(context);        //此处是当查看插件apk是否存在,如果存在就去加载(比如修改线上的bug,把插件apk下载,达到热修复)
        File pluginApk = new File(pluginFilePath);
        if (pluginApk.exists()) {
            try {
                pluginManager.loadPlugin(pluginApk);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 检查插件新版本
     *
     * @param context
     * @param sdCardPath       sd卡路径
     * @param pluginApkName    插件apk名称（xxx.apk）
     * @param pluginVersionKey 插件apk版本在手机端存储key
     */
    public static void checkPluginApkVersion(Context context, String sdCardPath, String pluginApkName, String pluginVersionKey, String pluginPackageName, String pluginActivityName) {
        String ver = LocalStorage.getString(context, pluginVersionKey);
        String pluginApkFilePath = sdCardPath + File.separator + pluginApkName;
        OkGo.<PluginModel>get(HttpRequestUrl.CheckPluginApkVersionUrl)
                .tag(context) //请求的TAG，用于取消对应的请求
                .params("versioncode", ver)
                .execute(new JsonCallback<PluginModel>(PluginModel.class) {
                    @Override
                    public void onSuccess(Response<PluginModel> response) {
                        PluginModel pluginEntry = response.body();
                        String versionCode = pluginEntry.getVersionCode();

                        if (response.code() == 200) {
                            if (pluginEntry.getNewVersion()) {
                                showAlert(context, sdCardPath, pluginApkName, versionCode, pluginVersionKey);
                            } else {
                                loadPlugin(context, pluginApkFilePath);
                                showPluginActivity(context, pluginPackageName, pluginActivityName);
                            }
                        }
                    }

                    @Override
                    public void onError(Response<PluginModel> response) {
                        super.onError(response);
                        LoadPluginApkUtils.loadPlugin(context, pluginApkFilePath);
                    }
                });
    }

    private static void DownloadPluginApk(Context context, String sdCardPath, String pluginApkName, String versionCode, String pluginVersionKey) {
        String pluginApkFilePath = sdCardPath + File.separator + pluginApkName;

        CommonProgressDialog mDialog = new CommonProgressDialog(context);
        mDialog.setMessage("正在下载");
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setCanceledOnTouchOutside(true);

        OkGo.<File>get(HttpRequestUrl.DownloadPluginApkUrl + pluginApkName).tag(context).execute(new FileCallback(sdCardPath, pluginApkName) { //文件下载时指定下载的路径以及下载的文件的名称
            @Override
            public void onStart(Request<File, ? extends Request> request) {
                super.onStart(request);
                Log.d("TAG", "开始下载文件");
                mDialog.show();
            }

            @Override
            public void onSuccess(com.lzy.okgo.model.Response<File> response) {
                Log.d("TAG", "下载文件成功:" + response.body().length());
                String path = response.body().getAbsolutePath();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                Log.d("TAG", "下载文件完成");
//                mDialog.dismiss();
//                MessageDialog.show((AppCompatActivity) context,"插件更新","更新包下载完成");
                LocalStorage.saveString(context, pluginVersionKey, versionCode);
                loadPlugin(context, pluginApkFilePath);
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<File> response) {
                super.onError(response);
                Log.e("TAG", "下载文件出错:" + response.message());
                loadPlugin(context, pluginApkFilePath);
            }

            @Override
            public void downloadProgress(Progress progress) {
                super.downloadProgress(progress);
                long as = progress.totalSize;
                float dLProgress = progress.fraction;
                Log.e("TAG", "下载文件进度:" + dLProgress);
//                progressDialog.incrementProgressBy((int) (100 * dLProgress));
                mDialog.setMax((int) as);
                mDialog.setProgress((int) (as*dLProgress));
            }
        });
    }

    private static void showAlert(Context context, String sdCardPath, String pluginApkName, String versionCode, String pluginVersionKey) {

        MessageDialog.show((AppCompatActivity) context,"提示","该模块需要更新资源后使用","确认","取消")
                .setOkButton(new OnDialogButtonClickListener() {
                    @Override
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        DownloadPluginApk(context, sdCardPath, pluginApkName, versionCode, pluginVersionKey);
                        return false;
                    }
                });

    }
}
