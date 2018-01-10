package com.guoanshequ.dc.das.task;

import com.guoanshequ.dc.das.service.ProductCityService;
import com.guoanshequ.dc.das.service.TProductCityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
* @author CaoPs
* @date 2018年01月09日
* @version 1.0
* 说明:
 */
@Component
public class ProductCityTask {
    @Autowired
    ProductCityService productCityService;
    @Autowired
    TProductCityService tProductCityService;

    private static final Logger logger = LogManager.getLogger(ProductCityTask.class);
    
    @Scheduled(cron ="0 50 03 * * ?")
    public void productCityTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********商品按城市统计任务调度开始**********");
					Map<String, String> paraMap=new HashMap<String, String>();
					paraMap.put("status","1");
    	        	int addnum =0;
    	        	//生成基表数据
    	    		List<Map<String, String>> productCityList = productCityService.queryByProductCity(paraMap);
    	    		if(!productCityList.isEmpty()){
						tProductCityService.deleteByYearMonth();
    	    			for (Map<String, String> productCityMap : productCityList) {
    	    				int m = tProductCityService.addTProductCity(productCityMap);
    	    				addnum += m;
    	    			}
    	    		}
    	    		
    	    		logger.info("商品按城市统计任务调度结束,共调度数据记录行数："+productCityList.size()+",共插入记录行数："+addnum);
    	    		} catch (Exception e) {
    	    			logger.error(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
}
