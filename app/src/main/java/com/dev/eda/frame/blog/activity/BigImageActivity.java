package com.dev.eda.frame.blog.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dev.eda.R;
import com.dev.eda.app.base.BaseActivity;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BigImageActivity extends BaseActivity {

    @BindView(R.id.vp_image)
    ViewPager vpImage;
//    @BindView(R.id.pv_image)
    PhotoView photoView;

    //接受Intent传递的参数
    private int position;
    private ArrayList<String> paths;//存放图片url的数组
    private ArrayList<String> title;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_big_image;
    }

    @Override
    public void initView() {
//        ButterKnife.bind(this);
//        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        //先接受传递的参数
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        paths = intent.getStringArrayListExtra("paths");
        title = intent.getStringArrayListExtra("title");

        vpImage.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return paths == null ? 0 : paths.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = LayoutInflater.from(BigImageActivity.this).inflate(R.layout.vp_style_big, null);
                photoView = (PhotoView) view.findViewById(R.id.pv_image);
                TextView tvTitle = view.findViewById(R.id.tv_title);
                TextView tvIndex = view.findViewById(R.id.tv_index);
                photoView.setBackgroundColor(getResources().getColor(R.color.Black));
                tvTitle.setText(title.get(position));
                tvIndex.setText((position + 1) + "/" + paths.size());
                final String url = paths.get(position);//图片的url
                Glide.with(BigImageActivity.this)
                        .load(paths.get(position))
                        .placeholder(R.drawable.shanghai)//加载等待图
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存模式
                        .into(photoView);
                container.addView(view);
                photoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(BigImageActivity.this,"clickIamge",Toast.LENGTH_SHORT).show();
                        BigImageActivity.this.finish();
                    }
                });
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        vpImage.setCurrentItem(position, true);
    }

    @Override
    public void initData() {

    }
}

