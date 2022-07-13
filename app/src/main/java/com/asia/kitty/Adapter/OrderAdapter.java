package com.asia.kitty.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.asia.kitty.R;
import com.asia.kitty.model.FYOrderModel;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<FYOrderModel> orderModels;
    private static OrderAdapter.RecycleItemClickListener itemClickListener;

    public OrderAdapter(List<FYOrderModel> list, OrderAdapter.RecycleItemClickListener clickListener) {
        orderModels = list;
        itemClickListener = clickListener;

    }

    //item里面有多个控件可以点击
    public enum ViewName {
        ITEM1,
    }

    // 定义点击的回调接口
    public interface RecycleItemClickListener {
        // 点击布局中item事件回调
        void onItemClick(View v, OrderAdapter.ViewName viewName, int position);

        void onItemClick(View view, int layoutPosition);
    }

    //定义方法并暴露给外面的调用者
    public void setOnItemClickListener(OrderAdapter.RecycleItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Log.i("OrderAdapter_onCreateViewHolder","2222");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_list,parent,false);
        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Log.i("OrderAdapter_onBindViewHolder", orderModels.get(position).getCompanyName());
        holder.tv_title.setText(orderModels.get(position).getCompanyName());
        holder.tv_time.setText(orderModels.get(position).getOrderTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                OrderAdapter.this.onItemClick(view, position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return orderModels.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title;
        TextView tv_time;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_companyName);
            tv_time = (TextView) itemView.findViewById(R.id.tv_order_time);
        }

//        @Override
//        public void onClick(View view) {
//            Log.i("prod11111","222");
//            itemClickListener.onItemClick(view, this.getLayoutPosition());
//        }
    }

    private void onItemClick(View v, int position) {
        if (itemClickListener != null) {
            itemClickListener.onItemClick(v, OrderAdapter.ViewName.ITEM1, position);
        }
    }

}
