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
    
    /**
     * 以小时为单位调度，将查询数据写入df_order_realtime表中
     */
    //@Scheduled(cron ="0 25 15 * * ?")
    public void dfOrderRealtimeTask() {
    	try {
    	logger.info("**********order实时业务数据任务调度开始**********");
    	/*//得到上月的月初月末日期
    	Map<String, String> datemap = DateUtils.getPreHoursTime();	
    	//上月月初日期
    	String begintime = datemap.get("preHourbegin");
    	//上月月末日期
    	String endtime = datemap.get("preHourend");*/
    	String begintime = "2016-07-05 16:00:00";
    	String endtime = "2016-07-05 17:00:00";
    	//给后台接口构建参数
    	Map<String, String> paraMap=new HashMap<String, String>();
    	
    	paraMap.put("begintime",begintime);
    	paraMap.put("endtime", endtime);
    	List<DFOrderRealtime> realtimeList = orderRealtimeService.queryOrdersRealtimeByHours(paraMap);
    	if(!realtimeList.isEmpty()){
    		for (DFOrderRealtime dfOrderRealtime : realtimeList) {
    			dfOrderRealtimeService.deleteDfOrderRealtimeByOrderId(dfOrderRealtime.getOrder_id());
			}
    		for (DFOrderRealtime dfOrderRealtime : realtimeList) {
    			dfOrderRealtimeService.addDfOrderRealtimeByOrderId(dfOrderRealtime);
			}
    	}
		logger.info("**********order实时数据任务调度结束**********");
		logger.info("共调度数据记录行数"+realtimeList.size()); 
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 以天为单位调度，将查询数据写入df_order_daily表中
     */
    //@Scheduled(cron ="0 58 16 * * ?")
    public void dfOrderDailyTask() {
    	try {
    	logger.info("**********order实时业务数据任务调度开始**********");
    	dfOrderDailyService.deleteOrderDaily();
    	//得到当前时间前一天日期
    	//String preDate = DateUtils.getPreDate(new Date());
    	String preDate = "2016-07-05";
    	dfOrderDailyService.addOrderDaily(preDate);
		logger.info("**********order实时数据任务调度结束**********");
		logger.info("共调度数据记录行数"); 
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 以天为单位调度，将查询数据写入df_order_history表中
     */
//    @Scheduled(cron ="0 14 20 25 * ?")
    public void dfOrderHistoryTask() {
    	try {
    	logger.info("**********order实时业务数据任务调度开始**********");
    	//得到当前时间前一天日期
    	String preDate = DateUtils.getPreDate(new Date());
    	dfOrderHistoryService.addOrderHistory(preDate);
		logger.info("**********order实时数据任务调度结束**********");
		logger.info("共调度数据记录行数"); 
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
