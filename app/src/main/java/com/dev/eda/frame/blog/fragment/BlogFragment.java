package com.dev.eda.frame.blog.fragment;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.dev.eda.R;
import com.dev.eda.app.base.BaseFragment;
import com.dev.eda.frame.blog.adapter.BlogAdapter;
import com.dev.eda.frame.blog.model.Blog;
import com.dev.eda.frame.blog.model.BlogContent;
import com.dev.eda.frame.blog.model.BlogImage;
import com.dev.eda.frame.home.listener.AppBarStateChangeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BlogFragment extends BaseFragment {

    private static BlogFragment mInstance;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.back_view)
    ImageView backView;
    @BindView(R.id.blog_xiangji)
    ImageView blogXiangji;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.blog_toolbar_layout)
    CollapsingToolbarLayout blogToolbarLayout;
    @BindView(R.id.blog_app_bar)
    AppBarLayout blogAppBar;

    private List<Blog> blogList;

    public static BlogFragment getInstance() {
        if (null == mInstance) {
            mInstance = new BlogFragment();
        }
        return mInstance;
    }


    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
//        if(this.getView() != null){
//            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
//        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ImmersionBar.with(this).init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_blog;
    }

    @Override
    protected void initData() {
        super.initData();
        blogList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Blog blog = new Blog();
            blog.setTitleImageResource(R.drawable.shanghai);
            blog.setName("昵称"+i);

            List<BlogContent> blogContents = new ArrayList<>();

            BlogContent blogContent = new BlogContent(BlogContent.itemType_message);
            blogContent.setContent("我是一段很长很长很长很长很长很长很长很长很长很长很长很长的内容");
            blogContents.add(blogContent);

            BlogContent blogContent1 = new BlogContent(BlogContent.itemType_images);
            List<BlogImage> blogImages = new ArrayList<>();
            ArrayList imagePaths = new ArrayList();
            ArrayList imageTitles = new ArrayList();
            for (int j = 0; j < 7; j++) {
                BlogImage image = new BlogImage();
                image.setImageResource(R.drawable.shanghai);
                image.setImagePosition(j);
                imagePaths.add("http://img4.duitang.com/uploads/item/201511/02/20151102130410_Mds2x.thumb.700_0.jpeg");
                imageTitles.add("图片描述");
                blogImages.add(image);
            }
            blogContent1.setImagePaths(imagePaths);
            blogContent1.setImageTitles(imageTitles);
            blogContent1.setImages(blogImages);
            blogContents.add(blogContent1);

            BlogContent blogContent2 = new BlogContent(BlogContent.itemType_more);
            blogContent2.setTimeAndAddress(i+"分钟前");
            blogContents.add(blogContent2);

            BlogContent blogContent3 = new BlogContent(BlogContent.itemType_good);
            blogContent3.setGoodText("❤雷佳音,hurricane");
//            blogContents.add(blogContent3);

            blog.setBlogContents(blogContents);
            blogList.add(blog);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        blogAppBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开状态
                    blogToolbarLayout.setContentScrimColor(Color.parseColor("#00000000"));
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    blogAppBar.setVisibility(View.VISIBLE);
                    blogToolbarLayout.setContentScrimColor(Color.parseColor("#99CCFF"));
                } else {
                    //中间状态
                    blogToolbarLayout.setContentScrimColor(Color.parseColor("#00000000"));
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new BlogAdapter(R.layout.item_blog_content,getContext(), blogList));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mInstance = null;
    }
}
