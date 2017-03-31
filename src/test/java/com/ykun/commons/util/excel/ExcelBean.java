/*
 * Commons-Utils
 * Copyright (c) 2017.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 */

package com.ykun.commons.util.excel;

import com.ykun.commons.util.excel.annotation.ExcelField;

import java.util.Date;
import java.util.Random;

/**
 * @author Ykun 于 2017-02-08 11:31
 */
public class ExcelBean {

    @ExcelField(ignore = true)
    private final static Random random = new Random();

    @ExcelField("姓名")
    private String name;

    @ExcelField("地址")
    private String address;

    @ExcelField(value = "生日", dateFormatter = "yyyy-MM-dd")
    private Date birthday;

    @ExcelField("年龄")
    private int age;

    //    @ExcelField("存款")
    private Double money;

    //    @ExcelField("得瑟")
    private boolean handsome = true;

    public ExcelBean() {
        this.name = "姓名" + random.nextInt(Integer.MAX_VALUE);
        this.address = "地址" + random.nextInt(Integer.MAX_VALUE);
        this.birthday = new Date();
        this.age = random.nextInt(100);
        this.money = 2.33;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static ExcelBean randomExcel() {
        return new ExcelBean();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public boolean getHandsome() {
        return handsome;
    }

    public void setHandsome(boolean handsome) {
        this.handsome = handsome;
    }
}
