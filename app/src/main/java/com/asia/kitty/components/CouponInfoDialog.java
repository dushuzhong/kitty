package com.asia.kitty.components;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.asia.kitty.R;

/**
 * 券信息弹框
 */

public class CouponInfoDialog {
    private View view;
    private Dialog dialog;

    // 定义弹框事件回调接口
    public interface CouponInfoDialogListener {
        // 取消事件
        public void cancelAction();
        // 选择事件
        public void confirmAction(int position);
    }

    // 弹框构造方法
    public CouponInfoDialog(CouponInfoDialog.CouponInfoDialogListener bListener, Context context) {
        //1、使用Dialog、设置style
        //final Dialog
        dialog = new Dialog(context, R.style.DialogTheme);
        //2、设置布局
        view = View.inflate(context, R.layout.coupon_info_dialog, null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.CENTER);
        //设置弹出动画
        window.setWindowAnimations(R.style.dialog_fade_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置事件
        //configDialogHandler(bListener);
    }

    // 展示弹框
    public void showDialog(){
        dialog.show();
    }

}
