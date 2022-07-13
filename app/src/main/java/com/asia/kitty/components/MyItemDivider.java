package com.asia.kitty.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.asia.kitty.R;

// 实现列表分割线
public class MyItemDivider extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    private Context context;
    public MyItemDivider(Context context) {
        context = context;
        // 获取系统分割线的样式
        int[] mDrawableId = {android.R.attr.listDivider};

        @SuppressLint("ResourceType") android.content.res.TypedArray a = context.obtainStyledAttributes(mDrawableId);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);

        drawHorizonLine(c,parent);
    }

    private void drawHorizonLine(Canvas c, RecyclerView parent) {

        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + params.rightMargin;
            int top = parent.getBottom() + params.bottomMargin;
            int right = parent.getWidth() - parent.getPaddingRight();
            int bottom = top + parent.getPaddingBottom();

            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }
}
