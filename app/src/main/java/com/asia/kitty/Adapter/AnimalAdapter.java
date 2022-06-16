package com.asia.kitty.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.asia.kitty.R;
import com.asia.kitty.model.Animal;

import java.util.LinkedList;

/**
 * 动物列表适配器
 */
public class AnimalAdapter extends BaseAdapter {

    private LinkedList<Animal> mData;
    private Context mContext;
    // animalAdapter构造方法
    public AnimalAdapter(LinkedList<Animal>mData,Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() { // 列表条目数
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
    public View getView(int i, View view, ViewGroup viewGroup) { // 给子组件赋值
        // viewHolder,这个地方需要优化，不能直接赋值
        // 第一种赋值方法
        // view = LayoutInflater.from(mContext).inflate(R.layout.item_list_animal,viewGroup,false);
        // 第二种赋值方法
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_list_animal,viewGroup,false);
        }
        ImageView img_icon = (ImageView) view.findViewById(R.id.img_icon);
        TextView txt_aName = (TextView) view.findViewById(R.id.txt_aName);
        TextView txt_aSpeak = (TextView) view.findViewById(R.id.txt_aSpeak);
        img_icon.setBackgroundResource(mData.get(i).getaIcon());
        txt_aName.setText(mData.get(i).getaName());
        txt_aSpeak.setText(mData.get(i).getaSpeak());
        return view;

        // 第三种viewHolder上赋值,viewHolder重用
//        ViewHolder holder = null;
//        if(view == null){
//            view = LayoutInflater.from(mContext).inflate(R.layout.item_list,viewGroup,false);
//            holder = new ViewHolder();
//
//            holder.img_icon = (ImageView) view.findViewById(R.id.img_icon);
//            holder.txt_aName = (TextView) view.findViewById(R.id.txt_aName);
//            holder.txt_aSpeak = (TextView) view.findViewById(R.id.txt_aSpeak);
//            view.setTag(holder);   //将Holder存储到view中
//        }else{
//            holder = (ViewHolder) view.getTag();
//        }
//        holder.img_icon.setBackgroundResource(mData.get(i).getaIcon());
//        holder.txt_aName.setText(mData.get(i).getaName());
//        holder.txt_aSpeak.setText(mData.get(i).getaSpeak());
//        return view;
    }
}
