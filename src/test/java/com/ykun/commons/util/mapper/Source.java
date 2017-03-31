/*
 * Commons-Utils
 * Copyright (c) 2017.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 */

package com.ykun.commons.util.mapper;

import org.dozer.Mapping;

/**
 * @author Ykun 于 2017-03-29 14:27
 */
public class Source {

    @Mapping("pk")
    private int id;

    @Mapping("name")
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
