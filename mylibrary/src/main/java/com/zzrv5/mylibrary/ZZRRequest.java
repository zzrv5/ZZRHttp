package com.zzrv5.mylibrary;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Thread's callback operation
 * @author ZZR ,program.java@qq.com
 * @version 1.0.0
 * @since 2020-10-3
 */

 class ZZRRequest {


    /**
     * get
     */
    ZZRResponse getData(String requestURL, Map<String, String> headerMap){
        HttpURLConnection conn = null;
        try {
            conn= getHttpURLConnection(requestURL,"GET");
            conn.setDoInput(true);
            if(headerMap != null){
                setHeader(conn,headerMap);
            }
            conn.connect();
            return getRealResponse(conn);
        } catch (Exception e) {
            return getExceptonResponse(conn, e);
        }
    }

    /**
     * post
     */
    ZZRResponse postData(String requestURL, String body, String bodyType, Map<String, String> headerMap) {
        HttpURLConnection conn = null;
        try {
            conn = getHttpURLConnection(requestURL,"POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            if(!TextUtils.isEmpty(bodyType)) {
                conn.setRequestProperty("Content-Type", bodyType);
            }
            if(headerMap != null){
                setHeader(conn,headerMap);
            }
            conn.connect();
            if(!TextUtils.isEmpty(body)) {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
                writer.write(body);
                writer.close();
            }
            return getRealResponse(conn);
        } catch (Exception e) {
            return getExceptonResponse(conn, e);
        }
    }


    private HttpURLConnection getHttpURLConnection(String requestURL,String requestMethod) throws IOException {
        URL url = new URL(requestURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(10*1000);
        conn.setReadTimeout(15*1000);
        conn.setRequestMethod(requestMethod);
        return conn;
    }


    private void setHeader(HttpURLConnection conn, Map<String, String> headerMap) {
        if(headerMap != null){
            for (String key: headerMap.keySet()){
                conn.setRequestProperty(key, headerMap.get(key));
            }
        }
    }


    private ZZRResponse getRealResponse(HttpURLConnection conn) throws IOException {
        ZZRResponse response = new ZZRResponse();
        response.code = conn.getResponseCode();
        response.contentLength = conn.getContentLength();
        response.inputStream = conn.getInputStream();
        response.errorStream = conn.getErrorStream();
        return response;
    }


    private ZZRResponse getExceptonResponse(HttpURLConnection conn, Exception e) {
        if(conn != null){
            conn.disconnect();
        }
        e.printStackTrace();
        ZZRResponse response = new ZZRResponse();
        response.exception = e;
        return response;
    }
}
