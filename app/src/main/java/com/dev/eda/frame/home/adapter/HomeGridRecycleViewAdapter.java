package com.dev.eda.frame.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dev.eda.R;
import com.dev.eda.app.base.OwnApplication;
import com.dev.eda.app.utils.WifiDisPluginUtils;
import com.dev.eda.app.wifidis.utils.WifiDisUtil;
import com.dev.eda.frame.home.model.EntryModel;

import java.util.List;

public class HomeGridRecycleViewAdapter extends BaseQuickAdapter<EntryModel,BaseViewHolder> {

    private Context mContext;

    public HomeGridRecycleViewAdapter(int layoutResId, @Nullable List<EntryModel> data, Context context) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, EntryModel item) {
        helper.setImageResource(R.id.iv_grid,item.getImageResource());
        helper.setText(R.id.tv_grid,item.getName());

    }
}
