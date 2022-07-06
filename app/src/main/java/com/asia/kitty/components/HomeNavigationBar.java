package com.asia.kitty.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.asia.kitty.R;

public class HomeNavigationBar extends FrameLayout {

    private TextView tvTitle;
    private ImageView leftImage;
    private ImageView rightImage;

    public HomeNavigationBar(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public HomeNavigationBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public HomeNavigationBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view   = inflate(context, R.layout.navigationbar, this);
        tvTitle     = view.findViewById(R.id.navigationbar_title);
        leftImage   = view.findViewById(R.id.navigationbar_leftImage);
        rightImage  = view.findViewById(R.id.navigationbar_rightImage);
    }

    // 设置title
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setLeftImage(Integer image) {
        leftImage.setBackgroundResource(image);
    }


    public void init(String title, boolean isLeftVisiable, OnClickListener leftListener){
        tvTitle.setText(title);
        if (isLeftVisiable){
            leftImage.setVisibility(VISIBLE);
            leftImage.setOnClickListener(leftListener);
        } else {
            leftImage.setVisibility(INVISIBLE);
        }
    }

    // 右侧图片赋值并添加点击事件
    public void rightTabBar(boolean isRightVisiable, Integer image,  OnClickListener rightListener){
        rightImage.setBackgroundResource(image);
        if (isRightVisiable){
            rightImage.setVisibility(VISIBLE);
            rightImage.setOnClickListener(rightListener);
        } else {
            rightImage.setVisibility(INVISIBLE);
        }
    }

}
