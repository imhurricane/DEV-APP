package com.dev.eda.frame.home.model;

import com.github.mikephil.charting.components.Description;

import java.util.ArrayList;

public class ChartModel {

    private ArrayList chartData;

    private String label;

    private Description description;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public ArrayList getChartData() {
        return chartData;
    }

    public void setChartData(ArrayList chartData) {
        this.chartData = chartData;
    }


}
