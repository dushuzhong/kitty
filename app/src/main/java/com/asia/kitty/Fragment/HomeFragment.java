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
import com.asia.kitty.MusicPlayActivity;
import com.asia.kitty.R;
import com.asia.kitty.components.HomeNavigationBar;
import com.asia.kitty.model.Fruit;
import com.asia.kitty.service.TimeHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;


import java.util.ArrayList;
import java.util.List;

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
    private LinearLayout ll_dots;
    // 轮播图组件上的文本
    private TextView viewpager_tv;
    // 将ViewPager定义为全局变量,方便使用.
    private ViewPager viewpager_vp;
    // 建立一个ArrayList集合.泛型指定为ImageView.
    ArrayList<ImageView> imageViews = new ArrayList<ImageView>();
    private List<Fruit> fruitList = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    //得到当前VIewPager和用户交互的item条目.VIewPager对象.getCurrentItem 333
                    int currentItem = viewpager_vp.getCurrentItem();
                    viewpager_vp.setCurrentItem(currentItem + 1);
                    sendEmptyMessageDelayed(1,3000);
                break;
            }
        }
    };



    private int[] imageResIds = {R.drawable.lunbo1,R.drawable.lunbo2,R.drawable.lunbo3};
    private String[] descs = {"网页设计师联盟","教程网","PS联盟"};

    private void initViewPage(View view) {
        // 找到可以设置点的容器
        ll_dots = (LinearLayout) view.findViewById(R.id.ll_dots);
        // 找到文本对象
        viewpager_tv = (TextView) view.findViewById(R.id.viewpager_tv);
        // 找到viewPager对象
        viewpager_vp = (ViewPager) view.findViewById(R.id.viewpager_vp);

        for (int x = 0; x < imageResIds.length; x++) {
            ImageView imageView_t = new ImageView(getContext());
            imageView_t.setBackgroundResource(imageResIds[x]);
            imageViews.add(imageView_t);

            // 增加点
            dot();
        }

        viewpager_vp.setAdapter(new MyBannerAdapter(imageViews,imageResIds));
        viewpager_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //同过getCurrentItem方法拿到当前用户所交互ViewPager的item位置.
                int currentItem = viewpager_vp.getCurrentItem();
                //通过得到的这个item,给text和点进行选中的设置.
                changeTextAndDot(currentItem % imageResIds.length);
                Log.d("aaa", "onPageScrolled: Position-" + position + " positionOffset-" + positionOffset + " positionOffsetPixels-" + positionOffsetPixels);

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("aaa", "onPageSelected: position" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

            //指定VIewPager默认跳转到某页.一般是最大数的一般.setCurrentItem就是设置VIewPager跳到哪页,get是获取.333
            //viewpager_vp.setCurrentItem(Integer.MAX_VALUE / 2 - 3);
            //通过handler,3秒后开始循环ViwePager的item.sendEmptyMessageDelayed,333
            //    handler.sendEmptyMessageDelayed(1, 3000);
            //ViewPagerTouchEvent();
        });
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
                        //Toast.makeText(getActivity(),"你点击了水果"+(position+1),Toast.LENGTH_SHORT).show();
                        Log.i("MusicPlayActivity",String.valueOf(getActivity()));
                        Log.i("MusicPlayActivity",String.valueOf(MusicPlayActivity.class));
                        Intent intent = new Intent(getActivity(), MusicPlayActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        Toast.makeText(getActivity(),"你点击了item"+(position+1),Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onItemLongClick(View v) {

            }
        });
        return view;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//
//    }

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
        handler.removeMessages(1);
    }

    // 动态的创建点
    public void dot() {
        //创建一个View对象;
        View view = new View(getContext());
        //为这个View对象设置背景setBackgroundResource
        view.setBackgroundResource(R.drawable.jpush_richpush_progressbar);
        //为View对象设置宽高参数,使用参数对象LayoutParams(int,int),给哪个容器,就用哪个容器创建
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(8, 8);
        //使用参数对象LayoutParams.leftMargin=int,相当于布局里的padding.
        layoutParams.leftMargin = 8;
        //把准备好的layoutParams参数对象,设置给View对象.setLayoutParams
        view.setLayoutParams(layoutParams);
        //最后容器对象.addView(VIwe);
        ll_dots.addView(view);
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

    // 改变文本和dot
    public void changeTextAndDot(int position) {
        //根据ViewPager的item的变化,设置对应的文本.setText(descs[position]);
        viewpager_tv.setText(descs[position]);
        //对点进行判断是否是当前页的点,用for循环,拿到所有点的位置,然后和position对比
        for (int x = 0; x < imageResIds.length; x++) {
            //.getChildAt(x);拿到容器的子控件.得到VIew对象
            View childAt = ll_dots.getChildAt(x);
            //为View设置背景图片,,使用三元运算符.
            childAt.setBackgroundResource(x == position ? R.drawable.jpush_btn_bg_green_playable : R.drawable.jpush_richpush_progressbar);
        }
    }

}
