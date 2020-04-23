package com.dev.eda.app.http.callback;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.dev.eda.app.http.loding.LoadingDialog;
import com.dev.eda.app.http.model.Result;
import com.dev.eda.app.utils.LogUtils;
import com.dev.eda.app.utils.Logger;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.exception.HttpException;
import com.lzy.okgo.exception.StorageException;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.ResponseBody;

public abstract class DialogCallBackResult<T> extends AbsCallback<Result<T>> {

    private String tag_log = "DialogCallBack";
    private Result<T> result;
    LoadingDialog dialog;
    private Context context;
    private boolean isShowDialog;//是否显示dialog
    private boolean isCancel;//是否能返回键取消，默认能
    private Object tag;//用于取消网络连接
    private Type type;

    /**
     * @param context
     * @param isShowDialog 是否显示dialog
     * @param tag          用于取消网络连接
     * @param isCancel     是否能返回键取消，默认能
     */
    public DialogCallBackResult(Context context, boolean isShowDialog, Object tag, boolean isCancel) {
        this.context = context;
        this.isShowDialog = isShowDialog;
        this.tag = tag;
        this.isCancel = isCancel;
        result = new Result<>();
    }

    public DialogCallBackResult(Context context, boolean isShowDialog, Object tag) {
        this.context = context;
        this.isShowDialog = isShowDialog;
        this.tag = tag;
        isCancel = true;
        result = new Result<>();
    }

    public DialogCallBackResult(Context context, boolean isShowDialog,Type type) {
        this.context = context;
        this.isShowDialog = isShowDialog;
        isCancel = true;
        result = new Result<>();
    }

    @Override
    public void onStart(Request<Result<T>, ? extends Request> request) {
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
    public void onFinish() {
        super.onFinish();
        LogUtils.i(tag_log + "onFinish:");
        if (dialog != null && dialog.isShowing()) {
            LoadingDialog.dismissprogress();
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void onError(Response<Result<T>> response) {
        LogUtils.i(tag_log + "onError:" + response.body());
        Throwable exception = response.getException();
//        if (exception != null) exception.printStackTrace();
        result.setCode(response.code());
        if (exception instanceof UnknownHostException || exception instanceof ConnectException) {
            Logger.e("http", "网络连接失败");
            result.setMsg("网络连接失败");
        } else if (exception instanceof SocketTimeoutException) {
            Logger.e("http", "网络请求超时");
            result.setMsg("网络请求超时");
        } else if (exception instanceof HttpException) {
            Logger.e("http", "服务端错误，404 or 500");
            result.setMsg("服务端错误，404 or 500");
        } else if (exception instanceof StorageException) {
            Logger.e("http", "sd卡不存在或者没有权限");
            result.setMsg("sd卡不存在或者没有权限");
        } else if (exception instanceof IllegalStateException) {
            //自定义抛出的异常
            String message = exception.getMessage();
            Logger.e("http", message);
            result.setMsg(message);
        }
        response.setBody(result);
//        super.onError(response);
    }

    @Override
    public Result<T> convertResponse(okhttp3.Response response) throws Throwable {
        ResponseBody body = response.body();
        if (body == null) return null;
        T data = null;
        result = new Result<>();
        try{
            Gson gson = new Gson();
            if (type != null) {
                data = gson.fromJson(body.string(), type);
            }else {
                Type genType = getClass().getGenericSuperclass();
                Type type = ((ParameterizedType) genType).getActualTypeArguments()[0];
                data = gson.fromJson(body.string(), type);
            }
            result.setData(data);
            result.setCode(200);
            result.setMsg("请求成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(response.code());
            result.setMsg("数据解析错误"+data);
            Log.e("GSON","数据解析错误"+data);
        }
        return result;
    }
}