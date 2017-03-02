package com.weknowall.cn.wuwei.dagger.components;

import android.support.v4.app.Fragment;

import com.weknowall.app_presenter.dagger.modules.FragmentModule;
import com.weknowall.app_presenter.dagger.scope.PerFragment;

import dagger.Component;

/**
 * User: mao
 * Date: 2017/1/29
 * Time: 9:51
 */

@PerFragment
@Component(modules = FragmentModule.class,dependencies = ApplicationComponent.class)
public interface FragmentComponent {
    Fragment fragment();
}
