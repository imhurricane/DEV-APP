package com.dev.eda.app.http.callback;

import android.content.Context;
import android.content.DialogInterface;

import com.dev.eda.app.http.loding.LoadingDialog;
import com.dev.eda.app.utils.LogUtils;
import com.dev.eda.app.utils.Logger;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.exception.HttpException;
import com.lzy.okgo.exception.StorageException;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class DialogCallBack extends StringCallback {

    private String tag_log = "DialogCallBack";

    LoadingDialog dialog;
    private Context context;
    private boolean isShowDialog;//是否显示dialog
    private boolean isCancel;//是否能返回键取消，默认能
    private Object tag;//用于取消网络连接

    /**
     * @param context
     * @param isShowDialog 是否显示dialog
     * @param tag          用于取消网络连接
     * @param isCancel     是否能返回键取消，默认能
     */
    public DialogCallBack(Context context, boolean isShowDialog, Object tag, boolean isCancel) {
        this.context = context;
        this.isShowDialog = isShowDialog;
        this.tag = tag;
        this.isCancel = isCancel;
    }

    public DialogCallBack(Context context, boolean isShowDialog, Object tag) {
        this.context = context;
        this.isShowDialog = isShowDialog;
        this.tag = tag;
        isCancel = true;
    }

    public DialogCallBack(Context context, boolean isShowDialog) {
        this.context = context;
        this.isShowDialog = isShowDialog;
        isCancel = true;
    }

    @Override
    public void onStart(Request<String, ? extends Request> request) {
        super.onStart(request);
        if (isShowDialog) {

            dialog = LoadingDialog.showprogress(context, isCancel);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (tag != null) {
                        LogUtils.i(tag_log + "取消网络请求");
                        OkGo.getInstance().cancelTag(tag);
                    }
                }
            });
        }
    }

    @Override
    public void onSuccess(Response<String> response) {
        LogUtils.i(tag_log + "onSuccess:");
    }

    @Override
    public void onFinish() {
        super.onFinish();
        LogUtils.i(tag_log + "onFinish:");
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void onError(Response<String> response) {
        LogUtils.i(tag_log + "onError:" + response.body());
        Throwable exception = response.getException();
        if (exception != null) exception.printStackTrace();

        if (exception instanceof UnknownHostException || exception instanceof ConnectException) {
            Logger.e("http", "网络连接失败");
        } else if (exception instanceof SocketTimeoutException) {
            Logger.e("http", "网络请求超时");
        } else if (exception instanceof HttpException) {
            Logger.e("http", "服务端错误，404 or 500");

        } else if (exception instanceof StorageException) {
            Logger.e("http", "sd卡不存在或者没有权限");
        } else if (exception instanceof IllegalStateException) {
            //自定义抛出的异常
            String message = exception.getMessage();
            Logger.e("http", message);
        }
//        super.onError(response);
    }

}