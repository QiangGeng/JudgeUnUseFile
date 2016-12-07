package com.coder.qiang.service;

import com.coder.qiang.modal.UnUseFile;
import com.coder.qiang.util.Util;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Created by Administrator on 2016/12/6.
 */
public class JudeService {

    public static Vector<UnUseFile> getUnUserFiles(String path)
    {
        List<File> allFiles = Util.getAllFile(path);
        System.out.println("正在判断。。。。");
        Long start=new Date().getTime();
        Vector<UnUseFile> noUserFilesName = new Vector<>();
        for (File a :
                allFiles) {
            System.out.println(a.getName());
            String suffix= a.getName().substring( a.getName().lastIndexOf(".")+1, a.getName().length());
            if(suffix.equals("java")||suffix.equals("xml")||suffix.equals("properties")||suffix.equals("jar"))
                continue;
            boolean flag=false;
            for (int i=0;i<allFiles.size();i++) {
                File b=allFiles.get(i);
                String fileName=a.getName();
                if(fileName.lastIndexOf(".")>0)
                    fileName=fileName.substring(0,fileName.lastIndexOf("."));
                //如果是图片或者是配置文件，则不读取器内容
                suffix= b.getName().substring( b.getName().lastIndexOf(".")+1, b.getName().length());
                if(suffix.equals("png")||suffix.equals("jpg")||suffix.equals("xml")||suffix.equals("properties")||suffix.equals("gif"))
                    continue;
                if(judgeUse(fileName, b))
                {
                    flag=true;
                    break;
                }
            }
            if( !flag)
            {
                UnUseFile unUseFile=new UnUseFile();
                unUseFile.setName(a.getName());
                unUseFile.setPath(a.getPath());
                String fileName=a.getName();
                if(fileName.lastIndexOf(".")+1>0)
                {
                    String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
                    unUseFile.setSuffix(prefix);
                }
                noUserFilesName.add(unUseFile);
            }
        }
        Long end=new Date().getTime();
        System.out.println((end-start)/1000+"s");
        return noUserFilesName;
    }


    public static boolean judgeUse(String fileName, File file) {
        boolean b = false;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader((new InputStreamReader(new FileInputStream(file),"UTF-8")));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                if (tempString.contains(fileName)) {
                    return true;
                }
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
        return b;
    }
}
