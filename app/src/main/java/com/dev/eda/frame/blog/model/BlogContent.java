package com.dev.eda.frame.blog.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class BlogContent implements MultiItemEntity {

    public static final int itemType_message = 1;
    public static final int itemType_images = 2;
    public static final int itemType_more = 3;
    public static final int itemType_good = 4;

    private int itemType;

    private String content;

    private List<BlogImage> images;

    private String timeAndAddress;

    private String goodText;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<BlogImage> getImages() {
        return images;
    }

    public void setImages(List<BlogImage> images) {
        this.images = images;
    }

    public String getTimeAndAddress() {
        return timeAndAddress;
    }

    public void setTimeAndAddress(String timeAndAddress) {
        this.timeAndAddress = timeAndAddress;
    }

    public String getGoodText() {
        return goodText;
    }

    public void setGoodText(String goodText) {
        this.goodText = goodText;
    }

    public BlogContent(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
