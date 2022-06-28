package com.asia.kitty;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

// 倒计时测试页面
public class CountdownActivity extends AppCompatActivity {
    private TextView mTvCountdownTimer;
    // 24小时换算成毫秒
    private int timeStamp = 86400000;
    // 安卓自带的倒计时
    private CountDownTimer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        mTvCountdownTimer = (TextView) findViewById(R.id.tv_countdowntimer);

        timer = new CountDownTimer(timeStamp, 1000) {
            @Override
            public void onTick(long l) {
                long day = l / (1000 * 24 * 60 * 60); // 单位天
                long hour = (l - day * (1000 * 24 * 60 *60)) / (1000 * 60 * 60);
                long minute = (l - day * (1000 * 24 * 60 *60) - hour * (1000 * 60 * 60)) / (1000 * 60);
                long second = (l - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;
                String tvStr = hour + "小时" + minute + "分钟" + second + "秒";
                mTvCountdownTimer.setText(tvStr);
            }

            @Override
            public void onFinish() {

            }
        };

        timer.start();
    }
}
