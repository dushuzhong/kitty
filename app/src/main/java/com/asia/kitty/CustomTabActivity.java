package com.asia.kitty;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.asia.kitty.Adapter.OrderPageAdapter;
import com.asia.kitty.Fragment.OrderTabFragment;
import com.asia.kitty.model.FYOrderModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class CustomTabActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    //private TextView tv_tab_title;
    private ViewPager tab_vp;
    private List<FYOrderModel> dataList = new ArrayList<>();
    private List<OrderTabFragment> fragments = new ArrayList<>();
    private List<String> tabTitles = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        tabLayout = findViewById(R.id.tabLayout);
        tab_vp = findViewById(R.id.tab_vp);
        Log.i("CustomTab====>",String.valueOf(tabLayout));

        initData();
    }

    private void initData() {

        TabLayout.Tab tab = tabLayout.newTab().setText("全部");
        tabLayout.addTab(tab);
        tabTitles.add("全部");

        FYOrderModel orderModel = new FYOrderModel();
        orderModel.setOrderTime("2022-07-04 07:55:35");
        orderModel.setCompanyName("兴山县爱欣医药有限责任公司兴鑫药店");
        dataList.add(orderModel);

        FYOrderModel orderModel2 = new FYOrderModel();
        orderModel2.setOrderTime("2022-07-04 07:55:35");
        orderModel2.setCompanyName("兴山县爱欣医药有限责任公司兴鑫药店2");
        dataList.add(orderModel2);

        OrderTabFragment all_fragment = new OrderTabFragment(dataList);
        fragments.add(all_fragment);

        //待付款栏目-加载自定义显示小红点的布局
        tab = tabLayout.newTab();
        //tab.setCustomView(R.layout.tab_red_item);
        //tv_tab_title = tab.getCustomView().findViewById(R.id.tv_tab_title);
        tab.setText("待付款");
        tabTitles.add("待付款");

        tabLayout.addTab(tab);

        OrderTabFragment wait_fragment = new OrderTabFragment(dataList);
        fragments.add(wait_fragment);

        tab = tabLayout.newTab().setText("待发货");
        tabTitles.add("待发货");
        tabLayout.addTab(tab);

        OrderTabFragment pay_fragment = new OrderTabFragment(dataList);
        fragments.add(pay_fragment);

        //vp也要实现adapter方法
        OrderPageAdapter pageAdapter = new OrderPageAdapter(getSupportFragmentManager(), fragments, tabTitles);
        tab_vp.setAdapter(pageAdapter);


        //添加tabLayout选中监听
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //设置选中时的文字颜色
                if (tab.getCustomView() != null) {
                    TextView tv_tab_title = tab.getCustomView().findViewById(R.id.tv_tab_title);
                    tv_tab_title.setTextColor(getResources().getColor(R.color.red));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //设置未选中时的文字颜色
                if (tab.getCustomView() != null) {
                    TextView tv_tab_title = tab.getCustomView().findViewById(R.id.tv_tab_title);
                    tv_tab_title.setTextColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tab_vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //tabLayout和vp关联的核心方法
        tabLayout.setupWithViewPager(tab_vp);
    }
}
