/*
 * Commons-Utils
 * Copyright (c) 2017.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 */

package com.ykun.commons.utils.security;

import org.apache.commons.codec.binary.Base64;

/**
 * Base64加密解密，依赖commons-codec
 *
 * @author Ykun 于 2017-03-31 9:58
 */
public class Base64Utils {

    /**
     * encode
     */
    public static String encode(byte[] data) {
        return Base64.encodeBase64String(data);
    }

    /**
     * encode
     */
    public static String encode(String data) {
        return encode(org.apache.commons.codec.binary.StringUtils.getBytesUtf8(data));
    }

    /**
     * decode
     */
    public static String decode(String data) {
        return org.apache.commons.codec.binary.StringUtils.newStringUtf8(Base64.decodeBase64(data));
    }
}
