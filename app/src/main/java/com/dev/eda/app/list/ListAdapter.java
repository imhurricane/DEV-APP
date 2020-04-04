package com.dev.eda.app.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dev.eda.R;

import java.util.List;

public class ListAdapter extends BaseAdapter {
    private Context context;
    private List<String> arr;
    LayoutInflater inflater = null;
    public ListAdapter(Context context,List<String> arr){
        this.context = context;
        //listview中循环显示的数据
        this.arr=arr;
        inflater = LayoutInflater.from(context);
    }
    public ListAdapter(){

    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        //返回int可以保存的最大值，此值为2147483647
//        return Integer.MAX_VALUE;
        return Integer.MAX_VALUE;
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return arr.get(position % arr.size());
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position % arr.size();
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        if(view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_text, null);
            holder.text = view.findViewById(R.id.text);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        //strs[position%strs.length]实现listview中数据的循环
        holder.text.setText(arr.get(position % arr.size()));

        return view;
    }

}

class ViewHolder{
    public TextView text;
}

