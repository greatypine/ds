package com.guoanshequ.dc.das.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("MergeBizService")
@Transactional(value = "master", rollbackFor = Exception.class)
public class MergeBizService {

	@Autowired
	DfCustomerOrderMonthTradeService dfCustomerOrderMonthTradeService;
	@Autowired
	DsCronTaskService dsCronTaskService;
	
	/**
	 * 
	* @Title: customerTradeMerge 
	* @Description: 用户清洗事务控制，保证数据的插入与时间点的写入是一致的，当数据插入一半时如果遇到问题则整体都不写入
	* @param @param resultList
	* @param @param endtime
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String customerTradeMerge(List<Map<String, String>> resultList,String endtime){
		String resuFlag ="false";
		try {
			//1插入数据
			for (Map<String, String> map : resultList) {
				dfCustomerOrderMonthTradeService.addDfCustomerOrderMonthTrade(map);
			}
			//2将任务状态进行变更
			Map<String, String> paraMap = new HashMap<String,String>();
			paraMap.put("id", "3");
			paraMap.put("task_status", "DONE");
			dsCronTaskService.updateTaskStatusById(paraMap);
			//3更新时间点作为下一个任务的开始时间
			dfCustomerOrderMonthTradeService.updateNextBeginTime(endtime);
			resuFlag = "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resuFlag;
	}
	
}
