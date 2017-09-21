package com.guoanshequ.dc.das.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guoanshequ.dc.das.model.DFOrderRealtime;
import com.guoanshequ.dc.das.service.DfOrderDailyService;
import com.guoanshequ.dc.das.service.DfOrderHistoryService;
import com.guoanshequ.dc.das.service.DfOrderRealtimeService;
import com.guoanshequ.dc.das.service.OrderRealtimeService;
import com.guoanshequ.dc.das.utils.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 
* @author CaoPs
* @date 2017年8月17日
* @version 1.0
* 说明:     
*  1、对于signed去重得的说明：
*  同一小时同一订单出现多条signed的数据已经在查询的sql中进行处理；
*  非同一小时同一订单多出的signed数据会先将之前的订单删除掉保留最新的一条；
*  2、对于任务调度时间顺序的说明：
*  	a.以小时为单位，实时数据df_order_realtime的写入（写入之前先将已存在的订单删除再写入）；
*  	b.以天为单位，将df_order_realtime前一天状态为signed的数据写入到df_order_daily表中；
*  	c.daily数据完成写入到就可以考虑实际的业务处理，如用户customer的调度处理；
*  	d.将df_order_realtime前一天状态为signed的数据写入到df_order_history表中；
*  	e.将df_order_realtime前一天状态为signed的数据删除；
*/
@Component
public class OrderRealtimeTask {
    @Autowired
    OrderRealtimeService orderRealtimeService;
    @Autowired
    DfOrderRealtimeService dfOrderRealtimeService;
    @Autowired
    DfOrderHistoryService dfOrderHistoryService;
    @Autowired
    DfOrderDailyService dfOrderDailyService;
    
    private static final Logger logger = LogManager.getLogger(OrderRealtimeTask.class);
    
    /**当天6点-12点的数据，每天7点开始调度，一小时一次
     * 以小时为单位调度，将查询数据写入df_order_realtime表中
     * 调度时间：每一小时的第5分钟，cron ="0 05 *1 * * ?"
     */
    //@Scheduled(cron ="0 05 07-13 * * ?")
    public void dfOrderRealtimeTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********order实时业务数据任务调度开始（7-13）：**********"+DateUtils.getCurTime(new Date()));
    	        	//得到前一个小时的时间
    	        	Map<String, String> datemap = DateUtils.getPreHoursTime();	
    	        	//得到前一小时的开始时间
    	        	String begintime = datemap.get("preHourbegin");
    	        	//得到前一小时的结束时间
    	        	String endtime = datemap.get("preHourend");
    	        	//给后台接口构建参数
    	        	Map<String, String> paraMap=new HashMap<String, String>();
    	        	
