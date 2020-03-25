package com.dev.eda.frame.blog.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class Blog{

    private String Name;

    private int titleImageResource;

    private int backGroundImageResource;

    private List<BlogContent> blogContents;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getTitleImageResource() {
        return titleImageResource;
    }

    public void setTitleImageResource(int titleImageResource) {
        this.titleImageResource = titleImageResource;
    }

    public List<BlogContent> getBlogContents() {
        return blogContents;
    }

    public void setBlogContents(List<BlogContent> blogContent) {
        this.blogContents = blogContent;
    }

    public int getBackGroundImageResource() {
        return backGroundImageResource;
    }

    public void setBackGroundImageResource(int backGroundImageResource) {
        this.backGroundImageResource = backGroundImageResource;
    }

}
