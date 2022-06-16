package com.asia.kitty.components;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.asia.kitty.R;

/**
 *  使用方法如下
 *  BottomDialog bottomDialog = new BottomDialog(ls, PullRefreshActivity.this);
 *  bottomDialog.showDialog();
 *
 *  1.定义回调接口
 */

public class BottomDialog {
    private View view;
    private Dialog dialog;
    public String cancelTitle;

    // 定义弹框事件回调接口
    public interface BottomDialogListener {
        // 取消事件
        public void cancelAction();
        // 选择事件
        public void confirmAction(int position);
    }

    public BottomDialog(BottomDialogListener bListener,Context context) {
        //1、使用Dialog、设置style
        //final Dialog
        dialog = new Dialog(context, R.style.DialogTheme);
        //2、设置布局
        view = View.inflate(context, R.layout.bottom_dialog, null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置事件
        configDialogHandler(bListener);
    }

    public void setCancelTitle(String cancelTitle) {
        this.cancelTitle = cancelTitle;
        TextView cancelText = dialog.findViewById(R.id.tv_cancel);
        cancelText.setText(cancelTitle);
    }

    private void configDialogHandler(BottomDialogListener listener) {
        dialog.findViewById(R.id.tv_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.confirmAction(0);
            }
        });

        dialog.findViewById(R.id.tv_take_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.confirmAction(1);
            }
        });

        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.cancelAction();
            }
        });
    }

    // 展示底部弹框
    public void showDialog(){
        dialog.show();
    }
}
