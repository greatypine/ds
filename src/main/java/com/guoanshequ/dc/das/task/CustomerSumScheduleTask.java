package com.guoanshequ.dc.das.task;

import com.guoanshequ.dc.das.service.*;
import com.guoanshequ.dc.das.utils.DateUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 
 * @author CaoPs
 * @date 2018年2月11日
 * @version 1.0 说明:
 */
@Component
public class CustomerSumScheduleTask {

	private static final Logger logger = LogManager.getLogger(CustomerSumScheduleTask.class);

	@Autowired
	CustomerSumService CustomerSumService;
	
	
/**
 * 
* @Title: customerSumMonthTask 
* @Description: 1对于历史用户量的统计方法：数据以门店月为单位，先删除本月的数据，再插入新数据；
* 				2调度时间：每天凌晨2点30分开线程执行，
* @param     设定文件 
* @return void    返回类型 
* @throws
 */
	@Scheduled(cron = "0 30 02 * * ?")
	public void customerSumMonthTask() {
		new Thread() {
			public void run() {
				try {
					logger.info("*************统计门店历史用户量调度开始**************");
    	        	String preDate = DateUtils.getPreDate(new Date());
    	        	String year = preDate.substring(0,4);
    	        	String month = preDate.substring(5,7);
    	        	String order_ym = year+month;
    	        	int delnum = 0;
    	        	int addnum = 0;
    	        	Map<String, String> paraMap=new HashMap<String, String>();
    	        	paraMap.put("order_ym", order_ym);
    	        	delnum = CustomerSumService.deleteCusumMonthByMonth(paraMap);
    	        	addnum = CustomerSumService.addCusumMonth(paraMap);
					logger.info("************统计门店历史用户量调度结束,删除数据："+delnum+",新增数据："+addnum+"**********************");
				} catch (Exception e) {
					logger.info("统计门店历史用户量存在问题请检查！！！");
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}

	
	/**
	 * 
	* @Title: customerSumDayTask 
	* @Description: 1对于每天用户量统计方法：从mass_order_monthly中把当天的用户量统计出来，插入到day表中
	* 				2调度时间：每天凌晨2点30分开线程执行，
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
		@Scheduled(cron = "0 30 02 * * ?")
		public void customerSumDayTask() {
			new Thread() {
				public void run() {
					try {
						logger.info("*************统计每天用户量调度开始**************");
						String begintime = DateUtils.getPreDateTime(new Date());
						String endtime = DateUtils.getCurDateTime(new Date());
	    	        	int addnum = 0;
	    	        	Map<String, String> paraMap=new HashMap<String, String>();
	    	        	paraMap.put("begintime", begintime);
	    	        	paraMap.put("endtime", endtime);
	    	        	addnum = CustomerSumService.addCusumDay(paraMap);
						logger.info("************统计每天用户量调度结束,新增数据："+addnum+"**********************");
					} catch (Exception e) {
						logger.info("统计门店历史用户量存在问题请检查！！！");
						logger.info(e.toString());
						e.printStackTrace();
					}
				}
			}.start();
		}
		
		
	/**
	 * 
	* @Title: customerSumMonthCityTask 
	* @Description: 1对于历史用户量(按城市)的统计方法：数据以门店月为单位，先删除本月的数据，再插入新数据；
	* 				2调度时间：每天凌晨2点30分开线程执行，
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
		@Scheduled(cron = "0 30 02 * * ?")
		public void customerSumMonthCityTask() {
			new Thread() {
				public void run() {
					try {
						logger.info("*************统计门店历史用户量(按城市)调度开始**************");
	    	        	String preDate = DateUtils.getPreDate(new Date());
	    	        	String year = preDate.substring(0,4);
	    	        	String month = preDate.substring(5,7);
	    	        	String order_ym = year+month;
	    	        	int delnum = 0;
	    	        	int addnum = 0;
	    	        	Map<String, String> paraMap=new HashMap<String, String>();
	    	        	paraMap.put("order_ym", order_ym);
	    	        	delnum = CustomerSumService.deleteCusumMonthCityByMonth(paraMap);
	    	        	addnum = CustomerSumService.addCusumMonthCity(paraMap);
						logger.info("************统计门店历史用户量(按城市)调度结束,删除数据："+delnum+",新增数据："+addnum+"**********************");
					} catch (Exception e) {
						logger.info("统计门店历史用户量(按城市)存在问题请检查！！！");
						logger.info(e.toString());
						e.printStackTrace();
					}
				}
			}.start();
		}

		
		/**
		 * 
		* @Title: customerSumDayCityTask 
		* @Description: 1对于每天用户量(按城市)统计方法：从mass_order_monthly中把当天的用户量统计出来，插入到day表中
		* 				2调度时间：每天凌晨2点30分开线程执行，
		* @param     设定文件 
		* @return void    返回类型 
		* @throws
		 */
			@Scheduled(cron = "0 30 02 * * ?")
			public void customerSumDayCityTask() {
				new Thread() {
					public void run() {
						try {
							logger.info("*************统计每天用户量(按城市)调度开始**************");
							String begintime = DateUtils.getPreDateTime(new Date());
							String endtime = DateUtils.getCurDateTime(new Date());
		    	        	int addnum = 0;
		    	        	Map<String, String> paraMap=new HashMap<String, String>();
		    	        	paraMap.put("begintime", begintime);
		    	        	paraMap.put("endtime", endtime);
		    	        	addnum = CustomerSumService.addCusumDayCity(paraMap);
							logger.info("************统计每天用户量(按城市)调度结束,新增数据："+addnum+"**********************");
						} catch (Exception e) {
							logger.info("统计每天用户量(按城市)存在问题请检查！！！");
							logger.info(e.toString());
							e.printStackTrace();
						}
					}
				}.start();
			}		
}
