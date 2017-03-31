/*
 * Commons-Utils
 * Copyright (c) 2017.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 */

package com.ykun.commons.util.mapper;

import org.dozer.DozerBeanMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 简单封装Dozer
 *
 * @author Ykun 于 2017-03-29 14:10
 */
public class DozerMapper {

    /**
     * 单例
     */
    private static DozerBeanMapper mapper = new DozerBeanMapper();

    /**
     * BeanA->BeanB
     */
    public static <S, D> D map(S source, Class<D> destClass) {
        return mapper.map(source, destClass);
    }

    /**
     * copy BeanA->BeanB
     */
    public static <S, D> void copy(S source, D dest) {
        mapper.map(source, dest);
    }

    /**
     * List<BeanA>->List<BeanB>
     */
    public static <S, D> List<D> mapList(Collection<S> sourceList, Class<D> destClass) {
        List<D> list = new ArrayList<D>(sourceList.size());
        for (S s : sourceList) {
            list.add(mapper.map(s, destClass));
        }
        return list;
    }

}
