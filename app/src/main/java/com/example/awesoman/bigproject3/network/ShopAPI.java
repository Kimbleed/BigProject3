package com.example.awesoman.bigproject3.network;

/**
 * Created by Awesome on 2016/11/17.
 */

public class ShopAPI {
    /*易淘总路径*/
    public static String BASE_URL = "http://wx.feicuiedu.com:9094/yitao/";
    /*图片的基路径*/
    public static String IMAGE_URL = "http://wx.feicuiedu.com:9094";
    /*登录路径*/
    public static String LOGIN = "UserWeb?method=login";
    /*注册路径*/
    public static String REGISTER = "UserWeb?method=register";
    /*更新路径*/
    public static String UPDATE = "UserWeb?method=update";
    /*获取名字路径*/
    public static String GET_NAMES = "UserWeb?method=getNames";
    /*获取用户路径*/
    public static String GET_USER = "UserWeb?method=getUsers";



    /*获取所有商品路径*/
    public static String ALL_GOODS = "GoodsServlet?method=getAll";
    /*添加商品路径*/
    public static String ADD = "GoodsServlet?method=add";
    /*商品详情路径*/
    public static String DETAIL = "GoodsServlet?method=view";
    /*删除商品路径*/
    public static String DELETE = "GoodsServlet?method=delete";
}
