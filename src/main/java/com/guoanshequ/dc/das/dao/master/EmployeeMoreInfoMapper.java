package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.EmployeeMoreInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.dao.master
 * @Description:更多员工信息
 * @Author: gbl
 * @CreateDate: 2018/9/6 15:52
 */
@Repository
@DataSource("master")
public interface EmployeeMoreInfoMapper {

    /**
     * @Description 根据员工编号查询员工更多信息
     * @author gbl
     * @date 2018/9/6 15:55
     **/
    public List<Map<String,Object>> queryEmployeeMoreInfoByEmployeeNo(String employeeNo);


    /**
     * @Description 保存员工运行里程
     * @author gbl
     * @date 2018/9/6 16:32
     **/
    public void saveEmployeeMoveDistance(EmployeeMoreInfo employeeMoreInfo);

    /**
     * @Description  更新员工运行里程
     * @author gbl
     * @date 2018/9/6 16:35
     **/

    public void updateEmployeeMoveDistance(EmployeeMoreInfo employeeMoreInfo);

    /**
     * @Description 清空员工当天的里程
     * @author gbl
     * @date 2018/9/13 10:51
     **/

    public void updateEmployeeOneDayMoveDistanceZero(EmployeeMoreInfo employeeMoreInfo);

    /**
     * @Description 保存员工工龄
     * @author gbl
     * @date 2018/9/6 16:32
     **/
    public void saveEmployeeWorkingAge(EmployeeMoreInfo employeeMoreInfo);

    /**
     * @Description  更新员工工龄
     * @author gbl
     * @date 2018/9/6 16:35
     **/

    public void updateEmployeeWorkingAge(EmployeeMoreInfo employeeMoreInfo);

    /**
     * @Description 查询国安侠员工
     * @author gbl
     * @date 2018/9/10 11:23
     **/

    public List<Map<String, Object>> queryValidHumanresource();

    /**
     * @Description 查询店长
     * @author gbl
     * @date 2018/9/10 11:24
     **/

    public List<Map<String, Object>> selectValidStoreKepeer();


}
