package com.dev.eda.frame.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dev.eda.R;
import com.dev.eda.app.utils.Logger;
import com.dev.eda.frame.view.model.ItemDetailCheckSelect;

import java.util.List;

public class ListViewDetailCheckSelectRecycleViewAdapter extends BaseQuickAdapter<ItemDetailCheckSelect, BaseViewHolder> {

    private Context mContext;

    private List<ItemDetailCheckSelect> mData;

    public ListViewDetailCheckSelectRecycleViewAdapter(int layoutResId, @Nullable List<ItemDetailCheckSelect> data, Context context) {
        super(layoutResId, data);
        this.mContext = context;
        this.mData = data;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ItemDetailCheckSelect item) {
        TextView textView = helper.getView(R.id.check_select_column_value_desc);
        CheckBox checkBox = helper.getView(R.id.check_select_column_value);
        textView.setText(item.getDesc());
        checkBox.setChecked(item.isChecked());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Logger.e("isChecked", isChecked + "---");
                checkBox.post(new Runnable() {
                    @Override
                    public void run() {
                        if (isChecked){
                            for (int i = 0; i < mData.size(); i++) {
                                mData.get(i).setChecked(false);
                            }
                        }
                        mData.get(helper.getLayoutPosition()).setChecked(isChecked);
                        notifyDataSetChanged();
                    }
                });
            }
        });
    }
}
