package com.dev.eda.frame.blog.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dev.eda.R;
import com.dev.eda.app.utils.Logger;
import com.dev.eda.frame.blog.model.BlogContent;

import java.util.List;

public class BlogContentAdapter extends BaseMultiItemQuickAdapter<BlogContent, BaseViewHolder> {

    private Context mContext;

    public BlogContentAdapter(Context context,List<BlogContent> data) {
        super(data);
        addItemType(BlogContent.itemType_message, R.layout.item_blog_message);
        addItemType(BlogContent.itemType_images, R.layout.item_blog_image_recycle);
        addItemType(BlogContent.itemType_more, R.layout.item_blog_more);
        addItemType(BlogContent.itemType_good, R.layout.item_blog_good);
        mContext = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, BlogContent item) {
        switch (helper.getItemViewType()) {
            case BlogContent.itemType_message:
                helper.setText(R.id.item_blog_message, item.getContent());
                break;
            case BlogContent.itemType_images:
                RecyclerView view = helper.getView(R.id.item_blog_image_recycle);
                view.setLayoutManager(new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false));
                view.setAdapter(new BlogImageRecycleViewAdapter(R.layout.item_blog_image, item.getImages()));
                break;
            case BlogContent.itemType_more:
                helper.setText(R.id.item_blog_more_text, item.getTimeAndAddress());
                break;
            case BlogContent.itemType_good:
                helper.setText(R.id.item_blog_good_text, item.getGoodText());
                break;
            default:
                break;
        }
    }
}
