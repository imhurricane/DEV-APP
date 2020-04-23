package com.dev.eda.frame.login.data;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.dev.eda.app.http.callback.DialogCallBackResult;
import com.dev.eda.app.http.callback.JsonCallbackResult;
import com.dev.eda.app.http.model.Result;
import com.dev.eda.app.utils.DeviceUtils;
import com.dev.eda.app.utils.HttpRequestUrl;
import com.dev.eda.frame.login.model.LoginUser;
import com.kongzue.dialog.v3.MessageDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

public class LoginDataSever {

    public static void login(Context context, Handler handler, String userName, String password) {
        OkGo.<Result<LoginUser>>post(HttpRequestUrl.LoginAppUrl1)
                .tag(context) //请求的TAG，用于取消对应的请求
                .retryCount(0) //超时重连次数
                .params("plat", "android")
                .params("devicetoken", DeviceUtils.getDeviceId(context))
                .params("isjpush", "1")
                .params("registrationID", "")
                .params("appid", "")
                .params("username", userName) //添加请求参数，支持多次添加
                .params("password", password) //添加请求参数，支持多次添加
                .execute(new DialogCallBackResult<LoginUser>(context,true,LoginUser.class) {

                    @Override
                    public void onSuccess(Response<Result<LoginUser>> response) {
                        LoginUser data = response.body().getData();
                        data.setUserName(userName);
                        data.setPassWord(password);
                        Message message = handler.obtainMessage();
                        message.what = 11;
                        message.obj = data;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onError(Response<Result<LoginUser>> response) {
                        super.onError(response);
                        String msg = response.body().getMsg();
                        MessageDialog.show((AppCompatActivity) context, "提示", msg);
                    }
                });
    }


}
