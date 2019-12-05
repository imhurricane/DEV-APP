package com.dev.eda.appwidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.dev.eda.R;
import com.dev.eda.app.utils.Logger;
import com.dev.eda.appwidget.model.WidgetModel;

import java.util.ArrayList;
import java.util.List;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AppRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    public static List<WidgetModel> mList = new ArrayList();

    /*
     * 构造函数
     */
    public AppRemoteViewsFactory(Context context, Intent intent) {

        mContext = context;
    }

    /*
     * RemoteViewsFactory调用时执行，这个方法执行时间超过20秒回报错。
     * 如果耗时长的任务应该在onDataSetChanged或者getViewAt中处理
     */
    @Override
    public void onCreate() {
        // 需要显示的数据
        for (int i = 0; i < 5; i++) {
//            WidgetModel widgetModel = new WidgetModel();
//            widgetModel.setContent("item"+ i+"您有待审批项目,请审核");
//            widgetModel.setImageResource(R.drawable.logo);
//            widgetModel.setButtonMsg("通过");
//            mList.add(widgetModel);
        }
    }

    /*
     * 当调用notifyAppWidgetViewDataChanged方法时，触发这个方法
     * 例如：AppRemoteViewsFactory.notifyAppWidgetViewDataChanged();
     */
    @Override
    public void onDataSetChanged() {
        Logger.e("onDataSetChanged","onDataSetChanged");
    }

    /*
     * 这个方法不用多说了把，这里写清理资源，释放内存的操作
     */
    @Override
    public void onDestroy() {
        mList.clear();
    }

    /*
     * 返回集合数量
     */
    @Override
    public int getCount() {
        return mList.size();
    }

    /*
     * 创建并且填充，在指定索引位置显示的View，这个和BaseAdapter的getView类似
     */
    @Override
    public RemoteViews getViewAt(int position) {
        if (position < 0 || position >= mList.size())
            return null;
        WidgetModel widgetModel = mList.get(position);
        // 创建在当前索引位置要显示的View
        final RemoteViews rv = new RemoteViews(mContext.getPackageName(),
                R.layout.my_widget_layout_item);

        // 设置要显示的内容
        rv.setTextViewText(R.id.widget_list_item_tv, widgetModel.getContent());
        rv.setImageViewResource(R.id.item_image,widgetModel.getImageResource());
        rv.setTextViewText(R.id.widget_list_item_button, widgetModel.getButtonMsg());

        // 填充Intent，填充在AppWdigetProvider中创建的PendingIntent
        Intent intent = new Intent();
        // 传入点击行的数据
        intent.putExtra("content", widgetModel.getContent());
        intent.putExtra("imageResource", widgetModel.getImageResource());
        intent.putExtra("buttonMsg", widgetModel.getButtonMsg());
        rv.setOnClickFillInIntent(R.id.widget_list_item_tv, intent);
        rv.setOnClickFillInIntent(R.id.item_image, intent);
        rv.setOnClickFillInIntent(R.id.widget_list_item_button, intent);

        return rv;
    }

    /*
     * 显示一个"加载"View。返回null的时候将使用默认的View
     */
    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    /*
     * 不同View定义的数量。默认为1（本人一直在使用默认值）
     */
    @Override
    public int getViewTypeCount() {
        return 1;
    }

    /*
     * 返回当前索引的。
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
     * 如果每个项提供的ID是稳定的，即她们不会在运行时改变，就返回true（没用过。。。）
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }
}
