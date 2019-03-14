package com.example.weven.bankapp.Activity;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/5/26.
 */
public class BaseApplication extends Application {
    private static Context context;
    private static String token;
    private static String userId;
    private static String password;
    private static String payPassword;
    private static String cardId;

    @Override
    public void onCreate() {
        super.onCreate();
//        Thread.setDefaultUncaughtExceptionHandler(CrashHandler.getInstance());  //捕获全局异常
        initData();
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        BaseApplication.userId = userId;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        BaseApplication.token = token;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        BaseApplication.password = password;
    }

    public static String getPayPassword() {
        return payPassword;
    }

    public static void setPayPassword(String payPassword) {
        BaseApplication.payPassword = payPassword;
    }

    private void initData() {
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    public static String getCardId() {
        return cardId;
    }

    public static void setCardId(String cardId) {
        BaseApplication.cardId = cardId;
    }
}
