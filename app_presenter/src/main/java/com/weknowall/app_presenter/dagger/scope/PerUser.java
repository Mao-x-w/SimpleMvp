package com.weknowall.app_presenter.dagger.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * User: Joy
 * Date: 2016/10/27
 * Time: 17:57
 * 用户生命周期
 */

@Scope
@Retention(RUNTIME)
public @interface PerUser {}
