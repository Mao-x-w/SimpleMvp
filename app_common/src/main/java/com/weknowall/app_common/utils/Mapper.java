package com.weknowall.app_common.utils;

/**
 * User: laomao
 * Date: 2017-01-17
 * Time: 15-27
 */

public interface Mapper<A,B> {

    A transform(B b);

    B transformTo(A a);

}
