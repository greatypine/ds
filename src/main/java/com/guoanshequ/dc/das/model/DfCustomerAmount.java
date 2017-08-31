package com.guoanshequ.dc.das.model;

import java.math.BigDecimal;

public class DfCustomerAmount {

	private Long id;
	private String city_name;
	private String customer_id;
	private String store_id;
	private BigDecimal total_price;
	private String yearandmonth;
	private String order_id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	
	public BigDecimal getTotal_price() {
		return total_price;
	}
	public void setTotal_price(BigDecimal total_price) {
		this.total_price = total_price;
	}
	public String getYearandmonth() {
		return yearandmonth;
	}
	public void setYearandmonth(String yearandmonth) {
		this.yearandmonth = yearandmonth;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	
}