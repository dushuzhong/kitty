package com.asia.kitty.components;

import android.content.Context;
//import androidx.core.widget.
//import android.support.v4.widget.SwipeRefreshLayout;

import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.asia.kitty.R;

import java.util.Date;

/**
 * ListView下拉刷新
 *
 */
public class CustomListView extends ListView implements AbsListView.OnScrollListener {

    private final static int RELEASE_To_REFRESH = 0;
    private final static int PULL_To_REFRESH = 1;
    private final static int REFRESHING = 2;
    private final static int DONE = 3;
    private final static int LOADING = 4;

    // 实际的padding的距离与界面上偏移距离的比例
    private final static int RATIO = 3;
    private LayoutInflater inflater;
    private LinearLayout headView;

    private TextView tipsTextview;
    private TextView lastUpdatedTextView;
    private ImageView arrowImageView;
    private ProgressBar progressBar;
    private RotateAnimation animation;
    private RotateAnimation reverseAnimation;

    // 用于保证startY的值在一个完整的touch事件中只被记录一次
    private boolean isRecored;

    private int headContentWidth;
    private int headContentHeight;

    private int startY;
    private int firstItemIndex;

    private int state;

    private boolean isBack;

    private OnRefreshListener refreshListener;
    private OnLoadListener loadListener;

    private boolean isRefreshable;

    private ProgressBar moreProgressBar;
    private TextView loadMoreView;
    private View moreView;

