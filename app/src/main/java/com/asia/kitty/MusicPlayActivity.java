package com.asia.kitty;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.asia.kitty.model.MusicPlayer;

// 播放音乐的测试页面
public class MusicPlayActivity extends AppCompatActivity {
    private Button btnPause, btnPlay, btnStop;
    private SeekBar skbProgress;
    private MusicPlayer player;
    private int progress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicplay);

        this.setTitle("在线音乐播放");
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.play();
            }
        });
        btnPause = (Button) findViewById(R.id.btnPause);
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.pause();
            }
        });

        btnStop = (Button) findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.stop();
            }
        });
        skbProgress = (SeekBar)findViewById(R.id.seekBar);
        skbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress = i * player.mediaPlayer.getDuration()
                        / seekBar.getMax();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.mediaPlayer.seekTo(progress);
            }
        });

        //String url = "http://music.163.com/song/media/outer/url?id=447925558.mp3";
        //player = new MusicPlayer(skbProgress, url);
        player = new MusicPlayer(skbProgress,MusicPlayActivity.this);
    }
}
