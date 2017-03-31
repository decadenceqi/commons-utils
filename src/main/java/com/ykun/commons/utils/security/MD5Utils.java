/*
 * Commons-Utils
 * Copyright (c) 2017.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 */

package com.ykun.commons.utils.security;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;

import static com.ykun.commons.utils.constant.Constant.CHARSET_UTF8;

/**
 * MD5加密
 *
 * @author Ykun 于 2017-03-29 16:16
 */
public class MD5Utils {

    private final static String DIGEST = "MD5";

    /**
     * MD5加密，32位小写
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        StringBuilder result = new StringBuilder();
        try {
            MessageDigest md5 = MessageDigest.getInstance(DIGEST);
            md5.update(str.getBytes(CHARSET_UTF8));
            byte[] b = md5.digest();
            for (int i = 0; i < b.length; ++i) {
                int x = b[i] & 0xFF;
                int h = x >>> 4;
                int l = x & 0x0F;
                result.append((char) (h + ((h < 10) ? '0' : 'a' - 10)));
                result.append((char) (l + ((l < 10) ? '0' : 'a' - 10)));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result.toString();
    }

    /**
     * 校验MD5值
     *
     * @param orign  未加密的字符串
     * @param secret 比对字符串
     * @return
     */
    public static boolean check(String orign, String secret) {
        return StringUtils.equals(md5(orign), secret);
    }

}
