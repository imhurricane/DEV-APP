package com.dev.eda.app.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import java.util.Stack;

public class AppManager {

    private Stack<AppCompatActivity> mActivities = new Stack<>();

    private static class Holder {
        private static final AppManager INSTANCE = new AppManager();
    }

    public static AppManager getInstance() {
        return Holder.INSTANCE;
    }

    public void addActivity(AppCompatActivity activity) {
        mActivities.add(activity);
    }

    public void removeActivity(AppCompatActivity activity) {
        hideSoftKeyBoard(activity);
        activity.finish();
        mActivities.remove(activity);
    }

    public void removeAllActivity() {
        for (AppCompatActivity activity : mActivities) {
            hideSoftKeyBoard(activity);
            activity.finish();
        }
        mActivities.clear();
    }

    public <T extends AppCompatActivity> boolean hasActivity(Class<T> tClass) {
        for (AppCompatActivity activity : mActivities) {
            if (tClass.getName().equals(activity.getClass().getName())) {
                return !activity.isDestroyed() || !activity.isFinishing();
            }
        }
        return false;
    }

    public void hideSoftKeyBoard(AppCompatActivity activity) {
        View localView = activity.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (localView != null && imm != null) {
            imm.hideSoftInputFromWindow(localView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
