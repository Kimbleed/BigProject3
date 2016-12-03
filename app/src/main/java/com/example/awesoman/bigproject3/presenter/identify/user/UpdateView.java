package com.example.awesoman.bigproject3.presenter.identify.user;

import com.example.awesoman.bigproject3.presenter.MvpView;
import com.example.awesoman.bigproject3.model.entity.UserEntity;

/**
 * Created by Awesome on 2016/11/25.
 */

public interface UpdateView extends MvpView {
    void afterUpdate(UserEntity entity);
}
