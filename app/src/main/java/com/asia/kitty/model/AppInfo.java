package com.asia.kitty.model;
import android.graphics.Bitmap;

public class AppInfo {
    private Bitmap appIcon;
    private String appName;
    private String appVer;
    private String appSize;

    public AppInfo() {

    }

    public AppInfo(Bitmap appIcon, String appName, String appVer, String appSize) {
        this.appIcon = appIcon;
        this.appName = appName;
        this.appVer = appVer;
        this.appSize = appSize;
    }

    public void setAppIcon(Bitmap appIcon) {
        this.appIcon = appIcon;
    }

    public Bitmap getAppIcon() {
        return appIcon;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppVer(String appVer) {
        this.appVer = appVer;
    }

    public String getAppVer() {
        return appVer;
    }

    public void setAppSize(String appSize) {
        this.appSize = appSize;
    }

    public String getAppSize() {
        return appSize;
    }
}
