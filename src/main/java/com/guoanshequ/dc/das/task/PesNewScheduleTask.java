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
	 * 当手动分配完成后于当晚7点30分重新计算国安侠gmv
	 * 参数：begindate  enddate  storename  storeids
	 */
	@Scheduled(cron ="0 30 19 1 * ?")
	public void preEmpTradesByMassOrderTask() {
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
}
