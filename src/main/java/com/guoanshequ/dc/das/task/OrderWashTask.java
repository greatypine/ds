package com.guoanshequ.dc.das.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guoanshequ.dc.das.service.OrderWashService;
import com.guoanshequ.dc.das.utils.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
* @author CaoPs
* @date 2017年8月17日
* @version 1.0
* 说明:
 */
@Component
public class OrderWashTask {
    @Autowired
    OrderWashService orderWashService;
    
    private static final Logger logger = LogManager.getLogger(OrderWashTask.class);
    
    /**
     * 根据df_order_realtime实时表数据抽取写入到df_order_history表
     */
//    @Scheduled(cron ="0 38 10 18 * ?")
    public void dfOrderHistoryTask() {
    	try {
    	logger.info("**********order历史数据任务调度开始**********");
    	//得到上月的月初月末日期
    	Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
    	//上月月初日期
    	String begindate = datemap.get("first");
    	//上月月末日期
    	String enddate = datemap.get("last");
    	//给后台接口构建参数
    	Map<String, String> paraMap=new HashMap<String, String>();
    	
    	paraMap.put("begindate", begindate);
    	paraMap.put("enddate", enddate);
    	orderWashService.insertDfOrderHistory(paraMap);
		logger.info("**********order历史数据任务调度结束**********");
		logger.info("共调度数据记录行数");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 根据t_order、t_order_flow表生成df_order_realtime实时订单表
     */
//    @Scheduled(cron ="0 48 19 17 * ?")
    public void dfOrderRealtimeTask() {
    	try {
    	logger.info("**********order实时数据任务调度开始**********");
    	//得到上月的月初月末日期
    	Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
    	//上月月初日期
    	String begindate = datemap.get("first");
    	//上月月末日期
    	String enddate = datemap.get("last");
    	//给后台接口构建参数
    	Map<String, String> paraMap=new HashMap<String, String>();
    	
    	paraMap.put("begindate", begindate);
    	paraMap.put("enddate", enddate);
    	orderWashService.insertDfOrderRealtime(paraMap);
		logger.info("**********order实时数据任务调度结束**********");
		logger.info("共调度数据记录行数");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}
