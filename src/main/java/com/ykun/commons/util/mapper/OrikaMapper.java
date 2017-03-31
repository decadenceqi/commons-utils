/*
 * Commons-Utils
 * Copyright (c) 2017.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 */

package com.ykun.commons.util.mapper;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
import ma.glasnost.orika.metadata.TypeFactory;

import java.util.List;

/**
 * 封装orika，实现beanA->beanB
 * https://github.com/orika-mapper/orika
 *
 * @author Ykun 于 2017-02-10 14:50
 */
public class OrikaMapper {

    /**
     * 单例
     */
    private static MapperFacade mapper = (new DefaultMapperFactory.Builder().build()).getMapperFacade();

    /**
     * 简单复制新对象
     *
     * @param source    源对象
     * @param destClass 目标对象类型
     * @return D d
     */
    public static <S, D> D map(S source, Class<D> destClass) {
        return mapper.map(source, destClass);
    }

    /**
     * 简单复制新对象
     *
     * @param source     源对象
     * @param sourceType BeanMapper.getType()
     * @param destType   BeanMapper.getType()
     * @return D d
     */
    public static <S, D> D map(S source, Type<S> sourceType, Type<D> destType) {
        return mapper.map(source, sourceType, destType);
    }

    /**
     * 对象集合复制
     *
     * @param sourceList  源对象集合
     * @param sourceClass 源对象类型
     * @param destClass   目标对象类型
     * @return the list
     */
    public static <S, D> List<D> mapList(Iterable<S> sourceList, Class<S> sourceClass, Class<D> destClass) {
        return mapper.mapAsList(sourceList, TypeFactory.valueOf(sourceClass), TypeFactory.valueOf(destClass));
    }

    /**
     * 对象集合复制
     *
     * @param sourceList 源对象集合
     * @param sourceType BeanMapper.getType()
     * @param destType   BeanMapper.getType()
     * @return the list
     */
    public static <S, D> List<D> mapList(Iterable<S> sourceList, Type<S> sourceType, Type<D> destType) {
        return mapper.mapAsList(sourceList, sourceType, destType);
    }

    /**
     * 对象数组复制
     *
     * @param destination 目标对象数组
     * @param source      源对象数组
     * @param destClass   目标对象类型
     * @return the d [ ]
     */
    public static <S, D> D[] mapArray(final D[] destination, final S[] source, final Class<D> destClass) {
        return mapper.mapAsArray(destination, source, destClass);
    }

    /**
     * 对象数组复制
     *
     * @param destination 目标对象数组
     * @param source      源对象数组
     * @param sourceType  BeanMapper.getType()
     * @param destType    BeanMapper.getType()
     * @return the d [ ]
     */
    public static <S, D> D[] mapArray(D[] destination, S[] source, Type<S> sourceType, Type<D> destType) {
        return mapper.mapAsArray(destination, source, sourceType, destType);
    }

    /**
     * Gets type.
     *
     * @param <E>     the type parameter
     * @param rawType the raw type
     * @return the type
     */
    public static <E> Type<E> getType(final Class<E> rawType) {
        return TypeFactory.valueOf(rawType);
    }
}
