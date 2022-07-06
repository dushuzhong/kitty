package com.asia.kitty;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.asia.kitty.Fragment.HomeFragment;
import com.asia.kitty.Fragment.MyFragment;
import com.asia.kitty.Reciever.MyBRReceiver;
import com.asia.kitty.model.Song;
import com.asia.kitty.service.MusicUtils;

import java.util.ArrayList;
import java.util.List;

public class Home2Activity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    private RadioGroup rg_tab_bar;
    private RadioButton rb_channel;

    private HomeFragment fg1;
    private MyFragment fg2,fg3,fg4;
    private FragmentManager fManager;
    private MyBRReceiver myReciever;
    private List<Song> songList = new ArrayList<Song>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        //fManager = getFragmentManager();
        fManager = getSupportFragmentManager();
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        rg_tab_bar.setOnCheckedChangeListener(this);

        //获取第一个单选按钮，并设置其为选中状态
        rb_channel = (RadioButton) findViewById(R.id.rb_channel);
        rb_channel.setChecked(true);

        // 注册广播接收器
        myReciever = new MyBRReceiver();
        IntentFilter itFilter = new IntentFilter();
        itFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myReciever,itFilter);

        // 读取本地音乐
        songList = MusicUtils.getMusicData(Home2Activity.this);
        Log.i("Home2Activity",String.valueOf(songList));
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (checkedId){
            case R.id.rb_channel:
                if (fg1 == null){
                    fg1 = new HomeFragment("第一个Fragment");
                    fTransaction.add(R.id.ly_content,fg1);
                } else {
                    fTransaction.show(fg1);
                }
                break;
            case R.id.rb_message:
                if (fg2 == null){
                    fg2 = new MyFragment("第二个Fragment");
                    fTransaction.add(R.id.ly_content,fg2);
                    //fTransaction.add(R.id.ly_content,fg2);
                } else {
                    fTransaction.show(fg2);
                }
                break;
            case R.id.rb_better:
                if (fg3 == null) {
                    fg3 = new MyFragment("第三个Fragment");
                    fTransaction.add(R.id.ly_content,fg3);
                } else {
                    fTransaction.show(fg3);
                }
                break;
            case R.id.rb_setting:
                if (fg4 == null) {
                    fg4 = new MyFragment("第四个Fragment");
                    fTransaction.add(R.id.ly_content,fg4);
                } else{
                    fTransaction.show(fg4);
                }
                break;
        }
        fTransaction.commit();
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fg1 != null)fragmentTransaction.hide(fg1);
        if(fg2 != null)fragmentTransaction.hide(fg2);
        if(fg3 != null)fragmentTransaction.hide(fg3);
        if(fg4 != null)fragmentTransaction.hide(fg4);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReciever);
    }
}