package com.asia.kitty.service;

import android.util.Log;

import java.security.MessageDigest;

public class MD5Util {

    public static String getMD5(String content) {
        try {
            String secretStr = content + "FYYK.B2BAPP.Inv";
            Log.i("makeSign_bf",secretStr);
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(secretStr.getBytes("UTF-8"));
            return getHashString(digest);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String getHashString(MessageDigest digest) {
        byte[] bytes = digest.digest();
        String result = "";
        for (byte b : bytes) {
            String temp = Integer.toHexString(b & 0xff);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            result += temp;
        }
        Log.i("md5Encrypt:",result);
        return result;
    }
}
