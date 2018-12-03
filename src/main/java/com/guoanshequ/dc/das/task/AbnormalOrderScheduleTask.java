package com.guoanshequ.dc.das.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guoanshequ.dc.das.service.AbnormalOrderService;
import com.guoanshequ.dc.das.service.DfEshopPurchaseService;
import com.guoanshequ.dc.das.service.DsCronTaskService;
import com.guoanshequ.dc.das.service.EmployeeKeeperInfoService;
import com.guoanshequ.dc.das.service.StoreNumberService;
import com.guoanshequ.dc.das.service.TAbnormalOrderService;
import com.guoanshequ.dc.das.utils.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
* @author CaoPs
* @date 2017年9月24日
* @version 1.0
* 说明:异常订单任务调度
* 
 */
@Component
public class AbnormalOrderScheduleTask {
    @Autowired
    StoreNumberService storeNumberService;
    @Autowired
    AbnormalOrderService abnormalOrderService;
    @Autowired
    TAbnormalOrderService tabnormalOrderService;
    @Autowired
    DfEshopPurchaseService dfEshopPurchaseService;
    @Autowired
    EmployeeKeeperInfoService employeeKeeperInfoService;
	@Autowired
	DsCronTaskService dsCronTaskService;
    
    private static final Logger logger = LogManager.getLogger(AbnormalOrderScheduleTask.class);
    
    /**
     * 异常订单任务调度
     * 调度规则：每天0点30分开始调度
     * 参数：begindate  enddate  storename  storeids
     */
    @Scheduled(cron ="0 30 0 * * ?")
    public void abnormalOrderTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    				Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(99);
    				String runtype = taskMap.get("runtype");
    	        	logger.info("**********自动异常订单任务调度开始**********");
    	        	String begindate = null;
    	        	String enddate = null;
    	        	if("MANUAL".equals(runtype)) {
    	        		begindate = taskMap.get("begintime");
    	        		enddate = taskMap.get("endtime");
    	        	}else {
        	        	//前一天日期所在月份的1号
        	        	begindate = DateUtils.getPreDateFirstOfMonth(new Date());
        	        	//前一天日期
        	        	enddate = DateUtils.getPreDate(new Date());
    	        	}
    	    		//取得年份
    	    		String year = begindate.substring(0, 4);
    	    		//取得月份
    	    		String month = begindate.substring(5, 7);
    	        	//给后台接口构建参数
    	        	Map<String, String> paraMap=new HashMap<String, String>();
    	        	String storeIds = storeNumberService.queryStoreNumbers();
    	        	String eshopIds = dfEshopPurchaseService.queryEshopIds();
    	        	paraMap.put("year", year);
    	        	paraMap.put("month", month);
    	        	paraMap.put("begindate", begindate);
    	        	paraMap.put("enddate", enddate);
    	        	paraMap.put("storeids", storeIds);
    	        	paraMap.put("eshopids",eshopIds);
    	    		List<Map<String, String>> abnormalOrderAList =abnormalOrderService.queryAbnorOrderA(paraMap);
    	    		List<Map<String, String>> abnormalOrderBList =abnormalOrderService.queryAbnorOrderB(paraMap);
    	    		
    	    		if(!abnormalOrderAList.isEmpty()){
    	    			for (Map<String, String> abnormalOrderAMap : abnormalOrderAList) {
    	    				String ordersn = abnormalOrderAMap.get("ordersn");
    	    				paraMap.put("ordersn", ordersn);
    	    				String flag = tabnormalOrderService.queryTAbnorOrdersByOrdersn(paraMap);
    	    				if("".equals(flag)||flag==null){
    	    					tabnormalOrderService.addAbnorOrders(abnormalOrderAMap);
    	    				}
    	    			}
    	    			logger.info("自动类型A：企业购E店里面的订单共调度数据记录行数："+abnormalOrderAList.size());
    	    		}
    	    		
    	    		if(!abnormalOrderBList.isEmpty()){
    	    			for (Map<String, String> abnormalOrderBMap : abnormalOrderBList) {
    	    				String ordersn = abnormalOrderBMap.get("ordersn");
    	    				paraMap.put("ordersn", ordersn);
    	    				String flag = tabnormalOrderService.queryTAbnorOrdersByOrdersn(paraMap);
    	    				if("".equals(flag)||flag==null){
    	    					tabnormalOrderService.addAbnorOrders(abnormalOrderBMap);
    	    				}
    	    			}
    	    			logger.info("自动类型B：营销费用异常异常订单共调度数据记录行数："+abnormalOrderBList.size());
    	    		}
    	    		
