package com.dev.eda.frame.home.data;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.dev.eda.R;
import com.dev.eda.app.utils.HttpRequestUrl;
import com.dev.eda.app.http.callback.JsonCallback;
import com.dev.eda.frame.home.model.BannerModel;
import com.dev.eda.frame.home.model.ChartModel;
import com.dev.eda.frame.home.model.Home;
import com.dev.eda.frame.home.model.MenuMain;
import com.dev.eda.frame.home.model.NeedToDo;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.random;

public class DataSever {

    public static List<Home> getHomeMenu(Context context,Handler handler, boolean flag) {

        ArrayList<Home> mHome = new ArrayList<>();

        OkGo.<Home>get(HttpRequestUrl.LoadingMenuUrl)
                .tag(context)
                .retryCount(0)
                .execute(new JsonCallback<Home>() {
                    @Override
                    public void onSuccess(Response<Home> response) {
                        ArrayList<MenuMain> entryModels = response.body().getEntryModels();
                        Home homeBanner = new Home(Home.itemType_banner);
                        BannerModel bannerModel = new BannerModel();
                        ArrayList<String> titles = new ArrayList<>();
                        bannerModel.setTitle(titles);
                        titles.add("");
                        ArrayList<Integer> images = new ArrayList<>();
                        images.add(R.drawable.dc);
                        bannerModel.setImageResource(images);
                        homeBanner.setBannerModel(bannerModel);
                        mHome.add(homeBanner);

                        Home home0 = new Home(Home.itemType_grid);
                        home0.setEntryModels(entryModels);
                        mHome.add(home0);
                        Home home1 = new Home(Home.itemType_card);
                        NeedToDo needToDo = new NeedToDo();
                        needToDo.setResourceId(R.drawable.shanghai);
                        needToDo.setText("您有2条新消息");
                        home1.setNeedToDo(needToDo);
//                        mHome.add(home1);

                        Home home = new Home(Home.itemType_chart);
                        ChartModel chartModel = new ChartModel();
                        chartModel.setLabel("我是chart");

                        ArrayList<Entry> values = new ArrayList<>();
                        for (int j = 0; j < 40; j++) {
                            float v = (float) (random()) * (100 - 0 + 1) + 0;
                            values.add(new Entry(j, v));
                        }
                        Description description = new Description();
                        description.setText("我是description");
                        description.setTextSize(14);

                        chartModel.setChartData(values);
                        home.setChartModel(chartModel);
//                        mHome.add(home);

                        Home home4 = new Home(Home.itemType_chart_1);
                        ChartModel chartModel1 = new ChartModel();
                        chartModel1.setLabel("我是chart_1");
                        ArrayList<Entry> values1 = new ArrayList<>();

                        for (int j = 0; j < 20; j++) {
                            float v = new Random().nextFloat();
                            values1.add(new Entry(j, v));
                        }
                        chartModel1.setChartData(values1);
                        home4.setChartModel(chartModel1);
//                        mHome.add(home4);

//                        Home home3 = new Home(Home.itemType_footer);
//                        mHome.add(home3);
                        Message message = handler.obtainMessage();
                        if (flag){
                            message.what = 1;
                        }
                        message.obj = mHome;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onError(Response<Home> response) {
                        super.onError(response);
                    }
                });
        return mHome;
    }
}
