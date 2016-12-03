package com.example.awesoman.bigproject3.presenter.identify.user;

import com.example.awesoman.bigproject3.presenter.MvpView;
import com.example.awesoman.bigproject3.model.entity.UserEntity;

/**
 * Created by Awesome on 2016/11/24.
 */

public interface LoginView extends MvpView {
    public void afterLogin(UserEntity entity);

}
