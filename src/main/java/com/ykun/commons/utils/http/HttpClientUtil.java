/*
 * Commons-Utils
 * Copyright (c) 2017.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 */

package com.ykun.commons.utils.http;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 简单封装HttpClient 4.5.x
 *
 * @author Ykun 于 2017-03-28 10:20
 */
public class HttpClientUtil {

    /**
     * 连接超时，单位：毫秒
     */
    private final static int CONNECT_TIME_OUT = 3000;

    /**
     * 读超时，单位：毫秒
     */
    private final static int SOCKET_TIME_OUT = 5000;

    /**
     * 重试次数
     */
    private final static int RETRY_TIMES = 3;

    /**
     * 连接池大小
     */
    private final static int MAX_TOTAL = 500;

    /**
     * 每个路由最大连接数
     */
    private final static int MAX_PER_ROUTE = 100;

    /**
     * 可用空闲连接过期时间，重用空闲连接时会先检查是否空闲时间超过这个时间，如果超过，释放socket重新建立
     */
    private final static int VALIDATE_AFTER_INACTIVITY = 1000;

    /**
     * 默认字符集
     */
    private final static String CHARSET = "UTF-8";

    private final static String EMPTY_STRING = "";
    private final static String SYMBOL_MARK = "?";
    private final static String SYMBOL_CONNECTOR = "&";
    private final static String SYMBOL_EQUAL = "=";

    private static Executor executor;

    /**
     * 初始化HttpClient和connectionManager配置
     */
    static {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);
        connectionManager.setMaxTotal(MAX_TOTAL);
        connectionManager.setValidateAfterInactivity(VALIDATE_AFTER_INACTIVITY);

        RequestConfig.Builder builder = RequestConfig.custom();
        builder.setConnectTimeout(CONNECT_TIME_OUT);
        builder.setSocketTimeout(SOCKET_TIME_OUT);
        HttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(builder.build()).setConnectionManager(connectionManager).setRetryHandler(new RetryHandler()).build();
        executor = Executor.newInstance(httpClient);
    }

    public static String post(String url, Header[] headers, Map<String, String> params) {
        return execute(Request.Post(url).bodyForm(map2List(params), Consts.UTF_8).setHeaders(headers));
    }

    public static String post(String url, Map<String, String> params) {
        return execute(Request.Post(url).bodyForm(map2List(params), Consts.UTF_8));
    }

    public static String post(String url) {
        return execute(Request.Post(url));
    }

    public static String get(String url, Header[] headers, Map<String, String> params) {
        return execute(Request.Get(parse(url, params)).setHeaders(headers));
    }

    public static String get(String url, Map<String, String> params) {
        return execute(Request.Get(parse(url, params)));
    }

    public static String get(String url) {
        return execute(Request.Get(url));
    }

    /**
     * Url追加参数
     */
    private static String parse(String url, Map<String, String> params) {
        if (params == null && params.size() == 0) {
            return url;
        }
        StringBuilder builder = new StringBuilder(url);
        builder.append(SYMBOL_MARK);
        Iterator<String> it = params.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            try {
                builder.append(key).append(SYMBOL_EQUAL).append(URLEncoder.encode(params.get(key) == null ? EMPTY_STRING : params.get(key), CHARSET)).append(SYMBOL_CONNECTOR);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        String parse = builder.toString();
        return parse.substring(0, parse.length() - 1);
    }

    /**
     * 参数转换
     */
    private static List<NameValuePair> map2List(Map<String, String> params) {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        if (params != null && params.size() > 0) {
            Iterator<String> it = params.keySet().iterator();
            while (it.hasNext()) {
                String name = it.next();
                list.add(new BasicNameValuePair(name, params.get(name) == null ? EMPTY_STRING : params.get(name)));
            }
        }
        return list;
    }

    /**
     * 执行request
     * @param request Request
     * @return String
     */
    public static String execute(Request request) {
        try {
            return executor.execute(request).returnContent().asString(Consts.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 重试
     */
    private static class RetryHandler implements HttpRequestRetryHandler {
        public boolean retryRequest(IOException exception, int executionCount, HttpContext httpContext) {
            // 无论任何异常都重试3次
            if (executionCount > RETRY_TIMES) {
                return false;
            }
            return true;
        }
    }

}
