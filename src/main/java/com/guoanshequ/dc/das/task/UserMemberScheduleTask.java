package com.guoanshequ.dc.das.task;

import com.guoanshequ.dc.das.model.CustomerInfoRecord;
import com.guoanshequ.dc.das.model.IdCard;
import com.guoanshequ.dc.das.service.*;
import com.guoanshequ.dc.das.utils.DateUtils;
import com.guoanshequ.dc.das.utils.IdCardUtil;

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
	 * @Description: 社员信息表，每小时一次，采用多线程 
	 * @param 设定文件 
	 * @return void 返回类型
	 * @throws
	 */
	@Scheduled(cron = "0 0 */1 * * ?")
	public void userMemberTask() {
		new Thread() {
			public void run() {
				try {
					logger.info("************社员信息定时任务开始***********************");
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
							begintime = DateUtils.getCurDateTime(new Date());
							endtime = DateUtils.getCurTime(new Date());
						}

						Map<String, String> paraMap = new HashMap<String, String>();
						paraMap.put("begintime", begintime);
						paraMap.put("endtime", endtime);
						// 1执行查询，从gemini中查询出时间段内的用户数据
						List<Map<String, Object>> userMemberList = userMemberService.queryUserMemberByCreateTime(paraMap);
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
							for (Map<String, Object> userMember : userMemberList) {
								isnew_member="0";
								customerInfoRecord = mongoService.queryCusInfoByCusId(userMember.get("customer_id").toString());
								if(customerInfoRecord!=null) {
									idcardStr = customerInfoRecord.getIdCard();
									openCardTime = customerInfoRecord.getCreateTime();
									userMember.put("opencard_time", openCardTime);
									regitTime = userMember.get("regist_time").toString();
									if(DateUtils.StringToDate(openCardTime).equals(DateUtils.StringToDate(regitTime))) {
										isnew_member = "1";
									}
									if(!"".equals(idcardStr)) {
										userMember.put("idcard", idcardStr);
										idcard = IdCardUtil.getIdCardInfo(idcardStr);
										userMember.put("birthplace",idcard.getBirthplace());
										userMember.put("born_province", idcard.getProvince());
										userMember.put("born_city", idcard.getCity());
										userMember.put("birthday",idcard.getBirthday());
										userMember.put("sex",idcard.getGender());
										userMember.put("regist_cityno", customerInfoRecord.getCityCode());
										userMember.put("inviteCode", customerInfoRecord.getInviteCode());
									}
								}
								userMember.put("isnew_member", isnew_member);
								dfUserMemberService.addDfUserMember(userMember);
							}
				    		//设置任务为运行完成状态
				    		Map<String, String> doneMap = new HashMap<String,String>();
				    		doneMap.put("id", "10");
				    		doneMap.put("task_status", "DONE");
				    		dsCronTaskService.updateTaskStatusById(doneMap);
						}
						logger.info("社员信息任务执行结果为：开始时间：" + begintime + ",结束时间：" + endtime);
						logger.info("************社员信息任务结束***********************");
					}
				} catch (Exception e) {
					logger.info("社员信息任务出现问题，任务执行失败，请查看！");
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
}
