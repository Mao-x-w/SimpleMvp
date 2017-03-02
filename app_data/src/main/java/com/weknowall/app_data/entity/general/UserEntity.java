package com.weknowall.app_data.entity.general;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * User: laomao
 * Date: 2017-02-14
 * Time: 11-29
 */

public class UserEntity {
    private String userName;
    private String password;
    private String ack;
    private String phoneNum;
    @JSONField(name = "personalDescription")
    private String description;
    private int gender;
    private String userImage;
    private String nickName;

    public UserEntity() {
    }

    public UserEntity(String userName, String password, String ack) {
        this.userName = userName;
        this.password = password;
        this.ack = ack;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAck() {
        return ack;
    }

    public void setAck(String ack) {
        this.ack = ack;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
