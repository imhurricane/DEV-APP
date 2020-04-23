package com.dev.eda.frame.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dev.eda.R;
import com.dev.eda.app.base.BaseActivity;
import com.dev.eda.app.utils.Logger;
import com.dev.eda.frame.view.adapter.ListViewAdapter;
import com.dev.eda.frame.view.data.ListViewDataSever;
import com.dev.eda.frame.view.model.ItemListView;
import com.dev.eda.frame.view.model.PageInfo;
import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ListViewActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.list_view_refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.list_view_title)
    TextView textView;
    @BindView(R.id.back_btn)
    ImageView imageView;

    private String mToPage;
    private List<ItemListView> mData = new ArrayList<>();
    private ListViewAdapter mViewAdapter;
    private final int pageSize = 10;
    private int currentPage = 1;
    private PageInfo mPageInfo;


    @Override
    protected int getLayoutId() {
        return R.layout.list_view_recycle;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mViewAdapter = new ListViewAdapter(ListViewActivity.this, mData);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this,R.drawable.recycle_drivider));
//        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(mViewAdapter);
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            mData = (List<ItemListView>) msg.obj;
            if (mData.size() == 0){
                ItemListView itemListView = new ItemListView(ItemListView.item_type_default);
                mData.add(itemListView);
            }else{
                textView.setText(mData.get(0).getListTitle());
            }
            switch (msg.what){
                case 1:
                    mViewAdapter.setNewData(mData);
                    break;
                case 2:
                    if(mData.size() > 0){
                        currentPage++;
                    }
                    mViewAdapter.addData(mData);
                    break;
                default:
                    break;
            }
            mViewAdapter.notifyDataSetChanged();
            return false;
        }
    });


    @Override
    protected void initData() {
        mPageInfo = new PageInfo();
        mPageInfo.setPageSize(pageSize);
        mPageInfo.setPageNumber(currentPage);
        Intent intent = getIntent();
        mToPage = intent.getStringExtra("toPage");
        ListViewDataSever.getListData(ListViewActivity.this,mToPage,handler,mPageInfo,false);
    }

    @Override
    protected void setListener() {

        mViewAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NotNull RefreshLayout refreshlayout) {
                currentPage = 1;
                mPageInfo.setPageNumber(currentPage);
                ListViewDataSever.getListData(ListViewActivity.this,mToPage,handler,mPageInfo,false);
                refreshlayout.finishRefresh();//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NotNull RefreshLayout refreshlayout) {
                mPageInfo.setPageNumber(currentPage+1);
                ListViewDataSever.getListData(ListViewActivity.this,mToPage,handler,mPageInfo,true);
                refreshlayout.finishLoadMore();//传入false表示加载失败
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListViewActivity.this.finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
