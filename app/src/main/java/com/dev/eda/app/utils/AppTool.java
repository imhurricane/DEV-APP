package com.dev.eda.app.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.dev.eda.frame.login.model.LoginUser;

import org.litepal.LitePal;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class AppTool {


    public static int LoginLost = 1;

    public static String encodeByMd5(String string) {
        try {
            // 确定计算方法
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            Base64.Encoder base64Encoder = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                base64Encoder = Base64.getEncoder();
                return base64Encoder.encodeToString(md5.digest(string.getBytes(StandardCharsets.UTF_8)));
            } else {
                return string;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("encodeByMd5", "MD5加密错误");
        }
        return string;
    }

    public static String makeMD5(String password) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(password.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            String pwd = new BigInteger(1, md.digest()).toString(16);
            //			System.err.println(pwd);
            return pwd;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean checkLogin() {

        boolean flag = false;
        List<LoginUser> users = LitePal.where("yhxtm = currentUserXtm").find(LoginUser.class);
        if (users.size() == 1) {
            LoginUser loginUser = users.get(0);
            String lastLoginDate = loginUser.getLastLoginDate();
            Date date1 = DateTool.StrToDate(lastLoginDate);
            Date date = DateTool.subDate(LoginLost);
            if (date1.after(date)) {
                flag = true;
            }
        }
        return flag;
    }

    public static LoginUser getLoginUserInfo(){
        List<LoginUser> users = LitePal.where("yhxtm = currentUserXtm").find(LoginUser.class);
        if (users.size() == 1){
            LoginUser user = users.get(0);
            return user;
        }
        return null;
    }




    public static String getAppVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    public static int getAppVersionCode(Context context) {
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

}
