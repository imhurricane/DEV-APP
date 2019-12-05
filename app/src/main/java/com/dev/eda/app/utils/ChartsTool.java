package com.dev.eda.app.utils;

import android.graphics.Color;

import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.util.ArrayList;
import java.util.Collections;

public class ChartsTool {

    public static LineData getChartsData(ArrayList values, String label) {

        LineDataSet set = new LineDataSet(values, label);
        set.setMode(LineDataSet.Mode.LINEAR);
        set.setLabel(label);
        set.setCubicIntensity(0.2f);
        set.setDrawFilled(true);
        set.setDrawCircles(false);
        set.setLineWidth(1.8f);
        set.setCircleRadius(4f);
        set.setCircleColor(Color.WHITE);
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setColor(Color.WHITE);
        set.setFillColor(Color.WHITE);
        set.setFillAlpha(100);
        set.setDrawHorizontalHighlightIndicator(false);
//        set1.setFillFormatter(new IFillFormatter() {
//            @Override
//            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
//                return chart.getAxisLeft().getAxisMinimum();
//            }
//        });

        // create a data object with the data sets
        LineData data = new LineData(set);
//                    data.setValueTypeface(tfLight);
        data.setValueTextSize(9f);
        data.setDrawValues(false);
        return data;
    }

    public static LineData getChartsData_1(ArrayList values, String label) {

        Collections.sort(values, new EntryXComparator());
        LineDataSet set = new LineDataSet(values, "DataSet 1");
        set.setLineWidth(1.5f);
        set.setCircleRadius(4f);
        set.setLabel(label);
        LineData data = new LineData(set);
        return data;
    }


}
