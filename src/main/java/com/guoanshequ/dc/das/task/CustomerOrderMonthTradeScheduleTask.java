package com.guoanshequ.dc.das.task;

import com.guoanshequ.dc.das.model.TinyDispatch;
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
		    		//设置任务为运行中状态
		    		Map<String, String> runMap = new HashMap<String,String>();
		    		runMap.put("id", "3");
		    		runMap.put("task_status", "RUNNING");
		    		dsCronTaskService.updateTaskStatusById(runMap);
	        		//2从daqWeb表中查询上面人店月前最大的消费次数
	    			Integer order_month_count = 0;
	    			String order_id;
	    			TinyDispatch tinyDispatch;
	    			Map<String, Object> addrMap;
	    			Map<String, Object> tinyVillageMap;
	    			List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
	    			for (Map<String, String> cusMap : cusList) {
	    				order_month_count = dfCustomerOrderMonthTradeService.queryCustomerMaxCount(cusMap);
	    				if(order_month_count==null){
	    					order_month_count = 1;
	    				}else{
	    					order_month_count += 1;
	    				}

	    				cusMap.put("order_month_count", String.valueOf(order_month_count));
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
	
	/**
	 * 
	* @Title: customerTradePatchTask 
	* @Description: 每天晚上将整月数据进行人店月，将用户表中缺失补全
	* 				可手动：根据ds_crontask中begintime、endtime进行
	* 				可自动：每天凌晨0点30分，把1号到昨天晚上的数据补进来
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Scheduled(cron ="0 30 0 * * ?")
	public void customerTradePatchTask(){
	new Thread(){
		public void run() {
    	try{
    		logger.info("*************清洗用户打补丁任务调度开始**************");
			Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(5);
			String isrun = taskMap.get("isrun");
			if("ON".equals(isrun)){
				String runtype = taskMap.get("runtype");
				String maxSignedTime = null;
				String endSignedTime = null;
				//获取上次调度时的最大签收时间开始时间与结束时间
				if("MANUAL".equals(runtype)){
					maxSignedTime = taskMap.get("begintime");
					endSignedTime = taskMap.get("endtime");
				}else{
					//前一天日期所在月份的1号
					maxSignedTime = DateUtils.getPreDateFirstOfMonth(new Date());
					//前一天日期
					endSignedTime = DateUtils.getCurDate(new Date());
				}
				//给后台接口构建参数
				Map<String, String> paraMap=new HashMap<String, String>();
				paraMap.put("maxSignedTime", maxSignedTime);
				paraMap.put("endSignedTime", endSignedTime);
				
				int addnum = dfCustomerOrderMonthTradeService.customerTradePatch(paraMap);
				
				//插入完毕将状态设置为'DONE'
				Map<String, String> doneMap = new HashMap<String,String>();
				doneMap.put("id", "3");
				doneMap.put("task_status", "DONE");
				dsCronTaskService.updateTaskStatusById(doneMap);
				
				logger.info("***清洗用户打补丁任务调度结束******,开始时间："+maxSignedTime+",结束时间："+endSignedTime+"，共插入记录条数："+addnum);
			}
    		logger.info("************清洗用户打补丁任务调度结束***********************");
	    } catch (Exception e) {
	    	logger.info("用户清洗数据打补丁");
			logger.info(e.toString());
			e.printStackTrace();
		}
		}
	}.start();
}
	
	
	/**
	 * 
	* @Title: UpdateCustomerOrderMonthTrade 
	* @Description: 根据创建时间段内的订单号更新对应的小区信息 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
    public void UpdateCustomerOrderMonthTrade(){
	new Thread(){
		public void run() {
    	try {
			//1、根据创建的时间段循环订单号
    		Map<String, String> taskMap = new HashMap<String,String>();
    		Map<String, String> paraMap=new HashMap<String, String>();
    		Map<String, String> cusMap=new HashMap<String, String>();
    		taskMap = dsCronTaskService.queryDsCronTaskById(4);
    		String begintime =taskMap.get("begintime");
    		String endtime =taskMap.get("endtime");
    		paraMap.put("begintime", begintime);
    		paraMap.put("endtime", endtime);
    		List<String> cusordmonList = dfCustomerOrderMonthTradeService.queryCusOrderMonByCreatetime(paraMap);
    		int updatenum =0;
    		if(!cusordmonList.isEmpty()){
	    		//设置任务为运行中状态
	    		Map<String, String> runMap = new HashMap<String,String>();
	    		runMap.put("id", "4");
	    		runMap.put("task_status", "RUNNING");
	    		dsCronTaskService.updateTaskStatusById(runMap);
    			String order_id="";
    			TinyDispatch tinyDispatch;
    			Map<String, Object> tinyVillageMap;
    			for (String order_sn : cusordmonList) {
    				paraMap.put("order_sn", order_sn);
    				cusMap.put("order_sn", order_sn);
    				order_id = orderService.queryIdByOrderSn(cusMap);
    				tinyDispatch = tinyDispatchService.queryTinyDispatchByOrderId(order_id);
    				if(tinyDispatch!=null){
        				tinyVillageMap = tinyVillageService.queryTinyidByCode(tinyDispatch.getCode());
        				cusMap.put("tiny_village_code", tinyDispatch.getCode().toString());
        				cusMap.put("tiny_village_id", tinyVillageMap.get("tiny_village_id").toString());
        				cusMap.put("tiny_village_name", tinyVillageMap.get("tiny_village_name").toString());
        				dfCustomerOrderMonthTradeService.updateTinyInfo(cusMap);
        				updatenum+=1;
    				}
				}
	    		//设置任务为完成状态
	    		Map<String, String> doneMap = new HashMap<String,String>();
	    		doneMap.put("id", "4");
	    		doneMap.put("task_status", "DONE");
	    		dsCronTaskService.updateTaskStatusById(doneMap);
    		}
    		logger.info("**********清洗用户表根据订单更新小区信息，共更新数据条数："+updatenum+"**********");
			} catch (Exception e) {
				logger.info(e.toString());
				e.printStackTrace();
			}
    	  }
    	}.start();
    }
	
	
    /**
     * massOrder中新客订单任务调度打标签
     * 夜间1:30调度
     */
  @Scheduled(cron ="0 30 01 * * ?")
    public void newCustomerTagTask(){
    	new Thread(){
    		public void run() {
    			try{
    				logger.info("**********massorder打 拉新标签 任务调度开始**********");
    	        	String preDate = DateUtils.getPreDate(new Date());
    	        	String year = preDate.substring(0,4);
    	        	String month = preDate.substring(5,7);
    	        	String order_ym = year+month;
    	        	//给后台接口构建参数
    	        	Map<String, String> paraMap=new HashMap<String, String>();
    	        	paraMap.put("order_ym", order_ym);
    	        	dfCustomerOrderMonthTradeService.updateCustomerOrderNewDaily(paraMap);
    	        	dfCustomerOrderMonthTradeService.updateCustomerOrderNewMonthly(paraMap);
    	        	dfCustomerOrderMonthTradeService.updateCustomerOrderNewTotal(paraMap);
		        	logger.info("**********massorder打 拉新标签 任务调度结束**********");
    			} catch (Exception e) {
    		    	logger.info("massorder打拉新标签出问题请检查");
    				logger.info(e.toString());
    				e.printStackTrace();
	    		}
    		}
    	}.start();
    }
}
