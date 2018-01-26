package com.guoanshequ.dc.das.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guoanshequ.dc.das.service.GmvPercentService;
import com.guoanshequ.dc.das.service.NewaddCusService;
import com.guoanshequ.dc.das.service.RebuyCusService;
import com.guoanshequ.dc.das.service.RewardTimesService;
import com.guoanshequ.dc.das.service.SendOrderService;
import com.guoanshequ.dc.das.service.SendOrderSumService;
import com.guoanshequ.dc.das.service.StoreNumberService;
import com.guoanshequ.dc.das.service.StoreTradeChannelService;
import com.guoanshequ.dc.das.service.StoreTradeService;
import com.guoanshequ.dc.das.service.TGmvPercentService;
import com.guoanshequ.dc.das.service.TNewaddCusService;
import com.guoanshequ.dc.das.service.TRebuyCusService;
import com.guoanshequ.dc.das.service.TRewardTimesService;
import com.guoanshequ.dc.das.service.TSendOrderService;
import com.guoanshequ.dc.das.service.TSendOrderSumService;
import com.guoanshequ.dc.das.service.TStoreTradeChannelService;
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
* 说明:上门送单量任务调度、门店交易额任务调度、新增消费用户任务调度、复购用户任务调度、国安侠好评次数任务调度
* 每月1号0时30分开始调度 (cron ="0 30 0 1 * ?") ：上门送单量任务调度、门店交易额任务调度、复购用户任务调度
* 每月1号0时30分开始调度 (cron ="0 30 0 1 * ?") ：新增消费用户任务调度、国安侠好评次数任务调度
* 
* * @version 2.0
* 说明:上门送单量任务调度、门店交易额任务调度、新增消费用户任务调度、复购用户任务调度、国安侠好评次数任务调度、
* 上门送单量按国安侠、门店交易额按频道、gmv占比
* 每天0时30分开始调度 (cron ="0 30 0 * * ?") 
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
    @Autowired
    RewardTimesService rewardTimesService;
    @Autowired
    TRewardTimesService tRewardTimesService;
    @Autowired
    SendOrderSumService sendOrderSumService;
    @Autowired
    TSendOrderSumService tSendOrderSumService;
    @Autowired
    StoreTradeChannelService storeTradeChannelService;
    @Autowired
    TStoreTradeChannelService tstoreTradeChannelService;
    @Autowired
    GmvPercentService gmvPercentService;
    @Autowired
    TGmvPercentService tgmvPercentService;
    
    
    private static final Logger logger = LogManager.getLogger(ScheduleTask.class);
    
    /**
     * 上门送单量任务调度
     * 调度规则：每月1号0点30分开始调度
     * 参数：begindate  enddate  storename  storeids
     */
