package com.asia.kitty.Adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.asia.kitty.R;

import org.w3c.dom.Text;

import java.util.List;

public class MyBannerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

    private List<ImageView> list_imgs;
    private List<ImageView> points;
    private View view = null;
    private TextView tv_title;
    private String [] titles;
    public MyBannerAdapter(List<ImageView> list_images, List<ImageView> points, String [] titles,View view) {
        this.list_imgs = list_images;
        this.points = points;
        this.view = view;
        this.titles = titles;
        // 找到文本对象
        tv_title = (TextView) view.findViewById(R.id.viewpager_tv);
    }

    @NonNull
    @Override //设置view
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(list_imgs.get(position));
        return list_imgs.get(position);
    }

    @Override
    public int getCount() {
        return list_imgs.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(list_imgs.get(position));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.i("MyBannerAdapter","onPageScrolled");
    }

    @Override
    public void onPageSelected(int position) {
        Log.i("MyBannerAdapter","onPageSelected");
        for ( int i = 0; i < list_imgs.size(); i++){
            ImageView currentPoint = (ImageView) points.get(i);
            if (position == i) {
                String title = this.titles[i];
                Log.i("MyBannerAdapter_title",title);
                tv_title.setText(title); // 设置选中的新标题
                currentPoint.setImageResource(R.drawable.icon_red_24);
            } else {
                currentPoint.setImageResource(R.drawable.icon_blue_24);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.i("MyBannerAdapter","onPageScrollStateChanged");

    }

}
