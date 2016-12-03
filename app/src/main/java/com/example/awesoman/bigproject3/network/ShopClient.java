package com.example.awesoman.bigproject3.network;


import android.util.Log;

import com.example.awesoman.bigproject3.model.entity.GoodsEntity;
import com.example.awesoman.bigproject3.model.entity.UserEntity;
import com.google.gson.Gson;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Awesome on 2016/11/17.
 */

public class ShopClient {
    private OkHttpClient okHttpClient;
    private ShopClient(){
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }
    private static ShopClient shopClient;

    public static ShopClient getInstance(){
        if(shopClient==null){
            shopClient = new ShopClient();
        }
        return shopClient;
    }
    public Call getGoods(int pageNo,String type){
        RequestBody requestBody = new FormBody.Builder()
                .add("pageNo",String.valueOf(pageNo))
                .add("type",type)
                .build();
        Request request = new Request.Builder()
                .url(ShopAPI.BASE_URL+ShopAPI.ALL_GOODS)
                .post(requestBody)
                .build();
        Log.i("request", ShopAPI.BASE_URL+ShopAPI.ALL_GOODS+"&pageNo="+pageNo+"&type="+type);
        return okHttpClient.newCall(request);
    }

    public Call getGoodDetail(String uuid){
        RequestBody requestBody = new FormBody.Builder()
                .add("uuid",uuid)
                .build();
        Request request = new Request.Builder()
                .url(ShopAPI.BASE_URL+ShopAPI.DETAIL)
                .post(requestBody)
                .build();
        Log.i("request", ShopAPI.BASE_URL+ShopAPI.DETAIL+"&uuid="+uuid);
        return okHttpClient.newCall(request);

    }
    public Call getLoginResult(String username,String password){
        RequestBody requestBody = new FormBody.Builder()
                .add("username",username)
                .add("password",password)
                .build();
        Request request = new Request.Builder()
                .url(ShopAPI.BASE_URL+ShopAPI.LOGIN)
                .post(requestBody)
                .build();
        return okHttpClient.newCall(request);
    }
    public Call getRequestResult(String username,String password){
        RequestBody requestBody = new FormBody.Builder()
                .add("username",username)
                .add("password",password)
                .build();
        Request request = new Request.Builder()
                .url(ShopAPI.BASE_URL+ShopAPI.REGISTER)
                .post(requestBody)
                .build();
        Log.i("getRequestResult",ShopAPI.BASE_URL+ShopAPI.REGISTER+"&username="+username+"&password="+password);
        return okHttpClient.newCall(request);
    }
    public Call getUpdateRequest(UserEntity entity,File file){
        String jsonString = new Gson().toJson(entity);
        RequestBody requestBody = null;
        if(file!=null) {
            requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("user", jsonString)
                    .addFormDataPart("images", file.getName(),
                            RequestBody.create(MediaType.parse("image/png"), file))
                    .build();
        }
        else{
            requestBody = new FormBody.Builder()
                    .add("user",jsonString)
                    .build();
        }
        Request request = new Request.Builder()
                .url(ShopAPI.BASE_URL+ShopAPI.UPDATE)
                .post(requestBody)
                .build();
        Log.i("request",request.url().toString());
        Log.i("requestBody",jsonString);
        return okHttpClient.newCall(request);
    }

    public Call getUploadGoodsRequest(GoodsEntity entity,List<File> files){
        String jsonString = new Gson().toJson(entity);
        MultipartBody requestBody =null;
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("good",jsonString);
        for (int i = 0; i < files.size(); i++) {
                builder.addFormDataPart("image", files.get(i).getName(),
                        RequestBody.create(MediaType.parse("image/png"), files.get(i)));
        }
        requestBody = builder.build();
        Request request = new Request.Builder()
                .url(ShopAPI.BASE_URL+ShopAPI.ADD)
                .post(requestBody)
                .build();
        Log.i("request",request.url().toString());
        Log.i("requestBody",jsonString);
        return okHttpClient.newCall(request);
    }
}
