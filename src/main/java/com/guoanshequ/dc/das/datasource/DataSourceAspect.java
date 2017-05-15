package com.guoanshequ.dc.das.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * 注解切面指定数据源
 * Created by liuxi on 2017/2/21.
 */
public class DataSourceAspect {

    public void before(JoinPoint point){
        Class<?> target = point.getTarget().getClass();
        MethodSignature signature = (MethodSignature) point.getSignature();
        // 默认使用目标类型的注解，如果没有则使用其实现接口的注解
        try {
            if(target != null){
                Class<?>[] calzzs = target.getInterfaces();
                if(calzzs.length > 0){
                    for (Class<?> clazz : target.getInterfaces()) {
                        resolveDataSource(clazz, signature.getMethod());
                    }
                }else{
                    resolveDataSource(target, signature.getMethod());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resolveDataSource(Class<?> clazz, Method method) {
        try {
            Class<?>[] types = method.getParameterTypes();
            // 默认使用类型注解
            if (clazz.isAnnotationPresent(DataSource.class)) {
                DataSource source = clazz.getAnnotation(DataSource.class);
                DynamicDataSourceHolder.setDataSource(source.value());
            }
            // 方法注解可以覆盖类型注解
            Method m = clazz.getMethod(method.getName(), types);
            if (m != null && m.isAnnotationPresent(DataSource.class)) {
                DataSource source = m.getAnnotation(DataSource.class);
                DynamicDataSourceHolder.setDataSource(source.value());
            }
        } catch (Exception e) {
            System.out.println(clazz + ":" + e.getMessage());
        }
    }
}
