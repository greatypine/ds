package com.guoanshequ.dc.das.model;

import java.math.BigDecimal;
import java.util.Date;

public class DFOrderRealtime {
	
	private Long id;
	private String order_id;
	private String order_sn;
	private String group_id;
	private String order_type;
	private String business_model_id;
	private String customer_id;
	private String order_address_id;
	private String addr_adname;
	private String addr_placename;
	private double addr_latitude;
	private double addr_longitude;
	private String addr_address;
	private String addr_name;
	private String addr_mobilephone;
	private String tc_mobilephone;
	private String store_id;
	private String eshop_id;
	private String order_status;
	private String order_source;
	private String invoice_status;
	private String buyer_remark;
	private String seller_remark;
	private String employee_remark;
	private String store_remark;
	private String abnormal_type;
	private String abnormal_remark;
	private String delivery_type;
	private BigDecimal trading_price;
	private BigDecimal payable_price;
	private Integer score;
	private String is_split;
	private String employee_id;
	private String employee_phone;
	private String employee_name;
	private Date appointment_start_time;
	private Date appointment_end_time;
	private String eshop_combo_pro_id;
	private Date expiry_date;
	private BigDecimal combo_price;
	private Integer total_quantity;
	private String groupon_instance_id;
	private Integer status;
	private Integer version;
	private String create_user;
	private Date create_time;
	private String update_user;
	private Date update_time;
	private String create_user_id;
	private String update_user_id;
	private String order_sn_reserve;
	private String normal_store_id;
	
	private String eshop_name;
	private String eshop_white;
	private String department_id;
	private String deptname;
	private String channelname;
	
	
	private String store_name;
	private String storeno;
	private Integer store_status;
	private String store_white;
	private Integer store_number;
	private String city_code;
	private String city_name;
	
	private Integer order_quantity;
	private Date signed_time;
	private String signed_status;
	
	private Integer receipts_status;
	private String receipts_type;
	private String receipts_paystatus;
	private String receipts_payplatform;
	
	private Date insert_time;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOrder_sn() {
		return order_sn;
	}

	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getBusiness_model_id() {
		return business_model_id;
	}

