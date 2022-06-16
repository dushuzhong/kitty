package com.asia.kitty.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.asia.kitty.components.LoadingDialog;

import java.util.HashMap;
import java.util.Map;

// 存储和读取数据
public class SharedPreUtil {
    private Context mContext;
    private static SharedPreUtil instance;//定义单例核心变量

    public SharedPreUtil() {
        super();
    }

    public SharedPreUtil(Context context) {
        this.mContext = context;
    }

    public static SharedPreUtil getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreUtil(context);
        }
        return instance;
    }

    // 保存用户信息到mysp
    public void saveUserInfo(String username, String passwd) {
        SharedPreferences sp = mContext.getSharedPreferences("mysp",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username",username);
        editor.putString("passwd",passwd);
        editor.putInt("age",20);
        editor.commit();
    }

    // 从mysp文件读取用户信息
    public Map<String,String> readUserInfo() {
        Map<String, String> data = new HashMap<String,String>();
        SharedPreferences sp = mContext.getSharedPreferences("mysp",Context.MODE_PRIVATE);
        data.put("username",sp.getString("username",""));
        data.put("passwd",sp.getString("passwd",""));
        return data;
    }

    // 移除用户信息
    private void removeUserInfo() {
        SharedPreferences sp = mContext.getSharedPreferences("mysp",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("age");
        editor.commit();
        Log.i("removeUserInfo","移除年龄信息");
    }

    // 清空数据
    private void clearUserInfo() {
        SharedPreferences sp = mContext.getSharedPreferences("mysp",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
        Log.i("clearUserInfo","清空数据");
    }

}
