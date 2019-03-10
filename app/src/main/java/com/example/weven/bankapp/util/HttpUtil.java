package com.example.weven.bankapp.util;


import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.MediaType;

/**
 * Created by marlon on 2016-05-07.
 */
public class HttpUtil {
    private static final long CONNECT_TIMEOUT = 15000;
    private static final long READ_TIMEOUT = 15000;
    private static final long WRITE_TIMEOUT = 15000;
    /**
     * @param url      请求地址
     * @param params   请求参数
     * @param tag      绑定相应的Object,用于取消请求
     * @param callback 相应的CallBack
     */
    public static void getResponse(String url, Map<String, String> params, Object tag, Callback callback) {
        if (params != null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(url).append("?");
            Set<String> set = params.keySet();
            Iterator<String> iterator = set.iterator();
            while (iterator.hasNext()) {
                String s = iterator.next();
                stringBuffer.append(s).append("=").append(params.get(s)).append("&");
            }
            LogUtil.i("url", stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1));
        }else{
            LogUtil.i("url", url);
        }
        OkHttpUtils
                .get()
                .url(url)
                .tag(tag)
                .params(params)
                .build().connTimeOut(CONNECT_TIMEOUT).readTimeOut(READ_TIMEOUT).writeTimeOut(WRITE_TIMEOUT)
                .execute(callback);

    }

    /**
     * @param url      请求地址
     * @param params   请求参数
     * @param tag      绑定相应的Object,用于取消请求
     * @param callback 相应的CallBack
     */
    public static void postResponse(String url, Map<String, String> params, Object tag, Callback callback) {
        LogUtil.i("url", url);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("{").append("\n");
        Set<String> set = params.keySet();
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
            stringBuffer.append("\"").append(s).append("\":\"").append(params.get(s)).append("\",\n");
        }
        stringBuffer.append("}").append("\n");
        LogUtil.i("post", stringBuffer.toString());
        OkHttpUtils
                .post()
                .url(url)
                .tag(tag)
                .params(params)
                .build().connTimeOut(CONNECT_TIMEOUT).readTimeOut(READ_TIMEOUT).writeTimeOut(WRITE_TIMEOUT)
                .execute(callback);

    }

    /**
     * post请求，参数是Json
     *
     * @param url
     * @param postJson
     * @param tag
     * @param callback
     */
    public static void postResponse(String url, String postJson, Object tag, Callback callback) {
        LogUtil.i("url", url);
        LogUtil.i("post", postJson);
        OkHttpUtils
                .postString()
                .tag(tag)
                .url(url)
                .content(postJson)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build().connTimeOut(CONNECT_TIMEOUT).readTimeOut(READ_TIMEOUT).writeTimeOut(WRITE_TIMEOUT)
                .execute(callback);
    }

    //下载文件
    public static void downLoadFile(String url, Object tag, long startPart,Callback callback) {
        OkHttpUtils
                .get()
                .url(url)
                .tag(tag)
                .addHeader("RANGE", "bytes=" + startPart + "-")
                .build().
                connTimeOut(CONNECT_TIMEOUT)
                .readTimeOut(READ_TIMEOUT)
                .writeTimeOut(WRITE_TIMEOUT)
                .execute(callback);
    }

    //上传图片
    public static void postImgFile(String url, String fileName, Map<String, String> params, File file, Object tag, Callback callback) {

        OkHttpUtils.post()
                .addFile("image/png", fileName, file)
                .url(url)
                .tag(tag)
                .params(params)
                .build().connTimeOut(CONNECT_TIMEOUT).readTimeOut(READ_TIMEOUT).writeTimeOut(WRITE_TIMEOUT)
                .execute(callback);
    }

    //取消请求
    public static void cancelHttpRequest(Object tag) {
        OkHttpUtils.getInstance().cancelTag(tag);

    }

    //获取图片
    public static void getImgFromInternet(String url, Object tag, BitmapCallback callback) {

        OkHttpUtils
                .get()
                .url(url).tag(tag)
                .build().connTimeOut(CONNECT_TIMEOUT).readTimeOut(READ_TIMEOUT).writeTimeOut(WRITE_TIMEOUT)
                .execute(callback);


    }
}
