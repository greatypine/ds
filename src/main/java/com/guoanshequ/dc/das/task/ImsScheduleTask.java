package com.guoanshequ.dc.das.task;

import com.guoanshequ.dc.das.model.*;
import com.guoanshequ.dc.das.service.*;
import com.guoanshequ.dc.das.utils.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ImsScheduleTask {

	@Autowired
	TbsdGdsService tbsdGdsService;
	@Autowired
	DfTbsgdsService dfTbsgdsService;
	@Autowired
	ImsService imsService;
	@Autowired
	DfImsService dfImsService;
	@Autowired
	DsCronTaskService dsCronTaskService;

	private static final Logger logger = LogManager.getLogger(ImsScheduleTask.class);

	/**
	 * 同步进销存系统tbs_d_gds商品日结算表前一天数据
	 * 调度规则：每天凌晨4点01分
	 */	
	@Scheduled(cron ="0 01 4 * * ?")
	public void TbsdGdsTask() {
		new Thread() {
			public void run() {
				try {
					logger.info("**********进销存系统tbs_d_gds商品日结算表任务调度开始**********");
					Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(14);
					String isrun = taskMap.get("isrun");
					int addnums =0;
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
						List<ImsTbsdgds> imsTbsdgdsList = tbsdGdsService.queryTbsDGdsByTime(paraMap);
						if (!imsTbsdgdsList.isEmpty()) {
				    		Map<String, String> runMap = new HashMap<String,String>();
				    		runMap.put("id", "14");
				    		runMap.put("task_status", "RUNNING");
				    		dsCronTaskService.updateTaskStatusById(runMap);
				    		
							for (ImsTbsdgds imsTbsdgds : imsTbsdgdsList) {
								addnums += dfTbsgdsService.addDfTbsdgdsDaily(imsTbsdgds);
										   dfTbsgdsService.addDfTbsdgdsTotal(imsTbsdgds);
							}
						}
			    		//设置任务为完成状态
			    		Map<String, String> doneMap = new HashMap<String,String>();
			    		doneMap.put("id", "14");
			    		doneMap.put("task_status", "DONE");
			    		dsCronTaskService.updateTaskStatusById(doneMap);
				  }
				logger.info("**********进销存系统tbs_d_gds商品日结算表任务调度结束,共插入数据："+addnums+"**********");
				} catch (Exception e) {
					logger.info("进销存系统tbs_d_gds商品日结算表出现问题，任务执行失败，请查看！");
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * 定时用于更新df_ims_tb_o_count/countg/store表
	 * 调度规则：每天凌晨4点10分
	 */
	@Scheduled(cron ="0 10 4 * * ?")
	public void imsTbCountTask(){
		new Thread(){
			public void run() {
				try{
					logger.info("**********df_ims_o_count任务调度开始**********");
					Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(16);
					String isrun = taskMap.get("isrun");
					if("ON".equals(isrun)){
						String runtype = taskMap.get("runtype");
						String begintime = null;
						String endtime = null;
						//获取上次调度时的最大审核时间开始时间与结束时间
						if("MANUAL".equals(runtype)){
							begintime = taskMap.get("begintime");
							endtime = taskMap.get("endtime");
						}else{
							begintime = imsService.queryMaxAuDtTime()==null?DateUtils.getPreDate(new Date()):imsService.queryMaxAuDtTime();
							endtime = DateUtils.getCurDateTime(new Date());
						}

						//给后台接口构建参数
						Map<String, String> paraMap=new HashMap<String, String>();
						paraMap.put("begintime", begintime);
						paraMap.put("endtime", endtime);

						List<ImsTbOCount> list =imsService.queryImsTbOCountByDate(paraMap);
						for (ImsTbOCount count : list) {
							String c_id=count.getC_id();
							String c_store_id=count.getC_store_id();
							ImsTbOCount isExist = dfImsService.queryImsTbOCountIsExist(c_id);
							if(isExist!=null){
								dfImsService.deleteDfImsTbOCount(c_id);
								dfImsService.deleteDfImsTbOCountg(c_id);
							}
							ImsTbStore isExistStore = dfImsService.queryImsTbStoreIsExist(c_store_id);
							if(isExistStore!=null){
								dfImsService.deleteDfImsTbStore(c_store_id);
							}
							ImsTbStore store = imsService.queryImsTbStoreByStoreId(c_store_id);
							dfImsService.addDfImsTbStore(store);
							dfImsService.addDfImsTbOCount(count);
							List<ImsTbOCountg> countgList =imsService.queryImsTbOCountgByCId(c_id);
							dfImsService.addDfImsTbOCountg(countgList);

						}
					}
					logger.info("**********df_ims_o_count任务调度结束**********");
				} catch (Exception e) {
					logger.info("df_ims_o_count任务调度异常：",e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * 定时用于更新df_ims_tb_o_l/store表
	 * 调度规则：每天凌晨4点10分
	 */
	@Scheduled(cron ="0 10 4 * * ?")
	public void imsTblTask(){
		new Thread(){
			public void run() {
				try{
					logger.info("**********df_ims_o_l任务调度开始**********");
					Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(17);
					String isrun = taskMap.get("isrun");
					if("ON".equals(isrun)){
						String runtype = taskMap.get("runtype");
						String begintime = null;
						String endtime = null;
						//获取上次调度时的最大审核时间开始时间与结束时间
						if("MANUAL".equals(runtype)){
							begintime = taskMap.get("begintime");
							endtime = taskMap.get("endtime");
						}else{
							begintime = DateUtils.getPreDate(new Date());
							endtime = DateUtils.getCurDateTime(new Date());
						}

						//给后台接口构建参数
						Map<String, String> paraMap=new HashMap<String, String>();
						paraMap.put("begintime", begintime);
						paraMap.put("endtime", endtime);

						List<ImsTbOl> list =imsService.queryImsTbOlByDate(paraMap);
						for (ImsTbOl tbOl : list) {
							String c_id=tbOl.getC_id();
							ImsTbOl isExist = dfImsService.queryImsTbOlIsExist(c_id);
							if(isExist!=null){
								dfImsService.deleteDfImsTbOl(c_id);
							}
							String c_store_id=tbOl.getC_store_id();
							ImsTbStore isExistStore = dfImsService.queryImsTbStoreIsExist(c_store_id);
							if(isExistStore!=null){
								dfImsService.deleteDfImsTbStore(c_store_id);
							}
							ImsTbStore store = imsService.queryImsTbStoreByStoreId(c_store_id);
							dfImsService.addDfImsTbStore(store);
							dfImsService.addDfImsTbOl(tbOl);
						}
					}
					logger.info("**********df_ims_o_l任务调度结束**********");
				} catch (Exception e) {
					logger.info("df_ims_o_l任务调度异常：",e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}

}
