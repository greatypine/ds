package com.guoanshequ.dc.das.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guoanshequ.dc.das.service.AreaNewaddCusService;
import com.guoanshequ.dc.das.service.AreaNewaddCusStoreService;
import com.guoanshequ.dc.das.service.AreaTradeService;
import com.guoanshequ.dc.das.service.AreaTradeStoreService;
import com.guoanshequ.dc.das.service.AreaZdGmvService;
import com.guoanshequ.dc.das.service.AreaZdGmvStoreService;
import com.guoanshequ.dc.das.service.HumanresourceService;
import com.guoanshequ.dc.das.service.StoreNumberService;
import com.guoanshequ.dc.das.service.TAreaNewaddCusService;
import com.guoanshequ.dc.das.service.TAreaNewaddCusStoreService;
import com.guoanshequ.dc.das.service.TAreaTradeService;
import com.guoanshequ.dc.das.service.TAreaTradeStoreService;
import com.guoanshequ.dc.das.service.TAreaZdGmvService;
import com.guoanshequ.dc.das.service.TAreaZdGmvStoreService;
import com.guoanshequ.dc.das.utils.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
* @author CaoPs
* @date 2017年10月30日
* @version 1.0
* 说明:
* 1、基表数据调度在凌晨3:30
* 2、基表按门店数据调度在凌晨5:30
* 3、计算门店公海数据在凌晨6:00
* 4、计算片区数据在凌晨6:20
 */
@Component
public class AreaScheduleTask {
    @Autowired
    StoreNumberService storeNumberService;
    @Autowired
    AreaNewaddCusService areaNewaddCusService;
    @Autowired
    TAreaNewaddCusService tAreaNewaddCusService;
    @Autowired
    AreaNewaddCusStoreService areaNewaddCusStoreService;
    @Autowired
    TAreaNewaddCusStoreService tAreaNewaddCusStoreService;
	@Autowired
	AreaTradeService areaTradeService;
	@Autowired
	TAreaTradeService tAreaTradeService;
	@Autowired
	AreaTradeStoreService areaTradeStoreService;
    @Autowired
    TAreaTradeStoreService tAreaTradeStoreService;
    @Autowired
    AreaZdGmvService areaZdGmvService;
    @Autowired
    TAreaZdGmvService tAreaZdGmvService;
    @Autowired
    AreaZdGmvStoreService areaZdGmvStoreService;
    @Autowired
    TAreaZdGmvStoreService tAreaZdGmvStoreService;
    @Autowired
    HumanresourceService humanresourceService;
    
    private static final Logger logger = LogManager.getLogger(AreaScheduleTask.class);
    
