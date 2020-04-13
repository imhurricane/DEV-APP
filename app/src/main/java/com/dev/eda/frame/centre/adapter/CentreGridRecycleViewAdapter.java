package com.dev.eda.frame.centre.adapter;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dev.eda.R;
import com.dev.eda.frame.home.model.PluginModel;

import java.util.List;

public class CentreGridRecycleViewAdapter extends BaseQuickAdapter<PluginModel,BaseViewHolder> {


    public CentreGridRecycleViewAdapter(int layoutResId, @Nullable List<PluginModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, PluginModel item) {
        helper.setImageResource(R.id.item_centre_image,item.getImageResource());
        helper.setText(R.id.item_centre_text,item.getName());
    }
}
