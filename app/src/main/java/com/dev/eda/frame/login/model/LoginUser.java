package com.dev.eda.frame.login.model;


import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class LoginUser extends LitePalSupport {

    private String userXtm;

    private String userName;

    private String passWord;

    private String lastLoginDate;

    private String currentUserXtm;

    public String getCurrentUserXtm() {
        return currentUserXtm;
    }

    public void setCurrentUserXtm(String currentUserXtm) {
        this.currentUserXtm = currentUserXtm;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getUserXtm() {
        return userXtm;
    }

    public void setUserXtm(String userXtm) {
        this.userXtm = userXtm;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "userXtm='" + userXtm + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", lastLoginDate=" + lastLoginDate +
                ", currentUserXtm='" + currentUserXtm + '\'' +
                '}';
    }
}
