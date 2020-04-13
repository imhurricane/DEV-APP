package com.dev.eda.app.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.eda.R;
import com.dev.eda.app.LocalStorage.LocalStorage;
import com.dev.eda.frame.home.model.PluginModel;
import com.didi.virtualapk.PluginManager;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.v3.MessageDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;
import java.io.IOException;

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
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        progressDialog.setContentView(R.layout.loading);
        progressDialog.setTitle("定位系统有更新");
        progressDialog.setMessage("感谢您的支持");
        progressDialog.setIcon(R.drawable.shanghai);
        //设置进度条的属性
        progressDialog.setMax(100);
        //明确显示进度
//        progressDialog.setIndeterminate(true);
        //设置确定按钮
        progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.cancel();
            }
        });
        //是否通过返回按钮退出对话框
        progressDialog.setCancelable(false);

        CommonProgressDialog mDialog = new CommonProgressDialog(context);
        mDialog.setMessage("正在下载");
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub
                //cancel(true);
            }
        });



//        mDialog.setMax(100);

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
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View layout = layoutInflater.inflate(R.layout.alert_dialog,
                null);
        final AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle("").setView(layout)
                .setPositiveButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton("", null).show();

        TextView alert_title = (TextView) layout.findViewById(R.id.alert_title);
        alert_title.setText("定位系统有更新，确认更新吗？");
        Button confirm = (Button) layout.findViewById(R.id.alert_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                DownloadPluginApk(context, sdCardPath, pluginApkName, versionCode, pluginVersionKey);
            }
        });
        Button cancle = (Button) layout.findViewById(R.id.alert_cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
}
