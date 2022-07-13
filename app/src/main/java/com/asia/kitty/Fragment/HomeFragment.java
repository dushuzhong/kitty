package com.asia.kitty.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asia.kitty.Adapter.BookAdapter;
import com.asia.kitty.Adapter.MyBannerAdapter;
import com.asia.kitty.CountdownActivity;
import com.asia.kitty.MusicPlayActivity;
import com.asia.kitty.R;
import com.asia.kitty.components.HomeNavigationBar;
import com.asia.kitty.components.ListSpaceDecoration;
import com.asia.kitty.components.MyItemDivider;
import com.asia.kitty.components.RecyclerSpace;
import com.asia.kitty.model.Fruit;
import com.asia.kitty.service.TimeHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// 定义一个MyFragment
// getActivity 获取获取Fragment所在activity
@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment {

    private String content;
    private HomeNavigationBar navigationBar;
    public HomeFragment(String content) {
        this.content = content;
    }

    private ImageView imageView; //SimpleDraweeView,ImageView

    // 轮播图上显示的点
    private LinearLayout list_points;
    // 轮播图组件上的文本
    private TextView viewpager_tv;
    // 将ViewPager定义为全局变量,方便使用.
    private ViewPager viewpager_vp;
    // 建立一个ArrayList集合.泛型指定为ImageView.
    ArrayList<ImageView> list_images = new ArrayList<ImageView>();
    // 底部圆点
    ArrayList<ImageView> tvList = new ArrayList<>();
    private List<Fruit> fruitList = new ArrayList<>();
    private ScheduledThreadPoolExecutor executor;

    private int currentPage;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    viewpager_vp.setCurrentItem(msg.arg1);
                break;
            }
        }
    };

    private int[] imageResIds = {R.drawable.lunbo1,R.drawable.lunbo2,R.drawable.lunbo3};
    private String[] descriptions = {"网页设计师联盟","教程网","PS联盟"};

    private boolean isStop = false; // 是否停止轮播
    private MyBannerAdapter adapter;

    // 配置轮播
    private void initViewPage(View view) {

        // 找到viewPager对象
        viewpager_vp = (ViewPager) view.findViewById(R.id.viewpager_vp);
        // 找到可以设置点的容器
        list_points = (LinearLayout) view.findViewById(R.id.ll_dots);

        InitData();

        adapter = new MyBannerAdapter(list_images, tvList, descriptions, view);
        viewpager_vp.setAdapter(adapter);
        viewpager_vp.setOnPageChangeListener(adapter);
        viewpager_vp.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                //立体翻转
                int myWidth = page.getWidth();
                int myPivotX = 0;
                if(position <= 1 && position > 0){
                    myPivotX = 0;
                } else if(position < 0 && position >= -1){
                    myPivotX = myWidth;
                }
                page.setPivotX(myPivotX);
                page.setRotationY(45f * position);

            }
        });
    }

    private void InitData() {

        for (int img : imageResIds) {
            LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageViewParams.leftMargin = 15;
            imageViewParams.topMargin = 2;

            ImageView imageView = new ImageView(getContext());
            imageView.setBackgroundResource(img);
            imageView.setLayoutParams(imageViewParams);
            list_images.add(imageView);

            ImageView view = new ImageView(getContext());
            //为这个View对象设置背景setBackgroundResource
            view.setBackgroundResource(R.drawable.icon_blue_24);
            //为View对象设置宽高参数,使用参数对象LayoutParams(int,int),给哪个容器,就用哪个容器创建
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20, 20);
            layoutParams.rightMargin = 10;
            layoutParams.topMargin = 16;
            layoutParams.leftMargin = 10;
            //把准备好的layoutParams参数对象,设置给View对象.setLayoutParams
            view.setLayoutParams(layoutParams);
            tvList.add(view);

            list_points.addView(view);
        }

        // 第一个选中
        ImageView item = (ImageView)tvList.get(0);
        item.setImageResource(R.drawable.icon_red_24);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_home_content,container,false);

        initViewPage(view);

        //TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
        //txt_content.setText(content);

        // 设置标题
        navigationBar = (HomeNavigationBar) view.findViewById(R.id.home_navigationBar);
        navigationBar.setTitle("首页");

        // 设置图片
        imageView = view.findViewById(R.id.imageView_pic1);
//        Glide.with(this)
//                .load("https://images.unsplash.com/photo-1609225151006-1d67f4e88db0?ixid=MnwxMjA3fDB8MHxwcm9maWxlLWxpa2VkfDZ8fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=900&q=60")
//                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
//                .into(imageView);

        // 支持下gif
        Glide.with(this).load("https://p8.itc.cn/q_70/images03/20220425/56bd93a37b8c4aabaca048c9e1b5cb62.gif").into(imageView);

        // 列表需要的假数据
        initFruit();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        // 设置列表
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // 设置格子
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        //gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//HORIZONTAL,VERTICAL
        //recyclerView.setLayoutManager(gridLayoutManager);

        BookAdapter fruitAdapter = new BookAdapter(fruitList);
        recyclerView.setAdapter(fruitAdapter);
        fruitAdapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, BookAdapter.ViewName viewName, int position) {
                switch (v.getId()){
                    case R.id.fruit_image:
                        // Toast.makeText(getActivity(),"你点击了水果"+(position+1),Toast.LENGTH_SHORT).show();
                        // Log.i("MusicPlayActivity",String.valueOf(getActivity()));
                        // Log.i("MusicPlayActivity",String.valueOf(MusicPlayActivity.class));
                        Intent intent = new Intent(getActivity(), MusicPlayActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        Intent intent2 = new Intent(getActivity(), CountdownActivity.class);
                        startActivity(intent2);
                        // Toast.makeText(getActivity(),"你点击了item"+(position+1),Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onItemLongClick(View v) {

            }
        });

        RecyclerSpace divider = new RecyclerSpace(2,"#f7f7f7",200);
        recyclerView.addItemDecoration(divider);

        return view;
    }

    // 模拟分组数据
    private void initFruit() {
        for (int i = 1; i > 0; i--) { // 三组数据
            Fruit orange = new Fruit("orange",R.drawable.juzi);
            fruitList.add(orange);
            Fruit waterMelon = new Fruit("waterMelon",R.drawable.xigua);
            fruitList.add(waterMelon);
            Fruit apple = new Fruit("apple",R.drawable.pingguo);
            fruitList.add(apple);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("HomeFragment","用户离开时调用");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    // viewPager点击事件
    public void ViewPagerTouchEvent() {
        viewpager_vp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //当时MotionEvent.ACTION_DOWN和ACTION_MOVE,就移除handler发送的message.removeMessages.333
                        handler.removeMessages(1);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        handler.removeMessages(1);
                        break;
                    //当用户手松开时ACTION_UP,就继续使用sendEmptyMessageDelayed发送handler的消息.333
                    case MotionEvent.ACTION_UP:
                        handler.sendEmptyMessageDelayed(1, 3000);
                    default:
                        break;
                }
                return false;
            }

        });
    }

}
