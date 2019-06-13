package com.xyj.utils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 类属性复制
 */
public class BeanUtils {
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
