package com.dev.eda.frame.home.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dev.eda.R;
import com.dev.eda.app.base.BaseFragment;
import com.dev.eda.app.base.OwnApplication;
import com.dev.eda.app.camera.MyCamera;
import com.dev.eda.app.chart.ChartsTool;
import com.dev.eda.app.utils.Container;
import com.dev.eda.app.utils.Logger;
import com.dev.eda.frame.home.adapter.GlideImageLoader;
import com.dev.eda.frame.home.adapter.HomeAdapter;
import com.dev.eda.frame.home.data.DataSever;
import com.dev.eda.frame.home.listener.AppBarStateChangeListener;
import com.dev.eda.frame.home.model.Home;
import com.github.mikephil.charting.utils.Utils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

import static com.dev.eda.app.utils.Container.SCAN_CODE;

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
    @BindView(R.id.fragment_main_edit)
    TextView fragmentMainEdit;
    @BindView(R.id.saoyisao)
    ImageView saoyisao;
    @BindView(R.id.youjian)
    ImageView youjian;
    @BindView(R.id.home_fragment_refreshLayout)
    RefreshLayout refreshLayout;

    private List<Home> mHome = new ArrayList<>();
    private int mCurrentCounter;
    private int TOTAL_COUNTER;
    private HomeAdapter mHomeAdapter;

    public static HomeFragment getInstance() {
        if (null == mInstance) {
            mInstance = new HomeFragment();
        }
        return mInstance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.init(OwnApplication.getContext());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
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
        mHome = DataSever.getHomeMenu(getContext(),handler,false);
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mHomeAdapter.setNewData(mHome);
                default:
                    break;
            }
            mHomeAdapter.notifyDataSetChanged();
            return false;
        }
    });

    @Override
    protected void initView() {
        MergeRootFrame.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开状态
                    toolbarLayout.setContentScrimColor(Color.parseColor("#00000000"));
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    toolbarLayout.setContentScrimColor(Color.parseColor("#2689c6"));
                } else {
                    //中间状态
                    toolbarLayout.setContentScrimColor(Color.parseColor("#00000000"));
                }
            }
        });
        createGridManager();
    }

    private void createGridManager() {

        homeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHomeAdapter = new HomeAdapter(getContext(), mHome);
        mHomeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), ChartsTool.class));
            }
        });
        homeRecycler.setAdapter(mHomeAdapter);

    }

    @Override
    protected void setListener() {
        saoyisao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    Intent intent = new Intent(getActivity(), MyCamera.class);
                    startActivityForResult(intent, SCAN_CODE);
                }
            }
        });
        refreshLayout.setRefreshHeader(new ClassicsHeader(Objects.requireNonNull(getActivity())));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NotNull RefreshLayout refreshlayout) {
                mHome = DataSever.getHomeMenu(getContext(),handler,true);
                refreshlayout.finishRefresh();//传入false表示刷新失败
            }
        });

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
}
