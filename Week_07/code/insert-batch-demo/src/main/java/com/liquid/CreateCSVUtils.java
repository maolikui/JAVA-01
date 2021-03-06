package com.liquid;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建csv文件工具类
 *
 * @author Liquid
 */
public class CreateCSVUtils {

    public static File createCSVFile(List<Object> dataLists, List<Object> headList) throws IOException {
        File csvFile = null;
        BufferedWriter csvWrite = null;
        try {
            //定义文件类型
            csvFile = new File("C:\\Users\\maolikui\\Desktop\\test\\test.csv");
            //去文件目录
            File parent = csvFile.getParentFile();
            if (parent.exists()) {
                parent.mkdirs();

            }
            //创建文件
            csvFile.createNewFile();
            csvWrite = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"), 1024);

            //写入表头
            //write(headList, csvWrite);
            csvWrite.write("1,1,1,1,1,1,1,1");
            csvWrite.newLine();
            //写入数据
            //for (Object dataList : dataLists) {
            //    write((List<Object>) dataList, csvWrite);
            //}
            write(new ArrayList<Object>(), csvWrite);
            csvWrite.flush();
        } catch (IOException e) {
            throw new IOException("文件生成失败");
        } finally {
            try {
                csvWrite.close();
            } catch (IOException e) {

                throw new IOException("关闭文件流失败");
            }
        }
        return csvFile;
    }

    private static void write(List<Object> dataList, BufferedWriter csvWrite) throws IOException {
        //for (Object data : dataList) {
        //    StringBuffer buffer = new StringBuffer();
        //    String rowStr = buffer.append("\"").append(data).append("\",").toString();
        //    csvWrite.write(rowStr);
        //    //csvWrite.newLine();
        //}
        for (int i = 0; i < 1000000; i++) {
            csvWrite.write("1,1,1,1,1,1,1,1");
            csvWrite.newLine();
        }
    }
}