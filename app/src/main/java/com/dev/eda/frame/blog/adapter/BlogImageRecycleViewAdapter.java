package com.dev.eda.frame.blog.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dev.eda.R;
import com.dev.eda.app.utils.Logger;
import com.dev.eda.frame.blog.model.Blog;
import com.dev.eda.frame.blog.model.BlogImage;

import java.util.List;

public class BlogImageRecycleViewAdapter extends BaseQuickAdapter<BlogImage, BaseViewHolder> {


    public BlogImageRecycleViewAdapter(int layoutResId, @Nullable List<BlogImage> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, BlogImage item) {
        helper.setImageResource(R.id.item_blog_image,item.getImageResource());
    }

}
