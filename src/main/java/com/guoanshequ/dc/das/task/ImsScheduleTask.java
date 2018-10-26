package com.guoanshequ.dc.das.task;

import com.guoanshequ.dc.das.model.ImsTbsdgds;
import com.guoanshequ.dc.das.service.DfTbsgdsService;
import com.guoanshequ.dc.das.service.DsCronTaskService;
import com.guoanshequ.dc.das.service.TbsdGdsService;
import com.guoanshequ.dc.das.utils.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImsScheduleTask {

	@Autowired
	TbsdGdsService tbsdGdsService;
	@Autowired
	DfTbsgdsService dfTbsgdsService;
	@Autowired
	DsCronTaskService dsCronTaskService;

	private static final Logger logger = LogManager.getLogger(ImsScheduleTask.class);

	// @Scheduled(cron = "0/5 * * * * ? ")
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
}
