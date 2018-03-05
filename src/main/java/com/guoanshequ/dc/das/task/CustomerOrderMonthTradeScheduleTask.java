package com.guoanshequ.dc.das.task;

import com.guoanshequ.dc.das.model.TinyDispatch;
import com.guoanshequ.dc.das.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 
* @author CaoPs
* @date 2018年2月11日
* @version 1.0
* 说明:
 */
@Component
public class CustomerOrderMonthTradeScheduleTask {

	@Autowired
	AreaInfoService areaInfoService;
	@Autowired
	MassOrderService massOrderService;
	@Autowired
	DfMassOrderService dfMassOrderService;
	@Autowired
	TinyDispatchService tinyDispatchService;
	@Autowired
	DfOrderReturnedService dfOrderReturnedService;
	@Autowired
	DsCronTaskService dsCronTaskService;
	@Autowired
	CustomerOrderMonthTradeService customerOrderMonthTradeService;
	@Autowired
	DfCustomerOrderMonthTradeService dfCustomerOrderMonthTradeService;
	@Autowired
	OrderService orderService;
	@Autowired
	TinyVillageService tinyVillageService;
	@Autowired
	MergeBizService mergeBizService;


	private static final Logger logger = LogManager.getLogger(CustomerOrderMonthTradeScheduleTask.class);
	
    /**
     * 
    * @Title: CustomerOrderMonthTradeTask 
    * @Description: 清洗用户数据，每分钟执行一次
    * @param     设定文件 
    * @return void    返回类型 
    * @throws
     */
	@Scheduled(cron ="0 */1 * * * ?")
    public void CustomerOrderMonthTradeTask(){
    	try{
    		logger.info("************清洗用户数据开始***********************");
    		//查询任务是否正在运行
    		Map<String, String> taskMap = new HashMap<String,String>();
    		taskMap = dsCronTaskService.queryDsCronTaskById(3);
    		String isrun = taskMap.get("isrun");
    		String task_status = taskMap.get("task_status");
    		//判断任务是否开启
    		if("ON".equals(isrun)){
    		//如果任务状态为DONE，说明上一次任务执行成功，可以运行下一个任务
    		if("DONE".equals(task_status)){
	    		//设置任务为运行中状态
	    		Map<String, String> runMap = new HashMap<String,String>();
	    		runMap.put("id", "3");
	    		runMap.put("task_status", "RUNNING");
	    		dsCronTaskService.updateTaskStatusById(runMap);
	    		//构建参数
	    		//获取开始时间,本地记录的时间戳
	    		String begintime = dfCustomerOrderMonthTradeService.queryNextBeginTime();
	    		//获取结束时间,order表中的最大签收时间
	    		String endtime = customerOrderMonthTradeService.queryMaxSigntime();
	    		Map<String, String> paraMap=new HashMap<String, String>();
	    		paraMap.put("begintime", begintime);
	    		paraMap.put("endtime", endtime);
	    		//1执行查询，从gemini中查询出时间段内的人店月数据
	    		List<Map<String, String>> cusList = customerOrderMonthTradeService.queryCustomerOrderMonthTradeBySignTime(paraMap);
	    		String resuFlag ="";
	    		if (!cusList.isEmpty()) {
	        		//2从daqWeb表中查询上面人店月前最大的消费次数
	    			String order_month_count ;
	    			String order_id;
	    			TinyDispatch tinyDispatch;
	    			Map<String, Object> addrMap;
	    			Map<String, Object> tinyVillageMap;
	    			List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
	    			for (Map<String, String> cusMap : cusList) {
	    				order_month_count = dfCustomerOrderMonthTradeService.queryCustomerMaxCount(cusMap);
	    				if(StringUtils.isBlank(order_month_count)){
	    					order_month_count = "1";
	    				}
	    				cusMap.put("order_month_count", order_month_count);
	    				//3根据最小order_sn从gemini中获取区块、坐标，
	    				addrMap= orderService.queryOrderAddressByOrderSn(cusMap);
	    				if(addrMap!=null){
	    					cusMap.put("placename", addrMap.get("placename").toString());
	    					cusMap.put("latitude", addrMap.get("latitude").toString());
	    					cusMap.put("longitude", addrMap.get("longitude").toString());
	    				}
	    				
	    				//4根据最小order_sn得到最小order_id从mongo中获取小区code，再根据小区code获取小区Id,小区名称
	    				order_id = orderService.queryIdByOrderSn(cusMap);
	    				tinyDispatch = tinyDispatchService.queryTinyDispatchByOrderId(order_id);
	    				if(tinyDispatch!=null){
	        				tinyVillageMap = tinyVillageService.queryTinyidByCode(tinyDispatch.getCode());
	        				cusMap.put("tiny_village_code", tinyDispatch.getCode().toString());
	        				cusMap.put("tiny_village_id", tinyVillageMap.get("tiny_village_id").toString());
	        				cusMap.put("tiny_village_name", tinyVillageMap.get("tiny_village_name").toString());
	    				}
	    				//将结果每一行记录放入到list集合中
	    				resultList.add(cusMap);
					}
	    			//5数据写入完成后，更新下一次的开始时间，此次的结束时间为下一次的开始时间
	    			resuFlag = mergeBizService.customerTradeMerge(resultList,endtime);
	    		  }
	    		logger.info("用户清洗数据任务执行结果为："+resuFlag+"，开始时间："+begintime+",结束时间："+endtime);
    			}
    		}
    		logger.info("************清洗用户数据结束***********************");
	    } catch (Exception e) {
	    	logger.info("用户清洗数据任务出现问题，任务执行失败，请查看！");
			logger.info(e.toString());
			e.printStackTrace();
		}
    }
}
