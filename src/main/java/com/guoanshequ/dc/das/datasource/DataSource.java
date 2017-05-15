package com.guoanshequ.dc.das.datasource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 数据源注解，用于mapper接口指定操作的数据库
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {
    String value();
}
