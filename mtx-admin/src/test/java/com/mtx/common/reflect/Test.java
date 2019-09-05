package com.mtx.common.reflect;

import java.lang.reflect.Constructor;

public class Test {

    @org.junit.Test
    public void test(){
        //Class<T> clazz = mapper.getClass();   获取JVM中类的实例，只有一个
        Class clazz = getClass();
        Constructor[] conArray = clazz.getConstructors(); //获取当前类的构造方法


    }
}
