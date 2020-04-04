package com.dev.eda.frame.root.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.eda.R;
import com.dev.eda.app.base.BaseActivity;
import com.dev.eda.app.receiver.LocalBroadcastManager;
import com.dev.eda.app.utils.AppTool;
import com.dev.eda.app.utils.ExampleUtil;
import com.dev.eda.app.utils.Logger;
import com.dev.eda.frame.login.activity.LoginActivity1;
import com.dev.eda.frame.root.adapter.TabFragmentPagerAdapter;


import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseActivity {

    @BindView(R.id.vp_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout_view)
    TabLayout tabLayout;
    ProgressDialog progressDialog;
    //未选中的Tab图片
    private int[] unSelectTabRes = new int[]{R.drawable.sy_01, R.drawable.fl_01, R.drawable.bk_01, R.drawable.mine_01};
    //选中的Tab图片
    private int[] selectTabRes = new int[]{R.drawable.sy_02, R.drawable.fl_02, R.drawable.bk_02, R.drawable.mine_02};
    //Tab标题
    private String[] title = new String[]{"首页", "分类", "博客", "我的"};
    public static boolean isForeground = false;
    private Handler myHandler = new Handler();
    private Runnable mLoadingRunnable = new Runnable() {
        @Override
        public void run() {
            //更新UI线程
            registerMessageReceiver();  // used for receive msg
            if (lacksPermissions(PERMISSIONS)) {            // 开始请求权限
                requestPermissions(PERMISSIONS);
            }

            boolean login = AppTool.checkLogin();
            if (!login) {
                Intent intent = new Intent(MainActivity.this, LoginActivity1.class);
//            startActivity(intent);
//            MainActivity.this.finish();
            }
        }
    };

    /**
     * 请求权限请求码
     **/
    public static final int REQUEST_PERMISSION_CODE = 111;

    /**
     * 权限
     **/
    public static final String[] PERMISSIONS =
            new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                myHandler.post(mLoadingRunnable);
            }
        });


    }


    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initView() {
        super.initView();
        //使用适配器将ViewPager与Fragment绑定在一起
        viewPager.setAdapter(new TabFragmentPagerAdapter(getSupportFragmentManager(), title));
        //将TabLayout与ViewPager绑定
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setCustomView(getTabView(i));
        }

    }

    @Override
    protected void setListener() {
        super.setListener();
        initListener();
    }

    private void initListener() {
        //TabLayout切换时导航栏图片处理
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {//选中图片操作
                ImageView iv = tab.getCustomView().findViewById(R.id.tabicon);
                TextView tv = tab.getCustomView().findViewById(R.id.tabtext);
                switch (tab.getPosition()) {
                    case 0:
                        iv.setImageResource(selectTabRes[0]);
                        break;
                    case 1:
                        iv.setImageResource(selectTabRes[1]);
                        break;
                    case 2:
                        iv.setImageResource(selectTabRes[2]);
                        break;
                    case 3:
                        iv.setImageResource(selectTabRes[3]);
                        break;
                }
                tv.setTextColor(Color.parseColor("#3ca0ec"));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {//未选中图片操作
                ImageView iv = tab.getCustomView().findViewById(R.id.tabicon);
                TextView tv = tab.getCustomView().findViewById(R.id.tabtext);
                switch (tab.getPosition()) {
                    case 0:
                        iv.setImageResource(unSelectTabRes[0]);
                        break;
                    case 1:
                        iv.setImageResource(unSelectTabRes[1]);
                        break;
                    case 2:
                        iv.setImageResource(unSelectTabRes[2]);
                        break;
                    case 3:
                        iv.setImageResource(unSelectTabRes[3]);
                        break;
                }
                tv.setTextColor(Color.parseColor("#000000"));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public View getTabView(int position) {
        View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.icon_view, null);
        ImageView iv = v.findViewById(R.id.tabicon);
        TextView tv = v.findViewById(R.id.tabtext);
        tv.setText(title[position]);
        if (position == 0) {
            iv.setImageResource(selectTabRes[position]);
            tv.setTextColor(Color.parseColor("#3ca0ec"));
        } else {
            iv.setImageResource(unSelectTabRes[position]);
            tv.setTextColor(Color.parseColor("#000000"));
        }
        return v;
    }

    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void init() {
        JPushInterface.init(getApplicationContext());
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String message = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + message + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    setCostomMsg(showMsg.toString());
                }
            } catch (Exception e) {
            }
        }
    }

    private void setCostomMsg(String message) {
        Logger.e("message", message);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CODE) {

        }
    }

    /**
     * 判断是否缺少权限
     **/
    private boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) == PackageManager.PERMISSION_DENIED) {
                return true;
            }
        }
        return false;
    }


    /**
     * 请求权限
     **/
    private void requestPermissions(String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION_CODE);
    }



}
