package com.dev.eda.frame.view.spaceing;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private HashMap<String, Integer> spacingMap;
    private boolean includeEdge;

    public static String TOP_SPACE = "TOP_SPACE";
    public static String BUTTON_SPACE = "BUTTON_SPACE";
    public static String LEFT_SPACE = "LEFT_SPACE";
    public static String RIGHT_SPACE = "RIGHT_SPACE";

    public GridSpacingItemDecoration(int spanCount, HashMap<String, Integer> spacingMap) {
        this.spanCount = spanCount;
        this.spacingMap = spacingMap;
//        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(@NotNull Rect outRect, @NotNull View view, RecyclerView parent, @NotNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

//        if (includeEdge) {
            if (spacingMap.get(LEFT_SPACE) != null) {
                outRect.left = spacingMap.get(LEFT_SPACE) - column * spacingMap.get(LEFT_SPACE) / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            }
            if (spacingMap.get(RIGHT_SPACE) != null) {
                outRect.right = (column + 1) * spacingMap.get(RIGHT_SPACE) / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
            }

            if (spacingMap.get(TOP_SPACE) != null && position < spanCount) { // top edge
                outRect.top = spacingMap.get(TOP_SPACE);
            }
            if(spacingMap.get(BUTTON_SPACE) != null){
                outRect.bottom = spacingMap.get(BUTTON_SPACE); // item bottom
            }
//        } else {
//            if (spacingMap.get(LEFT_SPACE) != null) {
//                outRect.left = column * spacingMap.get(LEFT_SPACE) / spanCount; // column * ((1f / spanCount) * spacing)
//            }
//            if (spacingMap.get(RIGHT_SPACE) != null) {
//                outRect.right = spacingMap.get(RIGHT_SPACE) - (column + 1) * spacingMap.get(RIGHT_SPACE) / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
//            }
//            if (spacingMap.get(TOP_SPACE) != null && position >= spanCount) {
//                outRect.top = spacingMap.get(TOP_SPACE); // item top
//            }
//        }
    }



}