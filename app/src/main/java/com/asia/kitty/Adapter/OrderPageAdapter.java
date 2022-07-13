package com.asia.kitty.Adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.asia.kitty.Fragment.OrderTabFragment;

import java.util.ArrayList;
import java.util.List;

public class OrderPageAdapter extends FragmentPagerAdapter {
    private List<OrderTabFragment> mFragmentList = null;
    List<String> titles = new ArrayList<>();

    public OrderPageAdapter(FragmentManager mFragmentManager, List<OrderTabFragment> fragmentList, List<String>titles) {
        super(mFragmentManager);
        Log.i("OrderAdapter_OrderPageAdapter","init");
        this.mFragmentList = fragmentList;
        this.titles = titles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    // 必须实现，要不然会被清除掉
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);//每个item的title
    }
}
