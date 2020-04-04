//package com.dev.eda.app.wifidis.http;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.util.Log;
//
//import com.dev.eda.R;
//import com.dev.eda.app.LocalStorage.LocalStorage;
//import com.dev.eda.app.utils.DownloadFileUtil;
//import com.dev.eda.app.utils.HttpRequestUrl;
//import com.dev.eda.app.utils.JsonCallback;
//import com.dev.eda.app.utils.WifiDisPluginUtils;
//import com.dev.eda.app.wifidis.module.WifiDisEntry;
//import com.dev.eda.app.wifidis.utils.WifiDisUtil;
//import com.lzy.okgo.OkGo;
//import com.lzy.okgo.callback.FileCallback;
//import com.lzy.okgo.model.Progress;
//import com.lzy.okgo.model.Response;
//import com.lzy.okgo.request.base.Request;
//
//import java.io.File;
//
//public class WifiDisHttp {
//
//    private static String mVersionCode;
//
//    public static void DownloadWifiDis(Context context, String sdCardPath, String fileName) {
//
//        OkGo.<File>get(HttpRequestUrl.DownloadWifiApkUrl).tag(context).execute(new FileCallback(sdCardPath, fileName) { //文件下载时指定下载的路径以及下载的文件的名称
//            @Override
//            public void onStart(Request<File, ? extends Request> request) {
//                super.onStart(request);
//                Log.d("TAG", "开始下载文件");
//            }
//
//            @Override
//            public void onSuccess(com.lzy.okgo.model.Response<File> response) {
//                Log.d("TAG", "下载文件成功:" + response.body().length());
//                String path = response.body().getAbsolutePath();
//            }
//
//            @Override
//            public void onFinish() {
//                super.onFinish();
//                Log.d("TAG", "下载文件完成");
//                LocalStorage.saveString(context, "wifiVer", mVersionCode);
//                WifiDisUtil.loadPlugin(context);
//            }
//
//            @Override
//            public void onError(com.lzy.okgo.model.Response<File> response) {
//                super.onError(response);
//                Log.e("TAG", "下载文件出错:" + response.message());
//                WifiDisUtil.loadPlugin(context);
//            }
//
//            @Override
//            public void downloadProgress(Progress progress) {
//                super.downloadProgress(progress);
//                float dLProgress = progress.fraction;
//
//            }
//        });
//    }
//
//    public static void checkWifiDisPluginApk(Context context) {
//        String ver = LocalStorage.getString(context, "wifiVer");
//        OkGo.<WifiDisEntry>get(HttpRequestUrl.CheckWifiApkVerUrl)
//                .tag(context) //请求的TAG，用于取消对应的请求
//                .params("requesttype", "checkWifiDis")
//                .params("versioncode", ver)
//                .execute(new JsonCallback<WifiDisEntry>(WifiDisEntry.class) {
//                    @Override
//                    public void onSuccess(Response<WifiDisEntry> response) {
//                        WifiDisEntry wifiDisEntry = response.body();
//                        mVersionCode = wifiDisEntry.getVersionCode();
//                        if (response.code() == 200) {
//                            if (wifiDisEntry.getNewVersion()) {
//                                DownloadWifiDis(context, WifiDisPluginUtils.PLUGIN_SD_PATH, WifiDisPluginUtils.PLUGIN_APK_NAME);
//                            }else{
//                                WifiDisUtil.loadPlugin(context);
//                            }
//                        }else{
//                            WifiDisUtil.loadPlugin(context);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Response<WifiDisEntry> response) {
//                        super.onError(response);
//                        WifiDisUtil.loadPlugin(context);
//                    }
//                });
//    }
//
//
//}
