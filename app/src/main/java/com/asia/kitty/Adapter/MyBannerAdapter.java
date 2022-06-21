package com.asia.kitty.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MyBannerAdapter extends PagerAdapter {

    private ArrayList<ImageView> imageViews;
    private int[] imageResIds;
    public MyBannerAdapter(ArrayList<ImageView> imageViews,int[] imgIds) {
        this.imageViews = imageViews;
        this.imageResIds = imgIds;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    //isViewFromObject,判断ViewPager的条目View对象和InstantiateItem返回的Object对象是否一致,固定格式:return view==object;
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //instantiateItem,ViewPager添加条目的操作.container:VIewPager的化身,控件都是添加到他身上,position:代表用户滑动条目的位置
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //根据条目所在位置(利用Position),从ImageViews集合里获取相对应的ImageVIew图片.
        ImageView imageView = imageViews.get(position % imageResIds.length);
        //把得到ImageView对象,添加给VIewPager对象,也就是container,使用addView
        container.addView(imageView);
        //注意:你添加给VIewPager什么控件,就要返回该控件,给isViewFromObject进行比较判断,这里添加的是ImageView,返回的就是ImageView
        return imageView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //构造方法删除后,也是固定格式:container.removeView((View) object);
        container.removeView((View) object);
    }

}