    public CustomListView(Context context) {
        super(context);
        init(context);
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setCacheColorHint(context.getResources().getColor(R.color.transparent));

        inflater = LayoutInflater.from(context);
        headView = (LinearLayout) inflater.inflate(R.layout.header, null);

        arrowImageView = (ImageView) headView.findViewById(R.id.head_arrowImageView);
        arrowImageView.setMinimumWidth(70);
        arrowImageView.setMinimumHeight(50);
        progressBar = (ProgressBar) headView.findViewById(R.id.head_progressBar);
        tipsTextview = (TextView) headView.findViewById(R.id.head_tipsTextView);
        lastUpdatedTextView = (TextView) headView.findViewById(R.id.head_lastUpdatedTextView);

        measureView(headView);
        headContentHeight = headView.getMeasuredHeight();
        headContentWidth = headView.getMeasuredWidth();

        headView.setPadding(0, -1 * headContentHeight, 0, 0);
        headView.invalidate();

        Log.v("size", "width:" + headContentWidth + " height:" + headContentHeight);

        addHeaderView(headView, null, false);
        setOnScrollListener(this);

        animation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(250);
        animation.setFillAfter(true);

        reverseAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        reverseAnimation.setInterpolator(new LinearInterpolator());
        reverseAnimation.setDuration(200);
        reverseAnimation.setFillAfter(true);

        state = DONE;
        isRefreshable = false;

        moreView = LayoutInflater.from(context).inflate(R.layout.listfooter_more, null);
        moreView.setVisibility(View.VISIBLE);
        moreProgressBar = (ProgressBar) moreView.findViewById(R.id.pull_to_refresh_progress);
        loadMoreView = (TextView) moreView.findViewById(R.id.load_more);
        moreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoad();
            }
        });
        addFooterView(moreView);
    }

    public void onScroll(AbsListView arg0, int firstVisiableItem, int arg2, int arg3) {
        firstItemIndex = firstVisiableItem;
    }

    public void onScrollStateChanged(AbsListView arg0, int arg1) {

    }

    public boolean onTouchEvent(MotionEvent event) {

        if (isRefreshable) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (firstItemIndex == 0 && !isRecored) {
                        isRecored = true;
                        startY = (int) event.getY();
                    }
                    break;

                case MotionEvent.ACTION_UP:

                    if (state != REFRESHING && state != LOADING) {
                        if (state == DONE) {

                        }
                        if (state == PULL_To_REFRESH) {
                            state = DONE;
                            changeHeaderViewByState();
                        }
                        if (state == RELEASE_To_REFRESH) {
                            state = REFRESHING;
                            changeHeaderViewByState();
                            onRefresh();
                        }
                    }

                    isRecored = false;
                    isBack = false;

                    break;

                case MotionEvent.ACTION_MOVE:
                    int tempY = (int) event.getY();

                    if (!isRecored && firstItemIndex == 0) {
                        isRecored = true;
                        startY = tempY;
                    }

                    if (state != REFRESHING && isRecored && state != LOADING) {

                        // 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动
                        // 可以松手去刷新了
                        if (state == RELEASE_To_REFRESH) {

                            setSelection(0);

                            // 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
                            if (((tempY - startY) / RATIO < headContentHeight) && (tempY - startY) > 0) {
                                state = PULL_To_REFRESH;
                                changeHeaderViewByState();
                            }
                            // 一下子推到顶了
                            else if (tempY - startY <= 0) {
                                state = DONE;
                                changeHeaderViewByState();
                            }
                            // 往下拉了，或者还没有上推到屏幕顶部掩盖head的地步
                        }
                        // 还没有到达显示松开刷新的时候,DONE或者是PULL_To_REFRESH状态
                        if (state == PULL_To_REFRESH) {

                            setSelection(0);

                            // 下拉到可以进入RELEASE_TO_REFRESH的状态
                            if ((tempY - startY) / RATIO >= headContentHeight) {
                                state = RELEASE_To_REFRESH;
                                isBack = true;
                                changeHeaderViewByState();
                            } else if (tempY - startY <= 0) {
                                state = DONE;
                                changeHeaderViewByState();
                            }
                        }

                        if (state == DONE) {
                            if (tempY - startY > 0) {
                                state = PULL_To_REFRESH;
                                changeHeaderViewByState();
                            }
                        }

                        if (state == PULL_To_REFRESH) {
                            headView.setPadding(0, -1 * headContentHeight + (tempY - startY) / RATIO, 0, 0);

                        }

                        if (state == RELEASE_To_REFRESH) {
                            headView.setPadding(0, (tempY - startY) / RATIO - headContentHeight, 0, 0);
                        }

                    }

                    break;
            }
        }

        return super.onTouchEvent(event);
    }

    // 当状态改变时候，调用该方法，以更新界面
    private void changeHeaderViewByState() {
        switch (state) {
            case RELEASE_To_REFRESH:
                arrowImageView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                tipsTextview.setVisibility(View.VISIBLE);
                lastUpdatedTextView.setVisibility(View.VISIBLE);

                arrowImageView.clearAnimation();
                arrowImageView.startAnimation(animation);
                tipsTextview.setText("松开刷新");

                break;
            case PULL_To_REFRESH:
                progressBar.setVisibility(View.GONE);
                tipsTextview.setVisibility(View.VISIBLE);
                lastUpdatedTextView.setVisibility(View.VISIBLE);
                arrowImageView.clearAnimation();
                arrowImageView.setVisibility(View.VISIBLE);
                // 是由RELEASE_To_REFRESH状态转变来的
                if (isBack) {
                    isBack = false;
                    arrowImageView.clearAnimation();
                    arrowImageView.startAnimation(reverseAnimation);

                    tipsTextview.setText("下拉刷新");
                } else {
                    tipsTextview.setText("下拉刷新");
                }
                break;

            case REFRESHING:
                headView.setPadding(0, 0, 0, 0);
                progressBar.setVisibility(View.VISIBLE);
                arrowImageView.clearAnimation();
                arrowImageView.setVisibility(View.GONE);
                tipsTextview.setText("正在刷新...");
                lastUpdatedTextView.setVisibility(View.VISIBLE);

                break;
            case DONE:
                headView.setPadding(0, -1 * headContentHeight, 0, 0);
                progressBar.setVisibility(View.GONE);
                arrowImageView.clearAnimation();
                arrowImageView.setImageResource(R.drawable.arrow);

                tipsTextview.setText("下拉刷新");
                lastUpdatedTextView.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void setonRefreshListener(OnRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
        isRefreshable = true;
    }

    public void setonLoadListener(OnLoadListener loadListener) {
        this.loadListener = loadListener;
    }

    public interface OnRefreshListener {
        public void onRefresh();
    }

    public interface OnLoadListener { // 直接定义接口方法?
        public void onLoad();
    }

    @SuppressWarnings("deprecation")
    public void onRefreshComplete() {
        state = DONE;
        lastUpdatedTextView.setText("最近更新:" + new Date().toLocaleString());
        changeHeaderViewByState();
    }

    private void onLoad() {
        if (loadListener != null) {
            moreProgressBar.setVisibility(View.VISIBLE);
            loadMoreView.setText(getContext().getString(R.string.load_more));
            loadListener.onLoad();
        }
    }

    public void onLoadComplete() {
//		moreView.setVisibility(View.GONE);
        moreProgressBar.setVisibility(View.GONE);
        loadMoreView.setText(getContext().getString(R.string.more_data));
    }

    private void onRefresh() {
        if (refreshListener != null) {
            refreshListener.onRefresh();
        }
    }

    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    @SuppressWarnings("deprecation")
    public void setAdapter(BaseAdapter adapter) {
        lastUpdatedTextView.setText("最近更新:" + new Date().toLocaleString());
        super.setAdapter(adapter);
    }
}
