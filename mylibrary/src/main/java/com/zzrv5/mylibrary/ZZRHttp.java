package com.zzrv5.mylibrary;

import java.util.Map;

/**
 * Encapsulates a thread's callback operation
 * @author ZZR ,program.java@qq.com
 * @version 1.0.0
 * @since 2020-10-3
 */
public class ZZRHttp {
    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";

    public static final String FILE_TYPE_FILE = "file/*";
    public static final String FILE_TYPE_IMAGE = "image/*";
    public static final String FILE_TYPE_AUDIO = "audio/*";
    public static final String FILE_TYPE_VIDEO = "video/*";


    public static void get(String url, ZZRCallBack callBack) {
        get(url, null, null, callBack);
    }


    public static void get(String url, Map<String, String> paramsMap, ZZRCallBack callBack) {
        get(url, paramsMap, null, callBack);
    }


    public static void get(String url, Map<String, String> paramsMap, Map<String, String> headerMap, ZZRCallBack callBack) {
        new RequestUtil(METHOD_GET, url, paramsMap, headerMap, callBack).execute();
    }


    public static void post(String url, ZZRCallBack callBack) {
        post(url, null, callBack);
    }


    public static void post(String url, Map<String, String> paramsMap, ZZRCallBack callBack) {
        post(url, paramsMap, null, callBack);
    }


    public static void post(String url, Map<String, String> paramsMap, Map<String, String> headerMap, ZZRCallBack callBack) {
        new RequestUtil(METHOD_POST,url,paramsMap,headerMap,callBack).execute();
    }

    public static void postJson(String url, String jsonStr, ZZRCallBack callBack) {
        postJson(url, jsonStr, null, callBack);
    }


    public static void postJson(String url, String jsonStr, Map<String, String> headerMap, ZZRCallBack callBack) {
        new RequestUtil(url,jsonStr,headerMap,callBack).execute();
    }



}
