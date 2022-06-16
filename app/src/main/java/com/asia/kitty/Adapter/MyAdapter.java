package com.asia.kitty.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.asia.kitty.R;
import com.asia.kitty.model.TestData;

import java.util.LinkedList;

public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private LinkedList<TestData> mData;

    public MyAdapter() {}

    public MyAdapter(LinkedList<TestData> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_list,viewGroup,false);
            holder = new ViewHolder();
            holder.img_icon = (ImageView) view.findViewById(R.id.img_icon);
            holder.txt_content = (TextView) view.findViewById(R.id.txt_content);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.img_icon.setImageResource(mData.get(i).getImgId());
        holder.txt_content.setText(mData.get(i).getContent());
        return view;
    }

    private class ViewHolder{ // 组件对象，java内部类.专门给这个类使用
        ImageView img_icon;
        TextView txt_content;
    }

}
