package com.example.awesoman.bigproject3.presenter.identify.user;

import com.example.awesoman.bigproject3.presenter.MvpView;
import com.example.awesoman.bigproject3.model.entity.UserResult;

/**
 * Created by Awesome on 2016/11/25.
 */

public interface RegistView extends MvpView{
    public void afterRegist(UserResult userResult);
}
