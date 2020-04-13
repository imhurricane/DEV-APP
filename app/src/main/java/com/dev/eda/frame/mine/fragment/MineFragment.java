package com.dev.eda.frame.mine.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dev.eda.R;
import com.dev.eda.app.base.BaseFragment;
import com.dev.eda.app.utils.AlertSetting;
import com.dev.eda.app.utils.CommonProgressDialog;
import com.kongzue.dialog.v3.MessageDialog;

import butterknife.BindView;

public class MineFragment extends BaseFragment {

    private static MineFragment mInstance;
    @BindView(R.id.image_title)
    ImageView imageTitle;
    @BindView(R.id.alert_layout)
    LinearLayout alertLayout;
    @BindView(R.id.download)
    LinearLayout download;
    CommonProgressDialog mDialog1;

    public static MineFragment getInstance() {
        if (null == mInstance) {
            mInstance = new MineFragment();
        }
        return mInstance;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
//        if(this.getView() != null){
//            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
//        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ImmersionBar.with(this).statusBarColor(R.color.lightDark).statusBarDarkFont(true).init();
        AlertSetting.init(mActivity);
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

            }
        });
        alertLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MessageDialog.build(mActivity)
                MessageDialog.show(mActivity, "提示", "这是一条消息", "确定");
            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog1 = new CommonProgressDialog(mActivity);
                mDialog1.setMessage("正在下载");
                mDialog1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mDialog1.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        // TODO Auto-generated method stub
                        //cancel(true);
                    }
                });
                mDialog1.show();
                mDialog1.setMax(100*1024*1024);
                mDialog1.setProgress(65*1024*1024);
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
