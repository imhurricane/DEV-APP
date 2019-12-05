package com.dev.eda.frame.home.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dev.eda.R;
import com.dev.eda.app.base.BaseFragment;
import com.dev.eda.app.camera.MyCamera;
import com.dev.eda.app.chart.ChartsTool;
import com.dev.eda.app.utils.Container;
import com.dev.eda.app.utils.Logger;
import com.dev.eda.frame.home.adapter.GlideImageLoader;
import com.dev.eda.frame.home.adapter.HomeAdapter;
import com.dev.eda.frame.home.listener.AppBarStateChangeListener;
import com.dev.eda.frame.home.model.ChartModel;
import com.dev.eda.frame.home.model.EntryModel;
import com.dev.eda.frame.home.model.Home;
import com.dev.eda.frame.home.model.NeedToDo;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

import static com.dev.eda.app.utils.Container.SCAN_CODE;
import static java.lang.Math.random;

public class HomeFragment extends BaseFragment {

    private static HomeFragment mInstance;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.MergeRootFrame)
    AppBarLayout MergeRootFrame;
    @BindView(R.id.home_recycler)
    RecyclerView homeRecycler;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.fragment_main_edit)
    TextView fragmentMainEdit;
    @BindView(R.id.saoyisao)
    ImageView saoyisao;
    @BindView(R.id.youjian)
    ImageView youjian;

    private List<Home> mHome = new ArrayList<>();

    private HomeFragment() {
    }

    public static HomeFragment getInstance() {
        if (null == mInstance) {
            mInstance = new HomeFragment();
        }
        return mInstance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
        mHome.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mInstance = null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        super.initData();
        initBanner();

        Home home0 = new Home(Home.itemType_grid);
        List<EntryModel> entryModels = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            EntryModel entryModel = new EntryModel();
            entryModel.setImageResource(R.drawable.shanghai);
            entryModel.setName("名称"+i);
            entryModels.add(entryModel);
        }
        home0.setEntryModels(entryModels);
        mHome.add(home0);

        Home home1 = new Home(Home.itemType_card);
        NeedToDo needToDo = new NeedToDo();
        needToDo.setResourceId(R.drawable.shanghai);
        needToDo.setText("您有2条新消息");
        home1.setNeedToDo(needToDo);
        mHome.add(home1);

        Home home = new Home(Home.itemType_chart);
        ChartModel chartModel = new ChartModel();
        chartModel.setLabel("我是chart");

        ArrayList<Entry> values = new ArrayList<>();
        for (int j = 0; j < 40; j++) {
            float v = (float)(random()) * (100 - 0 + 1) + 0;
            values.add(new Entry(j, v));
        }
        Description description = new Description();
        description.setText("我是description");
        description.setTextSize(14);
//        chartModel.setDescription(description);

        chartModel.setChartData(values);
        home.setChartModel(chartModel);
        mHome.add(home);

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
        mHome.add(home4);

        Home home3 = new Home(Home.itemType_footer);
        mHome.add(home3);
    }

    @Override
    protected void initView() {
        super.initView();
        MergeRootFrame.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开状态
                    toolbarLayout.setContentScrimColor(Color.parseColor("#00000000"));
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    toolbarLayout.setContentScrimColor(Color.parseColor("#99CCFF"));
                } else {
                    //中间状态
                    toolbarLayout.setContentScrimColor(Color.parseColor("#00000000"));
                }
            }
        });
        createGridManager();

    }

    private void createGridManager() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4, GridLayoutManager.VERTICAL, false);
        //设置item的分割线
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL);
//        homeRecycler.addItemDecoration(itemDecoration,20);

        homeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        HomeAdapter homeAdapter = new HomeAdapter(getContext(), mHome);
        homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Logger.d(mTag, "onItemClick: ");
                Toast.makeText(getActivity(), "onItemClick" + position, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), ChartsTool.class));
            }
        });
//        homeAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
//                return mHome.get(position).getSpanSize();
//            }
//        });
        homeRecycler.setAdapter(homeAdapter);

//        homeRecycler.setAdapter(new MyAdapter(mData));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String scanBarcodeResult = "";
        switch (requestCode) {
            case Container.SCAN_CODE:
                if (null != data) {
                    Bundle bundle = data.getExtras();
                    if (bundle == null) {
                        return;
                    }
                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        scanBarcodeResult = bundle.getString(CodeUtils.RESULT_STRING);
                        Toast.makeText(getContext(), scanBarcodeResult, Toast.LENGTH_LONG).show();
                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                        Toast.makeText(getContext(), "解析二维码失败", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void setListener() {
        saoyisao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    Intent intent = new Intent(getActivity(), MyCamera.class);
                    startActivityForResult(intent, SCAN_CODE);
                }
            }
        });
    }

    /**
     * 初始化轮播图
     */
    private void initBanner() {
        //资源文件
        Integer[] images = {R.drawable.img0, R.drawable.img1, R.drawable.img2, R.drawable.img3};
        String[] titles = {"我是img0", "我是img1", "我是img2", "我是img3"};
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(Arrays.asList(images));
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Accordion);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(Arrays.asList(titles));
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

}
