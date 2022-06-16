package com.asia.kitty.service;

import android.util.Log;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

// [0,100] 生成随机数
//Random random = new Random();
//int r = random.nextInt(100)+1;

// 接口请求使用的签名
public class SignUtil {

    public static final String TOKEN = "";

    public static String sha1Encrypt(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;

            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }

    // 获取时间戳
    public static  String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    // 根据参数名对参数进行排序
    private static String sortParams(Map<String,String> params) {
        TreeMap<String,String> paramTreeMap = new TreeMap<String,String>(params);
        //Log.i("paramTreeMap",paramTreeMap.toString());

        StringBuffer sb = new StringBuffer();
        Set<Map.Entry<String,String>> es = paramTreeMap.entrySet();
        Iterator<Map.Entry<String,String>> it = es.iterator();
        sb.append("{");
        while (it.hasNext()) {
            Map.Entry<String,String> entry = it.next();
            String k = entry.getKey();
            String v = entry.getValue();
            //sb.append(k + "=" + v + "&");
            sb.append("\"" + k + "\"");
            sb.append(":");
            sb.append("\"" + v + "\"");
            sb.append(",");
        }
        ;
        //sb.append("}");

        Log.i("sortParamsStr:",sb.substring(0, sb.lastIndexOf(",")).toString() + "}");
        return sb.substring(0, sb.lastIndexOf(",")).toString() + "}";
    }

    public static String getRandomStr() {
        return "";
    }

    public static String createSignature(SortedMap<String,String> params) {
        return sha1Encrypt(sortParams(params));
    }

    public static String makeSign(Map<String,String> params) {
        String signStr = MD5Util.getMD5(sortParams(params));
        Log.i("makeSign:",signStr);
        return signStr;
    }

    public static String createSignature(String timeStamp, String nonce, String secretId) {
        SortedMap<String, String> signParams = new TreeMap<String,String>();
        signParams.put("token",TOKEN);
        signParams.put("timeStamp",timeStamp);
        signParams.put("nonce",nonce);
        signParams.put("secretId",secretId);
        return createSignature(signParams);
    }

}
