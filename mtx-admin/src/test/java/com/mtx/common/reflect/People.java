package com.mtx.common.reflect;

import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;

public class People  {


    @Test
    public void test(){
        // 打印的结果是：泛型参数String
        class AnonymousArrayListA extends HashMap<String,Integer> {

        }
        AnonymousArrayListA aa = new AnonymousArrayListA();
        Class typeA = (Class) ((ParameterizedType) aa.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        System.out.println(typeA);


        // 无法获取泛型参数
        class AnonymousArrayListB<T> extends ArrayList<T> {

        }

        AnonymousArrayListB<String> bb = new AnonymousArrayListB<String>();

            Class typeB = (Class) ((ParameterizedType) bb.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            System.out.println(typeB.getSimpleName());

    }
}
