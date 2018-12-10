package com.guoanshequ.dc.das.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DfMassOrder {
	private String id;
	private String order_sn;
	private String group_id;
	private String order_type;
	private String business_model_id;
	private String customer_id;
	private String order_address_id;
	private String store_id;
	private String eshop_id;
	private String order_status;
	private String order_source;
	private String invoice_status;
	private String buyer_remark;
	private String seller_remark;
	private String employee_remark;
	private String abnormal_type;
	private String abnormal_remark;
	private String delivery_type;
	private BigDecimal trading_price;
	private BigDecimal payable_price;
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
	private Integer status;
	private Integer version;
	private String create_user;
	private Date create_time;
	private String update_user;
	private Date update_time;
	private String create_user_id;
	private String update_user_id;
	private String order_sn_reserve;
	private String store_remark;
	private Integer score;
	private String groupon_instance_id;
	private String normal_store_id;
	private String third_part;
	private Date sign_time;
	private String info_village_code;
	private String area_code;
	private String info_employee_a_no;
	private String pubseas_label;
	private String abnormal_label;
	private String return_label;
	private Date return_time;
	private BigDecimal returned_amount;
	private String loan_label;
	private Integer order_quantity;
	private String eshop_name;
	private String eshop_white;
	private String store_name;
	private String store_code;
	private String store_city_name;
	private String store_city_code;
	private String store_white;
	private String store_province_code;
	private Integer store_status;
	private String customer_name;
	private String customer_mobile_phone;
	private String customer_isnew_flag;
	private String department_id;
	private String bussiness_group_id;
	private String department_name;
	private String area_company_id;
	private String company_name;
	private String channel_id;
	private String channel_name;
	private BigDecimal gmv_price;
	private String employee_no;
	private String addr_name;
	private String addr_mobilephone;
	private String addr_address;
	private Date insert_time;
	private String order_tag1;
	private BigDecimal apportion_rebate;
	private BigDecimal apportion_coupon;
	private BigDecimal cost_price;
	private BigDecimal order_profit;
	private String business_type;
	private String contract_id;
	private String contract_method;
	private String eshop_joint_ims;
	private Integer cct_proration_platform;
	private Integer cct_proration_seller;
	private BigDecimal contract_percent;
	private BigDecimal contract_price;
	private String order_tag3;
	private String order_tag4;
	private BigDecimal platform_price;
	private BigDecimal seller_price;
	private String tpl_supplier_code;
	private String real_store_id;
	
	
	public enum PubseasLabel{
		DEFAULT("0", "否"),
		PUBSEAS("1", "公海"),
    	;
    	
    	public String code;
    	public String desc;
    	
    	PubseasLabel(String code, String desc){
    		this.code = code;
    		this.desc = desc;
    	}
    }
	
	public enum AbnormalLabel{
		DEFAULT("0", "否"),
		ABNORMAL("1", "异常"),
    	;
    	
    	public String code;
    	public String desc;
    	
    	AbnormalLabel(String code, String desc){
    		this.code = code;
    		this.desc = desc;
    	}
	}
	
	
	public enum ReturnLabel{
		DEFAULT("0", "否"),
		RETURN("1", "退款"),
    	;
    	
    	public String code;
    	public String desc;
    	
    	ReturnLabel(String code, String desc){
    		this.code = code;
    		this.desc = desc;
    	}
    }

	public enum CustomerIsnewLabel{
		DEFAULT("-1", "否"),
		ZERO_CUSTOMER("0", "0元新客"),
		TEN_CUSTOMER("10", "10元新客"),
		TWENTY_CUSTOMER("20", "20元新客"),
    	;
    	
    	public String code;
    	public String desc;
    	
    	CustomerIsnewLabel(String code, String desc){
    		this.code = code;
    		this.desc = desc;
    	}
	}
	
	/**
	 * 判断新客标准
	 * @param trading_price
	 * @return
	 */
	public static String checkCustomerIsnew(String trading_price){
		BigDecimal tradingPrice = trading_price==null?BigDecimal.ZERO:new BigDecimal(trading_price);
		if(new BigDecimal(CustomerIsnewLabel.TEN_CUSTOMER.code).compareTo(tradingPrice)>=0){
			return CustomerIsnewLabel.ZERO_CUSTOMER.code;
		}else if(new BigDecimal(CustomerIsnewLabel.TWENTY_CUSTOMER.code).compareTo(tradingPrice)>=0){
			return CustomerIsnewLabel.TEN_CUSTOMER.code;
		}else{
			return CustomerIsnewLabel.TWENTY_CUSTOMER.code;
		}
	}
	
}
