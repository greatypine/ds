package com.guoanshequ.dc.das.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
* @ClassName: CustomerInfoRecord
* @Description:mongo中社员信息表
* @author caops
* @date 2018年5月18日 下午5:37:25
*
 */
@Document(collection = "customer_info_record")
public class CustomerInfoRecord {

    private String cityCode;
    private String idCard;
    private String createTime;
    private String inviteCode;
    
	public String getInviteCode() {
		return inviteCode;
	}
	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

}
