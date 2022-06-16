package com.asia.kitty.service;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

// 获取外网IP地址
public class IPUitl {
    public static String getNetIp() {
        String ip = "";
        InputStream inStream = null;
        try {
            URL infoUrl = new URL("http://pv.sohu.com/cityjson?ie=utf-8");
            URLConnection conn = infoUrl.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            int responseCode = httpConn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inStream = httpConn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream,"gb2312"));
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    builder.append(line + "\n");
                }
                inStream.close();

                int start = builder.indexOf("{");
                int end = builder.indexOf("}");
                String json = builder.substring(start, end + 1);
                if (json != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        ip =jsonObject.optString("cip");
                        Log.i("ipaddr",ip);
                        return ip;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e){
            Log.i("ipaddr_err","获取IP失败");
            e.printStackTrace();
        }
        return null;
    }

}
