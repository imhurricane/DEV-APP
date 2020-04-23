package com.dev.eda.frame.view.model;

public class ItemListViewContent {

    private String id;

    private String title;

    private String describe;

    private String note;

    private String subTitle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    @Override
    public String toString() {
        return "ItemListViewContent{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", describe='" + describe + '\'' +
                ", note='" + note + '\'' +
                ", subTitle='" + subTitle + '\'' +
                '}';
    }
}
