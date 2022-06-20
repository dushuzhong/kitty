package com.asia.kitty.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asia.kitty.R;
import com.asia.kitty.components.HomeNavigationBar;

// 定义一个MyFragment
// getActivity 获取获取Fragment所在activity
@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment {

    private String content;
    private HomeNavigationBar navigationBar;
    public HomeFragment(String content) {
        this.content = content;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_home_content,container,false);
        TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
        //txt_content.setText(content);
        navigationBar = (HomeNavigationBar) view.findViewById(R.id.home_navigationBar);
        navigationBar.setTitle("首页");
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("HomeFragment","用户离开时调用");

    }
}