	public void setBusiness_model_id(String business_model_id) {
		this.business_model_id = business_model_id;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getOrder_address_id() {
		return order_address_id;
	}

	public void setOrder_address_id(String order_address_id) {
		this.order_address_id = order_address_id;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getEshop_id() {
		return eshop_id;
	}

	public void setEshop_id(String eshop_id) {
		this.eshop_id = eshop_id;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public String getOrder_source() {
		return order_source;
	}

	public void setOrder_source(String order_source) {
		this.order_source = order_source;
	}

	public String getBuyer_remark() {
		return buyer_remark;
	}

	public void setBuyer_remark(String buyer_remark) {
		this.buyer_remark = buyer_remark;
	}

	public String getSeller_remark() {
		return seller_remark;
	}

	public void setSeller_remark(String seller_remark) {
		this.seller_remark = seller_remark;
	}

	public String getEmployee_remark() {
		return employee_remark;
	}

	public void setEmployee_remark(String employee_remark) {
		this.employee_remark = employee_remark;
	}

	public String getAbnormal_remark() {
		return abnormal_remark;
	}

	public void setAbnormal_remark(String abnormal_remark) {
		this.abnormal_remark = abnormal_remark;
	}

	public BigDecimal getTrading_price() {
		return trading_price;
	}

	public void setTrading_price(BigDecimal trading_price) {
		this.trading_price = trading_price;
	}

	public BigDecimal getPayable_price() {
		return payable_price;
	}

	public void setPayable_price(BigDecimal payable_price) {
		this.payable_price = payable_price;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public String getEmployee_phone() {
		return employee_phone;
	}

	public void setEmployee_phone(String employee_phone) {
		this.employee_phone = employee_phone;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public Date getAppointment_start_time() {
		return appointment_start_time;
	}

	public void setAppointment_start_time(Date appointment_start_time) {
		this.appointment_start_time = appointment_start_time;
	}

	public Date getAppointment_end_time() {
		return appointment_end_time;
	}

	public void setAppointment_end_time(Date appointment_end_time) {
		this.appointment_end_time = appointment_end_time;
	}

	public String getEshop_combo_pro_id() {
		return eshop_combo_pro_id;
	}

	public void setEshop_combo_pro_id(String eshop_combo_pro_id) {
		this.eshop_combo_pro_id = eshop_combo_pro_id;
	}

	public Date getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(Date expiry_date) {
		this.expiry_date = expiry_date;
	}

	public BigDecimal getCombo_price() {
		return combo_price;
	}

	public void setCombo_price(BigDecimal combo_price) {
		this.combo_price = combo_price;
	}

	public Integer getTotal_quantity() {
		return total_quantity;
	}

	public void setTotal_quantity(Integer total_quantity) {
		this.total_quantity = total_quantity;
	}

	public String getGroupon_instance_id() {
		return groupon_instance_id;
	}

	public void setGroupon_instance_id(String groupon_instance_id) {
		this.groupon_instance_id = groupon_instance_id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public String getCreate_user_id() {
		return create_user_id;
	}

	public void setCreate_user_id(String create_user_id) {
		this.create_user_id = create_user_id;
	}

	public String getUpdate_user_id() {
		return update_user_id;
	}

	public void setUpdate_user_id(String update_user_id) {
		this.update_user_id = update_user_id;
	}

	public String getOrder_sn_reserve() {
		return order_sn_reserve;
	}

	public void setOrder_sn_reserve(String order_sn_reserve) {
		this.order_sn_reserve = order_sn_reserve;
	}

	public String getEshop_name() {
		return eshop_name;
	}

	public void setEshop_name(String eshop_name) {
		this.eshop_name = eshop_name;
	}

	public String getEshop_white() {
		return eshop_white;
	}

	public void setEshop_white(String eshop_white) {
		this.eshop_white = eshop_white;
	}

	public String getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(String department_id) {
		this.department_id = department_id;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getStoreno() {
		return storeno;
	}

	public void setStoreno(String storeno) {
		this.storeno = storeno;
	}

	public Integer getStore_status() {
		return store_status;
	}

	public void setStore_status(Integer store_status) {
		this.store_status = store_status;
	}

	public String getStore_white() {
		return store_white;
	}

	public void setStore_white(String store_white) {
		this.store_white = store_white;
	}

	public Integer getStore_number() {
		return store_number;
	}

	public void setStore_number(Integer store_number) {
		this.store_number = store_number;
	}

	public String getCity_code() {
		return city_code;
	}

	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public Integer getOrder_quantity() {
		return order_quantity;
	}

	public void setOrder_quantity(Integer order_quantity) {
		this.order_quantity = order_quantity;
	}

	public Date getSigned_time() {
		return signed_time;
	}

	public void setSigned_time(Date signed_time) {
		this.signed_time = signed_time;
	}

	public Integer getReceipts_status() {
		return receipts_status;
	}

	public void setReceipts_status(Integer receipts_status) {
		this.receipts_status = receipts_status;
	}

	public String getReceipts_type() {
		return receipts_type;
	}

	public void setReceipts_type(String receipts_type) {
		this.receipts_type = receipts_type;
	}

	public String getReceipts_paystatus() {
		return receipts_paystatus;
	}

	public void setReceipts_paystatus(String receipts_paystatus) {
		this.receipts_paystatus = receipts_paystatus;
	}

	public String getReceipts_payplatform() {
		return receipts_payplatform;
	}

	public void setReceipts_payplatform(String receipts_payplatform) {
		this.receipts_payplatform = receipts_payplatform;
	}

	public Date getInsert_time() {
		return insert_time;
	}

	public void setInsert_time(Date insert_time) {
		this.insert_time = insert_time;
	}

	public String getInvoice_status() {
		return invoice_status;
	}

	public void setInvoice_status(String invoice_status) {
		this.invoice_status = invoice_status;
	}

	public String getAbnormal_type() {
		return abnormal_type;
	}

	public void setAbnormal_type(String abnormal_type) {
		this.abnormal_type = abnormal_type;
	}

	public String getDelivery_type() {
		return delivery_type;
	}

	public void setDelivery_type(String delivery_type) {
		this.delivery_type = delivery_type;
	}

	public String getIs_split() {
		return is_split;
	}

	public void setIs_split(String is_split) {
		this.is_split = is_split;
	}

	public String getSigned_status() {
		return signed_status;
	}

	public void setSigned_status(String signed_status) {
		this.signed_status = signed_status;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getChannelname() {
		return channelname;
	}

	public void setChannelname(String channelname) {
		this.channelname = channelname;
	}

	public String getAddr_adname() {
		return addr_adname;
	}

	public void setAddr_adname(String addr_adname) {
		this.addr_adname = addr_adname;
	}

	public String getAddr_placename() {
		return addr_placename;
	}

	public void setAddr_placename(String addr_placename) {
		this.addr_placename = addr_placename;
	}

	public double getAddr_latitude() {
		return addr_latitude;
	}

	public void setAddr_latitude(double addr_latitude) {
		this.addr_latitude = addr_latitude;
	}

	public double getAddr_longitude() {
		return addr_longitude;
	}

	public void setAddr_longitude(double addr_longitude) {
		this.addr_longitude = addr_longitude;
	}

	public String getAddr_address() {
		return addr_address;
	}

	public void setAddr_address(String addr_address) {
		this.addr_address = addr_address;
	}

	public String getAddr_name() {
		return addr_name;
	}

	public void setAddr_name(String addr_name) {
		this.addr_name = addr_name;
	}

	public String getAddr_mobilephone() {
		return addr_mobilephone;
	}

	public void setAddr_mobilephone(String addr_mobilephone) {
		this.addr_mobilephone = addr_mobilephone;
	}

	public String getTc_mobilephone() {
		return tc_mobilephone;
	}

	public void setTc_mobilephone(String tc_mobilephone) {
		this.tc_mobilephone = tc_mobilephone;
	}

	public String getNormal_store_id() {
		return normal_store_id;
	}

	public void setNormal_store_id(String normal_store_id) {
		this.normal_store_id = normal_store_id;
	}

	public String getStore_remark() {
		return store_remark;
	}

	public void setStore_remark(String store_remark) {
		this.store_remark = store_remark;
	}
	
}
