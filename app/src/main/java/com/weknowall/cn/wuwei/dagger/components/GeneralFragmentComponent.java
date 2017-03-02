package com.weknowall.cn.wuwei.dagger.components;

import com.weknowall.app_presenter.dagger.modules.FragmentModule;
import com.weknowall.app_presenter.dagger.modules.GeneralModule;
import com.weknowall.app_presenter.dagger.scope.PerFragment;

import dagger.Component;

/**
 * User: laomao
 * Date: 2017-02-15
 * Time: 14-34
 */

@PerFragment
@Component(dependencies = ApplicationComponent.class,modules = {FragmentModule.class, GeneralModule.class})
public interface GeneralFragmentComponent extends FragmentComponent {

}
