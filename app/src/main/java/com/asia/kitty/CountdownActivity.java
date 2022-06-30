package com.asia.kitty;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

// 倒计时测试页面
public class CountdownActivity extends AppCompatActivity {
    private TextView mTvCountdownTimer;
    // 24小时换算成毫秒
    private int timeStamp = 86400000;
    // 安卓自带的倒计时
    private CountDownTimer timer;
    private SurfaceHolder surfaceHolder;
    private Button btn_start;
    private Button btn_pause;
    private Button btn_stop;
    private SurfaceView sfv_show;
    private MediaPlayer mPlayer;

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
        // 启动倒计时
        timer.start();

        // 播放视频
        sfv_show = (SurfaceView) findViewById(R.id.sfv_show);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.start();
            }
        });
        btn_pause = (Button) findViewById(R.id.btn_pause);
        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.pause();
            }
        });
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.stop();
            }
        });
        //初始化SurfaceHolder类，SurfaceView的控制器
        surfaceHolder = sfv_show.getHolder();
        surfaceHolder.setFixedSize(320, 220);   //显示的分辨率,不设置为视频默认
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                playOnLineVideo();
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

            }
        });
    }

    // 播放在线视频
    public void playOnLineVideo() {
        try {
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setDisplay(surfaceHolder); //设置显示视频显示在SurfaceView上
            String url = "https://media.w3.org/2010/05/sintel/trailer.mp4";
            mPlayer.setDataSource(url);
            mPlayer.prepare();

        } catch (Exception e) {
            Log.e("countdownActivity",e.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer.isPlaying()) {
            mPlayer.stop();
        }
        mPlayer.release();
    }
}