    	        	paraMap.put("begintime",begintime);
    	        	paraMap.put("endtime", endtime);
    	        	List<DFOrderRealtime> realtimeList = orderRealtimeService.queryOrdersRealtimeByHours(paraMap);
    	        	if(!realtimeList.isEmpty()){
    	        		//先删除后添加
    	        		for (DFOrderRealtime dfOrderRealtime : realtimeList) {
    	        			dfOrderRealtimeService.deleteDfOrderRealtimeByOrderId(dfOrderRealtime.getOrder_id());
    	    			}
    	        		for (DFOrderRealtime dfOrderRealtime : realtimeList) {
    	        			dfOrderRealtimeService.addDfOrderRealtimeByOrderId(dfOrderRealtime);
    	    			}
    	        	}
    	    		logger.info("**********order实时数据任务调度结束（7-13）：**********"+DateUtils.getCurTime(new Date()));
    	    		logger.info("共调度数据记录行数"+realtimeList.size()); 
    	    		} catch (Exception e) {
    	    			logger.error(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
    /**
     * 当天13点-24点的数据，第二天凌晨0点调度，执行一次
     */
    //@Scheduled(cron ="0 05 0 * * ?")
    public void dfOrderRealtimeTask1() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********order实时业务数据任务调度开始（13-24）：**********"+DateUtils.getCurTime(new Date()));
    	        	//获取前一天日期
    	        	String preDate = DateUtils.getPreDate(new Date());
    	        	//获取当前日期
    	        	String curDate = DateUtils.getCurDate(new Date());
    	        	String begintime =preDate+" 13:00:00"; 
    	        	String endtime = curDate+" 00:00:00";
    	        	Map<String, String> paraMap=new HashMap<String, String>();
    	        	paraMap.put("begintime",begintime);
    	        	paraMap.put("endtime", endtime);
    	        	List<DFOrderRealtime> realtimeList = orderRealtimeService.queryOrdersRealtimeByHours(paraMap);
    	        	if(!realtimeList.isEmpty()){
    	        		//先删除后添加
    	        		for (DFOrderRealtime dfOrderRealtime : realtimeList) {
    	        			dfOrderRealtimeService.deleteDfOrderRealtimeByOrderId(dfOrderRealtime.getOrder_id());
    	    			}
    	        		for (DFOrderRealtime dfOrderRealtime : realtimeList) {
    	        			dfOrderRealtimeService.addDfOrderRealtimeByOrderId(dfOrderRealtime);
    	    			}
    	        	}
    	    		logger.info("**********order实时数据任务调度结束（13-24）：**********"+DateUtils.getCurTime(new Date()));
    	    		logger.info("共调度数据记录行数"+realtimeList.size()); 
    	    		} catch (Exception e) {
    	    			logger.error(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
  
    /**
     * 第二天0点-5点的数据，每天凌晨6点开始调度，执行一次
     */
    //@Scheduled(cron ="0 05 06 * * ?")
    public void dfOrderRealtimeTask2() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********order实时业务数据任务调度开始（0-5）：**********"+DateUtils.getCurTime(new Date()));
    	        	//获取当前日期
    	        	String curDate = DateUtils.getCurDate(new Date());
    	        	//开始时间
    	        	String begintime = curDate+" 00:00:00";
    	        	//结束时间
    	        	String endtime =curDate+" 06:00:00";
    	        	//给后台接口构建参数
    	        	Map<String, String> paraMap=new HashMap<String, String>();
    	        	
    	        	paraMap.put("begintime",begintime);
    	        	paraMap.put("endtime", endtime);
    	        	List<DFOrderRealtime> realtimeList = orderRealtimeService.queryOrdersRealtimeByHours(paraMap);
    	        	if(!realtimeList.isEmpty()){
    	        		//先删除后添加
    	        		for (DFOrderRealtime dfOrderRealtime : realtimeList) {
    	        			dfOrderRealtimeService.deleteDfOrderRealtimeByOrderId(dfOrderRealtime.getOrder_id());
    	    			}
    	        		for (DFOrderRealtime dfOrderRealtime : realtimeList) {
    	        			dfOrderRealtimeService.addDfOrderRealtimeByOrderId(dfOrderRealtime);
    	    			}
    	        	}
    	    		logger.info("**********order实时数据任务调度结束（0-5）：**********"+DateUtils.getCurTime(new Date()));
    	    		logger.info("共调度数据记录行数"+realtimeList.size()); 
    	    		} catch (Exception e) {
    	    			logger.error(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
    
    
    /**
     * 以天为单位调度，将查询数据同时写入df_order_daily、df_order_history表中
     * 保证凌晨时realtime中24点的数据调度完成后，再将前一天所有signed数据写入表中
     * 每天凌晨的第35分钟，cron ="0 35 0 * * ?"
     */
    //@Scheduled(cron ="0 35 0 * * ?")
    public void dfOrderDailyTask() {
	    	try {
	        	logger.info("**********orderTodaily任务调度开始**********");
	        	dfOrderDailyService.deleteOrderDaily();
	        	//得到当前时间前一天日期
	        	String preDate = DateUtils.getPreDate(new Date());
	        	//写入到daily表中
	        	dfOrderDailyService.addOrderDaily(preDate);
	        	//写入到history表中
	        	dfOrderHistoryService.addOrderHistory(preDate);
	    		logger.info("**********orderTodaily任务调度结束**********");
	    		logger.info("共调度数据记录行数"); 
	    		} catch (Exception e) {
	    			logger.error(e.toString());
	    			e.printStackTrace();
	    		}
    	}
    
    /**
     * 当业务数据处理完成后，将orderRealtime中前一天状态为singed的数据删除
     * 每天凌晨2点15分将前一天signed数据删除
     */
    //@Scheduled(cron ="0 15 02 * * ?")
    public void dfOrderRealtimeDeleteTask() {
	    	try {
	        	logger.info("**********orderRealtime删除任务调度开始**********");
	        	//得到当前时间前一天日期
	        	String preDate = DateUtils.getPreDate(new Date());
	        	dfOrderRealtimeService.deleteDfOrdersRealtimeByPreDate(preDate);
	    		} catch (Exception e) {
	    			logger.error(e.toString());
	    			e.printStackTrace();
	    		}
	    		logger.info("**********orderRealtime删除任务调度结束**********");
    	}
}
