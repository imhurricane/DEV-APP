package com.dev.eda.frame.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dev.eda.R;
import com.dev.eda.app.utils.HttpRequestUrl;
import com.dev.eda.frame.view.activity.ListViewDetailActivity;
import com.dev.eda.frame.view.model.ItemListView;
import com.dev.eda.frame.view.utils.MyHtmlTagHandler;

import java.util.List;

public class ListViewAdapter extends BaseMultiItemQuickAdapter<ItemListView, BaseViewHolder> {

    public ListViewAdapter(Context context,List<ItemListView> data) {
        super(data);
        mContext = context;
        addItemType(ItemListView.item_type_all, R.layout.item_list_view_recycle);
        addItemType(ItemListView.item_type_default, R.layout.empty_view_recycle);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ItemListView item) {
        switch (helper.getItemViewType()) {
            case ItemListView.item_type_all:
                ImageView imageView = helper.getView(R.id.title_image);
                TextView textViewContent = helper.getView(R.id.default_description);
                TextView textViewSubTitle = helper.getView(R.id.item_sub_title);
                CardView linearLayout = helper.getView(R.id.item_layout);

                Glide.with(mContext)
                .load(HttpRequestUrl.BaseURI+item.getIcon())
                .placeholder(R.drawable.shanghai)
                .into(imageView);
                textViewContent.setHorizontallyScrolling(true);
                String title = item.getItemListViewContent().getTitle();
                String subTitle = item.getItemListViewContent().getSubTitle();
                String describe = item.getItemListViewContent().getDescribe();
                String note = item.getItemListViewContent().getNote();

                String contentHtml = "<span>";
                if(title != null){
                    contentHtml += "<myfont size='50px'>"+title+"</myfont><br/>";
                }
                if(describe != null){
                    contentHtml += "<myfont color='#D3D3D3' size='44px'>"+describe+"</myfont><br/>";
                }
                if(note != null){
                    contentHtml += "<myfont color='#D3D3D3' size='40px'>"+note+"</myfont>";
                }
                contentHtml += "</span>";
                Spanned myfont = Html.fromHtml(contentHtml, null, new MyHtmlTagHandler("myfont"));
//                textViewContent.setText(Html.fromHtml(contentHtml));
                textViewContent.setText(myfont);
                textViewContent.setLineSpacing(28,0.5f);
                if(subTitle != null){
                    textViewSubTitle.setTextSize(16);
//                    textViewSubTitle.setHorizontallyScrolling(true);
                    textViewSubTitle.setTextColor(Color.parseColor("#D3D3D3"));
                    textViewSubTitle.setText(subTitle);
                }else{
                    textViewSubTitle.setVisibility(View.GONE);
                }

                helper.addOnClickListener(R.id.item_layout);
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(item.getDetailPageId(),item.getItemListViewContent().getId());
                    }
                });
                break;
            default:
                break;
        }
    }

    private void startActivity(String detailPage,String id){
        Intent intent = new Intent(mContext, ListViewDetailActivity.class);
        intent.putExtra("detailPageId",detailPage);
        intent.putExtra("id",id);
        mContext.startActivity(intent);
    }
}
