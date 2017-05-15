package com.guoanshequ.dc.das.model;


import com.guoanshequ.dc.das.model.base.DataEntity;

/**
 * 员工数据实体类
 * Created by liuxi on 2016/12/28.
 */
public class Humanresources extends DataEntity {

	/**
	 * 部门
	 */
	private String deptname;
	
	/**
	 * 员工编号
	 */
	private String employee_no;
	/**
	 * 职级/岗位
	 */
	private String zw;
	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 性别
	 */
	private String sex;
	
	/**
	 * 民族
	 */
	private String nation;
	
	/**
	 * 籍贯
	 */
	private String nativeplace;
	
	/**
	 * 户籍
	 */
	private String censusregister;
	
	/**
	 * 出生年月
	 */
	private String birthday;
	
	/**
	 * 学历
	 */
	private String education;
	
	/**
	 * 学位
	 */
	private String educationlevel;
	
	/**
	 * 毕业学校
	 */
	private String school;
	
	/**
	 * 专业
	 */
	private String profession;
	
	/**
	 * 职称
	 */
	private String professiontitle;
	
	/**
	 * 职业   资格
	 */
	private String credentials;
	
	/**
	 * 婚姻状况
	 */
	private String marriage;
	
	/**
	 * 党派
	 */
	private String partisan;
	
	/**
	 * 入党时间
	 */
	private String partisandate;
	
	/**
	 * 参加工作时间
	 */
	private String workdate;
	
	
	/**
	 * 进中信 时间
	 */
	private String entrydate;
	
	
	/**
	 * 身份证号
	 */
	private String cardnumber;
	
	/**
	 * 到岗日
	 */
	private String topostdate;
	
	/**
	 * 劳动合同期限
	 */
	private String contractdate;
	
	/**
	 * 试用期
	 */
	private String trydate;
	
	/**
	 * 签订次数
	 */
	private String signcount;
	
	/**
	 * 是否转正
	 */
	private String isofficial;
	
	
	/**
	 * 补充医保
	 */
	private String tomedical;
	
	/**
	 * 联系方式
	 */
	private String phone;
	
	/**
	 * 紧急联络人姓名
	 */
	private String relationname;
	
	/**
	 * 电话
	 */
	private String tel;
	
	/**
	 * 与本人关系
	 */
	private String relationtype;
	
	/**
	 * 备  注
	 */
	private String remark;
	
	/**
	 * 是否签订劳动合同
	 */
	private String issigncontract;
	
	/**
	 * 身份证地址
	 */
	private String cardaddress;
	
	
	
	/****************************/
	/**
	 * 外包，实习生入职日期
	 */
	private String interndate;
	/**
	 * 合同开始时间
	 */
	private String contractdatestart;
	/**
	 * 合同结束时间
	 */
	private String contractdateend;
	
	
	
	/***************************完善*******************************/
	/**
	 * 所属机构
	 */

	private String orgname;
	
	/**
	 * 一级部门
	 */

	private String deptlevel1;
	
	/**
	 * 二级部门
	 */

	private String deptlevel2;
	
	/**
	 * 三级部门
	 */

	private String deptlevel3;
	
	/**
	 * 汇报上级
	 */

	private String reporthigher;
	

	/**
	 * 职级
	 */

	private String professnallevel;
	
	
	/**
	 * 招聘渠道
	 */

	private String jobchannel;
	
	/**
	 * 推荐人姓名 
	 */

	private String offername;
	
	
	
	
	
	/*****************************培训*********************************/
	
	
	/** 
	 * 门店编码 
	 */

	private Long store_id;
	

	private String storename;
	/** 
	 * 编制 
	 */

	private String authorizedtype;
	
	
	
	/********培训相关***********/
	
	/**
	 * 岗前培训汉字
	 */
	private String teachmsgstr;
	/**
	 * 岗前操作汉字
	 */
	private String onlinestartstr;
	/**
	 * 岗后培训次数
	 */
	private Integer offlinescount;
	/**
	 * 岗后操作汉字
	 */
	private String offlinestartstr;
	
	/**
	 * 理论见习培训 开始时间
	 */

	private String theorystartdate;
	
	/**
	 * 理论见习培训结束时间
	 */

	private String theoryenddate;
	
	
	/**
	 *理论见习培训 见习门店
	 */

	private String theorystorename;
	
	/**
	 * 岗前线上培训 开始时间
	 */

	private String onlinestartdate;
	
	/**
	 * 岗前线上培训 成绩
	 */

	private Double onlinescore;
	
	/**
	 * 岗前线上培训 时长
	 */

	private String onlinedate;
	
	
	
	/**
	 * 岗前线下培训 开始时间
	 */

	private String offlinestartdate;
	
	/**
	 * 岗前线下培训 成绩
	 */

	private Double offlinescore;
	
	/**
	 * 岗前线下培训 时长
	 */

	private String offlinedate;
	
	
	/**
	 * 实操见习培训 开始时间
	 */

