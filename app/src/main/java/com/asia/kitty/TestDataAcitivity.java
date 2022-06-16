package com.asia.kitty;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.asia.kitty.Adapter.MyAdapter;
import com.asia.kitty.model.TestData;

import java.util.LinkedList;
import java.util.List;

public class TestDataAcitivity extends AppCompatActivity {
    private ListView list_one;
    private MyAdapter mAdapter = null;
    private List<TestData> mData = null;
    private Context mContext = null;
    private TextView txt_empty =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_data_acitivity);

        mContext = TestDataAcitivity.this;
        bindViews();
        mData = new LinkedList<TestData>();
        mAdapter = new MyAdapter((LinkedList<TestData>) mData,mContext);
        list_one.setAdapter(mAdapter);

        // 设置空白页面
        txt_empty = (TextView) findViewById(R.id.txt_empty);
        txt_empty.setText("暂无数据~");
        list_one.setEmptyView(txt_empty);
    }

    private void bindViews(){
        list_one = (ListView) findViewById(R.id.list_one);
    }

    public void add(TestData data) {
        if (mData == null) {
            mData = new LinkedList<>();
        }
        mData.add(data);
        //notifyDataSetChanged();
    }

}