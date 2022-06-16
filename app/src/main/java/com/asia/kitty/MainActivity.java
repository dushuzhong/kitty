package com.asia.kitty;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

//import com.asia.kitty.components.EditTextWithDel; // 引入自定义组件
import com.asia.kitty.components.LoadingDialog;
import com.asia.kitty.model.Customer;
import com.asia.kitty.service.HttpUtil;
import com.asia.kitty.service.IPUitl;
import com.asia.kitty.service.MD5Util;
import com.asia.kitty.service.SharedPreUtil;
import com.asia.kitty.service.SignUtil;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private Button btn_start;
    private Button btn_second;
    private Button btn_third;

    private CheckBox box1;
    private Button btn_fouth;
    private Button btn_zhuche;// 注册
    private ImageView imv_pwd_cl;// 清空按钮
    private EditText password_edit;
    private EditText account_edit;
    private final static String TAG = "EditTextWithDel";
    private Drawable imgInable;
    private Drawable imgAble;
    private Context mContext;
    private TextWatcher tw;
    private String ipaddr_g = "";
    public void setNavigationBar() {
        Log.i("setNavigationBar","setNavigationBar");

        Window window = getWindow();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // 状态栏（以上几行代码必须，参考setStatusBarColor|setNavigationBarColor方法源码）
        window.setNavigationBarColor(Color.rgb(255,255,255));
        window.setStatusBarColor(Color.rgb(255,0,0));
        window.setTitle("首页");

    }

    public static void setTitle(Activity activity, int resId) {
        activity.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        activity.setContentView(resId); // activity的布局
        activity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);// 标题栏的布局
    }

    private void textColor() {
        box1.setTextColor(getApplicationContext().getColor(R.color.teal_200));
    }
    private void setTextBlack() {
        box1.setTextColor(getApplicationContext().getColor(R.color.darkGray));
    }

    // 设置删除图片
    public void setDrawable() {
//        if (account_edit.length() < 1)
//            account_edit.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
//        else
//            account_edit.setCompoundDrawablesWithIntrinsicBounds(null, null, imgInable, null);
    }

    // 处理删除事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (imgInable != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Log.e(TAG, "eventX = " + eventX + "; eventY = " + eventY);
            Rect rect = new Rect();
            //account_edit.getGlobalVisibleRect(rect);
            rect.left = rect.right - 100;
            if (rect.contains(eventX, eventY)){}
                //account_edit.setText("");
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("lifeCycle","onCreate");

        setNavigationBar();

        btn_third = findViewById(R.id.btn_third);
        btn_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,BaseAdapterActivity.class);
                startActivity(intent);
            }
        });

        btn_zhuche = findViewById(R.id.btn_zhuche);
        btn_zhuche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PullRefreshActivity.class);
                startActivity(intent);
            }
        });


        tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e("TextInputEditText", "beforeTextChanged 修改前：" + s);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("TextInputEditText", "onTextChanged 修改中：" + s);


            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("TextInputEditText", "afterTextChanged 修改后：" + s.toString());
                if (s.toString().length() > 0) {
                    imv_pwd_cl.setVisibility(View.VISIBLE);
                }
            }
        };


        imv_pwd_cl = findViewById(R.id.imv_pwd_cl);
        imv_pwd_cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password_edit.removeTextChangedListener(tw);
                password_edit.setText("");
                password_edit.addTextChangedListener(tw);
            }
        });
        password_edit = findViewById(R.id.password_edit);
        password_edit.addTextChangedListener(tw);

        account_edit = findViewById(R.id.account_edit);

        box1 = (CheckBox) findViewById(R.id.box);
        textColor();
        box1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
