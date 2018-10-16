package com.guoanshequ.dc.das.task;

import com.guoanshequ.dc.das.model.Customer;
import com.guoanshequ.dc.das.model.DfMassOrder;
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
	@Autowired
	HumanresourceService humanresourceService;


	private static final Logger logger = LogManager.getLogger(UserProfileScheduleTask.class);

	/**
	 * 
	 * @Title: UserProfileScheduleTask 
	 * @Description: 用户档案一天一次，每天凌晨02点05分，采用多线程 
	 * @param 设定文件 
	 * @return void 返回类型
	 * @throws
	 */
	@Scheduled(cron = "0 05 02 * * ?")
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
						if (!cusList.isEmpty()) {
				    		//设置任务为运行中状态
				    		Map<String, String> runMap = new HashMap<String,String>();
				    		runMap.put("id", "6");
				    		runMap.put("task_status", "RUNNING");
				    		dsCronTaskService.updateTaskStatusById(runMap);
							for (Map<String, String> cusMap : cusList) {
								DfMassOrder dfMassOrder = dfMassOrderService.queryMassOrder(cusMap);
								if (dfMassOrder!=null) {
									cusMap.put("tiny_village_code",dfMassOrder.getInfo_village_code());
									cusMap.put("area_code", dfMassOrder.getArea_code());
									cusMap.put("storeno", dfMassOrder.getStore_code());
									cusMap.put("dep_name", dfMassOrder.getDepartment_name());
									cusMap.put("channel_name", dfMassOrder.getChannel_name());
								}
								String customer_phone = cusMap.get("customer_phone");
								// 根据用户电话查询是否存在用户画像
								isExistCusDraw = dfUserProfileService.isExistCusDraw(customer_phone) > 0 ? 1 : 0;
								cusMap.put("user_model", String.valueOf(isExistCusDraw));
								
								String idcard =null;
								//根据用户电话获取内部员工身份证号
								String nb_idcard = humanresourceService.queryIdCardOfonlineByPhone(customer_phone);
								if(nb_idcard!=null) {
									idcard = nb_idcard;
									Map<String, String> usertagMap = new HashMap<>();
									usertagMap.put("customer_id", cusMap.get("customer_id"));
									usertagMap.put("usertag", "N");
									dfUserProfileService.addInterStaffUserTag(usertagMap);
								}
								//根据用户电话判断是否存在养老餐用户
								String ylc_idcard = dfUserProfileService.queryYlcIdcardByPhone(customer_phone);
								if(ylc_idcard!=null&&idcard==null) {
									idcard = ylc_idcard;
									cusMap.put("idcard",idcard);
									dfUserProfileService.addDfUserProfile(cusMap);
									dfUserProfileService.deleteYlcIdcardByPhone(customer_phone);
								}else {
									cusMap.put("idcard",idcard);
									dfUserProfileService.addDfUserProfile(cusMap);
								}
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
	
	public void addCusName() {
		String cus_id ="";
		String cus_name ="";
		try {
			logger.info("==============插入用户名称任务开始===============");
			List<Customer> cuslist = userProfileService.queryCusName();
			if (!cuslist.isEmpty()) {
				for (Customer cusobj : cuslist) {
					cus_id = cusobj.getId();
					cus_name = cusobj.getName();
					dfUserProfileService.addName(cusobj);
				}
			}
		} catch (Exception e) {
			logger.info("插入用户名称任务开始，任务执行哈哈失败，请查看！用户id: "+cus_id +",用户名： "+cus_name);
			logger.info(e.toString());
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @Title: UserStoreScheduleTask 
	 * @Description: 用户门店明细一天一次，每天凌晨02点05分，采用多线程 
	 * @param 设定文件 
	 * @return void 返回类型
	 * @throws
	 */
	@Scheduled(cron = "0 05 02 * * ?")
	public void userStoreTask() {
		new Thread() {
			public void run() {
				try {
					logger.info("************用户门店明细定时任务开始***********************");
					Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(8);
					String isrun = taskMap.get("isrun");
					// 判断任务是否开启
					if ("ON".equals(isrun)) {
						int addnum = 0;
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
						List<Map<String, String>> cusStoreList = userProfileService.queryCustomerStoreBySignTime(paraMap);
						Map<String, String> codeMap = new HashMap<String, String>();
						Map<String, Object> addrMap = new HashMap<String, Object>();
						if (!cusStoreList.isEmpty()) {
				    		//设置任务为运行中状态
				    		Map<String, String> runMap = new HashMap<String,String>();
				    		runMap.put("id", "8");
				    		runMap.put("task_status", "RUNNING");
				    		dsCronTaskService.updateTaskStatusById(runMap);
							for (Map<String, String> cusMap : cusStoreList) {
								codeMap = dfMassOrderService.queryVillageAreaCodeByOrdersn(cusMap.get("order_sn"));
								// 根据订单号查询是否有小区片区
								if (codeMap != null) {
									cusMap.put("tiny_village_code", codeMap.get("info_village_code"));
									cusMap.put("area_code", codeMap.get("area_code"));
									cusMap.put("employee_a_no", codeMap.get("info_employee_a_no"));
								}
			    				addrMap= orderService.queryOrderAddressByOrderSn(cusMap);
			    				if(addrMap!=null){
			    					cusMap.put("latitude", addrMap.get("latitude").toString());
			    					cusMap.put("longitude", addrMap.get("longitude").toString());
			    				}
								// 插入数据
			    				addnum += dfUserProfileService.addDfUserStore(cusMap);
							}
				    		//设置任务为运行完成状态
				    		Map<String, String> doneMap = new HashMap<String,String>();
				    		doneMap.put("id", "8");
				    		doneMap.put("task_status", "DONE");
				    		dsCronTaskService.updateTaskStatusById(doneMap);
						}
						logger.info("用户门店明细任务执行结果为：开始时间：" + begintime + ",结束时间：" + endtime +", 共插入数据行数："+addnum);
						logger.info("************用户门店明细任务结束***********************");
					}
				} catch (Exception e) {
					logger.info("用户门店明细任务任务出现问题，任务执行失败，请查看！");
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}	

}
