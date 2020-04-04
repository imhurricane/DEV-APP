package com.dev.eda.frame.centre.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dev.eda.R;
import com.dev.eda.frame.centre.model.Centre;

import java.util.List;

public class CentreAdapter extends BaseQuickAdapter<Centre,BaseViewHolder> {

    private Context mContext;
    private List<Centre> mData;

    public CentreAdapter(int layoutId, List<Centre> data, Context context) {
        super(layoutId,data);
        mContext = context;
        mData = data;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Centre item) {
        helper.setText(R.id.item_title_centre,item.getTitle());
        RecyclerView view = helper.getView(R.id.item_recycler_view_centre);
        view.setLayoutManager(new GridLayoutManager(mContext,4,GridLayoutManager.VERTICAL,false));
        view.setAdapter(new CentreGridRecycleViewAdapter(R.layout.item_grid_centre,item.getEntryModelList()));
    }
}
