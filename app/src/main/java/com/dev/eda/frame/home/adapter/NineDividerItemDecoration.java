package com.dev.eda.frame.home.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dev.eda.app.utils.Logger;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class NineDividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private int spanColumnpan;// spanColumn 是几列
    private Drawable mDivider;
    private HashMap<String, Integer> spacingMap;
    public static String TOP_SPACE = "TOP_SPACE";
    public static String BUTTON_SPACE = "BUTTON_SPACE";
    public static String LEFT_SPACE = "LEFT_SPACE";
    public static String RIGHT_SPACE = "RIGHT_SPACE";

    public NineDividerItemDecoration(Context context, int spanColumn) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        this.spanColumnpan = spanColumn;
    }

    public NineDividerItemDecoration(Context context, int spanColumn, HashMap<String, Integer> spacingMap) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        this.spanColumnpan = spanColumn;
        this.spacingMap = spacingMap;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin
                    + mDivider.getIntrinsicWidth();
            int top = child.getBottom() + params.bottomMargin;
            if(spacingMap.containsKey(NineDividerItemDecoration.BUTTON_SPACE)){
                top += spacingMap.get(NineDividerItemDecoration.BUTTON_SPACE)/2;
            }
            final int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int top = child.getTop() - params.topMargin;
            if (spacingMap.containsKey(NineDividerItemDecoration.BUTTON_SPACE)){
                top -=  spacingMap.get(NineDividerItemDecoration.BUTTON_SPACE)/2;
            }
            int bottom = child.getBottom() + params.bottomMargin;
            if (spacingMap.containsKey(NineDividerItemDecoration.BUTTON_SPACE)){
                bottom +=  spacingMap.get(NineDividerItemDecoration.BUTTON_SPACE)/2;
            }
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicWidth();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    /**
     * 是否是最后一行
     */
    private boolean isLastRow(int itemPosition, RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        //有多少列
        if (layoutManager instanceof GridLayoutManager) {
            int childCount = parent.getAdapter().getItemCount();

            double count = Math.ceil((double) childCount / (double) spanCount);//总行数
            double currentCount = Math.ceil((double) (itemPosition + 1) / spanCount);//当前行数

            //最后当前数量小于总的
            if (currentCount < count) {
                return false;
            }
        }
        return true;
    }


    /**
     * 判断是否是最后一列
     */
    private boolean isLastColumn(int itemPosition, RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        //有多少列
        if (layoutManager instanceof GridLayoutManager) {
            int spanCount = getSpanCount(parent);
            if ((itemPosition + 1) % spanCount == 0) {//因为是从0可以所以要将ItemPosition先加1
                return true;
            }
        }
        return false;
    }

    // 控件的padding
    @Override
    public void getItemOffsets(@NotNull Rect outRect, @NotNull View view, RecyclerView parent, @NotNull RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanColumnpan; // item column
        if (spacingMap.get(LEFT_SPACE) != null) {
            outRect.left = spacingMap.get(LEFT_SPACE) - column * spacingMap.get(LEFT_SPACE) / spanColumnpan; // spacing - column * ((1f / spanCount) * spacing)
        }
        if (spacingMap.get(RIGHT_SPACE) != null) {
            outRect.right = (column + 1) * spacingMap.get(RIGHT_SPACE) / spanColumnpan; // (column + 1) * ((1f / spanCount) * spacing)
        }

        if (spacingMap.get(TOP_SPACE) != null && position < spanColumnpan) { // top edge
            outRect.top = spacingMap.get(TOP_SPACE);
        }
        if (spacingMap.get(BUTTON_SPACE) != null) {
            outRect.bottom = spacingMap.get(BUTTON_SPACE); // item bottom
        }
        if (isLastRow(parent.getChildLayoutPosition(view), parent))// 如果是最后一行，则不需要绘制底部
        {
//            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        }

        if (isLastColumn(parent.getChildLayoutPosition(view), parent))// 如果是最后一列，则不需要绘制右边
        {
//            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
}
