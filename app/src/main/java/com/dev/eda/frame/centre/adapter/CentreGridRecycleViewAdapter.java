package com.dev.eda.frame.centre.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dev.eda.R;
import com.dev.eda.frame.home.model.EntryModel;

import java.util.List;

public class CentreGridRecycleViewAdapter extends BaseQuickAdapter<EntryModel, BaseViewHolder> {


    public CentreGridRecycleViewAdapter(int layoutResId, @Nullable List<EntryModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, EntryModel item) {
        helper.setImageResource(R.id.item_centre_image,item.getImageResource());
        helper.setText(R.id.item_centre_text,item.getName());
    }
}
