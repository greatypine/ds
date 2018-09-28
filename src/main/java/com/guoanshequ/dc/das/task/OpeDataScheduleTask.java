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
* @ClassName: OpeDataScheduleTask
* @Description: 运营数据任务调度
* @author caops
* @date 2018年4月24日 下午1:35:33
*
 */
@Component
public class OpeDataScheduleTask {
    @Autowired
    DsOpeGmvStoreService dsOpeGmvStoreService;
    @Autowired
    DsOpeGmvStoreChannelService dsOpeGmvStoreChannelService;

    
    private static final Logger logger = LogManager.getLogger(OpeDataScheduleTask.class);
    
	/**
	 * 
	* @Title: opeGmvStoreMonthByMassOrderTask
	* @Description: 运营数据：门店交易额，去组合订单去退货
	* @param     设定文件
	* @return void    返回类型
	* @throws
	 */
    @Scheduled(cron ="0 35 02 * * ?")
    public void opeGmvStoreMonthByMassOrderTask() {
    	new Thread(){
    		public void run(){
    	    	try {
    	        	logger.info("**********运营数据：门店交易额任务调度开始**********");
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
    	        	paraMap.put("tableName", "ds_ope_gmv_store_month");
					dsOpeGmvStoreService.deleteByYearMonth(paraMap);
					int num = dsOpeGmvStoreService.addDsOpeGmvStoreMonthByMassOrder(paraMap);
    	    		logger.info("**********运营数据：门店交易额任务调度结束**********共调度数据记录行数："+num);
    	    		} catch (Exception e) {
    	    			logger.info(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
    
    
    
    /**
     * 
    * @Title: opeGmvStoreChannelMonthByMassOrderTask
    * @Description: 运营数据：门店事业群、门店频道GMV任务调度
    * @param     设定文件
    * @return void    返回类型
    * @throws
     */
    @Scheduled(cron ="0 36 02 * * ?")
    public void opeGmvStoreChannelMonthByMassOrderTask() {
    	new Thread(){
    		public void run(){
    	    	try {
    	        	logger.info("**********运营数据：门店交易额按频道任务调度开始**********");
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
					dsOpeGmvStoreChannelService.deleteByYearMonth(paraMap);
					int num = dsOpeGmvStoreChannelService.addDsOpeGmvStoreChannelMonthByMassOrder(paraMap);
    	    		logger.info("**********运营数据：门店交易额按频道任务调度结束**********共调度数据记录行数："+num);
    	    		} catch (Exception e) {
    	    			logger.info(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }

}
