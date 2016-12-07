package com.coder.qiang.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/6.
 */
public class Util {
    /*
    * 以行为单位读取文件，常用于读面向行的格式化文件
    */
    public static String readFile(File file) {
        String content = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader((new InputStreamReader(new FileInputStream(file),"UTF-8")));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                content += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return content;
    }
    public static List<File> getAllFile(String path) {
        List<File> allFiles = new ArrayList<>();
        File file = new File(path);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                allFiles.add(tempList[i]);
            }
            if (tempList[i].isDirectory()) {
                if(tempList[i].getName().indexOf(".")==0)
                {
                    //隐藏文件 不读
                    continue;
                }
                allFiles.addAll(getAllFile(tempList[i].getPath()));
            }
        }
        return allFiles;
    }

}
