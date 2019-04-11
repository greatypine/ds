package com.guoanshequ.dc.das.task;

import com.guoanshequ.dc.das.model.*;
import com.guoanshequ.dc.das.service.*;
import com.guoanshequ.dc.das.utils.DateUtils;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.guoanshequ.dc.das.utils.HttpClientUtil;
import com.guoanshequ.dc.das.utils.HttpInterfaceUtils;
import com.guoanshequ.dc.das.utils.TunnelConstUtil;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
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
	//@Scheduled(cron ="0 01 4 * * ?")
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

						Map tunnelMap1 = new HashMap();
						tunnelMap1.put("sql",TunnelConstUtil.TunnelTbsdGds(begintime,endtime));
						String tunnelRet1 = HttpClientUtil.sendPost(TunnelConstUtil.SQLSERVER_URL,tunnelMap1);
						JSONArray jsonArray1 = JSONArray.fromObject(tunnelRet1);
						List<ImsTbsdgds> imsTbsdgdsList = JSONArray.toList(jsonArray1, new ImsTbsdgds(), new JsonConfig());

						if (!imsTbsdgdsList.isEmpty()) {
				    		Map<String, String> runMap = new HashMap<String,String>();
				    		runMap.put("id", "14");
				    		runMap.put("task_status", "RUNNING");
				    		dsCronTaskService.updateTaskStatusById(runMap);
				    		
				    		logger.info("************sqlserver中tbs_d_gds表此次共查询的数据条数：*************"+imsTbsdgdsList.size());
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
	 * 同步进销存系统tbs_d_gds商品日结算表前一天数据
	 * 调度规则：每天凌晨4点01分
	 */	
	//@Scheduled(cron ="0 01 4 * * ?")
	public void TbsdGdsSplitByRowTask() {
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

//						Map tunnelCountMap = new HashMap();
//						tunnelCountMap.put("sql",TunnelConstUtil.TunnelTbsdGds_Count(begintime,endtime));
//						String tunnelCount = HttpClientUtil.sendPost(TunnelConstUtil.SQLSERVER_URL,tunnelCountMap);
//						JSONArray jsonArray = JSONArray.fromObject(tunnelCount);
						
						int tnum = 2267964;

						
						if(tnum>0) {
							logger.info("记录总条数为："+tnum);
							int step =3000;
							int startPoint = 0;
							int endPoint = 0;
							
							for (int x = 1;x <= tnum/step+1;x++) {
								
								startPoint = endPoint;
								endPoint += step;
								
								Map tunnelMap1 = new HashMap();
								tunnelMap1.put("sql",TunnelConstUtil.TunnelTbsdGds_splitByRow(begintime,endtime,startPoint,endPoint));
								String tunnelRet1 = HttpClientUtil.sendPost(TunnelConstUtil.SQLSERVER_URL,tunnelMap1);
								JSONArray jsonArray1 = JSONArray.fromObject(tunnelRet1);
								List<ImsTbsdgds> imsTbsdgdsList = JSONArray.toList(jsonArray1, new ImsTbsdgds(), new JsonConfig());
		
								if (!imsTbsdgdsList.isEmpty()) {
						    		Map<String, String> runMap = new HashMap<String,String>();
						    		runMap.put("id", "14");
						    		runMap.put("task_status", "RUNNING");
						    		dsCronTaskService.updateTaskStatusById(runMap);
						    		
						    		logger.info("************sqlserver中tbs_d_gds表此次共查询的数据条数：*************"+imsTbsdgdsList.size()+"startPoint: "+startPoint+",endPoint: "+endPoint);
									for (ImsTbsdgds imsTbsdgds : imsTbsdgdsList) {
										addnums += dfTbsgdsService.addDfTbsdgdsDaily(imsTbsdgds);
												   dfTbsgdsService.addDfTbsdgdsTotal(imsTbsdgds);
									}
								}
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
							begintime = DateUtils.getPreDateTime(new Date());
							endtime = DateUtils.getCurDateTime(new Date());
						}

						Map tunnelMap1 = new HashMap();
						String sql1 = URLEncoder.encode(TunnelConstUtil.TunnelTbOCount(begintime,endtime));
						tunnelMap1.put("sql",sql1);
						String tunnelRet1 = HttpClientUtil.sendPost(TunnelConstUtil.SQLSERVER_URL,tunnelMap1);
						JSONArray jsonArray1 = JSONArray.fromObject(tunnelRet1);
						List<ImsTbOCount> list1 = JSONArray.toList(jsonArray1, new ImsTbOCount(), new JsonConfig());

						for (ImsTbOCount count : list1) {
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

							Map tunnelMap2 = new HashMap();
							tunnelMap2.put("sql",TunnelConstUtil.TunnelStore(c_store_id));
							String tunnelRet2 = HttpClientUtil.sendPost(TunnelConstUtil.SQLSERVER_URL,tunnelMap2);
							JSONArray jsonArray2 = JSONArray.fromObject(tunnelRet2);
							List<ImsTbStore> list2 = JSONArray.toList(jsonArray2, new ImsTbStore(), new JsonConfig());
							ImsTbStore store = list2.get(0);

							dfImsService.addDfImsTbStore(store);
							dfImsService.addDfImsTbOCount(count);

							Map tunnelMap3 = new HashMap();
							tunnelMap3.put("sql",TunnelConstUtil.TunnelTbOCountg(c_id));
							String tunnelRet3 = HttpClientUtil.sendPost(TunnelConstUtil.SQLSERVER_URL,tunnelMap3);
							JSONArray jsonArray3 = JSONArray.fromObject(tunnelRet3);
							List<ImsTbOCountg> countgList = JSONArray.toList(jsonArray3, new ImsTbOCountg(), new JsonConfig());

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

						Map tunnelMap1 = new HashMap();
						String sql1 = URLEncoder.encode(TunnelConstUtil.TunnelTbOl(begintime,endtime));
						tunnelMap1.put("sql",sql1);
						String tunnelRet1 = HttpClientUtil.sendPost(TunnelConstUtil.SQLSERVER_URL,tunnelMap1);
						JSONArray jsonArray1 = JSONArray.fromObject(tunnelRet1);
						List<ImsTbOl> list = JSONArray.toList(jsonArray1, new ImsTbOl(), new JsonConfig());

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

							Map tunnelMap2 = new HashMap();
							tunnelMap2.put("sql",TunnelConstUtil.TunnelStore(c_store_id));
							String tunnelRet2 = HttpClientUtil.sendPost(TunnelConstUtil.SQLSERVER_URL,tunnelMap2);
							JSONArray jsonArray2 = JSONArray.fromObject(tunnelRet2);
							List<ImsTbStore> list2 = JSONArray.toList(jsonArray2, new ImsTbStore(), new JsonConfig());
							ImsTbStore store = list2.get(0);

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
