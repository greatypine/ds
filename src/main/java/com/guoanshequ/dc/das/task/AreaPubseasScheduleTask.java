package com.guoanshequ.dc.das.task;

import com.guoanshequ.dc.das.service.*;
import com.guoanshequ.dc.das.utils.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
* @author CaoPs
* @date 2017年11月23日
* @version 1.0
* 说明:
* 1、指定人员订单分配 基表数据调度+基表按门店数据调度,在每天凌晨3:30;
 */
@Component
public class AreaPubseasScheduleTask {
    @Autowired
    StoreNumberService storeNumberService;
    @Autowired
    AreaPubseasNewaddCusService areaPubseasNewaddCusService;
    @Autowired
    TAreaPubseasNewaddCusService tAreaPubseasNewaddCusService;
	@Autowired
	AreaPubseasTradeService areaPubseasTradeService;
	@Autowired
	TAreaPubseasTradeService tAreaPubseasTradeService;
    @Autowired
    AreaPubseasZdGmvService areaPubseasZdGmvService;
    @Autowired
    TAreaPubseasZdGmvService tAreaPubseasZdGmvService;
    @Autowired
    TAreaPubseasNewaddCusStoreService tAreaPubseasNewaddCusStoreService;
    @Autowired
    TAreaPubseasTradeStoreService tAreaPubseasTradeStoreService;
    @Autowired
    TAreaPubseasZdGmvStoreService tAreaPubseasZdGmvStoreService;
  
    
    private static final Logger logger = LogManager.getLogger(AreaPubseasScheduleTask.class);
    
    /**
     * 指定公海人员分配新增用户 + 指定人员订单新增用户门店调度
     * 调度规则：每天凌晨3点40分开始调度
     * 参数：begindate  enddate 
     */
//    @Scheduled(cron ="0 40 03 * * ?")
    public void areaPubseasNewAddCusTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********指定公海分配新增用户任务调度开始**********");
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
    	        	paraMap.put("year", year);
    	        	paraMap.put("month", month);
    	        	paraMap.put("begindate", begindate);
    	        	paraMap.put("enddate", enddate);
    	        	
