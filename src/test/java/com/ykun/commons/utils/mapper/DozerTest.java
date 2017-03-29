/*
 * Commons-Utils
 * Copyright (c) 2017.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 */

package com.ykun.commons.utils.mapper;

import com.ykun.commons.utils.collection.ListUtil;

import java.util.List;

/**
 * @author Ykun 于 2017-03-29 14:29
 */
public class DozerTest {

    public static void main(String[] args) {
        Source source = new Source();
        source.setUsername("hello");
        source.setId(5);

        Destination destination = DozerMapper.map(source, Destination.class);
        System.out.println(destination.toString());

        List<Source> list = ListUtil.newArrayList(source, source, source);
        System.out.println(DozerMapper.mapList(list, Destination.class));

        Destination dest = new Destination();
        DozerMapper.copy(source, dest);
        System.out.println(dest.toString());
    }
}
