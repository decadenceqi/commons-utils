/*
 * Commons-Utils
 * Copyright (c) 2017.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 */

package com.ykun.commons.utils.collection;

import com.google.common.collect.Lists;

import java.util.*;

/**
 * List工具集合，封装些常用的方法和简单的集合运算
 * 依赖Google Guava
 *
 * @author Ykun 于 2017-02-13 15:35
 */
public class ListUtil {

    /**
     * 判断集合是否为空
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(List<?> list) {
        return (list == null) || (list.isEmpty());
    }

    /**
     * 判断集合不为空
     *
     * @param list
     * @return
     */
    public static boolean isNotEmpty(List<?> list) {
        return (list != null) && !(list.isEmpty());
    }

    /**
     * 获取第一个元素，如果list为空返回null
     *
     * @param list
     * @return
     */
    public static <T> T getFirst(List<T> list) {
        if (isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 获取最后一个元素，如果list为空返回null
     *
     * @param list
     * @return
     */
    public static <T> T getLast(List<T> list) {
        if (isEmpty(list)) {
            return null;
        }

        return list.get(list.size() - 1);
    }

    /**
     * @see com.google.common.collect.Lists#newArrayList
     */
    public static <E> ArrayList<E> newArrayList() {
        return Lists.newArrayList();
    }

    /**
     * @see com.google.common.collect.Lists#newArrayList
     */
    public static <E> ArrayList<E> newArrayList(E... elements) {
        return Lists.newArrayList(elements);
    }

    /**
     * @see com.google.common.collect.Lists#newArrayList
     */
    public static <E> ArrayList<E> newArrayList(Iterable<E> elements) {
        return Lists.newArrayList(elements);
    }

    /**
     * @see com.google.common.collect.Lists#newArrayList
     */
    public static <E> LinkedList<E> newLinkedList() {
        return Lists.newLinkedList();
    }

    /**
     * @see com.google.common.collect.Lists#newArrayList
     */
    public static <T> LinkedList<T> newLinkedList(Iterable<? extends T> elements) {
        return Lists.newLinkedList(elements);
    }

    /**
     * 返回空List，并且不可写入
     *
     * @return
     */
    public static final <T> List<T> emptyList() {
        return (List<T>) Collections.EMPTY_LIST;
    }

    /**
     * jdk 默认排序，实现compareTo方法
     *
     * @param list
     */
    public static <T extends Comparable<? super T>> void sort(List<T> list) {
        Collections.sort(list);
    }

    /**
     * 打乱List
     *
     * @param list
     */
    public static void shuffle(List<?> list) {
        Collections.shuffle(list);
    }

    /**
     * 反转List
     *
     * @see com.google.common.collect.Lists#reverse
     */
    public static <T> List<T> reverse(final List<T> list) {
        return Lists.reverse(list);
    }

    /**
     * 合并List
     *
     * @param list1
     * @param list2
     */
    public static <E> List<E> union(final List<? extends E> list1, final List<? extends E> list2) {
        final List<E> result = new ArrayList<E>(list1.size() + list2.size());
        result.addAll(list1);
        result.addAll(list2);
        return result;
    }

    /**
     * 交集
     *
     * @param list1
     * @param list2
     * @return
     */
    public static <T> List<T> intersection(final List<? extends T> list1, final List<? extends T> list2) {
        List<? extends T> smaller = list1;
        List<? extends T> larger = list2;
        if (list1.size() > list2.size()) {
            smaller = list2;
            larger = list1;
        }

        List<T> newSmaller = new ArrayList<T>(smaller);
        List<T> result = new ArrayList<T>(smaller.size());
        for (final T e : larger) {
            if (newSmaller.contains(e)) {
                result.add(e);
                newSmaller.remove(e);
            }
        }
        return result;
    }

    /**
     * list1, list2的差集
     *
     * @param list1
     * @param list2
     * @return
     */
    public static <T> List<T> difference(final List<? extends T> list1, final List<? extends T> list2) {
        final List<T> result = new ArrayList<T>(list1);
        final Iterator<? extends T> iterator = list2.iterator();

        while (iterator.hasNext()) {
            result.remove(iterator.next());
        }

        return result;
    }

}
