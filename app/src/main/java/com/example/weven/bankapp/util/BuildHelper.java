package com.example.weven.bankapp.util;

import android.os.Build;

public class BuildHelper {

    // 型号
    public static String getMode()
    {
        return Build.MODEL;
    }
    // Android 系统版本
    public static String getAndroidVersion()
    {
        return Build.VERSION.RELEASE;
    }

}
