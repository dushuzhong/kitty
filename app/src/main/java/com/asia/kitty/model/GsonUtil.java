package com.asia.kitty.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 字符串和Java类的转换工具
 */

public class GsonUtil {

    private static final String TAG = "GsonUtil";

    public static String toJson(Object obj) {
        String result = createGson().toJson(obj);
        Log.d(TAG, "toJson : " + result);
        return result;
    }

    private static Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        return gsonBuilder.create();
    }


    /**
     * 将字符串转换成对应的Java对象
     */
    public static <T> T fromJSON(String json, Class<T> cls) {
        try {
            return createGson().fromJson(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到json文件中的内容
     * @param context
     * @param fileName
     * @return
     */
    public static String getAsstesJson(Context context, String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        //获得assets资源管理器
        AssetManager assetManager = context.getAssets();
        //使用IO流读取json文件内容
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName),"utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
