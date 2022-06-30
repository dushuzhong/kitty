package com.asia.kitty.service;

import okhttp3.RequestBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

// 定义Api接口
public interface ApiService {
    public static String BASE_URL = "https://test.hbglyy.cn/api-b2b-app/";
    // 其中一中发起POST请求的方法,@FormUrlEncoded和@Multipart注解同时使用
    @POST("login/pwlogin")
    Call<Object> getPostDataBody(@Body RequestBody body);

}