	private String realitystartdate;
	
	/**
	 * 实操见习培训结束时间
	 */

	private String realityenddate;
	
	/**
	 * 实操见习培训 所在门店
	 */

	private String realitystorename;
	
	/**
	 * 实操见习培训 成绩
	 */

	private Double realityscore;
	
	
	/**
	 * 状态｛0，未分配门店  1入职  2离职｝
	 */

	private Long humanstatus;
	
	/**
	 * 离职原因
	 */

	private String leavereason;
	
	/**
	 * 离职类型
	 */

	private String leavetype;
	
	
	/**
	 * 离职时间
	 */

	private String leavedate;
	
	/**
	 * 收单日期
	 */

	private String leavercvlistdate;
	
	
	/**
	 * 城市 
	 */

	private String citySelect;
	
//	public List<Humanenteach> humanenteachs;
	

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getEmployee_no() {
		return employee_no;
	}

	public void setEmployee_no(String employee_no) {
		this.employee_no = employee_no;
	}

	public String getZw() {
		return zw;
	}

	public void setZw(String zw) {
		this.zw = zw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getNativeplace() {
		return nativeplace;
	}

	public void setNativeplace(String nativeplace) {
		this.nativeplace = nativeplace;
	}

	public String getCensusregister() {
		return censusregister;
	}

	public void setCensusregister(String censusregister) {
		this.censusregister = censusregister;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getEducationlevel() {
		return educationlevel;
	}

	public void setEducationlevel(String educationlevel) {
		this.educationlevel = educationlevel;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getProfessiontitle() {
		return professiontitle;
	}

	public void setProfessiontitle(String professiontitle) {
		this.professiontitle = professiontitle;
	}

	public String getCredentials() {
		return credentials;
	}

	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}

	public String getMarriage() {
		return marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public String getPartisan() {
		return partisan;
	}

	public void setPartisan(String partisan) {
		this.partisan = partisan;
	}

	public String getPartisandate() {
		return partisandate;
	}

	public void setPartisandate(String partisandate) {
		this.partisandate = partisandate;
	}

	public String getWorkdate() {
		return workdate;
	}

	public void setWorkdate(String workdate) {
		this.workdate = workdate;
	}

	public String getEntrydate() {
		return entrydate;
	}

	public void setEntrydate(String entrydate) {
		this.entrydate = entrydate;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getTopostdate() {
		return topostdate;
	}

	public void setTopostdate(String topostdate) {
		this.topostdate = topostdate;
	}

	public String getContractdate() {
		return contractdate;
	}

	public void setContractdate(String contractdate) {
		this.contractdate = contractdate;
	}

	public String getTrydate() {
		return trydate;
	}

	public void setTrydate(String trydate) {
		this.trydate = trydate;
	}

	public String getSigncount() {
		return signcount;
	}

	public void setSigncount(String signcount) {
		this.signcount = signcount;
	}

	public String getIsofficial() {
		return isofficial;
	}

	public void setIsofficial(String isofficial) {
		this.isofficial = isofficial;
	}

	public String getTomedical() {
		return tomedical;
	}

	public void setTomedical(String tomedical) {
		this.tomedical = tomedical;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRelationname() {
		return relationname;
	}

	public void setRelationname(String relationname) {
		this.relationname = relationname;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getRelationtype() {
		return relationtype;
	}

	public void setRelationtype(String relationtype) {
		this.relationtype = relationtype;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIssigncontract() {
		return issigncontract;
	}

	public void setIssigncontract(String issigncontract) {
		this.issigncontract = issigncontract;
	}

	public String getCardaddress() {
		return cardaddress;
	}

	public void setCardaddress(String cardaddress) {
		this.cardaddress = cardaddress;
	}

	public Long getStore_id() {
		return store_id;
	}

	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

	public String getAuthorizedtype() {
		return authorizedtype;
	}

	public void setAuthorizedtype(String authorizedtype) {
		this.authorizedtype = authorizedtype;
	}

	public String getTheorystartdate() {
		return theorystartdate;
	}

	public void setTheorystartdate(String theorystartdate) {
		this.theorystartdate = theorystartdate;
	}

	public String getTheoryenddate() {
		return theoryenddate;
	}

	public void setTheoryenddate(String theoryenddate) {
		this.theoryenddate = theoryenddate;
	}

	public String getOnlinestartdate() {
		return onlinestartdate;
	}

	public void setOnlinestartdate(String onlinestartdate) {
		this.onlinestartdate = onlinestartdate;
	}

	public Double getOnlinescore() {
		return onlinescore;
	}

	public void setOnlinescore(Double onlinescore) {
		this.onlinescore = onlinescore;
	}

	public String getOnlinedate() {
		return onlinedate;
	}

	public void setOnlinedate(String onlinedate) {
		this.onlinedate = onlinedate;
	}

	public String getOfflinestartdate() {
		return offlinestartdate;
	}

	public void setOfflinestartdate(String offlinestartdate) {
		this.offlinestartdate = offlinestartdate;
	}

	public Double getOfflinescore() {
		return offlinescore;
	}

	public void setOfflinescore(Double offlinescore) {
		this.offlinescore = offlinescore;
	}

	public String getOfflinedate() {
		return offlinedate;
	}

	public void setOfflinedate(String offlinedate) {
		this.offlinedate = offlinedate;
	}

	public String getRealitystartdate() {
		return realitystartdate;
	}

	public void setRealitystartdate(String realitystartdate) {
		this.realitystartdate = realitystartdate;
	}

	public String getRealityenddate() {
		return realityenddate;
	}

	public void setRealityenddate(String realityenddate) {
		this.realityenddate = realityenddate;
	}

	public String getRealitystorename() {
		return realitystorename;
	}

	public void setRealitystorename(String realitystorename) {
		this.realitystorename = realitystorename;
	}

	public Double getRealityscore() {
		return realityscore;
	}

	public void setRealityscore(Double realityscore) {
		this.realityscore = realityscore;
	}

	public Long getHumanstatus() {
		return humanstatus;
	}

	public void setHumanstatus(Long humanstatus) {
		this.humanstatus = humanstatus;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getDeptlevel1() {
		return deptlevel1;
	}

	public void setDeptlevel1(String deptlevel1) {
		this.deptlevel1 = deptlevel1;
	}

	public String getDeptlevel2() {
		return deptlevel2;
	}

	public void setDeptlevel2(String deptlevel2) {
		this.deptlevel2 = deptlevel2;
	}

	public String getDeptlevel3() {
		return deptlevel3;
	}

	public void setDeptlevel3(String deptlevel3) {
		this.deptlevel3 = deptlevel3;
	}

	public String getReporthigher() {
		return reporthigher;
	}

	public void setReporthigher(String reporthigher) {
		this.reporthigher = reporthigher;
	}

	public String getProfessnallevel() {
		return professnallevel;
	}

	public void setProfessnallevel(String professnallevel) {
		this.professnallevel = professnallevel;
	}

	public String getJobchannel() {
		return jobchannel;
	}

	public void setJobchannel(String jobchannel) {
		this.jobchannel = jobchannel;
	}

	public String getOffername() {
		return offername;
	}

	public void setOffername(String offername) {
		this.offername = offername;
	}

	public String getStorename() {
		return storename;
	}

	public void setStorename(String storename) {
		this.storename = storename;
	}

	public String getTeachmsgstr() {
		return teachmsgstr;
	}

	public void setTeachmsgstr(String teachmsgstr) {
		this.teachmsgstr = teachmsgstr;
	}

	public String getOnlinestartstr() {
		return onlinestartstr;
	}

	public void setOnlinestartstr(String onlinestartstr) {
		this.onlinestartstr = onlinestartstr;
	}

	public Integer getOfflinescount() {
		return offlinescount;
	}

	public void setOfflinescount(Integer offlinescount) {
		this.offlinescount = offlinescount;
	}

	public String getOfflinestartstr() {
		return offlinestartstr;
	}

	public void setOfflinestartstr(String offlinestartstr) {
		this.offlinestartstr = offlinestartstr;
	}

//	public List<Humanenteach> getHumanenteachs() {
//		return humanenteachs;
//	}
//
//	public void setHumanenteachs(List<Humanenteach> humanenteachs) {
//		this.humanenteachs = humanenteachs;
//	}

	public String getTheorystorename() {
		return theorystorename;
	}

	public void setTheorystorename(String theorystorename) {
		this.theorystorename = theorystorename;
	}

	public String getLeavereason() {
		return leavereason;
	}

	public void setLeavereason(String leavereason) {
		this.leavereason = leavereason;
	}

	public String getLeavetype() {
		return leavetype;
	}

	public void setLeavetype(String leavetype) {
		this.leavetype = leavetype;
	}

	public String getLeavedate() {
		return leavedate;
	}

	public void setLeavedate(String leavedate) {
		this.leavedate = leavedate;
	}

	public String getCitySelect() {
		return citySelect;
	}

	public void setCitySelect(String citySelect) {
		this.citySelect = citySelect;
	}

	public String getInterndate() {
		return interndate;
	}

	public void setInterndate(String interndate) {
		this.interndate = interndate;
	}

	public String getContractdatestart() {
		return contractdatestart;
	}

	public void setContractdatestart(String contractdatestart) {
		this.contractdatestart = contractdatestart;
	}

	public String getContractdateend() {
		return contractdateend;
	}

	public void setContractdateend(String contractdateend) {
		this.contractdateend = contractdateend;
	}

	public String getLeavercvlistdate() {
		return leavercvlistdate;
	}

	public void setLeavercvlistdate(String leavercvlistdate) {
		this.leavercvlistdate = leavercvlistdate;
	}
	
	/***********培训结束********************/
	
}
