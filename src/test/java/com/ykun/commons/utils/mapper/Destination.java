/*
 * Commons-Utils
 * Copyright (c) 2017.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 */

package com.ykun.commons.utils.mapper;

/**
 * @author Ykun 于 2017-03-29 14:27
 */
public class Destination {

    private String name;

    private int age;

    private int pk;

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

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    @Override
    public String toString() {
        return "Destination{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", pk=" + pk +
                '}';
    }
}
