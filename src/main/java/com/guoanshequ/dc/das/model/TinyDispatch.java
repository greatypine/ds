package com.guoanshequ.dc.das.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by greatypine on 2018/1/3.
 */
@Document(collection = "tiny_dispatch")
public class TinyDispatch {

    private String orderId;
    private String code;
    private String employee_a_no;
    private String employee_b_no;
    private String employee_dispatch_no;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmployee_a_no() {
        return employee_a_no;
    }

    public void setEmployee_a_no(String employee_a_no) {
        this.employee_a_no = employee_a_no;
    }

    public String getEmployee_b_no() {
        return employee_b_no;
    }

    public void setEmployee_b_no(String employee_b_no) {
        this.employee_b_no = employee_b_no;
    }

    public String getEmployee_dispatch_no() {
        return employee_dispatch_no;
    }

    public void setEmployee_dispatch_no(String employee_dispatch_no) {
        this.employee_dispatch_no = employee_dispatch_no;
    }
}
