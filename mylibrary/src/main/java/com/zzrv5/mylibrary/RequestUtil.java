package com.zzrv5.mylibrary;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Http Request Util
 * @author ZZR ,program.java@qq.com
 * @version 1.0.0
 * @since 2020-10-3
 */
class RequestUtil {
    private Thread mThread;
    /**
     * General get or POST
     */
    RequestUtil(String method, String url, Map<String, String> paramsMap, Map<String, String> headerMap, ZZRCallBack callBack) {
        switch (method){
            case "GET":
            urlHttpGet(url,paramsMap,headerMap,callBack);
            break;
            case "POST":
            urlHttpPost(url,paramsMap,null,headerMap,callBack);
            break;
        }
    }

    /**
     * Post requests for JSON
     */
    RequestUtil(String url, String jsonStr, Map<String, String> headerMap, ZZRCallBack callBack) {
        urlHttpPost(url,null,jsonStr,headerMap,callBack);
    }



    private void urlHttpGet(final String url, final Map<String, String> paramsMap, final Map<String, String> headerMap, final ZZRCallBack callBack) {
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ZZRResponse response = new ZZRRequest().getData(getUrl(url,paramsMap),headerMap);
                if(response.code == HttpURLConnection.HTTP_OK){
                    callBack.onSeccess(response);
                }else {
                    callBack.onError(response);
                }
            }

        });
    }


    private void urlHttpPost(final String url, final Map<String, String> paramsMap, final String jsonStr, final Map<String, String> headerMap, final ZZRCallBack callBack) {
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ZZRResponse response = new ZZRRequest().postData(url, getPostBody(paramsMap,jsonStr),getPostBodyType(paramsMap,jsonStr),headerMap);
                if(response.code == HttpURLConnection.HTTP_OK){
                    callBack.onSeccess(response);
                }else {
                    callBack.onError(response);
                }

            }

        });

    }






    private String getUrl(String path,Map<String, String> paramsMap) {
        if(paramsMap != null){
            path = path+"?";
            for (String key: paramsMap.keySet()){
                path = path + key+"="+paramsMap.get(key)+"&";
            }
            path = path.substring(0,path.length()-1);
        }
        return path;
    }


    private  String getPostBody(Map<String, String> params,String jsonStr) {//throws UnsupportedEncodingException {
        if(params != null){
            return getPostBodyFormParameMap(params);
        }else if(!TextUtils.isEmpty(jsonStr)){
            return jsonStr;
        }
        return null;
    }



    private  String getPostBodyFormParameMap(Map<String, String> params) {//throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first)
                    first = false;
                else
                    result.append("&");
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            return result.toString();
        } catch (UnsupportedEncodingException e) {
            return null;
        }

    }


    private String getPostBodyType(Map<String, String> paramsMap, String jsonStr) {
        if(paramsMap != null){
            //return "text/plain";    unknown error

            return null;
        }else if(!TextUtils.isEmpty(jsonStr)){
            return "application/json;charset=utf-8";
        }
        return null;
    }
    /**
     * Execute thread
     */
    void execute(){
        if(mThread != null){
            mThread.start();
        }
    }
}
