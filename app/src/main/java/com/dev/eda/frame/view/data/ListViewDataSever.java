package com.dev.eda.frame.view.data;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dev.eda.app.http.callback.DialogCallBack;
import com.dev.eda.app.http.callback.DialogCallBackResult;
import com.dev.eda.app.http.model.Result;
import com.dev.eda.app.utils.HttpRequestUrl;
import com.dev.eda.app.http.callback.JsonCallback;
import com.dev.eda.app.utils.Logger;
import com.dev.eda.frame.view.model.ItemListView;
import com.dev.eda.frame.view.model.ItemListViewDetail;
import com.dev.eda.frame.view.model.PageInfo;
import com.kongzue.dialog.v3.MessageDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListViewDataSever {

    public static void getListData(Context context,String mToPage, Handler handler, PageInfo pageInfo, boolean flag) {

        List<ItemListView> data = new ArrayList<>();

        OkGo.<List<ItemListView>>get(HttpRequestUrl.LoadingMenuItemUrl)
                .tag(context)
                .retryCount(0)
                .params("toPage", mToPage)
                .params("pageSize", pageInfo.getPageSize())
                .params("pageNumber", pageInfo.getPageNumber())
                .execute(new JsonCallback<List<ItemListView>>() {
                    @Override
                    public void onSuccess(Response<List<ItemListView>> response) {
                        List<ItemListView> body = response.body();
                        data.addAll(body);
                        Message message = handler.obtainMessage();
                        if (!flag) {
                            message.what = 1;
                        } else {
                            message.what = 2;
                        }
                        message.obj = data;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onError(Response<List<ItemListView>> response) {
                        super.onError(response);
                    }
                });
    }

    public static void getListDetailData(Context context,Handler handler, String detailPageId, String id) {

        OkGo.<List<ItemListViewDetail>>get(HttpRequestUrl.LoadingMenuItemDetailUrl)
                .tag(context)
                .retryCount(0)
                .params("detailPageId", detailPageId)
                .params("id", id)
                .execute(new JsonCallback<List<ItemListViewDetail>>() {
                    @Override
                    public void onSuccess(Response<List<ItemListViewDetail>> response) {
                        List<ItemListViewDetail> body = response.body();
                        Message message = handler.obtainMessage();
                        message.obj = body;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onError(Response<List<ItemListViewDetail>> response) {
                        super.onError(response);
                    }
                });
    }

    public static void saveItemDetail(Context context, HashMap<String,String> data){

        OkGo.<Result<JSONObject>>post(HttpRequestUrl.LoadingMenuItemDetailSaveUrl1)
                .tag(context)
                .params(data,false)
                .execute(new DialogCallBackResult<JSONObject>(context,true,JSONObject.class){

                    @Override
                    public void onSuccess(Response<Result<JSONObject>> response) {
                        JSONObject data = response.body().getData();
                        MessageDialog.show((AppCompatActivity) context,"提示","保存成功");
                    }

                    @Override
                    public void onError(Response<Result<JSONObject>> response) {
                        super.onError(response);
                        Result<JSONObject> body = response.body();
                        MessageDialog.show((AppCompatActivity) context,"提示",body.getMsg());
                    }
                });

    }

}
