package com.guoanshequ.dc.das.model;

import java.math.BigDecimal;
import java.util.Date;

public class DfValidCustomer {
	
	private String customer_id;
	private String customer_phone;
	private Date first_order_time;
	private Date last_order_time;
	private BigDecimal total_price;
	private Integer total_ordercount;
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public String getCustomer_phone() {
		return customer_phone;
	}
	public void setCustomer_phone(String customer_phone) {
		this.customer_phone = customer_phone;
	}
	public Date getFirst_order_time() {
		return first_order_time;
	}
	public void setFirst_order_time(Date first_order_time) {
		this.first_order_time = first_order_time;
	}
	public Date getLast_order_time() {
		return last_order_time;
	}
	public void setLast_order_time(Date last_order_time) {
		this.last_order_time = last_order_time;
	}
	public BigDecimal getTotal_price() {
		return total_price;
	}
	public void setTotal_price(BigDecimal total_price) {
		this.total_price = total_price;
	}
	public Integer getTotal_ordercount() {
		return total_ordercount;
	}
	public void setTotal_ordercount(Integer total_ordercount) {
		this.total_ordercount = total_ordercount;
	}
	
	
	
}