//    @Scheduled(cron ="0 30 02 * * ?")
    public void sendOrdersTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********上门送单量任务调度开始**********");
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
    	    		List<Map<String, String>> sendOrderList = sendOrderService.querySendOrders(paraMap);
    	    		if(!sendOrderList.isEmpty()){
    	    			tsendOrderService.deleteByYearMonth(paraMap);
    	    			for (Map<String, String> sendOrderMap : sendOrderList) {
    	    				tsendOrderService.addTSendOrders(sendOrderMap);
    	    			}
    	    		}
    	    		logger.info("**********上门送单量任务调度结束**********");
    	    		logger.info("共调度数据记录行数："+sendOrderList.size());
    	    		} catch (Exception e) {
    	    			logger.info(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
    
    /**
     * 门店交易额任务调度
     * 参数：begindate  enddate  storename  storeids
     */
//    @Scheduled(cron ="0 30 02 * * ?")
    public void storeTradesTask() {
    	new Thread(){
    		public void run(){
    	    	try {
    	        	logger.info("**********门店交易额任务调度开始**********");
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
    	    		List<Map<String, String>> storeTradesList = storeTradeService.queryStoreTrades(paraMap);
    	    		if(!storeTradesList.isEmpty()){
    	    			tstoreTradeService.deleteByYearMonth(paraMap);
    	    			for (Map<String, String> storeTradeMap : storeTradesList) {
    	    				tstoreTradeService.addTStoreTrades(storeTradeMap);
    	    			}
    	    		}
    	    		logger.info("**********门店交易额任务调度结束**********");
    	    		logger.info("共调度数据记录行数："+storeTradesList.size());
    	    		} catch (Exception e) {
    	    			logger.info(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
    
    /**
     * 每月新增客户总量任务调度
     * 参数：begindate  enddate  storename  storeids
     */
//    @Scheduled(cron ="0 35 02 * * ?")
    public void newAddCusTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********每月新增客户总量调度开始**********");
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
    	    		List<Map<String, String>> newaddCusList = newaddCusService.queryNewaddCus(paraMap);
    	    		if(!newaddCusList.isEmpty()){
    	    			tnewaddCusService.deleteByYearMonth(paraMap);
    	    			for (Map<String, String> newaddCusMap : newaddCusList) {
    	    				tnewaddCusService.addTNewaddCus(newaddCusMap);
    	    			}
    	    		}
    	    		logger.info("**********每月新增客户总量调度结束**********");
    	    		logger.info("共调度数据记录行数："+newaddCusList.size());
    	    		} catch (Exception e) {
    	    			logger.info(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
    
    
    /**
     * 复购客户任务调度
     * 参数：year   month   rebuyStoreName  storeids
     */
//    @Scheduled(cron ="0 36 02 * * ?")
    public void rebuyCusTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********复购客户任务调度开始**********");
    	        	//上月月初日期
    	        	String begindate = DateUtils.getPreDate(new Date());
    	        	//取得年份
    	        	String year = begindate.substring(0, 4);
    	        	//取得月份
    	        	String month = begindate.substring(5, 7);
    	        	//给后台接口构建参数
    	        	Map<String, String> paraMap=new HashMap<String, String>();
    	        	String storeIds = storeNumberService.queryStoreNumbers();
    	        	paraMap.put("year", year);
    	        	paraMap.put("month", month);
    	        	paraMap.put("storeids", storeIds);
    	    		List<Map<String, String>> rebuyCusList = rebuyCusService.queryRebuyCus(paraMap);
    	    		if(!rebuyCusList.isEmpty()){
    	    			trebuyCusService.deleteByYearMonth(paraMap);
    	    			for (Map<String, String> rebuyCusMap : rebuyCusList) {
    	    				trebuyCusService.addTRebuyCus(rebuyCusMap);
    	    			}
    	    		}
    	    		logger.info("**********复购客户任务调度结束**********");
    	    		logger.info("共调度数据记录行数："+rebuyCusList.size());
    	    		} catch (Exception e) {
    	    			logger.info(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
    
    /**
     * 国安侠好评次数任务调度
     * 参数：begindate  enddate  storeids
     */
    @Scheduled(cron ="0 10 02 * * ?")
    public void rewardTimesTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********国安侠好评次数调度开始**********");
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
    	    		List<Map<String, String>> rewardTimesList = rewardTimesService.queryRewardTimes(paraMap);
    	    		if(!rewardTimesList.isEmpty()){
    	    			tRewardTimesService.deleteByYearMonth(paraMap);
    	    			for (Map<String, String> rewardTimesMap : rewardTimesList) {
    	    				tRewardTimesService.addTRewardTimes(rewardTimesMap);
    	    			}
    	    		}
    	    		logger.info("**********国安侠好评次数调度结束**********");
    	    		logger.info("共调度数据记录行数："+rewardTimesList.size());
    	    		} catch (Exception e) {
    	    			logger.info(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
    
    /**
     * 上门送单量按国侠个人总量
     * 参数：begindate  enddate  storeids
     */
//    @Scheduled(cron ="0 30 02 * * ?")
    public void sendOrderSumTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********上门送单量按国侠个人总量调度开始**********");
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
    	    		List<Map<String, String>> sendOrderSumList = sendOrderSumService.querySendOrderSum(paraMap);
    	    		if(!sendOrderSumList.isEmpty()){
    	    			tSendOrderSumService.deleteByYearMonth(paraMap);
    	    			for (Map<String, String> sendOrderSumMap : sendOrderSumList) {
    	    				tSendOrderSumService.addTSendOrderSum(sendOrderSumMap);
    	    			}
    	    		}
    	    		logger.info("**********上门送单量按国侠个人总量调度结束**********");
    	    		logger.info("共调度数据记录行数："+sendOrderSumList.size());
    	    		} catch (Exception e) {
    	    			logger.info(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
    
    /**
     * 门店交易额（按频道）任务调度
     * 参数：begindate  enddate  storeids
     */
//    @Scheduled(cron ="0 30 02 * * ?")
    public void storeTradeChannelTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********门店交易额（按频道）任务调度开始**********");
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
    	    		List<Map<String, String>> storetradeChannelList = storeTradeChannelService.queryStoreTradeChannels(paraMap);
    	    		if(!storetradeChannelList.isEmpty()){
    	    			tstoreTradeChannelService.deleteByYearMonth(paraMap);
    	    			for (Map<String, String> storetradeChannelMap : storetradeChannelList) {
    	    				tstoreTradeChannelService.addTStoreTradeChannel(storetradeChannelMap);
    	    			}
    	    		}
    	    		logger.info("**********门店交易额（按频道）任务调度结束**********");
    	    		logger.info("共调度数据记录行数："+storetradeChannelList.size());
    	    		} catch (Exception e) {
    	    			logger.info(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
    
    /**
     * gmv占比任务调度
     * 参数：begindate  enddate  storeids
     */
//    @Scheduled(cron ="0 30 02 * * ?")
    public void gmvPercentTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********gmv占比任务调度开始**********");
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
    	    		List<Map<String, String>> gmvPercentList = gmvPercentService.queryGmvPercentByDate(paraMap);
    	    		if(!gmvPercentList.isEmpty()){
    	    			tgmvPercentService.deleteByYearMonth(paraMap);
    	    			for (Map<String, String> gmvPercentMap : gmvPercentList) {
    	    				tgmvPercentService.addTGmvPercent(gmvPercentMap);
    	    			}
    	    		}
    	    		logger.info("**********gmv占比任务调度结束**********");
    	    		logger.info("共调度数据记录行数："+gmvPercentList.size());
    	    		} catch (Exception e) {
    	    			logger.info(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
}