//                    if(show.getVisibility()==View.VISIBLE){
//                        show.setText(love+box1.getText());
//                        textColor();
//                    }else {
//                        show.setVisibility(View.VISIBLE);
//                        show.setText(love+box1.getText());
//                    }
                    Log.i("checkbox",String.valueOf(isChecked));
                    textColor();
                }else{
//                    show.setText(not);
//                    setTextBlack();
                    Log.i("checkbox",String.valueOf(isChecked));
                    setTextBlack();
                }
            }
        });

        btn_start = findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                Log.i("btn_start","abc");

                //String url = "http://api.jisuapi.com/illegal/carorg2?appkey=de394933e1a3e2db";
                //getAsyncRequest(url);

                loginService();


//                PopupMenu popup = new PopupMenu(MainActivity.this,btn_start);
//                popup.getMenuInflater().inflate(R.xml.menu_pop, popup.getMenu());
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()){
//                            case R.id.lpig:
//                                Toast.makeText(MainActivity.this,"你点了小猪~",
//                                        Toast.LENGTH_SHORT).show();
//                                break;
//                            case R.id.bpig:
//                                Toast.makeText(MainActivity.this,"你点了大猪~",
//                                        Toast.LENGTH_SHORT).show();
//                                break;
//                        }
//                        return true;
//                    }
//                });
//                popup.show();

            }
        });
        btn_second = findViewById(R.id.btn_second);
        btn_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                //startActivity(intent);
                startActivityForResult(intent,1);
            }
        });

        btn_fouth = findViewById(R.id.btn_fouth);
        btn_fouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this,Home2Activity.class);
                //Intent intent = new Intent(MainActivity.this,AnimalActivity.class);
                Intent intent = new Intent(MainActivity.this,ChatActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
     通过startActivityForResult的方式接受返回数据的方法
     requestCode：请求的标志,给每个页面发出请求的标志不一样，这样以后通过这个标志接受不同的数据
     resultCode：这个参数是setResult(int resultCode,Intent data)方法传来的,这个方法用在传来数据的那个页面
    */
    @Override
    protected  void onActivityResult(int requestCode,int resultCode ,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("onActivityResult","onActivityResult");
        if (requestCode == 1 && resultCode == 2) {//当请求码是1&&返回码是2进行下面操作
            String content = data.getStringExtra("data");
            //tv.setText(content);
            Toast.makeText(MainActivity.this,content,Toast.LENGTH_LONG).show();
        }
    }

    // 同步调用接口
    public void getSyn(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //创建OkHttpClient对象
                    OkHttpClient client = new OkHttpClient();
                    //创建Request
                    Request request = new Request.Builder()
                            .url(url)//访问连接
                            .get()
                            .build();
                    //创建Call对象
                    Call call = client.newCall(request);
                    //通过execute()方法获得请求响应的Response对象
                    Response response = call.execute();
                    if (response.isSuccessful()) {
                        //处理网络请求的响应，处理UI需要在UI线程中处理
                        //...
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 异步GET请求
    public void getAsyncRequest(String url) {
        Log.i("getAsyncRequest","OnRequest");
        LoadingDialog.getInstance(MainActivity.this).show();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //...
                Log.i("getAsyncRequest_onFailure", e.toString());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String result = response.body().string();
                    //处理UI需要切换到UI线程处理
                    Log.i("getAsyncRequest_onResponse", result);
                    SharedPreUtil sh = SharedPreUtil.getInstance(MainActivity.this);
                    sh.saveUserInfo(account_edit.getText().toString(),password_edit.getText().toString());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LoadingDialog.getInstance(MainActivity.this).hide();

                            Intent intent = new Intent(MainActivity.this,Home2Activity.class);
                            startActivityForResult(intent,1);
                        }
                    });

                    response.body().close();

                }
            }
        });
    }

    // 异步登录post请求
    private void loginService() {
        Log.i("TAG","loginService");
        LoadingDialog.getInstance(MainActivity.this).show();

        Thread loginThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String ipaddr = "27.17.114.163";
                String loginName = "15527890231";
                String loginPwd = "123456";
                String loginType = "1";
                String signStr = "";
                String systemType = "ios";
                String versionCode = "1.3.2";
                String versionNum = "32";

                Map<String,String> map = new HashMap<String,String>();
                map.put("ipaddr",ipaddr);
                map.put("loginName",loginName);
                map.put("loginPwd", loginPwd);
                map.put("loginType", loginType);
                map.put("sign",signStr);
                map.put("systemType",systemType);
                map.put("versionCode",versionCode);
                map.put("versionNum",versionNum);

                signStr = SignUtil.makeSign(map);
                map.put("sign",signStr);

                JSONObject jsonObject = new JSONObject(map);
                Log.i("map_test:",jsonObject.toString());

                OkHttpClient client = new OkHttpClient();
//                FormBody formBody = new FormBody
//                        .Builder()
//                        .add("ipaddr",ipaddr)
//                        .add("loginName",loginName)
//                        .add("loginPwd",loginPwd)
//                        .add("loginType",loginType)
//                        .add("sign",signStr)
//                        .add("systemType",systemType)
//                        .add("versionCode",versionCode)
//                        .add("versionNum",versionNum)
//                        .build();


                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, jsonObject.toString());

                Request request = new Request
                        .Builder()
                        .addHeader("Content-Type","application/json;charset=UTF-")
                        .post(body)
                        .url("https://test.hbglyy.cn/api-b2b-app/login/pwlogin")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LoadingDialog.getInstance(MainActivity.this).hide();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LoadingDialog.getInstance(MainActivity.this).hide();
                            }
                        });

                        String jsonData = response.body().string();
                        Log.i("login_response:",jsonData);
                        if (response.code() == 200) {
                            jsonJXLoginData(jsonData);
                        }
                        response.body().close();
                    }
                });
            }
        });
        loginThread.start();
    }

    private void jsonJXLoginData(String jsonData) {
        if (jsonData != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonData);
                int responseCode = jsonObject.getInt("code");
                if (responseCode == 0) {
                    String customerJson = jsonObject.getString("customer");
                    Gson gson = new Gson();
                    Customer customer = gson.fromJson(customerJson,Customer.class);
                    Log.i("login_response_uInfo",customer.getCustomerId());
                    Looper.prepare();
                    Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,Home2Activity.class);
                    startActivity(intent);
                    Looper.loop();
                } else {
                    Looper.prepare();
                    Toast.makeText(MainActivity.this,"用户名或密码不正确",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    // 异步POST请求
    public void post(String url,String key,String value){
        OkHttpClient client = new OkHttpClient();

        FormBody body = new FormBody.Builder()
                .add(key,value)
                .build();
        // MultipartBody body = new MultipartBody.Builder()
        // 添加表单参数,支持文件传输
        // .addFormDataPart(key,value)
        // .addFormDataPart(name, fileName, RequestBody.create(MediaType.get("application/octet-stream"), file))
        // .build();

        // MediaType jsonType = MediaType.parse("application/json; charset=utf-8");
        // String jsonStr = "{\"username\":\"Sia\"}";//json数据.
        //RequestBody body = RequestBody.create(jsonType, jsonStr);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //...
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String result = response.body().string();
                    //处理UI需要切换到UI线程处理
                }
            }
        });
    }

    // 上传文件
    public void uploadFile(String url, File file) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body =  RequestBody.create(MediaType.get("application/octet-stream"), file);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        //call.enqueue();
        //...
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i("lifeCycle","onStart");
        Log.i("generateTime",HttpUtil.generateTime());
        //Log.i("HeHe", MD5Util.getMD5("呵呵"));
        SharedPreUtil sh = SharedPreUtil.getInstance(MainActivity.this);

        Map<String,String> data = sh.readUserInfo();
        account_edit.setText(data.get("username"));
        password_edit.setText(data.get("passwd"));

        IPUitl.getNetIp();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i("lifeCycle","onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i("lifeCycle","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i("lifeCycle","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("lifeCycle","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i("lifeCycle","onDestroy");
    }
}