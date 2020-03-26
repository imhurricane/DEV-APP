package com.dev.eda.frame.blog.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dev.eda.R;
import com.dev.eda.app.base.OwnApplication;
import com.dev.eda.frame.blog.activity.BigImageActivity;
import com.dev.eda.frame.blog.model.BlogImage;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

public class BlogImageRecycleViewAdapter extends BaseQuickAdapter<BlogImage, BaseViewHolder> {

    private Context mContext;
    private ArrayList imagePaths;
    private ArrayList imageTitles;

    public BlogImageRecycleViewAdapter(Context context,int layoutResId, @Nullable List<BlogImage> data,ArrayList imagePaths,ArrayList imageTitles) {
        super(layoutResId, data);
        this.mContext = context;
        this.imagePaths = imagePaths;
        this.imageTitles = imageTitles;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, BlogImage item) {
        helper.setImageResource(R.id.item_blog_image, item.getImageResource());

        int itemId = (int) helper.getItemId();

        ImageView view = helper.getView(R.id.item_blog_image);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BigImageActivity.class);
                intent.putExtra("paths",imagePaths);
                intent.putExtra("title",imageTitles);
                intent.putExtra("position",item.getImagePosition());
                mContext.startActivity(intent);
            }
        });


    }

}
