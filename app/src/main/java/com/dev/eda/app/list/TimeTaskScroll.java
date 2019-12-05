package com.dev.eda.app.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import java.util.TimerTask;

public class TimeTaskScroll extends TimerTask {

    private ListView listView;
    public TimeTaskScroll(Context context, ListView listView) {
        this.listView = listView;
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            //  控制速度
            listView.smoothScrollBy(5, 0);
        }
    };
    @Override
    public void run() {
        Message msg = handler.obtainMessage();
        handler.sendMessage(msg);
    }
}
