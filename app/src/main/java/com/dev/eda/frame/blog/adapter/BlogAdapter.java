package com.dev.eda.frame.blog.adapter;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.dev.eda.R;
import com.dev.eda.frame.blog.model.Blog;


import java.util.List;

public class BlogAdapter extends BaseQuickAdapter<Blog, BaseViewHolder> {

    private Context mContext;

    public BlogAdapter(int layoutResId, Context context, @Nullable List<Blog> data) {
        super(layoutResId,data);
        mContext = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Blog item) {
        helper.setText(R.id.user_name, item.getName());
        helper.setImageResource(R.id.user_title_image, item.getTitleImageResource());
        RecyclerView view = helper.getView(R.id.item_blog_recycle_view);
        view.setLayoutManager(new LinearLayoutManager(mContext));
        view.setAdapter(new BlogContentAdapter(mContext,item.getBlogContents()));
    }
}
