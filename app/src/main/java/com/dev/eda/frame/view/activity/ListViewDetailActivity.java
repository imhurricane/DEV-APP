package com.dev.eda.frame.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dev.eda.R;
import com.dev.eda.app.base.BaseActivity;
import com.dev.eda.app.utils.AppTool;
import com.dev.eda.app.utils.DeviceUtils;
import com.dev.eda.app.utils.Logger;
import com.dev.eda.frame.view.adapter.ListViewDetailAdapter;
import com.dev.eda.frame.view.data.ListViewDataSever;
import com.dev.eda.frame.view.model.ItemDetailCheckSelect;
import com.dev.eda.frame.view.model.ItemListViewDetail;
import com.dev.eda.frame.view.utils.DateUtils;
import com.kongzue.dialog.v3.MessageDialog;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class ListViewDetailActivity extends BaseActivity {

    @BindView(R.id.detail_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.list_view_detail_refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.list_view_detail_title)
    TextView textView;
    @BindView(R.id.save_btn)
    Button saveBtn;
    @BindView(R.id.back_btn)
    ImageView imageView;

    private List<ItemListViewDetail> mData = new ArrayList<>();
    private ListViewDetailAdapter mViewAdapter;
    private String mDetailPageId;
    private String Id;

    @Override
    protected int getLayoutId() {
        return R.layout.list_view_detail_recycle;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }

        return super.dispatchTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mViewAdapter = new ListViewDetailAdapter(ListViewDetailActivity.this, mData);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this,R.drawable.recycle_drivider));
        recyclerView.addItemDecoration(dividerItemDecoration);
//        DividerItemDecoration mDivider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
//        mViewAdapter.addItemDecoration(mDivider);
        recyclerView.setAdapter(mViewAdapter);
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            mData = (List<ItemListViewDetail>) msg.obj;
            if (mData.size() == 0){
                ItemListViewDetail itemListViewDetail = new ItemListViewDetail(ItemListViewDetail.item_type_default);
                mData.add(itemListViewDetail);
            }else{
                textView.setText(mData.get(0).getPageTitle());
            }

            mViewAdapter.setNewData(mData);
//            mViewAdapter.notifyDataSetChanged();
            return false;
        }
    });

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mDetailPageId = intent.getStringExtra("detailPageId");
        Id = intent.getStringExtra("id");
        ListViewDataSever.getListDetailData(ListViewDetailActivity.this,handler,mDetailPageId,Id);
    }

    @Override
    protected void setListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                ListViewDataSever.getListDetailData(ListViewDetailActivity.this,handler,mDetailPageId,Id);
                refreshlayout.finishRefresh();//传入false表示刷新失败
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                HashMap<String,String> map = new HashMap<>();
                HashMap<String,String> postMap = new HashMap<>();
                List<ItemListViewDetail> data = mViewAdapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    String columnName = data.get(i).getColumnName();
                    String columnValue = data.get(i).getColumnValue();
                    int formatCode = data.get(i).getFormatCode();
                    if(data.get(i).getItemType()==ItemListViewDetail.item_type_checkselect){
                        ArrayList<ItemDetailCheckSelect> selects = data.get(i).getSelects();
                        Logger.e("itemDetailCheckSelect",selects.toString());
                        for (int j = 0; j < selects.size(); j++) {
                            ItemDetailCheckSelect itemDetailCheckSelect = selects.get(j);
                            if(itemDetailCheckSelect.isChecked()){
                                columnValue = itemDetailCheckSelect.getDesc();
                            }
                        }
                    }
                    if(formatCode == DateUtils.formatStr_HHmm || formatCode == DateUtils.formatStr_HHmmss){
                        columnName = "_TIMEPART_"+columnName;
                    }
                    map.put(columnName,columnValue);
                }
                postMap.put("postpara",JSON.toJSONString(map));
                postMap.put("xtm",Id);
                postMap.put("yhxtm", AppTool.getLoginUserInfo().getYhxtm());
                postMap.put("devicextm",AppTool.getLoginUserInfo().getDevicextm());
                postMap.put("detailpageid",mDetailPageId);
                postMap.put("netConnectWay","0");
                postMap.put("plat","android");
                postMap.put("screenwidth","360");
                postMap.put("screenheight","720");
                postMap.put("imei", DeviceUtils.getImei(ListViewDetailActivity.this));
                postMap.put("appversion",AppTool.getAppVersionName(ListViewDetailActivity.this));
                ListViewDataSever.saveItemDetail(ListViewDetailActivity.this,postMap);
                Logger.e("mData",mViewAdapter.getData().toString());
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListViewDetailActivity.this.finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        MessageDialog.unload();
    }
}
