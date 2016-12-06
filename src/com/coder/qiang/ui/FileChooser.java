package com.coder.qiang.ui;

/**
 * Created by Administrator on 2016/12/2.
 */

import com.coder.qiang.modal.UnUseFile;
import com.coder.qiang.service.JudeService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author 漆艾林 QQ 172794299 邮箱 qiailing.ok@163.com
 */
public class FileChooser implements ActionListener {
    JFrame frame = new JFrame("判断无用文件");// 框架布局
    Container con = new Container();//
    JLabel label1 = new JLabel("文件目录");
    JLabel label2 = new JLabel("判断结果：");
    JTextField text1 = new JTextField();// TextField 目录的路径
    JButton button1 = new JButton("选择文件目录");// 选择
    JFileChooser jfc = new JFileChooser();// 文件选择器
    JButton button3 = new JButton("确定");//
    private JList list = null;

    FileChooser() {
        jfc.setCurrentDirectory(new File("d://"));// 文件选择器的初始目录定为d盘

        double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();

        double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

        frame.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150));// 设定窗口出现位置
        frame.setSize(380, 500);// 设定窗口大小
        label1.setBounds(10, 10, 70, 20);
        label2.setBounds(10, 80, 70, 20);
        text1.setBounds(75, 10, 120, 20);
        button1.setBounds(210, 10, 100, 20);
        button3.setBounds(140, 60, 100, 20);
        button1.addActionListener(this); // 添加事件处理
        button3.addActionListener(this); // 添加事件处理
        con.add(label1);
        con.add(label2);
        con.add(text1);
        con.add(button1);
        con.add(button3);
        frame.setVisible(true);// 窗口可见
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 使能关闭窗口，结束程序
        frame.add(con);// 添加布局1
    }

    /**
     * 时间监听的方法
     */
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource().equals(button1)) {// 判断触发方法的按钮是哪个
            jfc.setFileSelectionMode(1);// 设定只能选择到文件夹
            int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
            if (state == 1) {
                return;
            } else {
                File f = jfc.getSelectedFile();// f为选择到的目录
                text1.setText(f.getAbsolutePath());
            }
        }
        if (e.getSource().equals(button3)) {
            // 判断
            String path=text1.getText();
            Vector<UnUseFile>  unUseFiles= JudeService.getUnUserFiles(path);
            System.out.println(unUseFiles.toArray());
            this.list = new JList(unUseFiles);
            list.setBounds(0, 100, 380, 400);
            con.add(list);
            con.repaint();
            frame.repaint();


        }
    }

    public static void main(String[] args) {
        new FileChooser();
    }
}

