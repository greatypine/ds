package com.guoanshequ.dc.das.model;

import lombok.Data;

/**
 * Created by greatypine on 2018/1/11.
 */
@Data
public class DsCronTask {

    private String taskno;
    private String taskname;
    private String description;
    private String begintime;
    private String endtime;
    private String runtype;
    private String isrun;

}
