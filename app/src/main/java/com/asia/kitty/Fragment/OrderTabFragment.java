package com.asia.kitty.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asia.kitty.Adapter.OrderAdapter;
import com.asia.kitty.Adapter.ProductAdapter;
import com.asia.kitty.R;
import com.asia.kitty.model.FYOrderModel;

import java.util.ArrayList;
import java.util.List;

// 定义一个Tab的Fragment
@SuppressLint("ValidFragment")
public class OrderTabFragment extends Fragment {
    private String content;
    private RecyclerView recycleView;
    private List<FYOrderModel> orderList;
    //private List<FYOrderModel> dataList = new ArrayList<>();
    private FYOrderModel model;
    public OrderTabFragment(String content) {
        this.content = content;
    }

    public OrderTabFragment(List<FYOrderModel> list) {
        Log.i("OrderAdapter_fg_init",String.valueOf(list.size()));
        this.orderList = list;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_order_item,container,false);

        recycleView = (RecyclerView) view.findViewById(R.id.recyclerview);

        OrderAdapter.RecycleItemClickListener itemClickListener = new OrderAdapter.RecycleItemClickListener() {
            @Override
            public void onItemClick(View v, OrderAdapter.ViewName viewName, int position) {
                Log.i("OrderAdapter_click",String.valueOf(position));
            }

            @Override
            public void onItemClick(View view, int layoutPosition) {

            }
        };

        OrderAdapter orderAdapter = new OrderAdapter(orderList,  itemClickListener);
        Log.i("OrderAdapter_fg_onCreateView",String.valueOf(orderList.size()));
        
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycleView.setLayoutManager(layoutManager); // 必须设置
        recycleView.setAdapter(orderAdapter);
        return view;
    }
}
