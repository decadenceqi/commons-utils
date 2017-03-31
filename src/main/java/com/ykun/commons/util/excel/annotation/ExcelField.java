/*
 * Commons-Utils
 * Copyright (c) 2017.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 */

package com.ykun.commons.util.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Ykun 于 2017-02-08 11:04
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {

    /**
     * 导出字段名
     */
    String value() default "";

    /**
     * 是否忽略字段，默认不忽略
     */
    boolean ignore() default false;

    /**
     * 仅对java.util.Date类型生效，默认格式：yyyy-MM-dd HH:mm:ss
     */
    String dateFormatter() default "yyyy-MM-dd HH:mm:ss";

}
