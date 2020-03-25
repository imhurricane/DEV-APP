package com.dev.eda.frame.blog.PopupWindow;

public interface OnPraiseOrCommentClickListener {

    void onPraiseClick(int position);

    void onCommentClick(int position);

    void onClickFrendCircleTopBg();

    void onDeleteItem(String id, int position);
}
