package com.dev.eda.frame.home.model;

import java.util.ArrayList;

public class BannerModel {

    private ArrayList<String> title;

    private ArrayList<String> imageUrl;

    private ArrayList<Integer> imageResource;

    public ArrayList<String> getTitle() {
        return title;
    }

    public void setTitle(ArrayList<String> title) {
        this.title = title;
    }

    public ArrayList<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(ArrayList<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<Integer> getImageResource() {
        return imageResource;
    }

    public void setImageResource(ArrayList<Integer> imageResource) {
        this.imageResource = imageResource;
    }

    @Override
    public String toString() {
        return "BannerModel{" +
                "title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageResource=" + imageResource +
                '}';
    }
}
