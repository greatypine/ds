package com.guoanshequ.dc.das.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guoanshequ.dc.das.service.AreaNewaddCusService;
import com.guoanshequ.dc.das.service.AreaNewaddCusStoreService;
import com.guoanshequ.dc.das.service.AreaPubseasNewaddCusService;
import com.guoanshequ.dc.das.service.AreaPubseasTradeService;
import com.guoanshequ.dc.das.service.AreaPubseasZdGmvService;
import com.guoanshequ.dc.das.service.AreaTradeService;
import com.guoanshequ.dc.das.service.AreaTradeStoreService;
import com.guoanshequ.dc.das.service.AreaZdGmvService;
import com.guoanshequ.dc.das.service.AreaZdGmvStoreService;
import com.guoanshequ.dc.das.service.HumanresourceService;
import com.guoanshequ.dc.das.service.StoreNumberService;
import com.guoanshequ.dc.das.service.TAreaNewaddCusService;
import com.guoanshequ.dc.das.service.TAreaNewaddCusStoreService;
import com.guoanshequ.dc.das.service.TAreaPubseasNewaddCusService;
import com.guoanshequ.dc.das.service.TAreaPubseasNewaddCusStoreService;
import com.guoanshequ.dc.das.service.TAreaPubseasTradeService;
import com.guoanshequ.dc.das.service.TAreaPubseasTradeStoreService;
import com.guoanshequ.dc.das.service.TAreaPubseasZdGmvService;
import com.guoanshequ.dc.das.service.TAreaPubseasZdGmvStoreService;
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
* @date 2017年11月23日
* @version 1.0
* 说明:
* 由于指定公海订单上月数据在下月1号下午截止，则对于上月数据需要进行重新计算;每月2号重新计算上月指定公海订单;
* 1、
* 2、
* 3、
 */
@Component
public class PreMonthAreaScheduleTask {
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
    
    private static final Logger logger = LogManager.getLogger(PreMonthAreaScheduleTask.class);
    
