package com.dev.eda.frame.home.model;


import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class Home implements MultiItemEntity {

    public static final int itemType_grid = 1;
    public static final int itemType_card = 2;
    public static final int itemType_chart = 3;
    public static final int itemType_chart_1 = 4;
    public static final int itemType_footer = 5;

    private int itemType;

    private List<PluginModel> entryModels;

    private NeedToDo needToDo;

    private ChartModel chartModel;

    public List<PluginModel> getEntryModels() {
        return entryModels;
    }

    public void setEntryModels(List<PluginModel> entryModels) {
        this.entryModels = entryModels;
    }

    public NeedToDo getNeedToDo() {
        return needToDo;
    }

    public void setNeedToDo(NeedToDo needToDo) {
        this.needToDo = needToDo;
    }

    public ChartModel getChartModel() {
        return chartModel;
    }

    public void setChartModel(ChartModel chartModel) {
        this.chartModel = chartModel;
    }

    public Home(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

}
