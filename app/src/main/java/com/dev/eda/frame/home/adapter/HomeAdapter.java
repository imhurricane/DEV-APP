package com.dev.eda.frame.home.adapter;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dev.eda.R;
import com.dev.eda.app.chart.MyMarkerView;
import com.dev.eda.app.utils.ChartsTool;
import com.dev.eda.frame.home.model.Home;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;


public class HomeAdapter extends BaseMultiItemQuickAdapter<Home, BaseViewHolder> {

    private Context mContext;

    public HomeAdapter(Context context, List<Home> home) {
        super(home);
        mContext = context;
        addItemType(Home.itemType_grid, R.layout.item_grid_recycle);
        addItemType(Home.itemType_card, R.layout.item_card);
        addItemType(Home.itemType_chart, R.layout.item_chart);
        addItemType(Home.itemType_chart_1, R.layout.item_chart);
        addItemType(Home.itemType_footer, R.layout.item_footer);
    }

    @Override
    protected void convert(BaseViewHolder helper, Home item) {

        switch (helper.getItemViewType()) {
            case Home.itemType_grid:
                RecyclerView view = helper.getView(R.id.item_grid_recycle);
                view.setLayoutManager(new GridLayoutManager(mContext,4,GridLayoutManager.VERTICAL,false));
                view.setAdapter(new HomeGridRecycleViewAdapter(R.layout.item_grid,item.getEntryModels()));
                break;
            case Home.itemType_card:
                helper.setImageResource(R.id.iv_avatar, item.getNeedToDo().getResourceId());
                helper.setText(R.id.tv_name, item.getNeedToDo().getText());
                break;
            case Home.itemType_chart:
                LineChart chart = helper.getView(R.id.lineChart);
                ArrayList chartData = item.getChartModel().getChartData();
                String label = item.getChartModel().getLabel();
                Description description = item.getChartModel().getDescription();

                if (description == null) {
                    chart.getDescription().setEnabled(false);
                } else {
                    chart.setDescription(description);
                }

                if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
                    LineDataSet set = (LineDataSet) chart.getData().getDataSetByIndex(0);
                    set.setValues(chartData);
                    chart.getData().notifyDataChanged();
                    chart.notifyDataSetChanged();
                } else {
                    LineData lineData = ChartsTool.getChartsData(chartData, label);
                    chart.setData(lineData);
                }
                break;
            case Home.itemType_chart_1:
                LineChart chart_1 = helper.getView(R.id.lineChart);
                ArrayList chartData_1 = item.getChartModel().getChartData();
                String label_1 = item.getChartModel().getLabel();
                Description description_1 = item.getChartModel().getDescription();

                chart_1.setDrawGridBackground(false);
                chart_1.setTouchEnabled(false);
                chart_1.setDragEnabled(true);
                chart_1.setScaleEnabled(true);
                chart_1.setPinchZoom(true);
                if (description_1 == null) {
                    chart_1.getDescription().setEnabled(false);
                } else {
                    chart_1.setDescription(description_1);
                }
                MyMarkerView mv = new MyMarkerView(mContext, R.layout.custom_marker_view);
                mv.setChartView(chart_1);
                chart_1.setMarker(mv);
                XAxis xl = chart_1.getXAxis();
                xl.setAvoidFirstLastClipping(true);
                xl.setAxisMinimum(0f);

                YAxis leftAxis = chart_1.getAxisLeft();
                leftAxis.setInverted(true);
                leftAxis.setAxisMinimum(0f);

                YAxis rightAxis = chart_1.getAxisRight();
                rightAxis.setEnabled(false);

                if (chart_1.getData() != null && chart_1.getData().getDataSetCount() > 0) {
                    LineDataSet set = (LineDataSet) chart_1.getData().getDataSetByIndex(0);
                    set.setValues(chartData_1);
                    chart_1.getData().notifyDataChanged();
                    chart_1.notifyDataSetChanged();
                } else {
                    LineData lineData = ChartsTool.getChartsData_1(chartData_1, label_1);
                    chart_1.setData(lineData);
                }
                Legend l = chart_1.getLegend();
                l.setForm(Legend.LegendForm.LINE);
                chart_1.invalidate();
                break;
            case Home.itemType_footer:
//                        helper.setText()
                break;
            default:
                break;
        }


    }
}
