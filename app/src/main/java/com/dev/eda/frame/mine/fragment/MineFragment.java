package com.dev.eda.frame.mine.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dev.eda.R;
import com.dev.eda.app.base.BaseFragment;
import com.dev.eda.app.base.OwnApplication;
import com.dev.eda.app.utils.WifiDisPluginUtils;
import com.dev.eda.frame.root.activity.MainActivity;

import butterknife.BindView;

public class MineFragment extends BaseFragment {

    private static MineFragment mInstance;
    @BindView(R.id.image_title)
    ImageView imageTitle;

    public static MineFragment getInstance() {
        if (null == mInstance) {
            mInstance = new MineFragment();
        }
        return mInstance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ImmersionBar.with(this).statusBarColor(R.color.lightDark).statusBarDarkFont(true).init();

    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void setListener() {
        imageTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WifiDisPluginUtils.showPluginActivity(OwnApplication.getContext());
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mInstance = null;
    }
}
