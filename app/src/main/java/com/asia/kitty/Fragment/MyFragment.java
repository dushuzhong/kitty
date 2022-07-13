package com.asia.kitty.Fragment;

import android.annotation.SuppressLint;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import androidx.annotation.Nullable;
//import android.support.annotation.Nullable;

import com.asia.kitty.Adapter.ProductAdapter;
import com.asia.kitty.CountdownActivity;
import com.asia.kitty.CustomTabActivity;
import com.asia.kitty.R;
import com.asia.kitty.YYHHomeActivity;
import com.asia.kitty.components.SpaceItemDecoration;
import com.asia.kitty.model.GoodProduct;
import com.google.android.gms.analytics.ecommerce.Product;

import java.util.ArrayList;
import java.util.List;

// 定义一个MyFragment
@SuppressLint("ValidFragment")
public class MyFragment extends Fragment {

    private String content;
    private RecyclerView recycleView;
    private List<GoodProduct> productList;

    public MyFragment(String content) {
        this.content = content;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_my_content,container,false);

        recycleView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recycleView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        ProductAdapter.RecycleItemClickListener itemClickListener = new ProductAdapter.RecycleItemClickListener() {
            @Override
            public void onItemClick(View v, ProductAdapter.ViewName viewName, int position) {
                Log.i("MyFragment","111");
                //Intent intent2 = new Intent(getActivity(), YYHHomeActivity.class);
                Intent intent2 = new Intent(getActivity(), CustomTabActivity.class);
                startActivity(intent2);
            }

            @Override
            public void onItemLongClick(View v) {

            }

            @Override
            public void onItemClick(View view, int position) {

            }
        };

        initData();
        ProductAdapter productAdapter = new ProductAdapter(productList,  itemClickListener);
        recycleView.setAdapter(productAdapter);

        SpaceItemDecoration decoration = new SpaceItemDecoration(16);
        recycleView.addItemDecoration(decoration);

        //TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
        //txt_content.setText(content);

        // 美化字体展示效果
        //Typeface typeFace = Typeface.createFromAsset(getContext().getAssets(),"fonts/造字工房乐真体.ttf");// 应用字体
        //txt_content.setElegantTextHeight(true);
        //txt_content.setTypeface(typeFace);

        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initData() {
        productList = new ArrayList<GoodProduct>();
        GoodProduct p1 = new GoodProduct(R.drawable.p1,"我是照片1");
        productList.add(p1);
        GoodProduct p2 = new GoodProduct(R.drawable.p2,"我是照片2");
        productList.add(p2);
        GoodProduct p3 = new GoodProduct(R.drawable.p3,"我是照片3");
        productList.add(p3);
        GoodProduct p4 = new GoodProduct(R.drawable.p4,"我是照片4");
        productList.add(p4);
        GoodProduct p5 = new GoodProduct(R.drawable.p5,"我是照片5");
        productList.add(p5);
        GoodProduct p6 = new GoodProduct(R.drawable.p6,"我是照片6");
        productList.add(p6);
        GoodProduct p7 = new GoodProduct(R.drawable.p2,"我是照片7");
        productList.add(p7);
        GoodProduct p8 = new GoodProduct(R.drawable.p1,"我是照片8");
        productList.add(p8);
        GoodProduct p9 = new GoodProduct(R.drawable.p4,"我是照片9");
        productList.add(p9);
        GoodProduct p10 = new GoodProduct(R.drawable.p6,"我是照片10");
        productList.add(p10);
        GoodProduct p11 = new GoodProduct(R.drawable.p3,"我是照片11");
        productList.add(p11);
    }
}
