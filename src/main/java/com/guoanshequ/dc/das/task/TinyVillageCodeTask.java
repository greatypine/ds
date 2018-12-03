package com.guoanshequ.dc.das.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guoanshequ.dc.das.service.TinyVillageService;



/**
 * 
* @author liugt
* @date 2018年11月29日
* @version 1.0
* 说明:更新tiny_village_code表的已消费人数字段（consumer_number_2018）
* 
 */
@Component
public class TinyVillageCodeTask {
	 @Autowired
	 TinyVillageService tinyVillageService;
	  private static final Logger logger = LogManager.getLogger(TinyVillageCodeTask.class);
	   @Scheduled(cron = "0 30 02 * * ?")
	    public void syncTinyVillageCode(){
		   
		   new Thread(){
	    		public void run() {
	    	    	try {
	    	        	logger.info("**********数据库中任务调度开始**********");
	    	        	tinyVillageService.updateTinyVillageCode();
	    	        	logger.info("**********数据库中任务调度结束**********");   	    	
	    	    		} catch (Exception e) {
	    	    			logger.error(e.toString());
	    	    			e.printStackTrace();
	    	    		}
	    		}
	    	}.start();
	    }
}
