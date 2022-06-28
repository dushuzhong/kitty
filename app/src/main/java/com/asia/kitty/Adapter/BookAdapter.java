package com.asia.kitty.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.asia.kitty.R;
import com.asia.kitty.model.Fruit;

import java.util.ArrayList;
import java.util.List;

// 水果列表适配器，数据绑定和复用

public class BookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static enum ITEM_TYPE {
        ITEM_TYPE_SECTION,
        ITEM_TYPE_ITEM
    }
    private int SECTION_ITEM_NUM  = 10;
    private List<Fruit> mFruitList;
    private ArrayList<Integer> mHeights = new ArrayList<>();

    // 0~300 生成随机数
    private int getRandomHeight() {
        int randomHeight = 100;
//        do {
//            randomHeight = (int) (Math.random() * 300);
//        } while (randomHeight == 0);
//        randomHeight += 100;
        return randomHeight;
    }


    // 定义可以复用的样式1
    public class NormalHolder extends RecyclerView.ViewHolder {
        ImageView fruitImage;
        TextView fruitName;
        View fruitView;

        public NormalHolder(@Nullable View itemView) {
            super(itemView);
            fruitView = itemView; // 单行
            fruitImage = (ImageView)itemView.findViewById(R.id.fruit_image); // 图片
            fruitName = (TextView)itemView.findViewById(R.id.fruit_name); // 文本
        }
    }

    // 可以复用的样式2
    public class SectionHolder extends RecyclerView.ViewHolder {
        public TextView mSectionTv;
        public SectionHolder(View itemView) {
            super(itemView);
            mSectionTv = itemView.findViewById(R.id.item_section_tv);
        }
    }

    public BookAdapter(List<Fruit>mFruitList) {
        this.mFruitList = mFruitList;
        // Log.i("BookAdapter",String.valueOf( mFruitList.size()));

//        if (mFruitList.size() > 0) {
//            for (int i = 0; i < mFruitList.size(); i++) {
//                mHeights.add(getRandomHeight());
//            }
//        }
    }

    // 下面三个方法必须实现
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == ITEM_TYPE.ITEM_TYPE_ITEM.ordinal()) {
            View view = inflater.inflate(R.layout.fruit_item,parent,false);
            NormalHolder viewHolder = new NormalHolder(view);
            viewHolder.fruitView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = viewHolder.getAdapterPosition();
                    BookAdapter.this.onItemClick(v,position);

                    //Fruit fruit = mFruitList.get(position);
                    //Toast.makeText(v.getContext(), "你点击了这个布局", Toast.LENGTH_SHORT).show();
                }
            });

            viewHolder.fruitImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = viewHolder.getAdapterPosition();
                    BookAdapter.this.onItemClick(v,position);

                    //Fruit fruit = mFruitList.get(position);
                    //Toast.makeText(v.getContext(), "你点击了图片", Toast.LENGTH_SHORT).show();
                }
            });
            return viewHolder;
        }
        return new SectionHolder(inflater.inflate(R.layout.base_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof SectionHolder) {
            SectionHolder sectionHolder = (SectionHolder) holder;
            sectionHolder.mSectionTv.setText("第 " + ((position / SECTION_ITEM_NUM) + 1) + " 组");
        } else if (holder instanceof NormalHolder) {
            Fruit fruit = mFruitList.get(position);
            NormalHolder normalHolder = (NormalHolder) holder;
            normalHolder.fruitImage.setImageResource(fruit.getImageId());
            normalHolder.fruitName.setText(fruit.getName());
            // 如果是瀑布流
            // ViewGroup.LayoutParams lp = normalHolder.fruitName.getLayoutParams();
            // lp.height = getRandomHeight();
            // normalHolder.fruitName.setLayoutParams(lp);
        }
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    // 下面是高级方法
    @Override
    public int getItemViewType(int position) {
//        if (position % SECTION_ITEM_NUM == 0) {
//            return ITEM_TYPE.ITEM_TYPE_SECTION.ordinal();
//        }
        return ITEM_TYPE.ITEM_TYPE_ITEM.ordinal();
    }

    //item里面有多个控件可以点击
    public enum  ViewName {
        ITEM,
        PRACTISE
    }

    // 定义点击的回调接口
    public interface OnItemClickListener {
        // 点击布局中item事件回调
        void onItemClick(View v, ViewName viewName, int position);
        // 长按布局回调
        void onItemLongClick(View v);
    }

    private OnItemClickListener mOnItemClickListener;//声明自定义的接口

    //定义方法并暴露给外面的调用者
    public void setOnItemClickListener(OnItemClickListener  listener) {
        this.mOnItemClickListener = listener;
    }

    private void onItemClick(View v, int position) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, ViewName.ITEM, position);
        }

//        if (mOnItemClickListener != null) {
//            switch (v.getId()){
//                case R.id.fruit_name:
//                    mOnItemClickListener.onItemClick(v, ViewName.PRACTISE, position);
//                    break;
//                default:
//                    mOnItemClickListener.onItemClick(v, ViewName.ITEM, position);
//                    break;
//            }
//        }
    }
}