    	        	int addnum = 0;
    	        	int storeaddnum =0;
    	        	//生成基本数据
    	    		List<Map<String, String>> areaPubseasNewaddCusList = areaPubseasNewaddCusService.queryAreaPubseasNewaddCus(paraMap);
    	    		if(!areaPubseasNewaddCusList.isEmpty()){
    	    			tAreaPubseasNewaddCusService.deleteByYearMonth(paraMap);
    	    			for (Map<String, String> areaPubseasNewaddCusMap : areaPubseasNewaddCusList) {
    	    				int m = tAreaPubseasNewaddCusService.addTAreaPubseasNewaddCus(areaPubseasNewaddCusMap);
    	    				addnum += m;
    	    			}
    	    		}
    	    		//生成基表按门店数据
    	    		List<Map<String, String>> tareaPubseasNewaddCusList = tAreaPubseasNewaddCusService.queryTAreaPubseasNewaddCus(paraMap);
    	    		if(!tareaPubseasNewaddCusList.isEmpty()){
    	    			tAreaPubseasNewaddCusStoreService.deleteByYearMonth(paraMap);
    	    			storeaddnum = tAreaPubseasNewaddCusService.addTAreaPubseasNewaddCusByStore(paraMap);
    	    		}
    	    		logger.info("指定公海人员分配新增用户任务调度结束,共调度数据记录行数："+areaPubseasNewaddCusList.size()+",共插入记录行数："+addnum);
    	    		logger.info("指定公海人员分配新增用户任务(按门店)调度结束,共插入记录行数："+storeaddnum);
    	    		} catch (Exception e) {
    	    			logger.error(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
   
    /**
     * 指定公海人员分配交易额任务调度 + 按门店调度
     * 参数：begindate  enddate 
     */
//    @Scheduled(cron ="0 40 03 * * ?")
    public void areaPubseasTradeTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********指定公海人员分配交易额调度开始**********");
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
    	        	paraMap.put("year", year);
    	        	paraMap.put("month", month);
    	        	paraMap.put("begindate", begindate);
    	        	paraMap.put("enddate", enddate);
    	        	
    	        	int addnum =0;
    	        	int storeaddnum =0;
    	        	//生成基表数据
    	    		List<Map<String, String>> areaPubseasTradeList = areaPubseasTradeService.queryAreaPubseasTrades(paraMap);
    	    		if(!areaPubseasTradeList.isEmpty()){
    	    			tAreaPubseasTradeService.deleteByYearMonth(paraMap);
    	    			for (Map<String, String> areaPubseasTradeMap : areaPubseasTradeList) {
    	    				int m = tAreaPubseasTradeService.addTAreaPubseasTrades(areaPubseasTradeMap);
    	    				addnum += m;
    	    			}
    	    		}
    	    		//生成基表按门店数据
    	    		List<Map<String, String>> tareaPubseasTradeList = tAreaPubseasTradeService.queryTAreaPubseasTrades(paraMap);
    	    		if(!tareaPubseasTradeList.isEmpty()){
    	    			tAreaPubseasTradeStoreService.deleteByYearMonth(paraMap);
    	    			storeaddnum = tAreaPubseasTradeService.addTAreaPubseasTradeByStore(paraMap);
    	    		}
    	    		
    	    		logger.info("指定公海人员分配交易额调度结束,共调度数据记录行数："+areaPubseasTradeList.size()+",共插入记录行数："+addnum);
    	    		logger.info("指定公海人员分配交易额(按门店)调度结束,共插入记录行数："+storeaddnum);
    	    		} catch (Exception e) {
    	    			logger.error(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
    
    
    /**
     * 指定公海人员分配重点产品gmv任务调度
     * 参数：begindate  enddate  storeids
     */
//    @Scheduled(cron ="0 40 03 * * ?")
    public void areaPubseasZdGmvTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********指定公海人员分配重点产品gmv调度开始**********");
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
    	        	paraMap.put("year", year);
    	        	paraMap.put("month", month);
    	        	paraMap.put("begindate", begindate);
    	        	paraMap.put("enddate", enddate);
    	        	
    	        	int addnum =0;
    	        	int storeaddnum =0;
    	        	//生成基表数据
    	    		List<Map<String, String>> areaPubseasZdGmvList = areaPubseasZdGmvService.queryAreaPubseasZdGmv(paraMap);
    	    		if(!areaPubseasZdGmvList.isEmpty()){
    	    			tAreaPubseasZdGmvService.deleteByYearMonth(paraMap);
    	    			for (Map<String, String> areaPubseasZdGmvMap : areaPubseasZdGmvList) {
    	    				int m = tAreaPubseasZdGmvService.addTAreaPubseasZdGmv(areaPubseasZdGmvMap);
    	    				addnum+=m;
    	    			}
    	    		}
    	    		//生成基表按门店数据
    	    		List<Map<String, String>> tareaPubseasZdGmvList = tAreaPubseasZdGmvService.queryTAreaPubseasZdGmv(paraMap);
    	    		if(!tareaPubseasZdGmvList.isEmpty()){
    	    			tAreaPubseasZdGmvStoreService.deleteByYearMonth(paraMap);
    	    			storeaddnum = tAreaPubseasZdGmvService.addTAreaPubseasZdGmvByStore(paraMap);
    	    		}
    	    		logger.info("指定公海人员分配重点产品gmv调度结束,共调度数据记录行数："+areaPubseasZdGmvList.size()+",共插入记录行数："+addnum);
    	    		logger.info("指定公海人员分配重点产品gmv(按门店)调度结束,共插入记录行数："+ storeaddnum);
    	    		} catch (Exception e) {
    	    			logger.error(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
    
}
