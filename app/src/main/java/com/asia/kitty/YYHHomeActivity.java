package com.asia.kitty;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleCoroutineScope;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asia.kitty.components.HomeNavigationBar;
import com.asia.kitty.components.scroll.MainAdapter;
import com.asia.kitty.components.scroll.MainViewModel;
import com.asia.kitty.components.scroll.PagedListFetcher;

import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class YYHHomeActivity  extends AppCompatActivity {

    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private MainViewModel viewModel;
    private MainAdapter adapter;
    private DefaultItemAnimator itemAnimator;
    private HomeNavigationBar navigationBar;
    private String content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fg_my_set);

        //View view = inflater.inflate(R.layout.fg_my_set, container,false);

        Context context = YYHHomeActivity.this;
        layoutManager = new LinearLayoutManager(context);


        // 设置标题
        navigationBar = (HomeNavigationBar) findViewById(R.id.home_navigationBar);
        navigationBar.setTitle("优药汇");

        recyclerView = findViewById(R.id.rl_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        //recyclerView.setRecycledViewPool(viewModel.getRecyclerViewPool());

        adapter = new MainAdapter(context);
        recyclerView.setAdapter(adapter);

        bindScrollListener(recyclerView);

    }

    private void bindScrollListener(RecyclerView recyclerView) {
        LifecycleCoroutineScope scope = new LifecycleCoroutineScope() {
            @NonNull
            @Override
            public Lifecycle getLifecycle$lifecycle_runtime_ktx_release() {
                return null;
            }

            @NonNull
            @Override
            public CoroutineContext getCoroutineContext() {
                return null;
            }
        };

        LifecycleOwner owner = (LifecycleOwner) YYHHomeActivity.this;
        //LifecycleCoroutineScope scope1 = YYHHomeActivity.this.lifecycleCoroutineScope;
        PagedListFetcher fetch = new PagedListFetcher(recyclerView, owner, new Function2<Integer, Function1<? super Boolean, Unit>, Unit>() {
            @Override
            public Unit invoke(Integer integer, Function1<? super Boolean, Unit> unitFunction1) {

                if (integer == 1) {
                    if (recyclerView.getAdapter() instanceof MainAdapter) {
                        Log.i("MainAdapter_is" , String.valueOf(recyclerView.getAdapter()));

                        ((MainAdapter) recyclerView.getAdapter()).loadTabData(scope);
                        unitFunction1.invoke(false);
                    }
                }
                return null;
            }
        });
    }
}
