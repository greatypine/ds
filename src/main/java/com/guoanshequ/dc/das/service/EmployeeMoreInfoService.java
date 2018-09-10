package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.EmployeeMoreInfoMapper;
import com.guoanshequ.dc.das.model.EmployeeMoreInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.service
 * @Description:更多员工信息
 * @Author: gbl
 * @CreateDate: 2018/9/6 16:01
 */
@Service("EmployeeMoreInfoService")
@Transactional(value="master")
public class EmployeeMoreInfoService {

    @Autowired
    EmployeeMoreInfoMapper employeeMoreInfoMapper;

    /**
     * @Description 根据员工编号查询更多员工信息
     * @author gbl
     * @date 2018/9/6 16:03
     **/

    public List<Map<String,Object>> queryEmployeeMoreInfoByEmployeeNo(String employeeNo){
        return employeeMoreInfoMapper.queryEmployeeMoreInfoByEmployeeNo(employeeNo);
    }


    /**
     * @Description 保存员工移动里程
     * @author gbl
     * @date 2018/9/6 16:28
     **/

    public void  saveEmployeeMoveDistance(EmployeeMoreInfo employeeMoreInfo){
        employeeMoreInfoMapper.saveEmployeeMoveDistance(employeeMoreInfo);
    }

    /**
     * @Description 更新员工移动里程
     * @author gbl
     * @date 2018/9/6 16:23
     **/

    public void updateEmployeeMoveDistance(EmployeeMoreInfo employeeMoreInfo){
        employeeMoreInfoMapper.updateEmployeeMoveDistance(employeeMoreInfo);
    }


   /**
    * @Description 查询员工信息
    * @author gbl
    * @date 2018/9/10 11:31
    **/

    public List<Map<String,Object>> queryValidHumanresource(){
        return employeeMoreInfoMapper.queryValidHumanresource();
    }

    /**
     * @Description 查询店长信息
     * @author gbl
     * @date 2018/9/10 11:31
     **/

    public List<Map<String,Object>> selectValidStoreKepeer(){
        return employeeMoreInfoMapper.selectValidStoreKepeer();
    }

    /**
     * @Description 保存员工工龄
     * @author gbl
     * @date 2018/9/10 13:23
     **/

    public void  saveEmployeeWorkingAge(EmployeeMoreInfo employeeMoreInfo){
        employeeMoreInfoMapper.saveEmployeeWorkingAge(employeeMoreInfo);
    }

    /**
     * @Description 更新员工工龄
     * @author gbl
     * @date 2018/9/10 13:23
     **/

    public void updateEmployeeWorkingAge(EmployeeMoreInfo employeeMoreInfo){
        employeeMoreInfoMapper.updateEmployeeWorkingAge(employeeMoreInfo);
    }
}
