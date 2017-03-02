package com.weknowall.cn.wuwei.dagger.components;

import android.app.Activity;

import com.weknowall.app_presenter.dagger.modules.ActivityModule;
import com.weknowall.app_presenter.dagger.scope.PerActivity;

import dagger.Component;

/**
 * User: mao
 * Date: 2017/1/29
 * Time: 9:51
 */

@PerActivity
@Component(modules = ActivityModule.class,dependencies = ApplicationComponent.class)
public interface ActivityComponent {
    Activity activity();
}
