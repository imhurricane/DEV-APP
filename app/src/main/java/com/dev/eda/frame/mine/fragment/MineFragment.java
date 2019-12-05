package com.dev.eda.frame.mine.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.dev.eda.R;
import com.dev.eda.app.base.BaseFragment;
import com.gyf.immersionbar.ImmersionBar;

public class MineFragment extends BaseFragment {

    private static MineFragment mInstance;

    public static MineFragment getInstance() {
        if (null == mInstance){
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
        super.setListener();
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