    	    		logger.info("**********自动异常订单任务调度结束**********");
    	    		} catch (Exception e) {
    	    			logger.info(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
    /**
     * 异常订单下载任务调度
     * 调度规则：每月0点35分开始调度
     * 参数：begindate  enddate  storename  storeids
     */    
    @Scheduled(cron ="0 35 0 * * ?")
    public void abnormalDownTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********手动异常订单任务调度开始**********");
    	        	//前一天日期所在月份的1号
    	        	String begindate = DateUtils.getPreDateFirstOfMonth(new Date());
    	        	//前一天所在月份前N个月1号,此为3,说明为当前日期前3个月1号
    	        	String prenbegindate = DateUtils.getPreNMonthFirstDay(3);
    	        	//前一天日期
    	        	String enddate = DateUtils.getPreDate(new Date());
    	    		//取得年份
    	    		String year = begindate.substring(0, 4);
    	    		//取得月份
    	    		String month = begindate.substring(5, 7);
    	        	//给后台接口构建参数
    	        	Map<String, String> paraMap=new HashMap<String, String>();
    	        	String storeIds = storeNumberService.queryStoreNumbers();
    	        	paraMap.put("year", year);
    	        	paraMap.put("month", month);
    	        	paraMap.put("begindate", begindate);
    	        	paraMap.put("enddate", enddate);
    	        	paraMap.put("prenbegindate",prenbegindate);
    	        	paraMap.put("storeids", storeIds);
//    	    		List<Map<String, String>> abnormalOrderDownAList =abnormalOrderService.queryAbnorOrderDownA(paraMap);
    	    		List<Map<String, String>> abnormalOrderDownBList =abnormalOrderService.queryAbnorOrderDownB(paraMap);
    	    		List<Map<String, String>> abnormalOrderDownCList =abnormalOrderService.queryAbnorOrderDownC(paraMap);
//    	    		List<Map<String, String>> abnormalOrderDownDList =abnormalOrderService.queryAbnorOrderDownD(paraMap);
    	    		
//    	    		if(!abnormalOrderDownAList.isEmpty()){
//    	    			for (Map<String, String> abnormalOrderDownAMap : abnormalOrderDownAList) {
//    	    				String ordersn = abnormalOrderDownAMap.get("ordersn");
//    	    				paraMap.put("ordersn", ordersn);
//    	    				String flag = tabnormalOrderService.queryTAbnorDownByOrdersn(paraMap);
//    	    				if("".equals(flag)||flag==null){
//    	    					tabnormalOrderService.addAbnorDown(abnormalOrderDownAMap);
//    	    				}
//    	    			}
//    	    			logger.info("手动下载类型A连续购买型异常订单共调度数据记录行数："+abnormalOrderDownAList.size());
//    	    		}
    	    		
    	    		if(!abnormalOrderDownBList.isEmpty()){
    	    			List<Map<String,String>> phoneList = employeeKeeperInfoService.queryPhones();
    	    			if(!phoneList.isEmpty()){
    	    				for (Map<String, String> phoneMap : phoneList) {
    	    					String phone = phoneMap.get("phone");
    	    					for(int i=0;i<abnormalOrderDownBList.size();i++){
    	    						if(abnormalOrderDownBList.get(i).get("mobilephone").equals(phone)){
    	    							paraMap.put("ordersn", abnormalOrderDownBList.get(i).get("ordersn"));
    	        	    				String flag = tabnormalOrderService.queryTAbnorDownByOrdersn(paraMap);
    	        	    				if("".equals(flag)||flag==null){
    	        	    					tabnormalOrderService.addAbnorDown(abnormalOrderDownBList.get(i));
    	        	    				}
    	    						}
    	    					}
							}
    	    			}
    	    			logger.info("手动下载类型B：代客下单异常订单共调度数据记录行数："+abnormalOrderDownBList.size());
    	    		}
    	    		
    	    		if(!abnormalOrderDownCList.isEmpty()){
    	    			for (Map<String, String> abnormalOrderDownCMap : abnormalOrderDownCList) {
    	    				String ordersn = abnormalOrderDownCMap.get("ordersn");
    	    				paraMap.put("ordersn", ordersn);
    	    				String flag = tabnormalOrderService.queryTAbnorDownByOrdersn(paraMap);
    	    				if("".equals(flag)||flag==null){
    	    					tabnormalOrderService.addAbnorDown(abnormalOrderDownCMap);
    	    				}
    	    			}
    	    			logger.info("手动下载类型C：大额订单异常共调度数据记录行数："+abnormalOrderDownCList.size());
    	    		}
    	    		
//    	    		if(!abnormalOrderDownDList.isEmpty()){
//    	    			for (Map<String, String> abnormalOrderDownDMap : abnormalOrderDownDList) {
//    	    				String ordersn = abnormalOrderDownDMap.get("ordersn");
//    	    				paraMap.put("ordersn", ordersn);
//    	    				String flag = tabnormalOrderService.queryTAbnorDownByOrdersn(paraMap);
//    	    				if("".equals(flag)||flag==null){
//    	    					tabnormalOrderService.addAbnorDown(abnormalOrderDownDMap);
//    	    				}
//    	    			}
//    	    			logger.info("手动下载类型D：下架异常订单共调度数据记录行数："+abnormalOrderDownDList.size());
//    	    		}
    	    		
    	    		logger.info("**********手动异常订单任务调度结束**********");
    	    		} catch (Exception e) {
    	    			logger.info(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
}
