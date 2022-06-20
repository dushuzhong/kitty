package com.asia.kitty;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
//import android.support.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import cn.jpush.android.api.JPushInterface;

// 自定义过渡页面
public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();// 隐藏标题栏
        setContentView(R.layout.activity_splash);

        // 极光初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                    //(getApplicationContext()
                    Intent it = new Intent(Splash.this,MainActivity.class);
                    startActivity(it);
                    finish();// 关闭活动
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();// 启动线程
    }
}
