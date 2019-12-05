package com.dev.eda.frame.home.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dev.eda.R;
import com.dev.eda.frame.home.model.EntryModel;

import java.util.List;

public class HomeGridRecycleViewAdapter extends BaseQuickAdapter<EntryModel, BaseViewHolder> {


    public HomeGridRecycleViewAdapter(int layoutResId, @Nullable List<EntryModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, EntryModel item) {
        helper.setImageResource(R.id.iv_grid,item.getImageResource());
        helper.setText(R.id.tv_grid,item.getName());
    }
}
