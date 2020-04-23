package com.dev.eda.frame.login.model;


import org.litepal.crud.LitePalSupport;

public class LoginUser extends LitePalSupport {

    private int code;

    private String yhxtm;

    private String userName;

    private String passWord;

    private String lastLoginDate;

    private String currentUserXtm;

    private String sitextm;

    private String issysadmin;

    private String devicextm;

    private String usernamecn;

    private String orgxtm;

    private String deptname;

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

    public String getYhxtm() {
        return yhxtm;
    }

    public void setYhxtm(String yhxtm) {
        this.yhxtm = yhxtm;
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

    public String getSitextm() {
        return sitextm;
    }

    public void setSitextm(String sitextm) {
        this.sitextm = sitextm;
    }

    public String getIssysadmin() {
        return issysadmin;
    }

    public void setIssysadmin(String issysadmin) {
        this.issysadmin = issysadmin;
    }

    public String getDevicextm() {
        return devicextm;
    }

    public void setDevicextm(String devicextm) {
        this.devicextm = devicextm;
    }

    public String getUsernamecn() {
        return usernamecn;
    }

    public void setUsernamecn(String usernamecn) {
        this.usernamecn = usernamecn;
    }

    public String getOrgxtm() {
        return orgxtm;
    }

    public void setOrgxtm(String orgxtm) {
        this.orgxtm = orgxtm;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "yhxtm='" + yhxtm + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", lastLoginDate='" + lastLoginDate + '\'' +
                ", currentUserXtm='" + currentUserXtm + '\'' +
                ", sitextm='" + sitextm + '\'' +
                ", issysadmin='" + issysadmin + '\'' +
                ", devicextm='" + devicextm + '\'' +
                ", usernamecn='" + usernamecn + '\'' +
                ", orgxtm='" + orgxtm + '\'' +
                ", deptname='" + deptname + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
