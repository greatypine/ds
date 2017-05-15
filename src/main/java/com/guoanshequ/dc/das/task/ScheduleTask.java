package com.guoanshequ.dc.das.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guoanshequ.dc.das.service.NewaddCusService;
import com.guoanshequ.dc.das.service.RebuyCusService;
import com.guoanshequ.dc.das.service.SendOrderService;
import com.guoanshequ.dc.das.service.StoreNumberService;
import com.guoanshequ.dc.das.service.StoreTradeService;
import com.guoanshequ.dc.das.service.TNewaddCusService;
import com.guoanshequ.dc.das.service.TRebuyCusService;
import com.guoanshequ.dc.das.service.TSendOrderService;
import com.guoanshequ.dc.das.service.TStoreTradeService;
import com.guoanshequ.dc.das.utils.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
* @author CaoPs
* @date 2017年4月13日
* @version 1.0
* 说明:上门送单量任务调度、门店交易额任务调度、新增消费用户任务调度、复购用户任务调度
* 每月1号0时5分开始调度 (cron ="0 5 0 1 * ?") ：上门送单量任务调度、门店交易额任务调度、复购用户任务调度
* 每月2号0时5分开始调度 (cron ="0 5 0 2 * ?") ：新增消费用户任务调度
 */
@Component
public class ScheduleTask {
    @Autowired
    StoreNumberService storeNumberService;
    @Autowired
    SendOrderService sendOrderService;
    @Autowired
    TSendOrderService tsendOrderService;
    @Autowired
    StoreTradeService storeTradeService;
    @Autowired
    TStoreTradeService tstoreTradeService;
	@Autowired
	NewaddCusService newaddCusService;
	@Autowired
	TNewaddCusService tnewaddCusService;
	@Autowired
	RebuyCusService rebuyCusService;
    @Autowired
    TRebuyCusService trebuyCusService;
    
    private static final Logger logger = LogManager.getLogger(ScheduleTask.class);
    
    /**
     * 上门送单量任务调度
     * 调度规则：每月1号0点5分开始调度
     * 参数：begindate  enddate  storename  storeids
     */
    @Scheduled(cron = "0 5 0 14 * ?")
    public void sendOrdersTask() {
    	try {
    	logger.info("**********上门送单量任务调度开始**********");
    	//得到上月的月初月末日期
    	Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
    	//上月月初日期
    	String begindate = datemap.get("first");
    	//上月月末日期
    	String enddate = datemap.get("last");
    	//给后台接口构建参数
    	Map<String, String> paraMap=new HashMap<String, String>();
    	String storeIds = storeNumberService.queryStoreNumbers();
    	paraMap.put("begindate", begindate);
    	paraMap.put("enddate", enddate);
    	paraMap.put("storename", "全部");
    	paraMap.put("storeids", storeIds);
		List<Map<String, String>> sendOrderList = sendOrderService.querySendOrders(paraMap);
		if(!sendOrderList.isEmpty()){
			for (Map<String, String> sendOrderMap : sendOrderList) {
				tsendOrderService.addTSendOrders(sendOrderMap);
			}
		}
		logger.info("**********上门送单量任务调度结束**********");
		logger.info("共调度数据记录行数："+sendOrderList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 门店交易额任务调度
     * 参数：begindate  enddate  storename  storeids
     */
    @Scheduled(cron = "0 5 0 14 * ?")
    public void storeTradesTask() {
    	try {
    	logger.info("**********门店交易额任务调度开始**********");
    	//得到上月的月初月末日期
    	Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
    	//上月月初日期
    	String begindate = datemap.get("first");
    	//上月月末日期
    	String enddate = datemap.get("last");
    	//给后台接口构建参数
    	Map<String, String> paraMap=new HashMap<String, String>();
    	String storeIds = storeNumberService.queryStoreNumbers();
    	paraMap.put("begindate", begindate);
    	paraMap.put("enddate", enddate);
    	paraMap.put("storename", "全部");
    	paraMap.put("storeids", storeIds);
		List<Map<String, String>> storeTradesList = storeTradeService.queryStoreTrades(paraMap);
		if(!storeTradesList.isEmpty()){
			for (Map<String, String> storeTradeMap : storeTradesList) {
				tstoreTradeService.addTStoreTrades(storeTradeMap);
			}
		  }
		logger.info("**********门店交易额任务调度结束**********");
		logger.info("共调度数据记录行数："+storeTradesList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 每月新增客户总量任务调度
     * 参数：begindate  enddate  storename  storeids
     */
    @Scheduled(cron = "0 5 0 14 * ?")
    public void newAddCusTask() {
    	try {
    	logger.info("**********每月新增客户总量调度开始**********");
    	//得到上月的月初月末日期
    	Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
    	//上月月初日期
    	String begindate = datemap.get("first");
    	//上月月末日期
    	String enddate = datemap.get("last");
    	//给后台接口构建参数
    	Map<String, String> paraMap=new HashMap<String, String>();
    	String storeIds = storeNumberService.queryStoreNumbers();
    	paraMap.put("begindate", begindate);
    	paraMap.put("enddate", enddate);
    	paraMap.put("storename", "全部");
    	paraMap.put("storeids", storeIds);
		List<Map<String, String>> newaddCusList = newaddCusService.queryNewaddCus(paraMap);
		if(!newaddCusList.isEmpty()){
			for (Map<String, String> newaddCusMap : newaddCusList) {
				tnewaddCusService.addTNewaddCus(newaddCusMap);
			}
		}
		logger.info("**********每月新增客户总量调度结束**********");
		logger.info("共调度数据记录行数："+newaddCusList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    /**
     * 复购客户任务调度
     * 参数：year   month   rebuyStoreName  storeids
     */
    @Scheduled(cron = "0 5 0 14 * ?")
    public void rebuyCusTask() {
    	try {
    	logger.info("**********复购客户任务调度开始**********");
    	//得到上月的月初月末日期
    	Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
    	//上月月初日期
    	String begindate = datemap.get("first");
    	//取得年份
    	String year = begindate.substring(0, 4);
    	//取得月份
    	String month = begindate.substring(5, 7);
    	//给后台接口构建参数
    	Map<String, String> paraMap=new HashMap<String, String>();
    	String storeIds = storeNumberService.queryStoreNumbers();
    	paraMap.put("year", year);
    	paraMap.put("month", month);
    	paraMap.put("rebuyStoreName", "全部");
    	paraMap.put("storeids", storeIds);
		List<Map<String, String>> rebuyCusList = rebuyCusService.queryRebuyCus(paraMap);
		if(!rebuyCusList.isEmpty()){
			for (Map<String, String> rebuyCusMap : rebuyCusList) {
				trebuyCusService.addTRebuyCus(rebuyCusMap);
			}
		}
		logger.info("**********复购客户任务调度结束**********");
		logger.info("共调度数据记录行数："+rebuyCusList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
