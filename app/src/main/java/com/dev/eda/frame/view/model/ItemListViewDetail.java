package com.dev.eda.frame.view.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;

public class ItemListViewDetail implements MultiItemEntity {

    public static final int item_type_default = 0;
    public static final int item_type_textbox = 1;
    public static final int item_type_multextbox = 2;
    public static final int item_type_select = 3;
    public static final int item_type_datetime = 6;
    public static final int item_type_lookup = 7;
    public static final int item_type_switchbtn = 8;
    public static final int item_type_checkselect = 9;

    private int itemType;

    private String pageTitle;

    private String columnName;

    private String columnDes;

    private String columnValue;

    private String isReadOnlyCol;

    private int formatCode;

    private ArrayList<ItemDetailCheckSelect> selects;

    public String getColumnDes() {
        return columnDes;
    }

    public void setColumnDes(String columnDes) {
        this.columnDes = columnDes;
    }

    public ArrayList<ItemDetailCheckSelect> getSelects() {
        return selects;
    }

    public void setSelects(ArrayList<ItemDetailCheckSelect> selects) {
        this.selects = selects;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    public ItemListViewDetail(int itemType) {
        this.itemType = itemType;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getIsReadOnlyCol() {
        return isReadOnlyCol;
    }

    public void setIsReadOnlyCol(String isReadOnlyCol) {
        this.isReadOnlyCol = isReadOnlyCol;
    }

    public int getFormatCode() {
        return formatCode;
    }

    public void setFormatCode(int formatCode) {
        this.formatCode = formatCode;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    @Override
    public String toString() {
        return "ItemListViewDetail{" +
                "itemType=" + itemType +
                ", pageTitle='" + pageTitle + '\'' +
                ", columnName='" + columnName + '\'' +
                ", columnValue='" + columnValue + '\'' +
                ", isReadOnlyCol='" + isReadOnlyCol + '\'' +
                ", formatCode='" + formatCode + '\'' +
                '}';
    }
}
