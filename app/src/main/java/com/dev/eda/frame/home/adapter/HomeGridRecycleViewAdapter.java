package com.dev.eda.frame.home.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.dev.eda.R;
import com.dev.eda.app.image.RoundImageView;
import com.dev.eda.app.utils.HttpRequestUrl;
import com.dev.eda.frame.home.model.MenuMain;

import java.util.List;

public class HomeGridRecycleViewAdapter extends BaseQuickAdapter<MenuMain, BaseViewHolder> {

    private Context mContext;

    public HomeGridRecycleViewAdapter(int layoutResId, @Nullable List<MenuMain> data, Context context) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MenuMain item) {
//        helper.setImageResource(R.id.iv_grid,item.getIcon());
        ImageView view = helper.getView(R.id.iv_grid);
        Glide.with(mContext)
                .load(HttpRequestUrl.BaseURI+item.getIcon())
                .dontAnimate()
                .placeholder(R.drawable.shanghai)
                .into(view);

        view.setImageResource(R.drawable.shanghai);
        helper.setText(R.id.tv_grid, item.getTitle());

    }
}
