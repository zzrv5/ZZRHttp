package com.zzrv5.mylibrary;

import java.io.InputStream;

/**
 * the HTTP response object
 * @author ZZR   ,      Email:program.java@qq.com
 * @version 1.0.0
 * @since 2020-10-3
 */
public class ZZRResponse {
    public InputStream inputStream;
    public InputStream errorStream;
    public int code;
    public long contentLength;
    public Exception exception;
}