    /**
     * 指定人员订单新增用户
     * 调度规则：每月1号21点30分开始调度
     * 参数：begindate  enddate 
     */
    @Scheduled(cron ="0 30 21 1 * ?")
    public void areaPubseasNewAddCusTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********指定公海分配新增用户任务调度开始**********");
    	    		//得到上月的月初月末日期
    	    		Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
    	    		//上月月初日期
    	    		String begindate = datemap.get("first");
    	    		//上月月末日期
    	    		String enddate = datemap.get("last");
    	    		//取得上月年份
    	    		String year = begindate.substring(0, 4);
    	    		//取得上月月份
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
    	    		logger.info("指定公海分配新增用户任务调度结束,共调度数据记录行数："+areaPubseasNewaddCusList.size()+",共插入记录行数："+addnum);
    	    		logger.info("指定公海分配新增用户任务(按门店)调度结束,共插入记录行数："+storeaddnum);
    	    		} catch (Exception e) {
    	    			logger.error(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
    
    /**
     * 指定人员订单交易额任务调度
     * 调度规则：每月1号21点30分开始调度
     * 参数：begindate  enddate 
     */
    @Scheduled(cron ="0 30 21 1 * ?")
    public void areaPubseasTradeTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********指定公海分配交易额调度开始**********");
    	    		//得到上月的月初月末日期
    	    		Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
    	    		//上月月初日期
    	    		String begindate = datemap.get("first");
    	    		//上月月末日期
    	    		String enddate = datemap.get("last");
    	    		//取得上月年份
    	    		String year = begindate.substring(0, 4);
    	    		//取得上月月份
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
    	    		
    	    		logger.info("指定公海分配交易额调度结束,共调度数据记录行数："+areaPubseasTradeList.size()+",共插入记录行数："+addnum);
    	    		logger.info("指定公海分配交易额(按门店)调度结束,共插入记录行数："+storeaddnum);
    	    		} catch (Exception e) {
    	    			logger.error(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
    
    
    /**
     * 指定人员订单重点产品gmv任务调度
     * 调度规则：每月1号21点30分开始调度
     * 参数：begindate  enddate 
     */
    @Scheduled(cron ="0 30 21 1 * ?")
    public void areaPubseasZdGmvTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********指定公海分配重点产品gmv调度开始**********");
    	    		//得到上月的月初月末日期
    	    		Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
    	    		//上月月初日期
    	    		String begindate = datemap.get("first");
    	    		//上月月末日期
    	    		String enddate = datemap.get("last");
    	    		//取得上月年份
    	    		String year = begindate.substring(0, 4);
    	    		//取得上月月份
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
    	    		logger.info("指定公海分配重点产品gmv调度结束,共调度数据记录行数："+areaPubseasZdGmvList.size()+",共插入记录行数："+addnum);
    	    		logger.info("指定公海分配重点产品gmv(按门店)调度结束,共插入记录行数："+ storeaddnum);
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
     * 调度规则：每月1号22点30分开始调度
     * 参数：begindate  enddate  storeids
     */
    @Scheduled(cron ="0 30 22 1 * ?")
    public void areaNewAddCusStorePubseasTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********片区拉新用户门店公海数据更新任务开始**********");
    	    		//得到上月的月初月末日期
    	    		Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
    	    		//上月月初日期
    	    		String begindate = datemap.get("first");
    	    		//上月月末日期
    	    		String enddate = datemap.get("last");
    	    		//取得上月年份
    	    		String year = begindate.substring(0, 4);
    	    		//取得上月月份
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
     * 调度规则：每月1号22点31分开始调度
     * 参数：begindate  enddate  storeids
     */
    @Scheduled(cron ="0 31 22 1 * ?")
    public void areaTradeStorePubseasTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********片区交易额门店公海数据更新任务开始**********");
    	    		//得到上月的月初月末日期
    	    		Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
    	    		//上月月初日期
    	    		String begindate = datemap.get("first");
    	    		//上月月末日期
    	    		String enddate = datemap.get("last");
    	    		//取得上月年份
    	    		String year = begindate.substring(0, 4);
    	    		//取得上月月份
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
     * 调度规则：每月1号22点32分开始调度
     * 参数：begindate  enddate  storeids
     */
    @Scheduled(cron ="0 32 22 1 * ?")
    public void areaZdGmvStorePubseasTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********片区重点产品GMV按门店公海数据更新任务开始**********");
    	    		//得到上月的月初月末日期
    	    		Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
    	    		//上月月初日期
    	    		String begindate = datemap.get("first");
    	    		//上月月末日期
    	    		String enddate = datemap.get("last");
    	    		//取得上月年份
    	    		String year = begindate.substring(0, 4);
    	    		//取得上月月份
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
     * 调度规则：每月1号22点40分开始调度
     * 参数：begindate  enddate  storename  storeids
     */
    @Scheduled(cron ="0 40 22 1 * ?")
    public void areaNewAddCusPubseasTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********片区新增用户公海数据更新任务调度开始**********");
    	    		//得到上月的月初月末日期
    	    		Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
    	    		//上月月初日期
    	    		String begindate = datemap.get("first");
    	    		//上月月末日期
    	    		String enddate = datemap.get("last");
    	    		//取得上月年份
    	    		String year = begindate.substring(0, 4);
    	    		//取得上月月份
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
    @Scheduled(cron ="0 41 22 1 * ?")
    public void areaTradePubseasTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********片区交易额公海数据更新任务开始**********");
    	    		//得到上月的月初月末日期
    	    		Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
    	    		//上月月初日期
    	    		String begindate = datemap.get("first");
    	    		//上月月末日期
    	    		String enddate = datemap.get("last");
    	    		//取得上月年份
    	    		String year = begindate.substring(0, 4);
    	    		//取得上月月份
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
    @Scheduled(cron ="0 42 22 1 * ?")
    public void areaZdGmvPubseasTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********片区重点产品Gmv公海数据更新任务开始**********");
    	    		//得到上月的月初月末日期
    	    		Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
    	    		//上月月初日期
    	    		String begindate = datemap.get("first");
    	    		//上月月末日期
    	    		String enddate = datemap.get("last");
    	    		//取得上月年份
    	    		String year = begindate.substring(0, 4);
    	    		//取得上月月份
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
