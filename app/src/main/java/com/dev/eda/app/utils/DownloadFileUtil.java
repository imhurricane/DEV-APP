package com.dev.eda.app.utils;

import android.content.Context;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.base.Request;

import java.io.File;

public class DownloadFileUtil {


    private static String mBasePath; //本地文件存储的完整路径  /storage/emulated/0/book/a.txt

    /**
     * @param context 上下文
     * @param fileUrl 下载完整url
     * @param destFileDir  SD路径
     * @param destFileName  文件名
     */
    public static void downloadFile(Context context, String fileUrl, String destFileDir, String destFileName) {
        OkGo.<File>get(fileUrl).tag(context).execute(new FileCallback(destFileDir, destFileName) { //文件下载时指定下载的路径以及下载的文件的名称
            @Override
            public void onStart(Request<File, ? extends Request> request) {
                super.onStart(request);
                Log.d("TAG", "开始下载文件");
            }

            @Override
            public void onSuccess(com.lzy.okgo.model.Response<File> response) {
                Log.d("TAG", "下载文件成功:" + response.body().length());
                mBasePath = response.body().getAbsolutePath();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                Log.d("TAG", "下载文件完成");
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<File> response) {
                super.onError(response);
                Log.e("TAG", "下载文件出错:" + response.message());

            }

            @Override
            public void downloadProgress(Progress progress) {
                super.downloadProgress(progress);
                float dLProgress = progress.fraction;
                Log.d("TAG", "文件下载的进度:" + dLProgress);
            }
        });
    }
}
