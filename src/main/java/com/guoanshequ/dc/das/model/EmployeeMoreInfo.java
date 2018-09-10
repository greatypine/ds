package com.guoanshequ.dc.das.model;

import java.util.Date;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.model
 * @Description: 员工其他信息
 * @Author: gbl
 * @CreateDate: 2018/9/6 15:31
 */
public class EmployeeMoreInfo {

    private String employeeNo;//员工编号

    private Integer workingAge_year;//工龄 按年

    private String workingAge_year_precise;//工龄 例如 一年以上，两年以上

    private Integer workingAge_month;//工龄 按月

    private Integer workingAge_day;//工龄 按天

    private float moveDistance;//移动距离 /公里

    private Date create_time;

    private Date update_time;



    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public Integer getWorkingAge_year() {
        return workingAge_year;
    }

    public void setWorkingAge_year(Integer workingAge_year) {
        this.workingAge_year = workingAge_year;
    }

    public String getWorkingAge_year_precise() {
        return workingAge_year_precise;
    }

    public void setWorkingAge_year_precise(String workingAge_year_precise) {
        this.workingAge_year_precise = workingAge_year_precise;
    }

    public Integer getWorkingAge_month() {
        return workingAge_month;
    }

    public void setWorkingAge_month(Integer workingAge_month) {
        this.workingAge_month = workingAge_month;
    }

    public Integer getWorkingAge_day() {
        return workingAge_day;
    }

    public void setWorkingAge_day(Integer workingAge_day) {
        this.workingAge_day = workingAge_day;
    }

    public float getMoveDistance() {
        return moveDistance;
    }

    public void setMoveDistance(float moveDistance) {
        this.moveDistance = moveDistance;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}
