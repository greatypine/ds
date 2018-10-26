package com.guoanshequ.dc.das.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItem {
	
	private String id;
	private String order_id;
	private String eshop_pro_id;
	private BigDecimal unit_price;
	private Integer quantity;
	private BigDecimal cost_price;
	private String product_code;
}
