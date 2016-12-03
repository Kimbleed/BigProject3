package com.example.awesoman.bigproject3.model.entity;

/**
 * Created by Awesome on 2016/11/18.
 */

public class GoodDetailResult {
    int code ;
    String msg;
    GoodDetailEntity datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public GoodDetailEntity getDatas() {
        return datas;
    }

    public void setDatas(GoodDetailEntity datas) {
        this.datas = datas;
    }
}
