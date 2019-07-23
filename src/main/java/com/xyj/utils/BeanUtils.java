package com.xyj.utils;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.*;

/**
 * 类属性复制
 */
public class BeanUtils {

    static {
        ConvertUtils.register(new DateConverter(null), java.util.Date.class);
        ConvertUtils.register(new SqlDateConverter(null), java.sql.Date.class);
        ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
    }

    private static void handleReflectionException(Exception e) {
        ReflectionUtils.handleReflectionException(e);
    }

    public static Object cloneBean(Object bean){
        try {
            return org.apache.commons.beanutils.BeanUtils.cloneBean(bean);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    public static <T, U> List<U> copyProperties(List<T> sourceList, Class<U> clazz) {

        if(sourceList == null || sourceList.size() <= 0) {
            return null;
        }

        List<U> result = new ArrayList<>();
        for (T source : sourceList) {
            result.add(copyProperties(source, clazz));
        }

        return result;
    }
    /**
     * 如果source , 为空返回空对象
     * @param sourceList
     * @param clazz
     * @return
     */
    public static <T, U> List<U> copyPropertiesList(List<T> sourceList, Class<U> clazz) {

        if(sourceList == null || sourceList.size() <= 0) {
            return new ArrayList<U>();
        }

        List<U> result = new ArrayList<>();
        U target = null;
        for (T source : sourceList) {
            try {
                target = clazz.newInstance();
                copyProperties(source, target);
            }catch(Exception e) {
                handleReflectionException(e);
                return null;
            }
            result.add(target);
        }

        return result;
    }

    public static <T> T copyProperties(Object orig, Class<T> destClass) {

        if(orig == null) {
            return null;
        }

        try {
            Object target = destClass.newInstance();
            copyProperties(orig, (Object)target);
            return (T)target;
        }catch(Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    public static <T, U> List<U> copyProperties(List<T> sourceList, Class<U> clazz, String... ignoreProperties) {

        if(sourceList == null || sourceList.size() <= 0) {
            return new ArrayList<U>();
        }

        List<U> result = new ArrayList<>();
        for (T source : sourceList) {
            result.add(copyProperties(source, clazz, ignoreProperties));
        }

        return result;
    }

    public static <T> T copyProperties(Object orig, Class<T> destClass, String... ignoreProperties) {

        if(orig == null) {
            return null;
        }

        try {
            Object target = destClass.newInstance();
            org.springframework.beans.BeanUtils.copyProperties(orig, (Object)target, ignoreProperties);
            return (T)target;
        }catch(Exception e) {
            return null;
        }
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] propDesc = beanWrapper.getPropertyDescriptors();

        Set<String> emptynames = new HashSet<String>();

        for(java.beans.PropertyDescriptor pd : propDesc) {
            Object srcValue = beanWrapper.getPropertyValue(pd.getName());
            if (srcValue == null) emptynames.add(pd.getName());
        }

        String[] result = new String[emptynames.size()];
        return emptynames.toArray(result);
    }

    public static void copyProperties(Object orig, Object dest) {
        try {
            org.apache.commons.beanutils.BeanUtils.copyProperties(dest, orig);
        } catch (Exception e) {
            handleReflectionException(e);
        }
    }

    public static void copyProperty(Object bean, String name, Object value) {
        try {
            org.apache.commons.beanutils.BeanUtils.copyProperty(bean, name, value);
        } catch (Exception e) {
            handleReflectionException(e);
        }
    }

    public static Map<String, String> describe(Object bean) {
        try {
            return org.apache.commons.beanutils.BeanUtils.describe(bean);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    public static String[] getArrayProperty(Object bean, String name){
        try {
            return org.apache.commons.beanutils.BeanUtils.getArrayProperty(bean, name);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    public static String getIndexedProperty(Object bean, String name, int index){
        try {
            return org.apache.commons.beanutils.BeanUtils.getIndexedProperty(bean, name, index);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    public static String getIndexedProperty(Object bean, String name){
        try {
            return org.apache.commons.beanutils.BeanUtils.getIndexedProperty(bean, name);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    public static String getMappedProperty(Object bean, String name, String key){
        try {
            return org.apache.commons.beanutils.BeanUtils.getMappedProperty(bean, name, key);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    public static String getMappedProperty(Object bean, String name){
        try {
            return org.apache.commons.beanutils.BeanUtils.getMappedProperty(bean, name);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    public static String getNestedProperty(Object bean, String name){
        try {
            return org.apache.commons.beanutils.BeanUtils.getNestedProperty(bean, name);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    public static String getProperty(Object bean, String name){
        try {
            return org.apache.commons.beanutils.BeanUtils.getProperty(bean, name);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    public static String getSimpleProperty(Object bean, String name) {
        try {
            return org.apache.commons.beanutils.BeanUtils.getSimpleProperty(bean, name);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    public static void populate(Object bean, Map properties) {
        try {
            org.apache.commons.beanutils.BeanUtils.populate(bean, properties);
        } catch (Exception e) {
            handleReflectionException(e);
        }
    }

    public static void setProperty(Object bean, String name, Object value) {
        try {
            org.apache.commons.beanutils.BeanUtils.setProperty(bean, name, value);
        } catch (Exception e) {
            handleReflectionException(e);
        }
    }


    public static Map<String, Object> mapProperties(Object bean) throws Exception {
        Map<String, Object> properties = new HashMap<String, Object>();
        for (Method method : bean.getClass().getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers())
                    && method.getParameterTypes().length == 0
                    && method.getReturnType() != void.class
                    && method.getName().matches("^(get|is).+")
            ) {
                String name = method.getName().replaceAll("^(get|is)", "");
                name = Character.toLowerCase(name.charAt(0)) + (name.length() > 1 ? name.substring(1) : "");
                Object value = method.invoke(bean);
                properties.put(name, value);
            }
        }
        return properties;
    }

    /**
     * 将对象转为SortedMap
     * @param obj
     * @return
     * @throws Exception
     */
    public static SortedMap<String, String> objectToSortedMap(Object obj) throws Exception {
        if(obj == null){
            return null;
        }

        SortedMap<String, String> map = new TreeMap<String, String>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), (String)field.get(obj));
        }
        return map;
    }
    /**
     * 复制字段的值
     * @param source 源对象
     * @param target 目标对象
     * @param skipFieldNames 过滤的字段
     */
    public static void copyFields(Object source, Object target, String[] skipFieldNames){
        Set<String> skips = new HashSet<>();
        if (skipFieldNames != null){
            skips.addAll(Arrays.asList(skipFieldNames));
        }
        HashMap<String, Field> sourceFieldMap = getAllFields(source);
        HashMap<String, Field> targetFieldMap = getAllFields(target);

        sourceFieldMap.forEach((key, value) -> {
            value.setAccessible(true);
            try {
                if (value.get(source) != null && !skips.contains(key) && targetFieldMap.containsKey(key)){
                    targetFieldMap.get(key).setAccessible(true);
                    targetFieldMap.get(key).set(target, value.get(source));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 获取对象所有的字段 包括父类
     * @param obj 对象
     * @return 字段名与Field对象的映射map
     */
    private static HashMap<String, Field> getAllFields(Object obj){
        Class sourceClass = obj.getClass();
        //获取对象所有字段 包括父类
        ArrayList<Field> sourceFields = new ArrayList<>();
        while (sourceClass != null){
            sourceFields.addAll(Arrays.asList(sourceClass.getDeclaredFields()));
            sourceClass = sourceClass.getSuperclass();
        }
        //字段名去重
        HashMap<String, Field> sourceFieldMap = new HashMap<>(16);
        for (Field field : sourceFields){
            sourceFieldMap.put(field.getName(), field);
        }
        return sourceFieldMap;
    }

}
