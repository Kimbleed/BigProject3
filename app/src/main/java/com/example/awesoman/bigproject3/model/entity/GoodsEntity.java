package com.example.awesoman.bigproject3.model.entity;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by Awesome on 2016/11/16.
 */

public class GoodsEntity implements Serializable{
    String price;//商品价格
    String name; //商品名称
    String description;//商品描述
    String page;//商品的第一张图片
    String type;  //商品类型
    String uuid;//商品表中主键
    String master;//发布者

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }
}
