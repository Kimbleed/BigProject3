package com.example.awesoman.bigproject3.model.event;

/**
 * Created by Awesome on 2016/12/1.
 */

public class ImageChooseEvent {
    String uri;
    boolean isAdd;
    boolean isSurface;

    public boolean isSurface() {
        return isSurface;
    }

    public void setSurface(boolean surface) {
        isSurface = surface;
    }

    public ImageChooseEvent(String uri, boolean isAdd,boolean isSurface) {
        this.uri = uri;
        this.isAdd = isAdd;
        this.isSurface = isSurface;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
