package com.dev.eda.app.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.dev.eda.R;
import com.dev.eda.frame.login.model.LoginUser;

import org.litepal.LitePal;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class AppTool {


    public static int LoginLost = 1;


    public static String encodeByMd5(String string) {
        try{
            // 确定计算方法
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            Base64.Encoder base64Encoder = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                base64Encoder = Base64.getEncoder();
                return base64Encoder.encodeToString(md5.digest(string.getBytes("utf-8")));
            }else{
                return string;
            }
        }catch (Exception e){
            e.printStackTrace();
            Logger.e("encodeByMd5","MD5加密错误");
        }
        return string;
    }

    public static boolean checkLogin(){

        boolean flag = false;
        List<LoginUser> users = LitePal.where("userXtm = currentUserXtm").find(LoginUser.class);
        if (users.size() == 1){
            LoginUser loginUser = users.get(0);
            Logger.e("loginUser.getLastLoginDate()",loginUser.getLastLoginDate());
            String lastLoginDate = loginUser.getLastLoginDate();
            Date date1 = DateTool.StrToDate(lastLoginDate);
            Date date = DateTool.subDate(LoginLost);
            if (date1.after(date)){
                flag = true;
            }
        }
        return flag;
    }

    public static String getAppVersionName(Context context){
        return getPackageInfo(context).versionName;
    }

    public static int getAppVersionCode(Context context){
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pInfo = null;
        try {
            PackageManager pManager = context.getPackageManager();
            pInfo = pManager.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return pInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pInfo;
    }


    public static void showAlert(Context context, String msg) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View layout = layoutInflater.inflate(R.layout.promot_alert,
                null);

        final AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle("").setView(layout)
                .setPositiveButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton("", null).show();

        TextView alert_title = (TextView) layout.findViewById(R.id.promot_alert_title);
        alert_title.setText(msg);
        Button confirm = (Button) layout.findViewById(R.id.promot_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
}
