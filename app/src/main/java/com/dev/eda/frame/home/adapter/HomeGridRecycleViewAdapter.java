package com.dev.eda.frame.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dev.eda.R;
import com.dev.eda.frame.home.model.PluginModel;

import java.util.List;

public class HomeGridRecycleViewAdapter extends BaseQuickAdapter<PluginModel,BaseViewHolder> {

    private Context mContext;

    public HomeGridRecycleViewAdapter(int layoutResId, @Nullable List<PluginModel> data, Context context) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, PluginModel item) {
        helper.setImageResource(R.id.iv_grid,item.getImageResource());
        helper.setText(R.id.tv_grid,item.getName());

    }
}
