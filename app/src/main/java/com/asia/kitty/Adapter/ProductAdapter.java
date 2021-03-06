package com.asia.kitty.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.asia.kitty.Interface.RecycleItemClickListener;
import com.asia.kitty.R;
import com.asia.kitty.model.GoodProduct;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<GoodProduct> products;
    private static RecycleItemClickListener itemClickListener;

    public ProductAdapter(List<GoodProduct> list,ProductAdapter.RecycleItemClickListener clickListener) {
        products = list;
        itemClickListener = clickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.waterflow_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(products.get(position).getImg());
        holder.textView.setText(products.get(position).getTitle());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                ProductAdapter.this.onItemClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.waterflow_item_img);
            textView = (TextView) itemView.findViewById(R.id.waterflow_item_title);

        }

        @Override
        public void onClick(View view) {
            Log.i("prod11111","222");
            itemClickListener.onItemClick(view,this.getLayoutPosition());
        }
    }

    // ???????????????????????????
    public interface RecycleItemClickListener {
        // ???????????????item????????????
        void onItemClick(View v, ProductAdapter.ViewName viewName, int position);
        // ??????????????????
        void onItemLongClick(View v);

        void onItemClick(View view, int layoutPosition);
    }

    private ProductAdapter mOnItemClickListener;//????????????????????????

    //??????????????????????????????????????????
    public void setOnItemClickListener(ProductAdapter.RecycleItemClickListener listener) {
        this.itemClickListener = listener;
    }

    //item?????????????????????????????????
    public enum  ViewName {
        ITEM1,
    }

    private void onItemClick(View v, int position) {
        if (itemClickListener != null) {
            itemClickListener.onItemClick(v, ViewName.ITEM1, position);
        }
    }

}
