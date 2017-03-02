package com.weknowall.app_presenter.dagger.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * User: Joy
 * Date: 2016/11/1
 * Time: 17:23
 */
@Scope
@Retention(RUNTIME)
public @interface PerApp {

}
