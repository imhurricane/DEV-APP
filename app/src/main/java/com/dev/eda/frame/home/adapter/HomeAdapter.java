package com.dev.eda.frame.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dev.eda.R;
import com.dev.eda.app.chart.MyMarkerView;
import com.dev.eda.app.utils.ChartsTool;
import com.dev.eda.app.utils.Logger;
import com.dev.eda.frame.home.model.BannerModel;
import com.dev.eda.frame.home.model.Home;
import com.dev.eda.frame.home.model.MenuMain;
import com.dev.eda.frame.view.activity.ListViewActivity;
import com.dev.eda.frame.view.spaceing.GridSpacingItemDecoration;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class HomeAdapter extends BaseMultiItemQuickAdapter<Home, BaseViewHolder> {

    private Context mContext;
    private Banner mBanner;
    public HomeAdapter(Context context, List<Home> home) {
        super(home);
        mContext = context;
        addItemType(Home.itemType_banner, R.layout.item_grid_banner);
        addItemType(Home.itemType_grid, R.layout.item_grid_recycle);
        addItemType(Home.itemType_card, R.layout.item_card);
        addItemType(Home.itemType_chart, R.layout.item_chart);
        addItemType(Home.itemType_chart_1, R.layout.item_chart);
//        addItemType(Home.itemType_footer, R.layout.item_footer);
    }

    @Override
    protected void convert(BaseViewHolder helper, Home item) {

        switch (helper.getItemViewType()) {
            case Home.itemType_banner:
                mBanner = helper.getView(R.id.banner);
                BannerModel bannerModel = item.getBannerModel();
                initBanner(bannerModel.getImageResource(),bannerModel.getTitle());
//                banner.start();
                break;
            case Home.itemType_grid:
                RecyclerView view = helper.getView(R.id.item_grid_recycle);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false);

                HashMap<String,Integer> map = new HashMap<>();
//                map.put(GridSpacingItemDecoration.LEFT_SPACE,50);
//                map.put(GridSpacingItemDecoration.RIGHT_SPACE,50);
                map.put(GridSpacingItemDecoration.BUTTON_SPACE,50);
//                map.put(GridSpacingItemDecoration.TOP_SPACE,50);

                NineDividerItemDecoration nineDividerItemDecoration = new NineDividerItemDecoration(mContext, 4,map);
                if (view.getItemDecorationCount() == 0) {
                    view.addItemDecoration(nineDividerItemDecoration);
                }

                view.setNestedScrollingEnabled(false);//禁用滑动事件
                view.setLayoutManager(gridLayoutManager);

                HomeGridRecycleViewAdapter homeGridRecycleViewAdapter = new HomeGridRecycleViewAdapter(R.layout.item_grid, item.getEntryModels(), mContext);

                homeGridRecycleViewAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                        Log.e("position",position+"");
//                        PluginModel entryModel = item.getEntryModels().get(position);
//                        Log.e("entryModel.getApkName",entryModel.getApkName());
//                        LoadPluginApkUtils.checkPluginApkVersion(mContext,entryModel.getSdCardPath(),entryModel.getApkName(),entryModel.getApkVersionKey(),entryModel.getPackageName(),entryModel.getActivityName());
                        MenuMain menuMain = item.getEntryModels().get(position);
                        Intent intent = new Intent(mContext, ListViewActivity.class);
                        intent.putExtra("toPage", menuMain.getToPage());
                        mContext.startActivity(intent);
                    }
                });
                homeGridRecycleViewAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

                        return false;
                    }
                });
                view.setAdapter(homeGridRecycleViewAdapter);
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
                chart.setDrawGridBackground(false);
                chart.setTouchEnabled(false);
                chart.setDragEnabled(true);
                chart.setScaleEnabled(true);
                chart.setPinchZoom(true);
                if (description == null) {
                    chart.getDescription().setEnabled(false);
                } else {
                    chart.setDescription(description);
                }
                MyMarkerView mv1 = new MyMarkerView(mContext, R.layout.custom_marker_view);
                mv1.setChartView(chart);
                chart.setMarker(mv1);

                if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
                    LineDataSet set = (LineDataSet) chart.getData().getDataSetByIndex(0);
                    set.setValues(chartData);
                    chart.getData().notifyDataChanged();
                    chart.notifyDataSetChanged();
                } else {
                    LineData lineData = ChartsTool.getChartsData(chartData, label);
                    chart.setData(lineData);
                }
                Legend l1 = chart.getLegend();
                l1.setForm(Legend.LegendForm.LINE);
                chart.invalidate();
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
//            case Home.itemType_footer:
//                        helper.setText()
//                break;
            default:
                break;
        }


    }

    /**
     * 初始化轮播图
     */
    private void initBanner(List<Integer> images,List<String> titles) {
        //资源文件
//        Integer[] images = {R.drawable.dc};
//        String[] titles = {""};
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(images);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Accordion);
        //设置标题集合（当banner样式有显示title时）
        mBanner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }
}
