package com.dev.eda.frame.view.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class ItemListView implements MultiItemEntity {

    public static final int item_type_default = 0;
    public static final int item_type_all = 1;

    private int itemType;

    private ItemListViewContent itemListViewContent;

    private String icon;

    private String detailPageId;

    private String listTitle;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDetailPageId() {
        return detailPageId;
    }

    public void setDetailPageId(String detailPageId) {
        this.detailPageId = detailPageId;
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public ItemListViewContent getItemListViewContent() {
        return itemListViewContent;
    }

    public void setItemListViewContent(ItemListViewContent itemListViewContent) {
        this.itemListViewContent = itemListViewContent;
    }

    public ItemListView(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    @Override
    public String toString() {
        return "ItemListView{" +
                "itemType=" + itemType +
                ", itemListViewContent=" + itemListViewContent +
                ", icon='" + icon + '\'' +
                ", detailPageId='" + detailPageId + '\'' +
                ", listTitle='" + listTitle + '\'' +
                '}';
    }
}
