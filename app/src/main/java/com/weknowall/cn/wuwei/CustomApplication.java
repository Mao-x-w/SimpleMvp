package com.weknowall.cn.wuwei;

import android.app.Application;

import com.weknowall.app_common.Configuration;
import com.weknowall.app_presenter.dagger.modules.ApplicationModule;
import com.weknowall.cn.wuwei.dagger.components.ApplicationComponent;
import com.weknowall.cn.wuwei.dagger.components.DaggerApplicationComponent;

/**
 * User: mao
 * Date: 2017/2/12
 * Time: 11:47
 */

public class CustomApplication extends Application {

    private static CustomApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        Configuration.init(this);
        Configuration.getInstance().debug(BuildConfig.DEBUG);
    }

    public static CustomApplication getAppInstance(){
        return mInstance;
    }

    public ApplicationComponent getComponent(){
        return DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }
}
