package com.dev.eda.frame.blog.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dev.eda.R;
import com.dev.eda.app.utils.Logger;
import com.dev.eda.app.utils.ViewUtils;
import com.dev.eda.frame.blog.PopupWindow.LikePopupWindow;
import com.dev.eda.frame.blog.PopupWindow.OnPraiseOrCommentClickListener;
import com.dev.eda.frame.blog.model.BlogContent;

import java.util.List;

public class BlogContentAdapter extends BaseMultiItemQuickAdapter<BlogContent, BaseViewHolder> {

    private Context mContext;
    private LikePopupWindow likePopupWindow;
    private LinearLayout llComment;
    private EditText etComment;
    private TextView tvSend;
    private LinearLayout llScroll;
    private int currentKeyboardH;
    private int screenHeight;
    private int editTextBodyHeight;

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
                ImageView view1 = helper.getView(R.id.item_blog_popup);
                llComment = helper.getView(R.id.ll_comment);
                etComment = helper.getView(R.id.et_comment);
                tvSend = helper.getView(R.id.tv_send_comment);
                llScroll = helper.getView(R.id.ll_scroll);
                view1.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (llComment.getVisibility() == View.VISIBLE) {
                            updateEditTextBodyVisible(View.GONE);
                            return true;
                        }
                        return false;
                    }
                });
                tvSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if (TextUtils.isEmpty(etComment.getText().toString())) {
//                            Toast.makeText(mContext, "请输入评论内容", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
                        setViewTreeObserver();
//                        updateEditTextBodyVisible(View.GONE);
                    }
                });
                view1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int itemBottomY = getCoordinateY(v) + v.getHeight();//item 底部y坐标
                        if (likePopupWindow == null) {
                            likePopupWindow = new LikePopupWindow(mContext, 0);
                        }
                        likePopupWindow.setOnPraiseOrCommentClickListener(new OnPraiseOrCommentClickListener() {
                            @Override
                            public void onPraiseClick(int position) {
                                Toast.makeText(mContext, "点赞成功", Toast.LENGTH_SHORT).show();
                                likePopupWindow.dismiss();
                            }

                            @Override
                            public void onCommentClick(int position) {
                                llComment.setVisibility(View.VISIBLE);
                                etComment.requestFocus();
                                ViewUtils.showSoftInput(mContext, llComment);
                                likePopupWindow.dismiss();
                                etComment.setHint("说点什么");
                                etComment.setText("");

//                                v.postDelayed(() -> {
//                                    int y = getCoordinateY(llComment);
////                                    //评论时滑动到对应item底部和输入框顶部对齐
//                                    v.smoothScrollBy(0,itemBottomY-y);
//                                }, 300);

                            }

                            @Override
                            public void onClickFrendCircleTopBg() {

                            }

                            @Override
                            public void onDeleteItem(String id, int position) {

                            }
                        }).setTextView(0).setCurrentPosition(helper.getLayoutPosition());
                        if (likePopupWindow.isShowing()) {
                            likePopupWindow.dismiss();
                        } else {
                            likePopupWindow.showPopupWindow(v);
                        }
                    }
                });
                break;
            case BlogContent.itemType_good:
                helper.setText(R.id.item_blog_good_text, item.getGoodText());
                break;
            default:
                break;
        }
    }

    public void updateEditTextBodyVisible(int visibility) {
        llComment.setVisibility(visibility);
        if (View.VISIBLE == visibility) {
            llComment.requestFocus();
            //弹出键盘
            ViewUtils.showSoftInput(mContext, etComment);
        } else if (View.GONE == visibility) {
            //隐藏键盘
            ViewUtils.hideSoftInput(mContext, etComment);
        }
    }

    private int getCoordinateY(View view) {
        int[] coordinate = new int[2];
        view.getLocationOnScreen(coordinate);
        return coordinate[1];
    }

    private void setViewTreeObserver() {
        final ViewTreeObserver swipeRefreshLayoutVTO = llScroll.getViewTreeObserver();
        swipeRefreshLayoutVTO.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                llScroll.getWindowVisibleDisplayFrame(r);
                int statusBarH = ViewUtils.getStatusBarHeight();//状态栏高度
                int screenH = llScroll.getRootView().getHeight();
                if (r.top != statusBarH) {
                    //在这个demo中r.top代表的是状态栏高度，在沉浸式状态栏时r.top＝0，通过getStatusBarHeight获取状态栏高度
                    r.top = statusBarH;
                }
                int keyboardH = screenH - (r.bottom - r.top);
                Logger.e("BlogAdapter", "screenH＝ " + screenH + " &keyboardH = " + keyboardH + " &r.bottom=" + r.bottom + " &top=" + r.top + " &statusBarH=" + statusBarH);

                if (keyboardH == currentKeyboardH) {//有变化时才处理，否则会陷入死循环
                    return;
                }
                currentKeyboardH = keyboardH;
                screenHeight = screenH;//应用屏幕的高度
                editTextBodyHeight = llComment.getHeight();
                if (keyboardH < 170) {//说明是隐藏键盘的情况
                    updateEditTextBodyVisible(View.GONE);
                    return;
                }
            }
        });
    }
}
