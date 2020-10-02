package com.zzrv5.mylibrary;


import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Encapsulates a thread's callback operation
 * @author ZZR ,program.java@qq.com
 * @version 1.0.0
 * @since 2020-10-3
 */
public abstract class ZZRCallBack<T> {
    static EventHandler mMainHandler = new EventHandler(EventRunner.getMainEventRunner());

    public  void onProgress(float progress, long total ){}

    void onError(final ZZRResponse response){
        
        final String errorMessage;
        if(response.inputStream != null){
            errorMessage = getRetString(response.inputStream);
        }else if(response.errorStream != null) {
            errorMessage = getRetString(response.errorStream);
        }else if(response.exception != null) {
            errorMessage = response.exception.getMessage();
        }else {
            errorMessage = "";
        }
        mMainHandler.postTask(new Runnable() {
            @Override
            public void run() {
                onFailure(response.code,errorMessage);
            }
        });
    }
    void onSeccess(ZZRResponse response){
        final T obj = onParseResponse(response);
        mMainHandler.postTask(new Runnable() {
            @Override
            public void run() {
                onResponse(obj);
            }
        });
    }


    public abstract T onParseResponse(ZZRResponse response);


    public abstract void onFailure(int code,String errorMessage);


    public abstract void onResponse(T response);



    public static abstract class CallBackDefault extends ZZRCallBack<ZZRResponse> {
        @Override
        public ZZRResponse onParseResponse(ZZRResponse response) {
            return response;
        }
    }


    public static abstract class CallBackString extends ZZRCallBack<String> {
        @Override
        public String onParseResponse(ZZRResponse response) {
            try {
                return getRetString(response.inputStream);
            } catch (Exception e) {
                throw new RuntimeException("failure");
            }
        }
    }

    private static String getRetString(InputStream is) {
        String buf;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            buf = sb.toString();
            return buf;

        } catch (Exception e) {
            return null;
        }
    }

}
