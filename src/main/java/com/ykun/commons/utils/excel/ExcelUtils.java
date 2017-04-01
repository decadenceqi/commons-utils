/*
 * Commons-Utils
 * Copyright (c) 2017.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 */

package com.ykun.commons.utils.excel;

import com.ykun.commons.utils.excel.annotation.ExcelField;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.ykun.commons.utils.constant.Constant.CHARSET_UTF8;

/**
 * 封装apache-poi，暂时不支持自动拆分sheet，单sheet数据大于1048576会报错，基于xlsx
 * <p>
 * Excel 07-2003一个工作表最多可有65536行，行用数字1—65536表示;最多可有256列，列用英文字母A—Z，AA—AZ，BA—BZ，……，IA—IV表示；一个工作簿中最多含有255个工作表，默认情况下是三个工作表；
 * Excel 2007及以后版本，一个工作表最多可有1048576行，16384列；
 *
 * @author Ykun 于 2017-02-08 11:04
 */
public class ExcelUtils {

    private final static Log logger = LogFactory.getLog(ExcelUtils.class);

    /**
     * 日期格式化
     */
    private final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat();

    /**
     * get方法前缀
     */
    private final static String PREFIX_GETTER = "get";

    /**
     * xls 字符集
     */
    private final static String CONTENT_TYPE = "application/vnd.ms-excel;charset=utf-8";

    /**
     * header
     */
    private final static String HEADER_CONTENT_DISPOSITION = "Content-Disposition";

    /**
     * Disposition
     */
    private final static String PATTERN_DISPOSITION = "attachment; filename={0}.xlsx";

    /**
     * 导出xls，headers使用注解ExcelField，不设置默认取属性名
     *
     * @param list the list
     * @param out  the out
     */
    public static <T> void export(List<T> list, OutputStream out) {
        export(list, getHeaders(list), out);
    }

    /**
     * 导出xls，自定义headers
     *
     * @param list    the list
     * @param headers the headers
     * @param out     the out
     */
    public static <T> void export(List<T> list, String[] headers, OutputStream out) {
        export(list, Arrays.asList(headers), out);
    }

