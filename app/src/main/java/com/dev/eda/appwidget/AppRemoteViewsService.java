package com.dev.eda.appwidget;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

/**
 * Created by LIUYONGKUI726 on 2017-07-10.
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AppRemoteViewsService extends android.widget.RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new AppRemoteViewsFactory(this.getApplicationContext(), intent);
    }

}