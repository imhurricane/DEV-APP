package com.dev.eda.app.service;

import android.content.ComponentCallbacks;

import java.io.FileDescriptor;
import java.io.PrintWriter;

import cn.jpush.android.service.JCommonService;

public class PushService extends JCommonService {

    public PushService() {
        super();
    }

    @Override
    public void registerComponentCallbacks(ComponentCallbacks callback) {
        super.registerComponentCallbacks(callback);
    }

    @Override
    public void unregisterComponentCallbacks(ComponentCallbacks callback) {
        super.unregisterComponentCallbacks(callback);
    }
}

