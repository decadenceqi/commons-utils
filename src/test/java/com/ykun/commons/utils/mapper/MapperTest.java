/*
 * Commons-Utils
 * Copyright (c) 2017.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 */

package com.ykun.commons.utils.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ykun 于 2017-02-10 14:50
 */
public class MapperTest {

    public static void main(String[] args) {

        // bean copy
        Source source = new Source("zhangsan", 25, Arrays.asList(new String[]{"xxoo", "xxoo"}));
        Destnation d = BeanMapper.map(source, Destnation.class);

        // List<T> copy
        Source source1 = new Source("zhangsan1", 21, Arrays.asList(new String[]{"oo", "xxoo"}));
        Source source2 = new Source("zhangsan2", 22, Arrays.asList(new String[]{"xxoo", "oo"}));
        Source source3 = new Source("zhangsan3", 23, Arrays.asList(new String[]{"xo", "xo"}));
        List<Source> sourceList = new ArrayList<Source>(3);
        sourceList.add(source1);
        sourceList.add(source2);
        sourceList.add(source3);
        List<Destnation> list = BeanMapper.mapList(sourceList, Source.class, Destnation.class);

    }

    public static class Source {

        private String name;

        private int age;

        private List<String> hobbys;

        public Source() {
        }

        public Source(String name, int age, List<String> hobbys) {
            this.name = name;
            this.age = age;
            this.hobbys = hobbys;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public List<String> getHobbys() {
            return hobbys;
        }

        public void setHobbys(List<String> hobbys) {
            this.hobbys = hobbys;
        }
    }

    public static class Destnation {

        private String name;

        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
