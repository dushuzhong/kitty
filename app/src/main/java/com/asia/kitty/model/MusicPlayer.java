package com.asia.kitty.model;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.SeekBar;

import androidx.annotation.NonNull;

import com.asia.kitty.R;

import java.util.Timer;
import java.util.TimerTask;

// 音乐播放
public class MusicPlayer implements MediaPlayer.OnBufferingUpdateListener,MediaPlayer.OnPreparedListener{

    public MediaPlayer mediaPlayer;
    private SeekBar skbProgress;

    //通过定时器和handler来更新进度条
    TimerTask mTimeTask = new TimerTask() {
        @Override
        public void run() {
            if (mediaPlayer == null) {
                return;
            }
            if (mediaPlayer.isPlaying() && skbProgress.isPressed() == false) {
                handlerPress.sendEmptyMessage(0);
            }
        }
    };

    private Timer mTimer = new Timer();
    Handler handlerPress = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            int position = mediaPlayer.getCurrentPosition();
            int duration = mediaPlayer.getDuration();
            if (duration > 0) {
                long pos = skbProgress.getMax() * position / duration;
                skbProgress.setProgress((int)pos);
            }
        }
    };

    //OnBufferingUpdateListener
    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        skbProgress.setSecondaryProgress(i);
        int currentProgress = skbProgress.getMax() * mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration();
        Log.e(currentProgress+"% play", i + "% buffer");
    }

    //OnPreparedListener
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    // 音乐播放器实例
    public MusicPlayer(SeekBar skbProgress, String url) {
        this.skbProgress = skbProgress;
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();

        } catch (Exception e) {
            Log.e("mediaPlayer",e.toString());
        }
        mTimer.schedule(mTimeTask,0,1000);
    }

    // 播放本地实例
    public MusicPlayer(SeekBar skbProgress, Context context) {
        this.skbProgress = skbProgress;
        try {
            mediaPlayer = MediaPlayer.create(context, R.raw.test);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    //mediaPlayer.reset();
                }
            });
            mediaPlayer.prepare();//开始播放
        } catch (Exception e) {
            e.printStackTrace();//输出异常信息
        }
    }

    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    public void pause() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.start();
            }
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
