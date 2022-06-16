package com.asia.kitty.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.asia.kitty.R;
import com.asia.kitty.model.App;
import com.asia.kitty.model.Book;
import java.util.ArrayList;

// 实现类似聊天页面
public class MutiLayoutAdapter extends BaseAdapter {

    // 定义两个类别标志
    private static final int TYPE_BOOK = 0;
    private static final int TYPE_APP = 1;
    private Context mContext;
    private ArrayList<Object> mData = null;

    // 多类别列表
    public MutiLayoutAdapter(Context mContext,ArrayList<Object>mData){
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    //多布局的核心，通过这个判断类别
    @Override
    public int getItemViewType(int position) {
        if (mData.get(position) instanceof App) { // 判断数据类型
            return TYPE_APP;
        } else if (mData.get(position) instanceof Book) {
            return TYPE_BOOK;
        } else {
            return super.getItemViewType(position);
        }
    }

    //类别数目
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int type = getItemViewType(i);
        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        if(view == null){
            switch (type){
                case TYPE_APP:
                    holder1 = new ViewHolder1();
                    view = LayoutInflater.from(mContext).inflate(R.layout.item_one, viewGroup, false);
                    holder1.img_icon = (ImageView) view.findViewById(R.id.img_icon);
                    holder1.txt_aname = (TextView) view.findViewById(R.id.txt_aname);
                    view.setTag(R.id.Tag_APP,holder1);
                    break;
                case TYPE_BOOK:
                    holder2 = new ViewHolder2();
                    view = LayoutInflater.from(mContext).inflate(R.layout.item_two, viewGroup, false);
                    holder2.txt_bname = (TextView) view.findViewById(R.id.txt_bname);
                    holder2.txt_bauthor = (TextView) view.findViewById(R.id.txt_bauthor);
                    view.setTag(R.id.Tag_Book,holder2);
                    break;
            }
        }else{
            switch (type){
                case TYPE_APP:
                    holder1 = (ViewHolder1) view.getTag(R.id.Tag_APP);
                    break;
                case TYPE_BOOK:
                    holder2 = (ViewHolder2) view.getTag(R.id.Tag_Book);
                    break;
            }
        }

        Object obj = mData.get(i);
        //设置下控件的值
        switch (type){
            case TYPE_APP:
                App app = (App) obj;
                if(app != null){
                    holder1.img_icon.setImageResource(app.getaIcon());
                    holder1.txt_aname.setText(app.getaName());
                }
                break;
            case TYPE_BOOK:
                Book book = (Book) obj;
                if(book != null){
                    holder2.txt_bname.setText(book.getbName());
                    holder2.txt_bauthor.setText(book.getbAuthor());
                }
                break;
        }
        return view;
    }

    //两个不同的ViewHolder
    private static class ViewHolder1{
        ImageView img_icon;
        TextView txt_aname;
    }

    private static class ViewHolder2{
        TextView txt_bname;
        TextView txt_bauthor;
    }
}
