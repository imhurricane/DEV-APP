package com.dev.eda.frame.centre.model;

import com.dev.eda.frame.home.model.PluginModel;

import java.util.List;

public class Centre {

    private String title;

    private List<PluginModel> entryModelList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<PluginModel> getEntryModelList() {
        return entryModelList;
    }

    public void setEntryModelList(List<PluginModel> entryModelList) {
        this.entryModelList = entryModelList;
    }
}