    /**
     * 导出xls，自定义headers
     *
     * @param list    the list
     * @param headers the headers
     * @param out     the out
     */
    public static <T> void export(List<T> list, List<String> headers, OutputStream out) {
        // 非空校验
        if (list == null || list.size() == 0) {
            return;
        }

        try {
            Workbook workbook = new XSSFWorkbook(); // XSSFWorkbook
            Sheet sheet = workbook.createSheet(); // 生成Sheet

            // 生成表头
            int rowNo = 0;
            CellStyle headerStyle = createHeaderStyle(workbook);
            if (headers != null && headers.size() > 0) {
                Row row = sheet.createRow(rowNo++);
                for (int i = 0; i < headers.size(); i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellStyle(headerStyle);
                    cell.setCellValue(headers.get(i));
                }
            }

            // 填充数据
            CellStyle normalStyle = createNormalStyle(workbook);
            for (T t : list) {
                Row row = sheet.createRow(rowNo++);
                Field[] fields = t.getClass().getDeclaredFields();
                int column = 0;
                for (int i = 0; i < fields.length; i++) {
                    Object value;
                    Field field = fields[i];
                    ExcelField excelField = field.getAnnotation(ExcelField.class);
                    if (excelField != null && !excelField.ignore()) {
                        String methodName = PREFIX_GETTER + StringUtils.capitalize(field.getName()); // get方法名，如果自动生成的get方法类似isEnable，可能会出错
                        Method method = t.getClass().getMethod(methodName, new Class[]{});
                        value = method.invoke(t, new Object[]{});
                    } else if (excelField != null && excelField.ignore()) {
                        continue;
                    } else {
                        String methodName = PREFIX_GETTER + StringUtils.capitalize(field.getName()); // get方法名，如果自动生成的get方法类似isEnable，可能会出错
                        Method method = t.getClass().getMethod(methodName, new Class[]{});
                        value = method.invoke(t, new Object[]{});
                    }
                    row.setRowStyle(normalStyle);
                    addCell(row, column++, value, excelField);
                }
            }
            workbook.write(out);
        } catch (Exception e) {
            logger.error("Export error:", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成第一列表头样式
     *
     * @param workbook Workbook
     * @return CellStyle
     */
    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = workbook.createFont();
        headerFont.setBold(Boolean.TRUE);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        style.setFont(headerFont);
        return style;
    }

    /**
     * 生成数据表格样式（暂未设置样式）
     *
     * @param workbook
     * @return HSSFCellStyle
     */
    private static CellStyle createNormalStyle(Workbook workbook) {
        return workbook.createCellStyle();
    }

    /**
     * 根据数据中的对象获取headers，仅对注解ExcelField生效
     *
     * @param <T>  the type parameter
     * @param list 数据集合
     * @return List headers
     */
    public static <T> List<String> getHeaders(List<T> list) {
        List<String> headers = new ArrayList<String>();
        if (list != null && list.size() > 0) {
            T t = list.get(0);
            Field[] fields = t.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                ExcelField excelField = field.getAnnotation(ExcelField.class);
                if (excelField != null && !excelField.ignore()) {
                    headers.add(excelField.value());
                } else if (excelField != null && excelField.ignore()) {
                } else {
                    headers.add(field.getName());
                }
            }
        }
        return headers;
    }

    /**
     * 生成cell数据
     *
     * @param row
     * @param column
     * @param value
     * @param excelField
     * @return Cell
     */
    private static Cell addCell(Row row, int column, Object value, ExcelField excelField) {
        Cell cell = row.createCell(column);
        if (value == null) {
            cell.setCellValue("");
        } else {
            if (value instanceof String) {
                cell.setCellValue((String) value);
            } else if (value instanceof Integer) {
                cell.setCellValue((Integer) value);
            } else if (value instanceof Long) {
                cell.setCellValue((Long) value);
            } else if (value instanceof Double) {
                cell.setCellValue((Double) value);
            } else if (value instanceof Float) {
                cell.setCellValue((Float) value);
            } else if (value instanceof Date) {
                Date date = (Date) value;
                if (excelField != null) {
                    SIMPLE_DATE_FORMAT.applyPattern(excelField.dateFormatter());
                    cell.setCellValue(SIMPLE_DATE_FORMAT.format(date));
                }
            } else if (value instanceof Boolean) {
                cell.setCellValue((Boolean) value);
            } else {
                // 其余按照字符串强转
                cell.setCellValue((String) value);
            }
        }
        return cell;
    }

    /**
     * 下载xls
     *
     * @param <T>      the type parameter
     * @param response the response
     * @param list     the list
     * @param fileName the file name
     * @throws IOException the io exception
     */
    public static <T> void download(HttpServletResponse response, List<T> list, String fileName) throws IOException {
        setContentType(response, fileName);
        export(list, response.getOutputStream());
    }

    /**
     * 下载xls
     *
     * @param <T>      the type parameter
     * @param response the response
     * @param list     the list
     * @param headers  the headers
     * @param fileName the file name
     * @throws IOException the io exception
     */
    public static <T> void download(HttpServletResponse response, List<T> list, String[] headers, String fileName) throws IOException {
        setContentType(response, fileName);
        export(list, headers, response.getOutputStream());
    }

    /**
     * 下载xls
     *
     * @param <T>      the type parameter
     * @param response the response
     * @param list     the list
     * @param headers  the headers
     * @param fileName the file name
     * @throws IOException the io exception
     */
    public static <T> void download(HttpServletResponse response, List<T> list, List<String> headers, String fileName) throws IOException {
        setContentType(response, fileName);
        export(list, headers, response.getOutputStream());
    }

    /**
     * 设置下载xls的content-type
     *
     * @param response
     * @param fileName
     * @throws IOException
     */
    private static void setContentType(HttpServletResponse response, String fileName) throws IOException {
        response.setContentType(CONTENT_TYPE);
        response.setHeader(HEADER_CONTENT_DISPOSITION, MessageFormat.format(PATTERN_DISPOSITION, URLEncoder.encode(fileName, CHARSET_UTF8)));
    }
}
