package com.asia.kitty.components;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.StyleRes;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.asia.kitty.R;

//
//LoadingDialog.getInstance(this).show();
//LoadingDialog.getInstance(context).hide();
//LoadingDialog.setInstance(null);


public class LoadingDialog extends Dialog {

    private ImageView iv_ing;
    private AnimationSet animationSet;
    private static LoadingDialog instance; //这是单例？

    private LoadingDialog(@NonNull Context context) {
        super(context);
    }

    private LoadingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context,themeResId);
    }

    private LoadingDialog(@NonNull Context context, boolean cancelable,@NonNull OnCancelListener cancelListener) {
        super(context,cancelable,cancelListener);
    }

    public static LoadingDialog getInstance(Context context) {
        if (instance == null) {
            instance = new LoadingDialog(context);
        }
        return instance;
    }

    public static void setInstance(LoadingDialog instance) {
        LoadingDialog.instance = instance;
    }

    @Override
    protected void onStart() {
        super.onStart();
        iv_ing.startAnimation(animationSet);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void loadIng() {
        animationSet = new AnimationSet(true);
        RotateAnimation animation_rotate = new RotateAnimation(0, +359,RotateAnimation.RELATIVE_TO_SELF,0.5f, RotateAnimation.RELATIVE_TO_SELF,0.5f);
        // 第一个参数，为动画开始时的旋转角度，第二个参数为动画旋转到角度, 第三个参数为动画在X轴相对于物件位置类型，第四个参数为相对于物件的X坐标的开始位置
        animation_rotate.setRepeatCount(-1);
        animation_rotate.setStartOffset(0);
        animation_rotate.setDuration(1000);
        LinearInterpolator lir = new LinearInterpolator();
        animationSet.setInterpolator(lir);
        animationSet.addAnimation(animation_rotate);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.CYAN));
        getWindow().setDimAmount(0f);

        this.setContentView(R.layout.dialog_loading);

        setCancelable(true);
        setCanceledOnTouchOutside(false);

        iv_ing = findViewById(R.id.iv_ing);

        loadIng();
    }
}
