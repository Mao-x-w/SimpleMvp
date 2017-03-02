package com.weknowall.cn.wuwei.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weknowall.cn.wuwei.ui.activity.MainActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * User: laomao
 * Date: 2017-01-13
 * Time: 17-40
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.weknowall.cn.wuwei.R.layout.activity_splash);

        Observable.timer(3000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    startActivity(MainActivity.class);
                    finish();
                });
    }

}
