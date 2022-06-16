package com.asia.kitty;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

//import androidx.appcompat.app.AppCompatActivity;

import com.asia.kitty.Adapter.MutiLayoutAdapter;
import com.asia.kitty.model.App;
import com.asia.kitty.model.Book;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity implements AbsListView.OnScrollListener{
    private static final int TYPE_BOOK = 0;
    private static final int TYPE_APP = 1;
    private ListView list_content;
    private ArrayList<Object> mData = null;
    private MutiLayoutAdapter myAdapter = null;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View footerView;

    private Handler handler = new Handler(){ // 接收到信号
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x101:
                    if (swipeRefreshLayout.isRefreshing()){
                        myAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);//设置不刷新
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //数据准备：
        mData = new ArrayList<Object>();
        for(int i = 0;i < 20;i++){
            switch ((int)(Math.random() * 2)){
                case TYPE_BOOK:
                    mData.add(new Book("《第一行代码》","郭霖"));
                    break;
                case TYPE_APP:
                    mData.add(new App(R.mipmap.iv_icon_baidu,"百度"));
                    break;
            }
        }

        list_content = (ListView) findViewById(R.id.list_content);
        myAdapter = new MutiLayoutAdapter(ChatActivity.this,mData);
        // 绑定myAdapter
        list_content.setAdapter(myAdapter);

        footerView = getLayoutInflater().inflate(R.layout.menu_layout,null);
        list_content.addFooterView(footerView);

        // config 下拉刷新
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_srl);
        // 设置颜色
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,android.R.color.holo_orange_light, android.R.color.holo_red_light);
        // 刷新监听
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new LoadDataThread().start(); // 开启线程模拟数据加载
//            }
//        });
    }

    private int visibleLastIndex;//用来可显示的最后一条数据的索引
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(myAdapter.getCount() == visibleLastIndex && scrollState == SCROLL_STATE_IDLE){
            new LoadDataThread().start();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        visibleLastIndex = firstVisibleItem + visibleItemCount - 1;//减去最后一个加载中那条
    }

    /**
     * 模拟加载数据的线程
     */
    class LoadDataThread extends Thread{
        @Override
        public void run() {
            initData();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(0x101);//通过handler发送一个更新数据的标记
        }

        private void initData() {
            //list.addAll(Arrays.asList("Json","XML","UDP","http"));
            mData.add(new Book("《第100行代码》","郭霖"));
        }
    }
}