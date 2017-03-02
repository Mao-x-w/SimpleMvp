package com.weknowall.app_common.utils;


import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * User: laomao
 * Date: 2017-01-17
 * Time: 15-31
 */

public class MapperImpl<A,B> implements Mapper<A,B> {

    @Override
    public A transform(B b) {
        return null;
    }

    @Override
    public B transformTo(A a) {
        return null;
    }

    public List<A> transform(List<B> bs){
        if (bs==null)
            return null;
        List<A> as=new ArrayList<>();
        for (B b : bs) {
            as.add(transform(b));
        }
        return as;
    }

    public List<B> transformTo(List<A> as){
        if (as==null)
            return null;
        List<B> bs=new ArrayList<>();
        for (A a : as) {
            bs.add(transformTo(a));
        }
        return bs;
    }


    protected <R> R parse(String js, Class<R> clz){
        return JsonParser.parseObject(js, clz);
    }

    protected int parseInteger(String str){
        if(TextUtils.isEmpty(str) || "null".equals(str)){
            return 0;
        }
        try {
            return parseInt(str);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    protected String parseString(int i){
        return String.valueOf(i);
    }

    protected String parseString(boolean b){
        return b ? "1" : "0";
    }

    protected boolean parseBoolean(String s){
        return !TextUtils.isEmpty(s) && s.equals("1");
    }

}
