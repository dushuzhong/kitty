package com.asia.kitty;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.asia.kitty.components.BottomDialog;
import com.asia.kitty.components.CouponInfoDialog;
import com.asia.kitty.components.CustomListView;
import com.asia.kitty.model.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 下拉刷新
 */
public class PullRefreshActivity extends Activity {

    private static final String TAG = PullRefreshActivity.class.getSimpleName();
    private static final int LOAD_DATA_FINISH = 10;
    private static final int REFRESH_DATA_FINISH = 11;

    private List<AppInfo> mList = new ArrayList<AppInfo>();
    private CustomListAdapter mAdapter;
    private CustomListView mListView;
    private MyItemClickListener myItemClickListener;
    private CouponItemListener couponClickListener;
    private int count = 10;
    private BottomDialog bottomDialog;

    private CouponInfoDialog infoDialog;

    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_DATA_FINISH:
                    if(mAdapter!=null){
                        mAdapter.notifyDataSetChanged();
                    }
                    mListView.onRefreshComplete();	//下拉刷新完成
                    break;
                case LOAD_DATA_FINISH:
                    if(mAdapter!=null){
                        mAdapter.notifyDataSetChanged();
                    }
                    mListView.onLoadComplete();	//加载更多完成
                    break;
                default:
                    break;
            }
        };
    };

    // dialog 事件回调处理
    public void dialogHandle(int itemPosition) {
        BottomDialog.BottomDialogListener ls = new BottomDialog.BottomDialogListener() {
            @Override
            public void cancelAction() {
                Log.i(TAG,"cancelAction!" + itemPosition);
            }

            @Override
            public void confirmAction(int position) {
                if (position == 0) {
                    Log.i(TAG,"confirmAction-" + "拍照");
                } else if (position == 1) {
                    Log.i(TAG, "confirmAction-" + "相册" + itemPosition);
                }
            }
        };

        bottomDialog = new BottomDialog(ls, PullRefreshActivity.this);
        bottomDialog.setCancelTitle("隐藏弹框");
        bottomDialog.showDialog();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_pull);

        buildAppData();

        // 单元行点击事件监听
        myItemClickListener = new MyItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                // 在这里做处理
                Log.i(TAG,"myItemClickListener_onItemClick" + position);
                dialogHandle(position);
            }
        };

        couponClickListener = new CouponItemListener() {
            @Override
            public void onItemClick(View v, int position) {

            }
        };

        mAdapter = new CustomListAdapter(myItemClickListener,this);
        mListView = (CustomListView) findViewById(R.id.mListView);
        mListView.setAdapter(mAdapter);

        mListView.setonRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //TODO 下拉刷新
                Log.e(TAG, "onRefresh");
                loadData(0);
            }
        });

        mListView.setonLoadListener(new CustomListView.OnLoadListener() {
            @Override
            public void onLoad() {
                //TODO 加载更多
                Log.e(TAG, "onLoad");
                loadData(1);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.e(TAG, "click position:" + position);

                infoDialog = new CouponInfoDialog(new CouponInfoDialog.CouponInfoDialogListener() {
                    @Override
                    public void cancelAction() {

                    }

                    @Override
                    public void confirmAction(int position) {

                    }
                }, PullRefreshActivity.this);
                infoDialog.showDialog();
            }
        });

    }

    // 定义好接口方法
    public interface MyItemClickListener {
        void onItemClick(View v, int position);
    }

    // 券弹框
    public interface CouponItemListener {
        void onItemClick(View v, int position);
    }

    public void loadData(final int type){
        new Thread(){
            @Override
            public void run() {

                if(type == 0){//下拉刷新
                    count = 0;
                    mList.clear();
                }

                for(int i=count;i<count+10;i++){
                    AppInfo ai = new AppInfo();
                    ai.setAppIcon(BitmapFactory.decodeResource(getResources(),
                            R.drawable.progressbar_bg));
                    ai.setAppName("应用Demo_" + i);
                    ai.setAppVer("版本: " + (i % 10 + 1) + "." + (i % 8 + 2) + "."
                            + (i % 6 + 3));
                    ai.setAppSize("大小: " + i * 10 + "MB");
                    mList.add(ai);
                }
                count += 10;

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(type == 0){//下拉刷新
                    handler.sendEmptyMessage(REFRESH_DATA_FINISH);
                }else if(type == 1){
                    handler.sendEmptyMessage(LOAD_DATA_FINISH);
                }
            }
        }.start();
    }

    /**
     * 初始化应用数据
     */
    private void buildAppData() {
        for (int i = 0; i < 10; i++) {
            AppInfo ai = new AppInfo();
            ai.setAppIcon(BitmapFactory.decodeResource(getResources(), R.drawable.progressbar_bg));
            ai.setAppName("应用Demo_" + i);
            ai.setAppVer("版本: " + (i % 10 + 1) + "." + (i % 8 + 2) + "." + (i % 6 + 3));
            ai.setAppSize("大小: " + i * 10 + "MB");
            mList.add(ai);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    public class CustomListAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public CustomListAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        public CustomListAdapter(MyItemClickListener listener,Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int arg0) {
            return mList.get(arg0);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (getCount() == 0) {
                return null;
            }

            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item_nomal, null);
                holder = new ViewHolder();
                holder.ivImage = (ImageView) convertView
                        .findViewById(R.id.ivIcon);
                holder.tvName = (TextView) convertView
                        .findViewById(R.id.tvName);
                holder.tvVer = (TextView) convertView.findViewById(R.id.tvVer);
                holder.tvSize = (TextView) convertView
                        .findViewById(R.id.tvSize);
                holder.btnClick = (Button) convertView.findViewById(R.id.btnClick);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (mList.size() > 0) {
                AppInfo ai = mList.get(position);
                holder.ivImage.setImageBitmap(ai.getAppIcon());
                holder.tvName.setText(ai.getAppName());
                holder.tvVer.setText(ai.getAppVer());
                holder.tvSize.setText(ai.getAppSize());
                holder.btnClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myItemClickListener.onItemClick(view, position);
                    }
                });
            }
            return convertView;
        }
    }

    public static class ViewHolder {
        private ImageView ivImage;
        private TextView tvName;
        private TextView tvVer;
        private TextView tvSize;
        private Button btnClick;
    }
}
