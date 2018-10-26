package com.guoanshequ.dc.das.model;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

@Data
public class ImsTbsdgds {

    private String c_guid;
    private String c_store_id;
    private String c_gcode;
    private Date c_dt;
    private String c_adno;
    private String c_ccode;
    private String c_trademark;
    private String c_name;
    private String c_type;
    private String c_model;
    private String c_status;
    private String c_pro_status;
    private String c_sale_status;
    private String c_store_status;
    private String c_sale_frequency;
    private String c_abc;
    private BigDecimal c_number_sale;
    private BigDecimal c_a_sale;
    private BigDecimal c_at_sale;
    private BigDecimal cost_price;
    private String c_map_store_id;
    private Date insert_time;
}
