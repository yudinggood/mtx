package com.mtx.common.util.factory;

import com.google.common.collect.Lists;
import com.mtx.common.util.base.ToolUtil;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 转换工厂
 */
public abstract class BaseFactory {
    protected Mapper dozer = new DozerBeanMapper();
    //任意数组类型转换
    public <V,E> List<V> convertList(List<E> list, Class<V> clazz){
        List<V> voList=new ArrayList<>();
        if(ToolUtil.isEmpty(list)){
            return voList;
        }
        for(E e:list){
            V v = convertModel(e,clazz);
            voList.add(v);
        }
        return voList;
    }
    //任意数组类型转换  使用dozer
    public <T> List<T> convertListDozer(Collection sourceList, Class<T> destinationClass){
        List destinationList = Lists.newArrayList();
        for (Iterator iterator = sourceList.iterator(); iterator.hasNext();){//迭代器
            Object sourceObject = iterator.next();
            Object destinationObject = dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;
    }

    //任意实体类型转换
    public <V,E> V convertModel(E e,Class<V> clazz) {
        V v =newInstance(clazz);
        if(e != null){
            BeanUtils.copyProperties(e,v);
            return convertAttribute(e,v);
        }else {
            return v;
        }

    }

    //泛型实例化
    protected static <T> T newInstance(Class<T> clazz){
        T a= null;
        try {
            a = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return a;
    }
    //抽象方法，为了在不同子类里实现自定义的 内容
    protected abstract <V,E> V convertAttribute(E e,V v);

    /**
     * 根据属性，获取get方法
     */
    public Object getGetMethod(Object ob , String name){
        Method[] m = ob.getClass().getMethods();
        for(int i = 0;i < m.length;i++){
            if(("get"+name).toLowerCase().equals(m[i].getName().toLowerCase())){
                try {
                    return m[i].invoke(ob);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    //任意数组类型转换为基本数组
    public <V,E> List<V> convertIntList(List<E> sourceList, String fieldName)  {
        List<V> voList=new ArrayList<>();
        if(ToolUtil.isEmpty(sourceList)){
            return voList;
        }
        for(E e:sourceList){
            Field field = null;
            try {
                field = e.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                V v = (V) field.get(e);
                voList.add(v);
            } catch (NoSuchFieldException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }

        }
        return voList;

    }
}
