package com.guoanshequ.dc.das.model;


/**
 * 
* @ClassName: IdCard
* @Description: TODO(这里用一句话描述这个类的作用)
* @author caops
* @date 2018年5月21日 下午4:46:53
*
*
 */
public class IdCard {

	//出生地
	private String birthplace;
	 // 省份  
    private String province;  
    // 城市 
    private String city;
    // 年份  
    private int year;  
    // 出生日期  
    private String birthday; 
    // 性别  
    private String gender;
    
	public String getBirthplace() {
		return birthplace;
	}
	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}  
}
