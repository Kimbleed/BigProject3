package com.example.awesoman.bigproject3.model.entity;

import java.io.Serializable;

/**
 * Created by Awesome on 2016/11/24.
 */

public class UserEntity implements Serializable{
    String other;
    String name;
    String nickname;
    String uuid;
    String username;

    public UserEntity() {
    }

    public UserEntity(String name, String nickname, String uuid, String username) {
        this.name = name;
        this.nickname = nickname;
        this.uuid = uuid;
        this.username = username;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
