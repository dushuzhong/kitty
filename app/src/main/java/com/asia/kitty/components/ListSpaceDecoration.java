package com.asia.kitty.components;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.asia.kitty.R;

public class ListSpaceDecoration extends RecyclerView.ItemDecoration {

    public ListSpaceDecoration() {

    }
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getChildAdapterPosition(view) != 0) { // 不是第一项设置10dp间距
            outRect.top = parent.getContext().getResources().getDimensionPixelOffset(R.dimen.test_10dp);
        }
    }
}
