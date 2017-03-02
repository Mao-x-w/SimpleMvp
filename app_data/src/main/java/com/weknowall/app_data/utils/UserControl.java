package com.weknowall.app_data.utils;


import android.content.Context;
import android.text.TextUtils;

import com.weknowall.app_common.Configuration;
import com.weknowall.app_common.sharedPrefrence.SharePreferencePlus;
import com.weknowall.app_common.utils.JsonParser;
import com.weknowall.app_data.entity.general.UserEntity;

import javax.inject.Inject;

/**
 * User: laomao
 * Date: 2017-02-14
 * Time: 15-09
 */

public class UserControl {

    private static UserControl u;

    public static UserControl getInstance(){
        return u==null?u=new UserControl(new UserSharedPreferences(Configuration.getInstance().getContext())):u;
    }

    private UserSharedPreferences userSharedPreferences;

    @Inject
    UserControl(UserSharedPreferences userSharedPreferences) {
        this.userSharedPreferences = userSharedPreferences;
    }

    public boolean isLogin(){
        return userSharedPreferences.isLogin();
    }

    public void clearLoginInfo(){
        userSharedPreferences.clearLoginInfo();
    }

    public UserEntity getLoginInfo(){
        return userSharedPreferences.getLoginInfo();
    }

    public void saveLoginInfo(UserEntity entity){
        userSharedPreferences.saveLoginInfo(entity);
    }

    public void logout(){
        clearLoginInfo();
    }

    static class UserSharedPreferences extends SharePreferencePlus{

        private static final String NAME = "_user_control";
        private static final String ITEM_USER_INFO = "u";

        @Inject
        public UserSharedPreferences(Context context) {
            super(context,NAME);
        }

        boolean isLogin(){
            return !TextUtils.isEmpty(getStringValue(ITEM_USER_INFO,""));
        }

        /**
         * 清除用户数据
         */
        void clearLoginInfo(){
            editStringValue(ITEM_USER_INFO,"");
        }

        /**
         * 获取用户数据
         * @return
         */
        UserEntity getLoginInfo() {
            return JsonParser.parseObject(getStringValue(ITEM_USER_INFO, ""), UserEntity.class);
        }

        /**
         * 保存用户数据
         * @param entity
         */
        void saveLoginInfo(UserEntity entity) {
            editStringValue(ITEM_USER_INFO, JsonParser.toJson(entity));
        }
    }
}

