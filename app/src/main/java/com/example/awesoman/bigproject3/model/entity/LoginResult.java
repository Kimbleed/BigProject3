package com.example.awesoman.bigproject3.model.entity;

/**
 * Created by Awesome on 2016/11/24.
 */

public class LoginResult {
        String code;
        String msg;
        UserEntity data;

    public String getCode() {

        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserEntity getData() {
        return data;
    }

    public void setData(UserEntity data) {
        this.data = data;
    }
}
