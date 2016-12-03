package com.example.awesoman.bigproject3;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.awesoman.bigproject3.model.entity.UserEntity;

/**
 * Created by Awesome on 2016/12/2.
 */

public class SharedPreferenceManager {

    Context context;

    public SharedPreferenceManager(Context context) {
        this.context = context;
    }

    public void getLoginUser(){
        SharedPreferences sp =context.getSharedPreferences("login", Context.MODE_PRIVATE);
        sp.getString("username", null);
        sp.getString("password",null);
    }
}