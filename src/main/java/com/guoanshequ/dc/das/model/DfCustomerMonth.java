package com.guoanshequ.dc.das.model;

import java.math.BigDecimal;

public class DfCustomerMonth {

	private String customer_id;
	private String store_id;
	private String store_code;
	private BigDecimal trading_price;
	private String yearmonth;
	private Integer new_flag;
	private Integer rebuy_flag;
	private String new_place;
	
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
	public String getStore_code() {
		return store_code;
	}
	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}
	
	public BigDecimal getTrading_price() {
		return trading_price;
	}
	public void setTrading_price(BigDecimal trading_price) {
		this.trading_price = trading_price;
	}
	public String getYearmonth() {
		return yearmonth;
	}
	public void setYearmonth(String yearmonth) {
		this.yearmonth = yearmonth;
	}
	public Integer getNew_flag() {
		return new_flag;
	}
	public void setNew_flag(Integer new_flag) {
		this.new_flag = new_flag;
	}
	public Integer getRebuy_flag() {
		return rebuy_flag;
	}
	public void setRebuy_flag(Integer rebuy_flag) {
		this.rebuy_flag = rebuy_flag;
	}
	public String getNew_place() {
		return new_place;
	}
	public void setNew_place(String new_place) {
		this.new_place = new_place;
	}
	
}