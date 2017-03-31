/*
 * Commons-Utils
 * Copyright (c) 2017.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 */

package com.ykun.commons.util.http;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ykun 于 2017-03-28 16:34
 */
public class HttpClientTest {

    public static void main(String[] args) throws Exception {

        System.out.println(HttpClientUtils.get("http://www.weather.com.cn/data/sk/101010100.html"));

        Map<String, String> params = new HashMap<String, String>();
        params.put("gameID", "1000001");
        params.put("accountID", "helloworld2");
        System.out.println(HttpClientUtils.post("http://115.159.156.171/home/main", params));

        System.out.println(HttpClientUtils.execute(Request.Post("http://115.159.156.171/home/main").bodyForm(Form.form().add("gameID", "1000001").add("accountID", "helloworld2").build())));

    }
}
