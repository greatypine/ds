package com.guoanshequ.dc.das.task;

import com.guoanshequ.dc.das.model.CustomerInfoRecord;
import com.guoanshequ.dc.das.model.IdCard;
import com.guoanshequ.dc.das.model.OrderReceipts;
import com.guoanshequ.dc.das.service.*;
import com.guoanshequ.dc.das.utils.DateUtils;
import com.guoanshequ.dc.das.utils.HttpInterfaceUtils;
import com.guoanshequ.dc.das.utils.IdCardUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

/**
 * 
 * @author CaoPs
 * @date 2018年5月18日
 * @version 1.0 说明:
 */
@Component
public class UserMemberScheduleTask {

	@Autowired
	DsCronTaskService dsCronTaskService;
	@Autowired
	UserMemberService userMemberService;
	@Autowired
	DfUserMemberService dfUserMemberService;
	@Autowired
	MongoService mongoService;
	

	private static final Logger logger = LogManager.getLogger(UserMemberScheduleTask.class);

	/**
	 * 
	 * @Title: UserMemberScheduleTask 
	 * @Description: 社员信息表，每10分钟跑一次，采用多线程，每10分钟中跑前1小时的数据
	 * @param 设定文件 
	 * @return void 返回类型
	 * @throws
	 */
	@Scheduled(cron = "0 */10 * * * ?")
	public void userMemberTask() {
		new Thread() {
			public void run() {
				try {
					logger.info("************社员信息定时任务开始***********************");
					Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(10);
					String isrun = taskMap.get("isrun");
					String task_status = taskMap.get("task_status");
					// 判断任务是否开启
					if ("ON".equals(isrun)) {
						String runtype = taskMap.get("runtype");
						String begintime = null;
						String endtime = null;
							// 获取上次调度时的最大签收时间开始时间与结束时间
							if ("MANUAL".equals(runtype)) {
								begintime = taskMap.get("begintime");
								endtime = taskMap.get("endtime");
							} else {
								begintime = taskMap.get("flag_begintime");
								endtime = DateUtils.getCurTime(new Date());
							}
							
							Map<String, String> paraMap = new HashMap<String, String>();
							paraMap.put("begintime", begintime);
							paraMap.put("endtime", endtime);
							paraMap.put("level", "2");
							// 1执行查询，从gemini中查询出时间段内的用户数据
							List<Map<String, Object>> userMemberList = userMemberService.queryUserMemberByLevelTime(paraMap);
							if (!userMemberList.isEmpty()) {
					    		//设置任务为运行中状态
					    		Map<String, String> runMap = new HashMap<String,String>();
					    		runMap.put("id", "10");
					    		runMap.put("task_status", "RUNNING");
					    		dsCronTaskService.updateTaskStatusById(runMap);
					    		CustomerInfoRecord customerInfoRecord =null ;
					    		String idcardStr = null;
					    		IdCard idcard =null;
					    		String openCardTime ="";
					    		String regitTime="";
					    		String isnew_member="";
					    		String birthday ="";
					    		Object regist_cityno =null;
					    		Object regist_storeid =null;
					    		String customer_id ="";
					    		String member_type = null;
					    		Map<String, String> groupUserMember = new HashMap<String,String>(); 
					    		OrderReceipts receiptsMember = new OrderReceipts();
								for (Map<String, Object> userMember : userMemberList) {
									isnew_member="0";
									customer_id = userMember.get("customer_id").toString();
									customerInfoRecord = mongoService.queryCusInfoByCusId(customer_id);
									groupUserMember = userMemberService.queryStoreIdOfGroupByCusid(customer_id);
									receiptsMember = userMemberService.queryRegistInfoByCusIdOf2(customer_id);
									//获取收款表中信息:付款门店、付款时间、付款类型
									if(receiptsMember!=null) {
										userMember.put("regist_storeid", receiptsMember.getStore_id());
										userMember.put("member_type", receiptsMember.getType());
										userMember.put("opencard_time", receiptsMember.getPay_time());
									}
									//mongo中获取身份证数据
									if(customerInfoRecord!=null) {
										idcardStr = customerInfoRecord.getIdCard();
										//注册门店的获取顺序：付款表-集采表
										regist_storeid = userMember.get("regist_storeid");
										if(null==regist_storeid || "".equals(regist_storeid)){
											if(groupUserMember!=null) {
												regist_storeid = groupUserMember.get("store_id");
												member_type = groupUserMember.get("type");
												userMember.put("member_type", member_type);
												regist_cityno = userMemberService.queryCityNoByStoreid(regist_storeid.toString());
											}
										}else {
											//注册城市的获取顺序：付款表与集采表若无对应门店id,则无城市，再从mongo中获取
											regist_cityno = userMemberService.queryCityNoByStoreid(regist_storeid.toString());
										}
										userMember.put("regist_storeid", regist_storeid);
	
										if(null==regist_cityno || "".equals(regist_cityno)) {
											regist_cityno = customerInfoRecord.getCityCode();
										}
										userMember.put("regist_cityno", regist_cityno);
										if(customerInfoRecord.getCreateTime()!=null && !"".equals(customerInfoRecord.getCreateTime())){
											openCardTime= customerInfoRecord.getCreateTime();
										}
										//对身份证号的处理
										idcardStr = customerInfoRecord.getIdCard();
										birthday = customerInfoRecord.getBirthday();
										if(!StringUtils.isBlank(birthday)) {
											userMember.put("birthday",birthday.replace("-", ""));
										}
										if(idcardStr!=null && !"".equals(idcardStr)) {
											userMember.put("idcard", idcardStr);
											idcard = IdCardUtil.getIdCardInfo(idcardStr);
											userMember.put("birthplace",idcard.getBirthplace());
											userMember.put("born_province", idcard.getProvince());
											userMember.put("born_city", idcard.getCity());
											userMember.put("birthday",idcard.getBirthday());
											userMember.put("sex",idcard.getGender());
										}
										
										//社区邀请码
										userMember.put("inviteCode", customerInfoRecord.getInviteCode());
									}else {
										System.out.println("mongo中未存在的customer_id:"+userMember.get("customer_id").toString());
									}
									
									//付款开卡时间获取顺序：付款表-mongo-手功导入(t_member_operation_recrod)
									if(userMember.get("opencard_time")!=null && !"".equals(userMember.get("opencard_time"))) {
										openCardTime = userMember.get("opencard_time").toString();
									}else if(userMemberService.queryCreatetimeFromOpRecordByCusId(customer_id)!=null){
										openCardTime= userMemberService.queryCreatetimeFromOpRecordByCusId(customer_id);
										userMember.put("member_origin", "adminDefined2");
									}
									//获取社员注册时间
									regitTime = userMember.get("regist_time").toString();
									if(null!=openCardTime && !"".equals(openCardTime)) {
										userMember.put("opencard_time", openCardTime);
										//判断是否当天开卡新社员
										if(DateUtils.StringToDate(openCardTime).equals(DateUtils.StringToDate(regitTime))) {
											isnew_member = "1";
										}
									}
									userMember.put("isnew_member", isnew_member);
									
									dfUserMemberService.addDfUserMember(userMember);
								}
					    		//设置任务为运行完成状态
					    		Map<String, String> doneMap = new HashMap<String,String>();
					    		doneMap.put("id", "10");
					    		doneMap.put("task_status", "DONE");
					    		doneMap.put("flag_begintime",DateUtils.getPreNHoursTimeByAssign(DateUtils.StringToDateTime(endtime),1));
					    		dsCronTaskService.updateTaskStatusById(doneMap);
					    		dsCronTaskService.updateFlagBeginTimeById(doneMap);
							}
						logger.info("社员信息任务执行结果为：开始时间：" + begintime + ",结束时间：" + endtime);
						logger.info("************社员信息定时任务结束***********************");
					}
				} catch (Exception e) {
					logger.info("社员信息任务出现问题，任务执行失败，请查看！");
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	
	/**
	 * 
	 * @Title: UserMemberScheduleTask 
	 * @Description: 试用社员信息表，每小时跑一次，采用多线程，每小时跑前2小时的数据
	 * @param 设定文件 
	 * @return void 返回类型
	 * @throws
	 */
	@Scheduled(cron = "0 0 */1 * * ?")
	public void tryUserMemberTask() {
		new Thread() {
			public void run() {
				try {
					logger.info("************（试用）社员信息定时任务开始***********************");
					Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(10);
					String isrun = taskMap.get("isrun");
					// 判断任务是否开启
					if ("ON".equals(isrun)) {
						String runtype = taskMap.get("runtype");
						String begintime = null;
						String endtime = null;
						// 获取上次调度时的最大签收时间开始时间与结束时间
						if ("MANUAL".equals(runtype)) {
							begintime = taskMap.get("begintime");
							endtime = taskMap.get("endtime");
						} else {
							begintime = DateUtils.getPreNHoursTime(2);
							endtime = DateUtils.getCurTime(new Date());
						}

						Map<String, String> paraMap = new HashMap<String, String>();
						paraMap.put("begintime", begintime);
						paraMap.put("endtime", endtime);
						paraMap.put("level", "-2");
						// 1执行查询，从gemini中查询出时间段内的用户数据
						List<Map<String, Object>> userMemberList = userMemberService.queryUserMemberByLevelTime(paraMap);
						if (!userMemberList.isEmpty()) {
				    		CustomerInfoRecord customerInfoRecord =null ;
				    		String idcardStr = null;
				    		IdCard idcard =null;
				    		String openCardTime ="";
				    		String regitTime="";
				    		String isnew_member="";
				    		String birthday ="";
				    		Object regist_cityno =null;
				    		Object regist_storeid =null;
				    		String customer_id ="";
				    		String member_type = null;
				    		Map<String, String> groupUserMember = new HashMap<String,String>(); 
				    		OrderReceipts receiptsMember = new OrderReceipts(); 
							for (Map<String, Object> userMember : userMemberList) {
								isnew_member="0";
								customer_id = userMember.get("customer_id").toString();
								customerInfoRecord = mongoService.queryCusInfoByCusId(customer_id);
								groupUserMember = userMemberService.queryStoreIdOfGroupByCusid(customer_id);
								receiptsMember = userMemberService.queryRegistInfoByCusIdOftry2(customer_id);
								//获取收款表中信息:付款门店、付款时间、付款类型
								if(receiptsMember!=null) {
									userMember.put("regist_storeid", receiptsMember.getStore_id());
									userMember.put("member_type", receiptsMember.getType());
									userMember.put("opencard_time", receiptsMember.getPay_time());
								}
								//获取mongo表中信息：身份证号、注册城市、邀请码
								if(customerInfoRecord!=null) {
									idcardStr = customerInfoRecord.getIdCard();
									//注册门店的获取顺序：付款表-集采表
									regist_storeid = userMember.get("regist_storeid");
									if(null==regist_storeid || "".equals(regist_storeid)){
										if(groupUserMember!=null) {
											regist_storeid = groupUserMember.get("store_id");
											member_type = groupUserMember.get("type");
											userMember.put("member_type", member_type);
											regist_cityno = userMemberService.queryCityNoByStoreid(regist_storeid.toString());
										}
									}else {
										//注册城市的获取顺序：付款表与集采表若无对应门店id,则无城市，再从mongo中获取
										regist_cityno = userMemberService.queryCityNoByStoreid(regist_storeid.toString());
									}
									userMember.put("regist_storeid", regist_storeid);

									if(null==regist_cityno || "".equals(regist_cityno)) {
										regist_cityno = customerInfoRecord.getCityCode();
									}
									userMember.put("regist_cityno", regist_cityno);
									//付款开卡时间获取顺序：付款表-mongo
									if(userMember.get("opencard_time")!=null && !"".equals(userMember.get("opencard_time"))) {
										openCardTime = userMember.get("opencard_time").toString();
									}else {
										openCardTime= customerInfoRecord.getCreateTime();
									}
									//获取社员注册时间
									regitTime = userMember.get("regist_time").toString();
									if(null!=openCardTime && !"".equals(openCardTime)) {
										userMember.put("opencard_time", openCardTime);
										//判断是否当天开卡新社员
										if(DateUtils.StringToDate(openCardTime).equals(DateUtils.StringToDate(regitTime))) {
											isnew_member = "1";
										}
									}
									userMember.put("isnew_member", isnew_member);

									//对身份证号的处理
									idcardStr = customerInfoRecord.getIdCard();
									birthday = customerInfoRecord.getBirthday();
									if(!StringUtils.isBlank(birthday)) {
										userMember.put("birthday",birthday.replace("-", ""));
									}
									if(idcardStr!=null && !"".equals(idcardStr)) {
										userMember.put("idcard", idcardStr);
										idcard = IdCardUtil.getIdCardInfo(idcardStr);
										userMember.put("birthplace",idcard.getBirthplace());
										userMember.put("born_province", idcard.getProvince());
										userMember.put("born_city", idcard.getCity());
										userMember.put("birthday",idcard.getBirthday());
										userMember.put("sex",idcard.getGender());
									}
									
									//社区邀请码
									userMember.put("inviteCode", customerInfoRecord.getInviteCode());
								}else {
									System.out.println("mongo中未存在的customer_id:"+userMember.get("customer_id").toString());
								}
								dfUserMemberService.addDfTryUserMember(userMember);
							}
						}
						logger.info("（试用）社员信息任务执行结果为：开始时间：" + begintime + ",结束时间：" + endtime);
						logger.info("************（试用）社员信息任务结束***********************");
					}
				} catch (Exception e) {
					logger.info("（试用）社员信息任务出现问题，任务执行失败，请查看！");
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}	
	
	
	
	@RequestMapping(value = "rest/testMongo1Run",method = RequestMethod.POST)
	public void testMongo1() {
		try {
			CustomerInfoRecord customerInfoRecord = mongoService.queryCusInfoByCusId("acb81f1ec3564524821049c3bc8aa284");
			System.out.println(customerInfoRecord.getCityCode() + "====="+customerInfoRecord.getIdCard());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	* @Title: updateMemberInviteCodeByCusIdTask
	* @Description: 根据社员customer_id从mongo中找到对应的一条不为空的inviteCode
	* @param     设定文件
	* @return void    返回类型
	* @throws
	 */
	public void updateInviteCodeByCusIdTask() {
		new Thread() {
			public void run() {
				try {
					logger.info("************社员信息刷新邀请码inviteCode数据***********************");
					Map<String, String> paraMap = new HashMap<String, String>();
					List<Map<String, String>> userMemberList = dfUserMemberService.queryDfUserMember(paraMap);
					if (!userMemberList.isEmpty()) {
						int updatenum = 0;
						for (Map<String, String> userMemberMap : userMemberList) {
							CustomerInfoRecord customerInfoRecord = mongoService
									.queryCusInviteCodeByCusId(userMemberMap.get("customer_id"));
							if (customerInfoRecord != null) {
								userMemberMap.put("inviteCode", customerInfoRecord.getInviteCode());
								updatenum += dfUserMemberService.updateInviteCodeByCusId(userMemberMap);
							}
						}
						logger.info("************社员信息刷新邀請碼inviteCode数据，共更新数据："+updatenum+"***********************");
					}
				} catch (Exception e) {
					logger.info("社员信息刷新邀請碼inviteCode数据，任务执行失败，请查看！");
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}
	

	
	/**
	 * 
	* @Title: memberCancelOrderCityDayTask
	* @Description:统计安心合作社会相应E店下所取消的订单
	* @param     设定文件
	* @return void    返回类型
	* @throws
	 */
	@Scheduled(cron = "0 0 */1 * * ?")
	public void memberCancelOrderCityDayTask() {
		new Thread() {
			public void run() {
				try {
					logger.info("************安心合作社按天城市统计取消订单信息调度开始***********************");
					String begintime = DateUtils.getCurDateTime(new Date());
					String endtime = DateUtils.getCurTime(new Date());
					Map<String, String> paraMap = new HashMap<String, String>();
					paraMap.put("begintime", begintime);
					paraMap.put("endtime", endtime);
					List<Map<String, String>> cancelOrderList = userMemberService.queryCancelOrderCityByCreateTime(paraMap);
					if (!cancelOrderList.isEmpty()) {
						int addnum = 0;
						for (Map<String, String> cancelOrderMap : cancelOrderList) {
							addnum += dfUserMemberService.addDsOpeMemberCancelCityDay(cancelOrderMap);
						}
						logger.info("************安心合作社按天城市统计取消订单信息调度结束，共影响数据："+addnum+"***********************");
					}
				} catch (Exception e) {     
					logger.info("安心合作社按天城市统计取消订单信息调度，任务执行失败，请查看！");
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	
	
	
	
	
	/**
	 * 
	 * @Title: syncOnlineMemberTask 
	 * @Description: 同步线上人员信息。调用daqweb接口，每1分钟跑一次，采用多线程
	 * @param 
	 * @return void 返回类型
	 * @throws
	 */
	@Scheduled(cron = "0 */1 * * * ?")
	public void syncOnlineMemberTask() {
		try {
			   //调用daqweb同步线上人员接口。
				String URL=HttpInterfaceUtils.DAQWEB_URL;
		    	String param=String.format(HttpInterfaceUtils.PARAM, "InterManager", "syncOnLineHuman");
				String ret = HttpInterfaceUtils.sendPost(URL,param);
		    	logger.info(ret);
			} catch (Exception e) {
				logger.info("由于网络或其它原因，同步线上人员失败，请查看！");
				logger.info(e.toString());
				e.printStackTrace();
			}
	}
	
	
	/**
	 * 
	 * @Title: syncOnlineMemberTask 
	 * @Description:发送 预警消息的方法。(发频道负责人 )
	 * @param 
	 * @return void 返回类型
	 * @throws
	 * cron:  0 0 8 * * ? *
	 */
	@Scheduled(cron = "0 0 10 * * ? *")
	public void sendCareerMessageTask() {
		try {
				logger.info(">>>>>>>>>>>>>>>>>开始发频道负责人---sendshortmessagestart......... ");
			    //调用daqweb同步线上人员接口。
				String URL=HttpInterfaceUtils.DAQWEB_URL;
		    	String param=String.format(HttpInterfaceUtils.PARAM, "InterManager", "sendWarningMessage");
				String ret = HttpInterfaceUtils.sendPost(URL,param);
		    	logger.info(ret);
				logger.info(">>>>>>>>>>>>>>>>>完成发频道负责人---sendshortmessageend......... ");
			} catch (Exception e) {
				logger.info("由于网络或其它原因，发送失败，请查看！");
				logger.info(e.toString());
				e.printStackTrace();
			}
	}
	
	/**
	 * 
	 * @Title: syncOnlineMemberTask 
	 * @Description:发送 预警消息的方法。 (发店长 )
	 * @param 
	 * @return void 返回类型 暂时不开启 
	 * @throws
	 * cron:  0 0 8 * * ? *
	 */
	/*@Scheduled(cron = "0 0 12 * * ? *")
	public void sendCareerMessageDzTask() {
		try {
				logger.info(">>>>>>>>>>>>>>>>>开始发店长 ");
			    //调用daqweb同步线上人员接口。
				String URL=HttpInterfaceUtils.DAQWEB_URL;
		    	String param=String.format(HttpInterfaceUtils.PARAM, "InterManager", "sendWarningMessageDz");
				String ret = HttpInterfaceUtils.sendPost(URL,param);
		    	logger.info(ret);
			} catch (Exception e) {
				logger.info("由于网络或其它原因，发送失败，请查看！");
				logger.info(e.toString());
				e.printStackTrace();
			}
	}*/
	
}
