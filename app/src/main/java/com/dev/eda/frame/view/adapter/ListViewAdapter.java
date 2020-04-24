package com.dev.eda.frame.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dev.eda.R;
import com.dev.eda.frame.view.activity.ListViewDetailActivity;
import com.dev.eda.frame.view.model.ItemListView;
import com.dev.eda.frame.view.utils.MyHtmlTagHandler;

import java.util.List;

public class ListViewAdapter extends BaseMultiItemQuickAdapter<ItemListView, BaseViewHolder> {

    public ListViewAdapter(Context context, List<ItemListView> data) {
        super(data);
        mContext = context;
        addItemType(ItemListView.item_type_all, R.layout.item_list_view_recycle);
        addItemType(ItemListView.item_type_default, R.layout.empty_view_recycle);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ItemListView item) {
        switch (helper.getItemViewType()) {
            case ItemListView.item_type_all:
                TextView textViewContent = helper.getView(R.id.default_description);
//                WebView webView = helper.getView(R.id.default_web_description);
                TextView textViewSubTitle = helper.getView(R.id.item_sub_title);
                CardView linearLayout = helper.getView(R.id.item_layout);

//                textViewContent.setHorizontallyScrolling(true);
                String title = item.getItemListViewContent().getTitle();
                String subTitle = item.getItemListViewContent().getSubTitle();
                String describe = item.getItemListViewContent().getDescribe();
                String note = item.getItemListViewContent().getNote();

//                String contentHtml = "<div>";
//                if(title != null){
//                    contentHtml += "<span>  </span>";
//                    contentHtml += "<myfont color='#000000' size='60px'>"+title+"</myfont><br/>";
//                    contentHtml += "<span> </span>";
//                }
//                if(describe != null){
//                    contentHtml += "<myfont size='44px'>"+describe+"</myfont><br/>";
//                }
//                if(note != null){
//                    contentHtml += "<myfont size='44px'>"+note+"</myfont>";
//                }
//                contentHtml += "</div>";
//                Spanned myfont = Html.fromHtml(contentHtml, null, new MyHtmlTagHandler("myfont"));
////                textViewContent.setText(Html.fromHtml(contentHtml));
//                textViewContent.setText(myfont);
//                textViewContent.setLineSpacing(34,0.5f);
                StringBuffer sb = new StringBuffer();
//                sb.append("<html><head><meta http-equiv='content-type' content='text/html; charset=utf-8'>");
//                sb.append("<meta charset='utf-8'  content='1'></head><body style='color: red'><p></p>");
                if (title != null) {
                    sb.append("<myfont color='red' size='64px'>" + title + "</myfont><br/>");
                }
                if (describe != null) {
                    sb.append("<myfont color='red' size='44px'>" + describe + "</myfont><br/>");
                }
                if (note != null) {
                    sb.append("<myfont color='red' size='44px'>" + note + "</myfont>");
                }

                if (subTitle != null) {
                    textViewSubTitle.setText(subTitle);
                } else {
                    textViewSubTitle.setVisibility(View.GONE);
                }
                Spanned myfont = Html.fromHtml(sb.toString(), null, new MyHtmlTagHandler("myfont"));
                textViewContent.setText(myfont);
                helper.addOnClickListener(R.id.item_layout);
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(item.getDetailPageId(), item.getItemListViewContent().getId());
                    }
                });
                break;
            default:
                break;
        }
    }

    private void startActivity(String detailPage, String id) {
        Intent intent = new Intent(mContext, ListViewDetailActivity.class);
        intent.putExtra("detailPageId", detailPage);
        intent.putExtra("id", id);
        mContext.startActivity(intent);
    }
}
