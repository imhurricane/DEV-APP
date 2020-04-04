package com.dev.eda.app.wifidis.module;


import java.io.Serializable;

public class WifiDisEntry  implements Serializable {

    private static final long serialVersionUID = 2072893447591544302L;

    public int code;

    public String versionCode;

    public boolean newVersion;

    public boolean getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(boolean newVersion) {
        this.newVersion = newVersion;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

}
