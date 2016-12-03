package com.example.awesoman.bigproject3.presenter.shop;

import android.support.annotation.NonNull;

import com.example.awesoman.bigproject3.presenter.MvpPresenter;

/**
 * Created by Awesome on 2016/12/1.
 */

public class ImageLibraryPresenter extends MvpPresenter<ImageLibraryView> {



    @NonNull
    @Override
    protected ImageLibraryView getNullObject() {
        return null;
    }
}
