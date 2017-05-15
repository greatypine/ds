package com.guoanshequ.dc.das.datasource;

/**
 * 指定数据源名称
 * Created by liuxi on 2017/2/21.
 */
public class DynamicDataSourceHolder {

    public static final ThreadLocal<String> holder = new ThreadLocal<String>();

    public static void setDataSource(String name) {
        holder.set(name);
    }

    public static String getDataSouce() {
        return holder.get();
    }
}
