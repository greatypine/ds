package com.guoanshequ.dc.das.task;

import com.guoanshequ.dc.das.service.*;
import com.guoanshequ.dc.das.utils.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
* @author CaoPs
* @date 2018年01月23日
* @version 1.0
*
 */
@Component
public class PesNewScheduleTask {
    @Autowired
    TStoreTradeService tstoreTradeService;
	@Autowired
	TNewaddCusService tnewaddCusService;
	@Autowired
	EmployeeTradeService employeeTradeService;

    
    private static final Logger logger = LogManager.getLogger(PesNewScheduleTask.class);
    
    /**
     * 门店交易额任务调度
     * 参数：begindate  enddate  storename  storeids
     */
    @Scheduled(cron ="0 10 02 * * ?")
    public void storeTradesByMassOrderTask() {
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
    	        	paraMap.put("year", year);
    	        	paraMap.put("month", month);
    	        	paraMap.put("begindate", begindate);
    	        	paraMap.put("enddate", enddate);
					tstoreTradeService.deleteByYearMonth(paraMap);
					int num = tstoreTradeService.addTStoreTradesByMassOrder(paraMap);
    	    		logger.info("**********门店交易额任务调度结束**********");
    	    		logger.info("共调度数据记录行数："+num);
    	    		} catch (Exception e) {
    	    			logger.info(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }

	/**
	 * 国安侠gmv
	 * 参数：begindate  enddate  storename  storeids
	 */
	@Scheduled(cron ="0 10 02 * * ?")
	public void empTradesByMassOrderTask() {
		new Thread(){
			public void run(){
				try {
					logger.info("**********国安侠GMV任务调度开始**********");
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
					employeeTradeService.deleteByYearMonth(paraMap);
					int num = employeeTradeService.addEmployeeTradeByMassOrder(paraMap);
					logger.info("**********国安侠GMV任务调度结束**********");
					logger.info("共调度数据记录行数："+num);
				} catch (Exception e) {
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	
	
	
	/**
	 * 由于公海与异常订单都存在流程审批考核，绩效数据于3号进行关闭重新计算 
	 * 上月门店绩效GMV
	 * 参数：begindate  enddate  storename  storeids
	 */
	@Scheduled(cron ="0 0 03 3 * ?")
	public void preStoreTradesByMassOrderTask() {
		new Thread(){
			public void run(){
				try {
					logger.info("**********上月门店GMV任务调度开始**********");
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
					tstoreTradeService.deleteByYearMonth(paraMap);
					int num = tstoreTradeService.addPreTStoreTradesByMassOrder(paraMap);
					logger.info("**********上月门店GMV任务调度结束**********共调度数据记录行数："+num);
				} catch (Exception e) {
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	
	/**
	 * 由于公海与异常订单都存在流程审批考核，绩效数据于3号进行关闭重新计算 
	 * 上月国安侠GMV
	 * 参数：begindate  enddate  storename  storeids
	 */
	@Scheduled(cron ="0 0 03 3 * ?")
	public void preEmpTradesByMassOrderTask() {
		new Thread(){
			public void run(){
				try {
					logger.info("**********上月国安侠GMV任务调度开始**********");
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
					employeeTradeService.deleteByYearMonth(paraMap);
					int num = employeeTradeService.addPreEmployeeTradeByMassOrder(paraMap);
					logger.info("**********上月国安侠GMV任务调度结束**********共调度数据记录行数："+num);
				} catch (Exception e) {
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}
	
}
