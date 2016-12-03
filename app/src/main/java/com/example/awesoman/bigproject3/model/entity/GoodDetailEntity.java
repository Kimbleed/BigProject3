package com.example.awesoman.bigproject3.model.entity;

import java.util.List;

/**
 * Created by Awesome on 2016/11/18.
 */

public class GoodDetailEntity {

        String uuid;
        String name;
        String type;
        String price;
        String description;
        String master;
        int code ;
        String msg;
        List<PageEntity> pages;

public String getUuid() {
        return uuid;
        }

public void setUuid(String uuid) {
        this.uuid = uuid;
        }

public String getName() {
        return name;
        }

public void setName(String name) {
        this.name = name;
        }

public String getType() {
        return type;
        }

public void setType(String type) {
        this.type = type;
        }

public String getPrice() {
        return price;
        }

public void setPrice(String price) {
        this.price = price;
        }

public String getDescription() {
        return description;
        }

public void setDescription(String description) {
        this.description = description;
        }

public String getMaster() {
        return master;
        }

public void setMaster(String master) {
        this.master = master;
        }

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

public List<PageEntity> getPages() {
        return pages;
        }

public void setPages(List<PageEntity> datas) {
        this.pages = datas;
        }
        }
