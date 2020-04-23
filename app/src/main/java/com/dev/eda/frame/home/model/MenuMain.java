package com.dev.eda.frame.home.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.HashMap;

public class MenuMain implements MultiItemEntity {

    public static final int itemType_1 = 1;
    public static final int itemType_2 = 2;

    private int itemType;

    private String title;

    private String icon;

    private String toPage;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getToPage() {
        return toPage;
    }

    public void setToPage(String toPage) {
        this.toPage = toPage;
    }


    public MenuMain(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    @Override
    public String toString() {
        return "MenuMain{" +
                "itemType=" + itemType +
                ", title='" + title + '\'' +
                ", icon='" + icon + '\'' +
                ", toPage='" + toPage + '\'' +
                '}';
    }
}
