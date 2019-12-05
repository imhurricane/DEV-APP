package com.dev.eda.app.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.exception.HttpException;
import com.lzy.okgo.exception.StorageException;
import com.lzy.okgo.model.Response;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.ResponseBody;

public abstract class JsonCallback<T> extends AbsCallback<T> {

    private Type type;

    public Class<T> clazz;

    public JsonCallback() {
    }

    public JsonCallback(Type type) {
        this.type = type;
    }

    private JsonCallback(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {
        ResponseBody body = response.body();
        if (body == null) return null;
        T data = null;
        try{
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(body.charStream());
            if (type != null) {
                data = gson.fromJson(reader, type);
            } else if (clazz != null) {
                data = gson.fromJson(reader, clazz);
            } else {
                Type genType = getClass().getGenericSuperclass();
                Type type = ((ParameterizedType) genType).getActualTypeArguments()[0];
                data = gson.fromJson(reader, type);
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("GSON","数据解析错误");
        }
        return data;
    }

    @Override
    public void onError(Response<T> response) {

        Throwable exception = response.getException();
        if (exception != null) exception.printStackTrace();

        if (exception instanceof UnknownHostException || exception instanceof ConnectException) {
            Log.e("http", "网络连接失败");
        } else if (exception instanceof SocketTimeoutException) {
            Log.e("http", "网络请求超时");
        } else if (exception instanceof HttpException) {
            Log.e("http", "服务端错误，404 or 500");
        } else if (exception instanceof StorageException) {
            Log.e("http", "sd卡不存在或者没有权限");
        } else if (exception instanceof IllegalStateException) {
            //自定义抛出的异常
            String message = exception.getMessage();
            Log.e("http", message);
        }
    }
}
