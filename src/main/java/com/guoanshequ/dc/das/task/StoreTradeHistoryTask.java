package com.guoanshequ.dc.das.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guoanshequ.dc.das.service.OrderWashService;
import com.guoanshequ.dc.das.service.StoreTradeHistoryService;
import com.guoanshequ.dc.das.service.TStoreTradeHistoryService;
import com.guoanshequ.dc.das.utils.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
public class StoreTradeHistoryTask {
    @Autowired
    StoreTradeHistoryService storeTradeHistoryService;
    @Autowired
    TStoreTradeHistoryService tStoreTradeHistoryService;
    
    private static final Logger logger = LogManager.getLogger(StoreTradeHistoryTask.class);
    
    @Scheduled(cron ="0 10 04 * * ?")
    public void storeTradeHistoryTask() {
    	try {
    	logger.info("**********计算历史门店营业额数据开始**********");
    	//得到上月的月初月末日期
    	String preDay = DateUtils.getPreDate(new Date());
    	//给后台接口构建参数
    	Map<String, String> paraMap=new HashMap<String, String>();
    	String begindate =preDay+" 00:00:00";
    	String enddate =preDay+" 23:59:59";
    	paraMap.put("begindate", begindate);
    	paraMap.put("enddate", enddate);
    	List<Map<String, String>> storeTradeHistoyList = storeTradeHistoryService.queryStoreTrades(paraMap);
    	if(!storeTradeHistoyList.isEmpty()){
    		for (Map<String, String> map : storeTradeHistoyList) {
				tStoreTradeHistoryService.addTStoreTradeHistory(map);
			}
    	}
    	logger.info("**********计算历史门店营业额数据结束**********");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}
