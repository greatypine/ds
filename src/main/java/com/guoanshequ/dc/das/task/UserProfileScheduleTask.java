package com.guoanshequ.dc.das.task;

import com.guoanshequ.dc.das.service.*;
import com.guoanshequ.dc.das.utils.DateUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 
 * @author CaoPs
 * @date 2018年3月12日
 * @version 1.0 说明:
 */
@Component
public class UserProfileScheduleTask {

	@Autowired
	AreaInfoService areaInfoService;
	@Autowired
	TinyDispatchService tinyDispatchService;
	@Autowired
	DsCronTaskService dsCronTaskService;
	@Autowired
	OrderService orderService;
	@Autowired
	TinyVillageService tinyVillageService;
	@Autowired
	MergeBizService mergeBizService;
	@Autowired
	UserProfileService userProfileService;
	@Autowired
	DfUserProfileService dfUserProfileService;
	@Autowired
	DfMassOrderService dfMassOrderService;

	private static final Logger logger = LogManager.getLogger(UserProfileScheduleTask.class);

	/**
	 * 
	 * @Title: UserProfileScheduleTask 
	 * @Description: 用户档案一天一次，每天凌晨02点30分，采用多线程 
	 * @param 设定文件 
	 * @return void 返回类型
	 * @throws
	 */
	@Scheduled(cron = "0 30 02 * * ?")
	public void userProfileTask() {
		new Thread() {
			public void run() {
				try {
					logger.info("************用户档案定时任务开始***********************");
					Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(6);
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
							begintime = DateUtils.getPreDateTime(new Date());
							endtime = DateUtils.getCurDateTime(new Date());
						}

						Map<String, String> paraMap = new HashMap<String, String>();
						paraMap.put("begintime", begintime);
						paraMap.put("endtime", endtime);
						// 1执行查询，从gemini中查询出时间段内的用户数据
						List<Map<String, String>> cusList = userProfileService.queryCustomerInfoBySignTime(paraMap);
						int isExistCusDraw = 0;
						Map<String, String> codeMap = new HashMap<String, String>();
						if (!cusList.isEmpty()) {
				    		//设置任务为运行中状态
				    		Map<String, String> runMap = new HashMap<String,String>();
				    		runMap.put("id", "6");
				    		runMap.put("task_status", "RUNNING");
				    		dsCronTaskService.updateTaskStatusById(runMap);
							for (Map<String, String> cusMap : cusList) {
								codeMap = dfMassOrderService.queryVillageAreaCodeByOrdersn(cusMap.get("first_order_sn"));
								// 根据订单号查询是否有小区片区
								if (codeMap != null) {
									cusMap.put("tiny_village_code", codeMap.get("info_village_code"));
									cusMap.put("area_code", codeMap.get("area_code"));
								}
								// 根据用户电话查询是否存在用户画像
								isExistCusDraw = dfUserProfileService.isExistCusDraw(cusMap.get("customer_phone")) > 0
										? 1 : 0;
								cusMap.put("user_model", String.valueOf(isExistCusDraw));
								dfUserProfileService.addDfUserProfile(cusMap);
							}
				    		//设置任务为运行完成状态
				    		Map<String, String> doneMap = new HashMap<String,String>();
				    		doneMap.put("id", "6");
				    		doneMap.put("task_status", "DONE");
				    		dsCronTaskService.updateTaskStatusById(doneMap);
						}
						logger.info("用户档案任务执行结果为：开始时间：" + begintime + ",结束时间：" + endtime);
						logger.info("************用户档案任务结束***********************");
					}
				} catch (Exception e) {
					logger.info("用户档案任务任务出现问题，任务执行失败，请查看！");
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}
}
