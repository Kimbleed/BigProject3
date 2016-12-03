package com.example.awesoman.bigproject3.model.entity;

import java.util.List;

/**
 * Created by Awesome on 2016/11/17.
 */

public class GoodsResult{
    int code ;
    String msg;
    List<GoodsEntity> datas;

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

    public List<GoodsEntity> getData() {
        return datas;
    }

    public void setData(List<GoodsEntity> data) {
        this.datas = data;
    }
}
