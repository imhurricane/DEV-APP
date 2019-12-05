package com.dev.eda.frame.blog.model;

import java.util.List;

public class Blog {

    private String Name;

    private int titleImageResource;

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
}
