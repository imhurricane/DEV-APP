package com.dev.eda.frame.view.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dev.eda.R;
import com.dev.eda.app.utils.DateTool;
import com.dev.eda.app.utils.Logger;
import com.dev.eda.frame.home.adapter.HomeGridRecycleViewAdapter;
import com.dev.eda.frame.view.model.ItemDetailCheckSelect;
import com.dev.eda.frame.view.model.ItemListViewDetail;
import com.dev.eda.frame.view.spaceing.GridSpacingItemDecoration;
import com.dev.eda.frame.view.utils.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewDetailAdapter extends BaseMultiItemQuickAdapter<ItemListViewDetail, BaseViewHolder> {

    private TimePickerView pvTime;
    private List<ItemListViewDetail> mData;

    public ListViewDetailAdapter(Context context, List<ItemListViewDetail> data) {
        super(data);
        mData = data;
        mContext = context;
        addItemType(ItemListViewDetail.item_type_default, R.layout.empty_view_recycle);
        addItemType(ItemListViewDetail.item_type_textbox, R.layout.item_detail_text_view);
        addItemType(ItemListViewDetail.item_type_multextbox, R.layout.item_detail_edit_view);
        addItemType(ItemListViewDetail.item_type_select, R.layout.item_detail_select_view);
        addItemType(ItemListViewDetail.item_type_datetime, R.layout.item_detail_date_view);
        addItemType(ItemListViewDetail.item_type_lookup, R.layout.item_detail_select_view);
        addItemType(ItemListViewDetail.item_type_switchbtn, R.layout.item_detail_switch_view);
        addItemType(ItemListViewDetail.item_type_checkselect, R.layout.item_detail_check_select_recycle_view);
    }

//    @Override
//    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
//        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
//        return new BaseViewHolder(binding.getRoot());
//    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ItemListViewDetail item) {
        TextView viewName;
        EditText editValue;
        TextView textValue;
        switch (helper.getItemViewType()) {
            case ItemListViewDetail.item_type_textbox:
                viewName = helper.getView(R.id.column_name);
                viewName.setText(item.getColumnDes().concat(":"));
                editValue = helper.getView(R.id.column_value);
                setEditTextReadOnly(editValue,item.getIsReadOnlyCol().equals("1"));
                editValue.setText(item.getColumnValue());
                editValue.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        item.setColumnValue(editValue.getText().toString());
                    }
                });

                break;
            case ItemListViewDetail.item_type_multextbox:
                viewName = helper.getView(R.id.edit_column_name);
                viewName.setText(item.getColumnDes().concat(":"));
                editValue = helper.getView(R.id.edit_column_value);
                setEditTextReadOnly(editValue,item.getIsReadOnlyCol().equals("1"));
                editValue.setText(item.getColumnValue());
                editValue.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        item.setColumnValue(editValue.getText().toString());
                    }
                });
                break;
            case ItemListViewDetail.item_type_select:
                //数据
                ArrayList<String> data_list = new ArrayList<String>();
                ArrayList<ItemDetailCheckSelect> selects = item.getSelects();
                for (int i = 0; i < selects.size(); i++) {
                    ItemDetailCheckSelect itemDetailCheckSelect = selects.get(i);
                    data_list.add(itemDetailCheckSelect.getDesc());
                }
                viewName = helper.getView(R.id.spinner_column_name);
                viewName.setText(item.getColumnDes().concat(":"));
                Spinner spinner = helper.getView(R.id.spinner);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, data_list);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        item.setColumnValue(spinner.getSelectedItem().toString());
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                break;
            case ItemListViewDetail.item_type_datetime:
                viewName = helper.getView(R.id.date_column_name);
                viewName.setText(item.getColumnDes().concat(":"));
                textValue = helper.getView(R.id.date_value);
                initTimePicker(textValue,item.getFormatCode());
                String dateForm;
                try {
                    if (item.getColumnValue() != null && !item.getColumnValue().equals("")){
                        dateForm = DateUtils.getFormStr(item.getColumnValue(), item.getFormatCode());
                        textValue.setText(dateForm);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                textValue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvTime.show(v);
                    }
                });
                textValue.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        item.setColumnValue(textValue.getText().toString());
                    }
                });

                break;
            case ItemListViewDetail.item_type_switchbtn:
                viewName = helper.getView(R.id.switch_column_name);
                Switch aSwitch =helper.getView(R.id.switch_column_value);
                viewName.setText(item.getColumnDes().concat(":"));
                aSwitch.setChecked(item.getColumnValue().equals("1"));
                aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        item.setColumnValue(isChecked==false?"0":"1");
                    }
                });

                break;
            case ItemListViewDetail.item_type_checkselect:
                viewName = helper.getView(R.id.check_select_column_name);
                viewName.setText(item.getColumnDes().concat(":"));

                RecyclerView recyclerView = helper.getView(R.id.check_select_recycle_view);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false);
                HashMap<String,Integer> map = new HashMap();
//                map.put(GridSpacingItemDecoration.LEFT_SPACE,50);
                map.put(GridSpacingItemDecoration.RIGHT_SPACE,30);
                GridSpacingItemDecoration gridSpacingItemDecoration = new GridSpacingItemDecoration(3, map);
                if (recyclerView.getItemDecorationCount() == 0) {
                    recyclerView.addItemDecoration(gridSpacingItemDecoration);
                }
                recyclerView.setNestedScrollingEnabled(false);//禁用滑动事件
                recyclerView.setLayoutManager(gridLayoutManager);

                ListViewDetailCheckSelectRecycleViewAdapter listViewDetailCheckSelectRecycleViewAdapter = new ListViewDetailCheckSelectRecycleViewAdapter(R.layout.item_detail_checck_select, item.getSelects(), mContext);
                recyclerView.setAdapter(listViewDetailCheckSelectRecycleViewAdapter);

                break;
            default:
                break;
        }

    }

    public static void setEditTextReadOnly(TextView view,boolean flag) {
        if(flag){
            view.setTextColor(Color.parseColor("#D3D3D3"));   //设置只读时的文字颜色
            if (view instanceof android.widget.EditText) {
                view.setCursorVisible(false);             //设置输入框中的光标不可见
                view.setFocusable(false);                 //无焦点
                view.setFocusableInTouchMode(false);      //触摸时也得不到焦点
            }
        }else{
            view.setTextColor(Color.parseColor("#FF000000"));   //设置只读时的文字颜色
            if (view instanceof android.widget.EditText) {
                view.setCursorVisible(true);             //设置输入框中的光标不可见
                view.setFocusable(true);                 //无焦点
                view.setFocusableInTouchMode(true);      //触摸时也得不到焦点
            }
        }


    }

    private void initTimePicker(TextView textView,int format) {//Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                textView.setText(DateUtils.getTime(date, format));
            }
        }).setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
            @Override
            public void onTimeSelectChanged(Date date) {
                Log.i("pvTime", "onTimeSelectChanged");
            }
        })
                .setType(DateUtils.getTimePickerType(format))
                .isDialog(false) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("pvTime", "onCancelClickListener");
                    }
                })
                .setItemVisibleCount(5) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                .setLineSpacingMultiplier(2.0f)
                .isAlphaGradient(true)
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.3f);
            }
        }
    }


}
