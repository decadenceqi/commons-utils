/*
 * Commons-Utils
 * Copyright (c) 2017.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 */

package com.ykun.commons.utils.collection;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Map工具集合
 * 依赖Google Guava
 *
 * @author Ykun 于 2017-02-15 14:53
 */
public class MapUtil {

    /**
     * 判断是否为空
     *
     * @param map the map
     * @return boolean boolean
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断是否为空
     *
     * @param map the map
     * @return the boolean
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return map != null && !map.isEmpty();
    }

    /**
     * @return the hash map
     * @see com.google.common.collect.Maps#newHashMap
     */
    public static <K, V> HashMap<K, V> newHashMap() {
        return Maps.newHashMap();
    }

    /**
     * @param key   the key
     * @param value the value
     * @return the hash map
     * @see com.google.common.collect.Maps#newHashMap
     */
    public static <K, V> HashMap<K, V> newHashMap(K key, V value) {
        HashMap<K, V> map = newHashMap();
        map.put(key, value);
        return map;
    }

    /**
     * 空Map，切不可修改
     *
     * @return the map
     */
    public static final <K, V> Map<K, V> emptyMap() {
        return (Map<K, V>) Collections.EMPTY_MAP;
    }
}
