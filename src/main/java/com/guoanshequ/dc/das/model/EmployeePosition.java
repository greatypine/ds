package com.guoanshequ.dc.das.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.model
 * @Description: mongo中国安侠位置表
 * @Author: gbl
 * @CreateDate: 2018/9/6 11:06
 */
@Document(collection = "employee_position")
public class EmployeePosition {
    private String _id;

    private List<String[]> locations;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }



    public List<String[]> getLocations() {
        return locations;
    }

    public void setLocations(List<String[]> locations) {
        this.locations = locations;
    }
}
