package com.asia.kitty.Fragment;

import android.annotation.SuppressLint;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import androidx.annotation.Nullable;
import android.support.annotation.Nullable;

import com.asia.kitty.R;

// 定义一个MyFragment
@SuppressLint("ValidFragment")
public class MyFragment extends Fragment {

    private String content;
    public MyFragment(String content) {
        this.content = content;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content,container,false);
        TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
        txt_content.setText(content);
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