    /**
     * 片区新增用户
     * 调度规则：每月1号3点30分开始调度
     * 参数：begindate  enddate  storename  storeids
     */
    @Scheduled(cron ="0 30 03 * * ?")
    public void areaNewAddCusTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********片区新增用户任务调度开始**********");
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
    	    		List<Map<String, String>> areaNewaddCusList = areaNewaddCusService.queryAreaNewaddCus(paraMap);
    	    		if(!areaNewaddCusList.isEmpty()){
    	    			tAreaNewaddCusService.deleteByYearMonth(paraMap);
    	    			for (Map<String, String> areaNewaddCusMap : areaNewaddCusList) {
    	    				tAreaNewaddCusService.addTAreaNewaddCus(areaNewaddCusMap);
    	    			}
    	    		}
    	    		logger.info("**********片区新增用户任务调度结束**********");
    	    		logger.info("共调度数据记录行数："+areaNewaddCusList.size());
    	    		} catch (Exception e) {
    	    			logger.error(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
    
    /**
     * 片区新增用户按门店
     * 参数：begindate  enddate  storename  storeids
     */
    @Scheduled(cron ="0 30 05 * * ?")
    public void areaNewAddCusStoreTask() {
    	new Thread(){
    		public void run(){
    	    	try {
    	        	logger.info("**********片区新增用户按门店任务调度开始**********");
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
    	    		List<Map<String, String>> tareaNewaddCusList = tAreaNewaddCusService.queryTAreaNewaddCus(paraMap);
    	    		int num =0;
    	    		if(!tareaNewaddCusList.isEmpty()){
    	    			tAreaNewaddCusStoreService.deleteByYearMonth(paraMap);
    	    			num = tAreaNewaddCusService.addTAreaNewaddCusByStore(paraMap);
    	    		}
    	    		logger.info("**********片区新增用户按门店任务调度结束**********");
    	    		logger.info("共调度数据记录行数："+num);
    	    		} catch (Exception e) {
    	    			logger.error(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
    
    /**
     * 片区交易额任务调度
     * 参数：begindate  enddate  storename  storeids
     */
    @Scheduled(cron ="0 30 03 * * ?")
    public void areaTradeTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********片区交易额调度开始**********");
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
    	    		List<Map<String, String>> areaTradeList = areaTradeService.queryAreaTrades(paraMap);
    	    		if(!areaTradeList.isEmpty()){
    	    			tAreaTradeService.deleteByYearMonth(paraMap);
    	    			for (Map<String, String> areaTradeMap : areaTradeList) {
    	    				tAreaTradeService.addTAreaTrades(areaTradeMap);
    	    			}
    	    		}
    	    		logger.info("**********片区交易额调度结束**********");
    	    		logger.info("共调度数据记录行数："+areaTradeList.size());
    	    		} catch (Exception e) {
    	    			logger.error(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
    
    
    /**
     *片区交易额按门店任务调度
     * 参数：year   month   rebuyStoreName  storeids
     */
    @Scheduled(cron ="0 30 05 * * ?")
    public void areaTradeStoreTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********片区交易额按门店任务调度开始**********");
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
    	    		List<Map<String, String>> tareaTradeList = tAreaTradeService.queryTAreaTrades(paraMap);
    	    		int num =0;
    	    		if(!tareaTradeList.isEmpty()){
    	    			tAreaTradeStoreService.deleteByYearMonth(paraMap);
    	    			num = tAreaTradeService.addTAreaTradeByStore(paraMap);
    	    		}
    	    		logger.info("**********片区交易额按门店任务调度结束**********");
    	    		logger.info("共调度数据记录行数："+num);
    	    		} catch (Exception e) {
    	    			logger.error(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
    
    /**
     * 片区重点产品gmv任务调度
     * 参数：begindate  enddate  storeids
     */
    @Scheduled(cron ="0 30 03 * * ?")
    public void areaZdGmvTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********片区重点产品gmv调度开始**********");
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
    	    		List<Map<String, String>> areaZdGmvList = areaZdGmvService.queryAreaZdGmv(paraMap);
    	    		if(!areaZdGmvList.isEmpty()){
    	    			tAreaZdGmvService.deleteByYearMonth(paraMap);
    	    			for (Map<String, String> areaZdGmvMap : areaZdGmvList) {
    	    				tAreaZdGmvService.addTAreaZdGmv(areaZdGmvMap);
    	    			}
    	    		}
    	    		logger.info("**********片区重点产品gmv调度结束**********");
    	    		logger.info("共调度数据记录行数："+areaZdGmvList.size());
    	    		} catch (Exception e) {
    	    			logger.error(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
    
    /**
     * 片区重点产品gmv按门店
     * 参数：begindate  enddate  storeids
     */
    @Scheduled(cron ="0 30 05 * * ?")
    public void areaZdGmvStoreTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********片区重点产品gmv按门店调度开始**********");
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
    	    		List<Map<String, String>> tareaZdGmvList = tAreaZdGmvService.queryTAreaZdGmv(paraMap);
    	    		int num =0;
    	    		if(!tareaZdGmvList.isEmpty()){
    	    			tAreaZdGmvStoreService.deleteByYearMonth(paraMap);
    	    			num = tAreaZdGmvService.addTAreaZdGmvByStore(paraMap);
    	    		}
    	    		logger.info("**********片区重点产品gmv按门店调度结束**********");
    	    		logger.info("共调度数据记录行数："+num);
    	    		} catch (Exception e) {
    	    			logger.error(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
    
    /**
     * ==================================公海数据===================================================
     * 
     * 片区拉新用户门店公海数据
     * 参数：begindate  enddate  storeids
     */
    @Scheduled(cron ="0 01 06 * * ?")
    public void areaNewAddCusStorePubseasTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********片区拉新用户门店公海数据更新任务开始**********");
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
    	    		int num = tAreaNewaddCusStoreService.updatePubSeasByYearMonth(paraMap);
    	    		
    	    		logger.info("**********片区拉新用户按门店公海数据更新任务结束,共更新行数："+num+"**********");
    	    		} catch (Exception e) {
    	    			logger.error(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }   
    
    
    /**
     * 片区交易额门店公海数据
     * 参数：begindate  enddate  storeids
     */
    @Scheduled(cron ="0 05 06 * * ?")
    public void areaTradeStorePubseasTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********片区交易额门店公海数据更新任务开始**********");
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
    	    		int num = tAreaTradeStoreService.updatePubSeasByYearMonth(paraMap);
    	    		
    	    		logger.info("**********片区交易额按门店公海数据更新任务结束,共更新行数："+num+"**********");
    	    		} catch (Exception e) {
    	    			logger.error(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
    
    /**
     * 片区重点产品GMV按门店公海数据
     * 参数：begindate  enddate  storeids
     */
    @Scheduled(cron ="0 10 06 * * ?")
    public void areaZdGmvStorePubseasTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********片区重点产品GMV按门店公海数据更新任务开始**********");
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
    	    		int num = tAreaZdGmvStoreService.updatePubSeasByYearMonth(paraMap);
    	    		
    	    		logger.info("**********片区重点产品GMV按门店公海数据更新任务结束,共更新行数："+num+"**********");
    	    		} catch (Exception e) {
    	    			logger.error(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }   
    
    
    /**
     * 片区新增用户--公海数据更新
     * 调度规则：每天5点30分开始调度
     * 参数：begindate  enddate  storename  storeids
     */
    @Scheduled(cron ="0 20 06 * * ?")
    public void areaNewAddCusPubseasTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********片区新增用户公海数据更新任务调度开始**********");
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
    	    		List<Map<String, String>> tareaNewaddCusStoreList = tAreaNewaddCusStoreService.queryTAreaNewaddCusStore(paraMap);
    	    		if(!tareaNewaddCusStoreList.isEmpty()){
    	    			Object storestr =null;
    	    			int storesum =0;
    	    			String storeno ="";
    	    			int empcount=0;
    	    			int pubseas =0;
    	    			for (Map<String, String> tareaNewaddCusMap : tareaNewaddCusStoreList) {
    	    				storeno = tareaNewaddCusMap.get("storeno");
    	    				if(storeno!=null && !"".equals(storeno)){
        	    				storestr = tareaNewaddCusMap.get("pubseas");
        	    				storesum = Integer.parseInt(String.valueOf(storestr));
        	    				empcount = humanresourceService.queryStoreEmpCount(storeno);
        	    				if(storesum!=0 && empcount!=0){
        	    					pubseas = storesum/empcount;
        	    					paraMap.put("storeno", String.valueOf(storeno));
        	    					paraMap.put("pubseas", String.valueOf(pubseas));
        	    					tAreaNewaddCusService.updatePubSeasByYearMonth(paraMap);
        	    				}
    	    				}
    	    			}
    	    		}
    	    		logger.info("**********片区新增用户公海数据更新任务调度结束**********");
    	    		} catch (Exception e) {
    	    			logger.error(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
    
    /**
     * 片区交易额公海数据更新
     * 参数：begindate  enddate  storeids
     */
    @Scheduled(cron ="0 25 06 * * ?")
    public void areaTradePubseasTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********片区交易额公海数据更新任务开始**********");
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
    	    		List<Map<String, String>> tareaTradeStoreList = tAreaTradeStoreService.queryTAreaTradesStore(paraMap);
    	    		if(!tareaTradeStoreList.isEmpty()){
    	    			Object storestr =null;
    	    			double storenum =0;
    	    			String storeno ="";
    	    			int empcount=0;
    	    			double pubseas =0;
    	    			for (Map<String, String> tareaTradeStoreMap : tareaTradeStoreList) {
    	    				storeno = tareaTradeStoreMap.get("storeno");
    	    				if(storeno!=null && !"".equals(storeno)){
        	    				storestr = tareaTradeStoreMap.get("pubseas");
        	    				storenum = Double.parseDouble(String.valueOf(storestr));
        	    				empcount = humanresourceService.queryStoreEmpCount(storeno);
        	    				if(storenum!=0 && empcount!=0){
        	    					pubseas = storenum/empcount;
        	    					paraMap.put("storeno", String.valueOf(storeno));
        	    					paraMap.put("pubseas", String.valueOf(pubseas));
        	    					tAreaTradeService.updatePubSeasByYearMonth(paraMap);
        	    				}
    	    				}
    	    			}
    	    		}
    	    		logger.info("**********片区交易额公海数据更新任务结束**********");
    	    		} catch (Exception e) {
    	    			logger.error(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    } 
    
    /**
     * 片区重点产品Gmv公海数据更新
     * 参数：begindate  enddate  storeids
     */
    @Scheduled(cron ="0 30 06 * * ?")
    public void areaZdGmvPubseasTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********片区重点产品Gmv公海数据更新任务开始**********");
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
    	    		List<Map<String, String>> tareaZdGmvStoreList = tAreaZdGmvStoreService.queryTAreaZdGmvStore(paraMap);
    	    		if(!tareaZdGmvStoreList.isEmpty()){
    	    			Object storestr =null;
    	    			double storenum =0;
    	    			String storeno ="";
    	    			int empcount=0;
    	    			double pubseas =0;
    	    			for (Map<String, String> tareaZdGmvStoreMap : tareaZdGmvStoreList) {
    	    				storeno = tareaZdGmvStoreMap.get("storeno");
    	    				if(storeno!=null && !"".equals(storeno)){
        	    				storestr = tareaZdGmvStoreMap.get("pubseas");
        	    				storenum = Double.parseDouble(String.valueOf(storestr));
        	    				empcount = humanresourceService.queryStoreEmpCount(storeno);
        	    				if(storenum!=0 && empcount!=0){
        	    					pubseas = storenum/empcount;
        	    					paraMap.put("storeno", String.valueOf(storeno));
        	    					paraMap.put("pubseas", String.valueOf(pubseas));
        	    					tAreaZdGmvService.updatePubSeasByYearMonth(paraMap);
        	    				}
    	    				}
    	    			}
    	    		}
    	    		logger.info("**********片区重点产品Gmv公海数据更新任务结束**********");
    	    		} catch (Exception e) {
    	    			logger.error(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }     
}
