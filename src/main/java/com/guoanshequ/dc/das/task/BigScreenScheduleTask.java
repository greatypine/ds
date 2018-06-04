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
* @ClassName: BigScreenScheduleTask
* @Description: TODO(这里用一句话描述这个类的作用)
* @author caops
* @date 2018年6月4日 上午10:53:21
*
 */
@Component
public class BigScreenScheduleTask {

	@Autowired
	BigScreenService bigScreenService;

	private static final Logger logger = LogManager.getLogger(BigScreenScheduleTask.class);

	/**
	 * 
	* @Title: bigScreenCusForHQTask
	* @Description: 
	* 	每天早7点10分将大屏左上角用户统计数据更新一下
	* @param     设定文件
	* @return void    返回类型
	* @throws
	 */
	@Scheduled(cron = "0 10 07 * * ?")
	public void bigScreenCusForHQTask() {
		new Thread() {
			public void run() {
				try {
					logger.info("*************大屏左上角用户数统计调度开始**************");
					//1、为后台查询构建参数
    	        	String curDate = DateUtils.getCurDate(new Date());
    	        	String year = curDate.substring(0,4);
    	        	String month = curDate.substring(5,7);
    	        	String order_ym = year+month;
    	        	
					Map<String,String> paraMap = new HashMap<String,String>();
					Map<String,Integer> resultMap = new HashMap<String,Integer>();
					
					paraMap.put("order_ym", order_ym);
					Integer cusnum_month = bigScreenService.queryCusnumMonthForHQ(paraMap);
					Integer cusnum_history = bigScreenService.queryCusnumHistoryForHQ(paraMap);
					resultMap.put("cusnum_month", cusnum_month);
					resultMap.put("cusnum_history", cusnum_history);
					Integer updatenum = bigScreenService.updateCusnumForHQ(resultMap);

					logger.info("************大屏左上角用户数统计调度结束,共更新条数："+updatenum+"***********************");
				} catch (Exception e) {
					logger.info("大屏左上角用户数统计异常");
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}
}
