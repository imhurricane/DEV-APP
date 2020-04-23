package com.dev.eda.frame.home.model;


import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

public class Home implements MultiItemEntity {

    public static final int itemType_banner = 0;
    public static final int itemType_grid = 1;
    public static final int itemType_card = 2;
    public static final int itemType_chart = 3;
    public static final int itemType_chart_1 = 4;
//    public static final int itemType_footer = 5;

    private int itemType;

    private BannerModel bannerModel;

    private ArrayList<MenuMain> entryModels;

    private NeedToDo needToDo;

    private ChartModel chartModel;

    public ArrayList<MenuMain> getEntryModels() {
        return entryModels;
    }

    public void setEntryModels(ArrayList<MenuMain> entryModels) {
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

    public BannerModel getBannerModel() {
        return bannerModel;
    }

    public void setBannerModel(BannerModel bannerModel) {
        this.bannerModel = bannerModel;
    }

    public Home(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    @Override
    public String toString() {
        return "Home{" +
                "itemType=" + itemType +
                ", entryModels=" + entryModels +
                ", needToDo=" + needToDo +
                ", chartModel=" + chartModel +
                ", bannerModel=" + bannerModel +
                '}';
    }
}
