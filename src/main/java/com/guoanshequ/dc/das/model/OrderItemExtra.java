package com.guoanshequ.dc.das.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderItemExtra {

    private String order_id;
    private String order_item_id;
    private BigDecimal apportion_rebate;
    private BigDecimal apportion_coupon;
}
