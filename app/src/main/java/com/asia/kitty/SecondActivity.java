package com.asia.kitty;

import android.content.Intent;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    private Button btBack;
    private ScrollView scrollView;
    private TextView textView;
    String content="你好";//想返回的内容
    private LinearLayout ll;

    // 点击返回按钮
    public void backUp() {
        Intent data = new Intent();
        //name相当于一个key,content是返回的内容
        data.putExtra("data",content);
        //resultCode是返回码,用来确定是哪个页面传来的数据，这里设置返回码是2
        //这个页面传来数据,要用到下面这个方法setResult(int resultCode,Intent data)
        setResult(2,data);
        //结束当前页面
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        textView = findViewById(R.id.textView);
        ll = findViewById(R.id.ll);

//        StringBuilder sb = new StringBuilder();
//        for (int i = 1; i <= 100; i++) {
//            sb.append("呵呵 * " + i + "\n");
//        }
//        textView.setText(sb.toString());

        for (int i = 0; i < 100; i++) {
            String str = new String();
            str = "第" + i + "行";
            TextView text_view = new TextView(SecondActivity.this);
            text_view.setText(str);
            text_view.setBackgroundColor(Color.CYAN);
            text_view.setTextColor(Color.rgb(255,255,255));

            RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            lp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            text_view.setLayoutParams(lp);
            ll.addView(text_view);// ②
        }

        btBack =(Button) findViewById(R.id.button);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //backUp();
                scrollView.fullScroll(ScrollView.FOCUS_UP);// 滑动到顶部
            }
        });
    }
}