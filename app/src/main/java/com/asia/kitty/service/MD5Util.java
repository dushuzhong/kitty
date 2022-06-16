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

//        byte b[] = digest.digest();
//        int i;
//        String result = "";
//        StringBuffer buf = new StringBuffer("");
//        for (int offset = 0; offset < b.length; offset++) {
//            i = b[offset];
//            if (i < 0) {
//                i += 256;
//            }
//            if (i < 16) {
//                buf.append("0");
//            }
//            buf.append(Integer.toHexString(i));
//        }
//        result = buf.toString();

        // 32位小写 result
        // result.toUppercase
        // buf.tostring().substring(8,24)

        //return result;
    }
}
