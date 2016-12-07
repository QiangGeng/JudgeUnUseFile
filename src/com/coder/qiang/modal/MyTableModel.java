package com.coder.qiang.modal;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Administrator on 2016/12/7.
 */
public class MyTableModel extends AbstractTableModel {

    String[] columnNames =
            {"选择", "文件名称", "文件类型", "文件地址"};
    List<List<Object>>  data = null;
    List<List<Object>>  oldData = null;

    @Override
    public void fireTableRowsDeleted(int firstRow, int lastRow) {
        super.fireTableRowsDeleted(firstRow, lastRow);
    }

    public MyTableModel(Vector<UnUseFile> unUseFiles) {
        int row = unUseFiles.size();
        data = new ArrayList<>();
        oldData = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            UnUseFile unUseFile = unUseFiles.get(i);
            List<Object> list=new ArrayList<>();
            list.add(new Boolean(false));
            list.add(unUseFile.getName());
            list.add(unUseFile.getSuffix());
            list.add(unUseFile.getPath());
            data.add(list);
            oldData.add(list);
        }
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        List<Object> list=data.get(rowIndex);
        list.set(columnIndex,aValue);
        data.set(rowIndex,list);
        fireTableCellUpdated(rowIndex, columnIndex);
    }
//
    /**
     * 得到指定列的数据类型
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        try {
            return data.get(0).get(columnIndex).getClass();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 指定设置数据单元是否可编辑
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(columnIndex>0)
            return false;
        else
            return true;
    }

    public boolean deleteRow(Integer row)
    {
        if(data.remove(oldData.get(row)))
        {
            super.fireTableRowsDeleted(row,row);
            return true;
        }
        return false;
    }





}
