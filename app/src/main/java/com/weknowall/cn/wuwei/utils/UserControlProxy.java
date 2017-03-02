package com.weknowall.cn.wuwei.utils;

import com.weknowall.app_data.entity.general.UserEntity;
import com.weknowall.app_data.utils.UserControl;
import com.weknowall.app_presenter.entity.general.User;

/**
 * User: laomao
 * Date: 2017-02-14
 * Time: 15-49
 */

public class UserControlProxy {

    /**
     * 判断是否登录
     * @return
     */
    public static boolean isLogin(){
        return UserControl.getInstance().isLogin();
    }

    /**
     * 退出登录
     */
    public static void logout(){
        UserControl.getInstance().logout();
    }

    /**
     * 获取登录信息
     * @return
     */
    public static User getLoginInfo(){
        User user=new User();
        UserEntity userEntity = UserControl.getInstance().getLoginInfo();
        if (userEntity!=null){
            user.setAck(userEntity.getAck());
            user.setUserName(userEntity.getUserName());
            user.setPassword(userEntity.getPassword());
        }
        return user;
    }
}
