package com.asia.kitty.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asia.kitty.R;

import com.asia.kitty.components.HomeNavigationBar;
import com.asia.kitty.components.scroll.MainAdapter;
import com.asia.kitty.components.scroll.MainViewModel;
import com.asia.kitty.components.scroll.PagedListFetcher;

import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import androidx.lifecycle.LifecycleCoroutineScope;

// 自定义首页
public class YYHHomeFragment extends Fragment {

    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private MainViewModel viewModel;
    private MainAdapter adapter;
    private DefaultItemAnimator itemAnimator;
    private HomeNavigationBar navigationBar;
    private String content;
    private LifecycleCoroutineScope lifecycleCoroutineScope;

    public YYHHomeFragment(String content) {
        this.content = content;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fg_my_set, container,false);

        Context context = getContext();
        layoutManager = new LinearLayoutManager(context);

        lifecycleCoroutineScope = new LifecycleCoroutineScope() {
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

        // 设置标题
        navigationBar = (HomeNavigationBar) view.findViewById(R.id.home_navigationBar);
        navigationBar.setTitle("优药汇");

        recyclerView = view.findViewById(R.id.rl_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        //recyclerView.setRecycledViewPool(viewModel.getRecyclerViewPool());

        adapter = new MainAdapter(context);
        recyclerView.setAdapter(adapter);

        bindScrollListener(recyclerView);

        return view;
    }

    private void bindScrollListener(RecyclerView recyclerView) {

        PagedListFetcher fetch = new PagedListFetcher(recyclerView, getViewLifecycleOwner(), new Function2<Integer, Function1<? super Boolean, Unit>, Unit>() {
            @Override
            public Unit invoke(Integer integer, Function1<? super Boolean, Unit> unitFunction1) {

                if (integer == 1) {
                    if (recyclerView.getAdapter() instanceof MainAdapter) {
                        Log.i("MainAdapter_is" , String.valueOf(recyclerView.getAdapter()));
                        LifecycleOwner owner = (LifecycleOwner) getViewLifecycleOwner();
                        Lifecycle lifecycle = (Lifecycle)owner.getLifecycle();
                        ((MainAdapter) recyclerView.getAdapter()).loadTabData(lifecycleCoroutineScope);
                        unitFunction1.invoke(false);
                    }
                }
                return null;
            }
        });
    }
}
