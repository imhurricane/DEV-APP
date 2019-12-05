package com.dev.eda.frame.centre.model;

import com.dev.eda.frame.home.model.EntryModel;

import java.util.List;

public class Centre {

    private String title;

    private List<EntryModel> entryModelList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<EntryModel> getEntryModelList() {
        return entryModelList;
    }

    public void setEntryModelList(List<EntryModel> entryModelList) {
        this.entryModelList = entryModelList;
    }
}
