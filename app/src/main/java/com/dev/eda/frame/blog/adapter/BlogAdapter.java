package com.dev.eda.frame.blog.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dev.eda.R;
import com.dev.eda.app.utils.Logger;
import com.dev.eda.app.utils.ViewUtils;
import com.dev.eda.frame.blog.PopupWindow.LikePopupWindow;
import com.dev.eda.frame.blog.PopupWindow.OnPraiseOrCommentClickListener;
import com.dev.eda.frame.blog.model.Blog;


import java.util.List;

public class BlogAdapter extends BaseQuickAdapter<Blog, BaseViewHolder> {

    private Context mContext;

    public BlogAdapter(int layoutId,Context context, @Nullable List<Blog> data) {
        super(layoutId,data);
        mContext = context;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void convert(@NonNull BaseViewHolder helper, Blog item) {

        helper.setText(R.id.user_name, item.getName());
        helper.setImageResource(R.id.user_title_image, item.getTitleImageResource());
        RecyclerView recyclerView = helper.getView(R.id.item_blog_recycle_view);
        LinearLayoutManager li = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(li);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        BlogContentAdapter blogContentAdapter = new BlogContentAdapter(mContext, item.getBlogContents());
        recyclerView.setAdapter(blogContentAdapter);
    }

}
