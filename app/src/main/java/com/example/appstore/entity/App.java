package com.example.appstore.entity;

public class App {

    private String appName, appVersion, appDescription, appDownloadUrl, appIconUrl, appPackageName,  appModel, appFlyVersion;


    public App(String appName, String appVersion, String appDescription, String appDownloadUrl, String appIconUrl, String appPackageName) {
        this.appName = appName;
        this.appVersion = appVersion;
        this.appDescription = appDescription;
        this.appDownloadUrl = appDownloadUrl;
        this.appIconUrl = appIconUrl;
        this.appPackageName = appPackageName;

    }

    public App(String appName, String appVersion, String appDescription, String appDownloadUrl, String appIconUrl, String appPackageName, String appModel, String appFlyVersion) {
        this.appName = appName;
        this.appVersion = appVersion;
        this.appDescription = appDescription;
        this.appDownloadUrl = appDownloadUrl;
        this.appIconUrl = appIconUrl;
        this.appPackageName = appPackageName;
        this.appModel = appModel;
        this.appFlyVersion = appFlyVersion;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppPackageName() {
        return appPackageName;
    }

    public void setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppDescription() {
        return appDescription;
    }

    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    public String getAppDownloadUrl() {
        return appDownloadUrl;
    }

    public void setAppDownloadUrl(String appDownloadUrl) {
        this.appDownloadUrl = appDownloadUrl;
    }

    public String getAppIconUrl() {
        return appIconUrl;
    }

    public void setAppIconUrl(String appIconUrl) {
        this.appIconUrl = appIconUrl;
    }

    public String getAppModel() {
        return appModel;
    }

    public void setAppModel(String appModel) {
        this.appModel = appModel;
    }

    public String getAppFlyVersion() {
        return appFlyVersion;
    }

    public void setAppFlyVersion(String appFlyVersion) {
        this.appFlyVersion = appFlyVersion;
    }

    @Override
    public String toString() {
        return "App{" +
                "appName='" + appName + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", appDescription='" + appDescription + '\'' +
                ", appDownloadUrl='" + appDownloadUrl + '\'' +
                ", appIconUrl='" + appIconUrl + '\'' +
                ", appModel='" + appModel + '\'' +
                ", appFlyVersion='" + appFlyVersion + '\'' +
                '}';
    }
}
