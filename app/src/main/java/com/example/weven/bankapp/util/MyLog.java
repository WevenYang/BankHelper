package com.example.weven.bankapp.util;

import android.util.Log;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class MyLog {
    public MyLog() {
    }

    private static int LOG_LEVEL = 6;

    /*
    * error级别日志
    * @param tag 日志的tag
    * @param msg 日志的输出信息
    * */
    public static void e(String tag, String msg){
        if(LOG_LEVEL <= 1)
            Log.e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable tr){
        if(LOG_LEVEL <= 1)
            Log.e(tag, msg, tr);
    }

    /*
    * warn级别日志
    * @param tag 日志的tag
    * @param msg 日志的输出信息
    * */
    public static void w(String tag, String msg){
        if(LOG_LEVEL <= 2)
            Log.e(tag, msg);
    }

    public static void w(String tag, String msg, Throwable tr){
        if(LOG_LEVEL <= 2)
            Log.e(tag, msg, tr);
    }

    /*
    * info级别日志
    * @param tag 日志的tag
    * @param msg 日志的输出信息
    * */
    public static void i(String tag, String msg){
        if(LOG_LEVEL <= 3)
            Log.e(tag, msg);
    }

    public static void i(String tag, String msg, Throwable tr){
        if(LOG_LEVEL <= 3)
            Log.e(tag, msg, tr);
    }

    /*
    * debug级别日志
    * @param tag 日志的tag
    * @param msg 日志的输出信息
    * */
    public static void d(String tag, String msg){
        if(LOG_LEVEL <= 4)
            Log.e(tag, msg);
    }

    public static void d(String tag, String msg, Throwable tr){
        if(LOG_LEVEL <= 4)
            Log.e(tag, msg, tr);
    }

    /*
    * verbose级别日志
    * @param tag 日志的tag
    * @param msg 日志的输出信息
    * */
    public static void v(String tag, String msg){
        if(LOG_LEVEL <= 5)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg, Throwable tr){
        if(LOG_LEVEL <= 5)
            Log.e(tag, msg, tr);
    }

    /**
     * 更改日志显示等级
     */
    public static void changeLogLevel(LogLevel level){
        switch (level){
            case VERBOSE:
                LOG_LEVEL = 5;
                break;
            case DEBUG:
                LOG_LEVEL = 4;
                break;
            case INFO:
                LOG_LEVEL = 3;
                break;
            case WARN:
                LOG_LEVEL = 2;
                break;
            case ERROR:
                LOG_LEVEL = 1;
                break;
            case NOLOG:
                LOG_LEVEL = 0;
            default:
                break;
        }
    }


    public enum LogLevel{
        VERBOSE, DEBUG, INFO, WARN, ERROR, NOLOG
    }
}
