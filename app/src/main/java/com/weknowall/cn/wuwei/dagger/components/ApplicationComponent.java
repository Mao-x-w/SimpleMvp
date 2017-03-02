package com.weknowall.cn.wuwei.dagger.components;

import android.content.Context;

import com.weknowall.app_domain.executor.PostExecutionThread;
import com.weknowall.app_domain.executor.ThreadExecutor;
import com.weknowall.app_domain.repository.IGeneralRepository;
import com.weknowall.app_presenter.dagger.modules.ApplicationModule;
import com.weknowall.cn.wuwei.ui.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * User: mao
 * Date: 2017/1/29
 * Time: 9:51
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent{
    void inject(BaseActivity activity);
    Context context();
    ThreadExecutor threadScheduler();
    PostExecutionThread postScheduler();
    IGeneralRepository generalRepository();
}
