package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.dao.master
 * @Description:东方大地保险用户
 * @Author: gbl
 * @CreateDate: 2019/1/29 13:35
 */
@Repository
@DataSource("master")
public interface WesterLandUserMapper {

    /**
     * @Description 保存用户信息
     * @author gbl
     * @date 2019/1/29 13:38
     **/

    public void addUser(Map<String,Object> param);

    /**
     * @Description 删除指定日期的用户信息
     * @author gbl
     * @date 2019/1/29 13:39
     **/

    public void  deleteUser(String date);

    /**
     * @Description 查询用户
     * @author gbl
     * @date 2019/1/29 13:41
     **/

    public List<Map<String,Object>> queryUser(Map<String,Object> param);
}
