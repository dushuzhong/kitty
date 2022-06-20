package com.asia.kitty.Interface;

// Fragment获得Activity中的组件: getActivity().findViewById(R.id.list)；
// Activity获得Fragment中的组件(根据id和tag都可以)：getFragmentManager.findFragmentByid(R.id.fragment1);

// 定义接口
public interface FragmentCallback {
    // 定义一个获取信息的方法
    public void getResult(String result);
}
