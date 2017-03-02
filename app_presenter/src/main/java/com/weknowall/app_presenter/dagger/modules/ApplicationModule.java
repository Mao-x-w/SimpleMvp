package com.weknowall.app_presenter.dagger.modules;

import android.app.Application;
import android.content.Context;

import com.weknowall.app_data.cahe.general.GeneralCacheImpl;
import com.weknowall.app_data.cahe.general.IGeneralCache;
import com.weknowall.app_data.net.api.service.GeneralRetrofitNetImpl;
import com.weknowall.app_data.net.general.IGeneralNet;
import com.weknowall.app_data.repository.GeneralRepositoryImpl;
import com.weknowall.app_domain.executor.JobExecutor;
import com.weknowall.app_domain.executor.PostExecutionThread;
import com.weknowall.app_domain.executor.ThreadExecutor;
import com.weknowall.app_domain.repository.IGeneralRepository;
import com.weknowall.app_presenter.dagger.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * User: laomao
 * Date: 2017-01-18
 * Time: 16-29
 */
@Module
public class ApplicationModule {

    private Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Context provideApplication(){
        return mApplication;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor executor){
        return executor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutor(UIThread uiThread) {
        return uiThread;
    }

    /***********************************************************************/
    /***********************      Repository       *************************/
    /***********************************************************************/

    @Provides
    @Singleton
    IGeneralRepository provideGeneralRepository(GeneralRepositoryImpl generalRepository){
        return generalRepository;
    }


    /***********************************************************************/
    /**************************      cache       ***************************/
    /***********************************************************************/

    @Provides
    @Singleton
    IGeneralCache provideGeneralCache(GeneralCacheImpl generalCache){
        return generalCache;
    }

    /***********************************************************************/
    /**************************      net       *****************************/
    /***********************************************************************/

    @Provides
    @Singleton
    IGeneralNet provideGeneralNet(GeneralRetrofitNetImpl net){
        return net;
    }
}
