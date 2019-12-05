package com.dev.eda.frame.login.model;

import org.litepal.LitePal;

public class LoginData {

    public static LoginUser getLoginUser(){
        return null;
    }

    public static boolean saveLoginUser(LoginUser user){
        return user.save();
    }
}
