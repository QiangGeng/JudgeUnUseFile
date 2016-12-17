package com.coder.qiang.ui;

/**
 * Created by Administrator on 2016/12/2.
 */

import com.coder.qiang.modal.MyTableModel;
import com.coder.qiang.modal.UnUseFile;
import com.coder.qiang.service.JudeService;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

/**
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
    JButton button4 = new JButton("删除文件");//
    JTable result = null;
    JScrollPane scrollPane = null;
    MyTableModel myTableModel = null;

    FileChooser() {
        jfc.setCurrentDirectory(new File("d://"));// 文件选择器的初始目录定为d盘

        double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();

        double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

        frame.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150));// 设定窗口出现位置
        frame.setSize(680, 500);// 设定窗口大小
        label1.setBounds(10, 10, 70, 20);
        label2.setBounds(10, 80, 120, 20);
        text1.setBounds(75, 10, 420, 20);
        button1.setBounds(510, 10, 100, 20);
        button3.setBounds(300, 60, 100, 20);
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
            String path = text1.getText();
            new Thread(new Runnable() {
                public void run() {
                    //do something...
                    if (result != null) {
                        con.remove(scrollPane);
                        con.remove(button4);
                        con.repaint();
                        frame.repaint();
                    }
                    InfiniteProgressPanel glasspane = new InfiniteProgressPanel();	Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                    glasspane.setBounds(100, 100, (dimension.width) / 2, (dimension.height) / 2);
                    frame.setGlassPane(glasspane);
                    glasspane.start();//开始动画加载效果
                    frame.setVisible(true);

                    Vector<UnUseFile> unUseFiles = JudeService.getUnUserFiles(path);
                    myTableModel = new MyTableModel(unUseFiles);
                    result = new JTable(myTableModel);
                    result.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                    FitTableColumns(result);
                    scrollPane = new JScrollPane(result);
                    scrollPane.setBounds(0, 100, 670, 300);
                    button4.setBounds(480, 420, 100, 20);
                    con.add(scrollPane);
                    con.add(button4);
                    button4.addActionListener(FileChooser.this); // 添加事件处理
                    con.repaint();
                    frame.repaint();
                    glasspane.stop();
                }
            }).start();
        }

        if (e.getSource().equals(button4)) {
            Map<Integer, String> deleteFiles = new HashMap<>();
            if (result != null) {
                for (int i = 0; i < result.getRowCount(); i++) {
                    if (result.getValueAt(i, 0).toString().equals("true")) {
                        deleteFiles.put(i, result.getValueAt(i, 3).toString());
                    }
                }
            }
            if (deleteFiles.size() > 0) {
                //删除文件
                for (Integer key : deleteFiles.keySet()) {
                    File f = new File(deleteFiles.get(key));  // 输入要删除的文件位置
                    if(f.exists())
                    {
                        if(f.delete())
                        {
                            myTableModel.deleteRow(key);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "请选择要删除的文件",
                        "错误提示", JOptionPane.ERROR_MESSAGE);
            }

        }

    }
    public void FitTableColumns(JTable myTable) {
        JTableHeader header = myTable.getTableHeader();
        int rowCount = myTable.getRowCount();

        Enumeration columns = myTable.getColumnModel().getColumns();
        while (columns.hasMoreElements()) {
            TableColumn column = (TableColumn) columns.nextElement();
            int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
            int width = (int) myTable.getTableHeader().getDefaultRenderer()
                    .getTableCellRendererComponent(myTable, column.getIdentifier()
                            , false, false, -1, col).getPreferredSize().getWidth();
            for (int row = 0; row < rowCount; row++) {
                int preferedWidth = (int) myTable.getCellRenderer(row, col).getTableCellRendererComponent(myTable,
                        myTable.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
                width = Math.max(width, preferedWidth);
            }
            header.setResizingColumn(column); // 此行很重要
            column.setWidth(width + myTable.getIntercellSpacing().width);
        }
    }


    public static void main(String[] args) {
        new FileChooser();
    }
}

