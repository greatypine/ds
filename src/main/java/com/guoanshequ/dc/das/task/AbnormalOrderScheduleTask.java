package com.guoanshequ.dc.das.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guoanshequ.dc.das.service.AbnormalOrderService;
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
    
    
    private static final Logger logger = LogManager.getLogger(AbnormalOrderScheduleTask.class);
    
    /**
     * 异常订单任务调度
     * 调度规则：每天03点10分开始调度
     * 参数：begindate  enddate  storename  storeids
     */
//    @Scheduled(cron ="0 10 03 * * ?")
    public void abnormalOrderTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********异常订单任务调度开始**********");
    	        	//前一天日期所在月份的1号
    	        	String begindate = DateUtils.getPreDateFirstOfMonth(new Date());
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
    	        	paraMap.put("storeids", storeIds);
    	    		List<Map<String, String>> abnormalOrderAList =abnormalOrderService.queryAbnorOrderA(paraMap);
    	    		List<Map<String, String>> abnormalOrderBList =abnormalOrderService.queryAbnorOrderB(paraMap);
    	    		List<Map<String, String>> abnormalOrderD1List =abnormalOrderService.queryAbnorOrderD1(paraMap);
    	    		
    	    		if(!abnormalOrderAList.isEmpty()){
    	    			for (Map<String, String> abnormalOrderAMap : abnormalOrderAList) {
    	    				String ordersn = abnormalOrderAMap.get("ordersn");
    	    				paraMap.put("ordersn", ordersn);
    	    				String flag = tabnormalOrderService.queryTAbnorOrdersByOrdersn(paraMap);
    	    				if("".equals(flag)||flag==null){
    	    					tabnormalOrderService.addAbnorOrders(abnormalOrderAMap);
    	    				}
    	    			}
    	    			logger.info("类型A异常订单共调度数据记录行数："+abnormalOrderAList.size());
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
    	    			logger.info("类型B异常订单共调度数据记录行数："+abnormalOrderBList.size());
    	    		}
    	    		
    	    		if(!abnormalOrderD1List.isEmpty()){
    	    			for (Map<String, String> abnormalOrderD1Map : abnormalOrderD1List) {
    	    				String ordersn = abnormalOrderD1Map.get("ordersn");
    	    				paraMap.put("ordersn", ordersn);
    	    				String flag = tabnormalOrderService.queryTAbnorOrdersByOrdersn(paraMap);
    	    				if("".equals(flag)||flag==null){
    	    					tabnormalOrderService.addAbnorOrders(abnormalOrderD1Map);
    	    				}
    	    			}
    	    			logger.info("类型D1异常订单共调度数据记录行数："+abnormalOrderD1List.size());
    	    		}
    	    		
    	    		logger.info("**********异常订单任务调度结束**********");
    	    		} catch (Exception e) {
    	    			logger.info(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
    /**
     * 异常订单下载任务调度
     * 调度规则：每月03点25分开始调度
     * 参数：begindate  enddate  storename  storeids
     */    
    //@Scheduled(cron ="0 25 03 * * ?")
    public void abnormalDownTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********异常订单任务调度开始**********");
    	        	//前一天日期所在月份的1号
    	        	String begindate = DateUtils.getPreDateFirstOfMonth(new Date());
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
    	        	paraMap.put("storeids", storeIds);
    	    		List<Map<String, String>> abnormalOrderD2List =abnormalOrderService.queryAbnorOrderD2(paraMap);
    	    		List<Map<String, String>> abnormalOrderFList =abnormalOrderService.queryAbnorOrderF(paraMap);
    	    		
    	    		if(!abnormalOrderD2List.isEmpty()){
    	    			for (Map<String, String> abnormalOrderD2Map : abnormalOrderD2List) {
    	    				String ordersn = abnormalOrderD2Map.get("ordersn");
    	    				paraMap.put("ordersn", ordersn);
    	    				String flag = tabnormalOrderService.queryTAbnorDownByOrdersn(paraMap);
    	    				if("".equals(flag)||flag==null){
    	    					tabnormalOrderService.addAbnorDown(abnormalOrderD2Map);
    	    				}
    	    			}
    	    			logger.info("类型D2异常订单共调度数据记录行数："+abnormalOrderD2List.size());
    	    		}
    	    		
    	    		if(!abnormalOrderFList.isEmpty()){
    	    			for (Map<String, String> abnormalOrderFMap : abnormalOrderFList) {
    	    				String ordersn = abnormalOrderFMap.get("ordersn");
    	    				paraMap.put("ordersn", ordersn);
    	    				String flag = tabnormalOrderService.queryTAbnorDownByOrdersn(paraMap);
    	    				if("".equals(flag)||flag==null){
    	    					tabnormalOrderService.addAbnorDown(abnormalOrderFMap);
    	    				}
    	    			}
    	    			logger.info("类型F异常订单共调度数据记录行数："+abnormalOrderFList.size());
    	    		}
    	    		logger.info("**********异常订单任务调度结束**********");
    	    		} catch (Exception e) {
    	    			logger.info(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
    
}
