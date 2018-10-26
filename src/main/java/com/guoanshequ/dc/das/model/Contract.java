package com.guoanshequ.dc.das.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Contract {
	
	private String order_id;
	private String contract_id;
	private String contract_method;
	private BigDecimal contract_percent;
	private BigDecimal contract_price;
}
