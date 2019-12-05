package com.dev.eda.app.receiver;

import android.content.Context;

import cn.jpush.android.service.WakedResultReceiver;

public class MyWakedResultReceiver extends WakedResultReceiver {
    public MyWakedResultReceiver() {
        super();
    }

    @Override
    public void onWake(int i) {
        super.onWake(i);
    }

    @Override
    public void onWake(Context context, int i) {
        super.onWake(context, i);
    }
}
