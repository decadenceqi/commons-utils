package com.ykun.commons.utils.excel;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created By Ykun 2017-02-07 18:37
 */
public class ExcelTest {

    public static void main(String[] args) throws Exception {
        List<ExcelBean> list = new ArrayList<ExcelBean>(10000);
        for (int i = 0; i < 10000; i++) {
            list.add(ExcelBean.randomExcel());
        }

        FileOutputStream fileOutputStream = new FileOutputStream("target/test.xls");
        ExcelUtil.export(list, fileOutputStream);
        fileOutputStream.close();
        System.out.println("end");
    }
}
