package com.guoanshequ.dc.das.task;

import com.guoanshequ.dc.das.service.DsCronTaskService;
import com.guoanshequ.dc.das.service.MergeBizService;
import com.guoanshequ.dc.das.service.OrderService;
import com.guoanshequ.dc.das.service.ProductCityService;
import com.guoanshequ.dc.das.service.TProductCityService;
import com.guoanshequ.dc.das.utils.DateUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
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
	@Autowired
	DsCronTaskService dsCronTaskService;
	@Autowired
	OrderService orderService;
	@Autowired
	MergeBizService mergeBizService;
	
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
    
    /**
     * 
    * @Title: productCityDayTask
    * @Description: 518购物节产品销售量，5月15号-5月18号每1分钟执行一次
    * @param     设定文件
    * @return void    返回类型
    * @throws
     */
//    @Scheduled(cron ="0 * * 15-18 5 ?")
    @Scheduled(cron ="0 */1 * * * ?")
    public void productCityDayTask() {
//    	new Thread(){
//    		public void run() {
    	    	try {
    				Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(9);
    				String isrun = taskMap.get("isrun");
	    				if("ON".equals(isrun)){
		    	        	logger.info("**********518商品按城市统计任务调度开始**********");
							Map<String, String> paraMap=new HashMap<String, String>();
							String resuFlag ="";
							//获取上次数据结束时间，即本次开始时间
							String begintime = taskMap.get("begintime");
							//获取结束时间,order表中的最大签收时间
							String endtime = orderService.queryMaxSigntime(paraMap);
							paraMap.put("begintime",begintime);
							paraMap.put("endtime",endtime);
		    	        	//生成基表数据
		    	    		List<Map<String, String>> productCityDayList = productCityService.queryByProductCityDay(paraMap);
		    	    		if(!productCityDayList.isEmpty()) {
		    		    		//设置任务为运行中状态
		    		    		Map<String, String> runMap = new HashMap<String,String>();
					    		runMap.put("id", "9");
					    		runMap.put("task_status", "RUNNING");
					    		dsCronTaskService.updateTaskStatusById(runMap);
				    			//5数据写入完成后，更新下一次的开始时间，此次的结束时间为下一次的开始时间
				    			resuFlag = mergeBizService.festProductSalesMerge(productCityDayList,endtime);
		    	    		}
		    	    		logger.info("调度时间："+DateUtils.getCurTime(new Date())+",518商品按城市统计任务调度结束结果为："+resuFlag+"开始时间："+begintime+",结束时间："+endtime);
	    				}
    	    		} catch (Exception e) {
    	    			logger.error(e.toString());
    	    			e.printStackTrace();
    	    		}
//    		}
//    	}.start();
    	}
    
    /**
     * 
    * @Title: deleProductCityDayTask
    * @Description: 518购物节商品销量计算，调度时间5月15号-5月18号凌晨30分钟清除
    * @param     设定文件
    * @return void    返回类型
    * @throws
     */
//    @Scheduled(cron ="30 0 0 15-18 5 ?")
    @Scheduled(cron ="30 0 0 * * ?")
    public void deleProductCityDayTask() {
//    	new Thread(){
//    		public void run() {
    	    	try {
    				Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(9);
    				String isrun = taskMap.get("isrun");
	    				if("ON".equals(isrun)){
		    	        	logger.info("**********518商品按城市清空**********");
		    	        	tProductCityService.deleteTProductCityDay();
		    	    		logger.info("**********518商品按城市清空，调度时间："+DateUtils.getCurTime(new Date()));
	    				}
    	    		} catch (Exception e) {
    	    			logger.error(e.toString());
    	    			e.printStackTrace();
    	    		}
//    		}
//    	}.start();
    	}    
    
}
