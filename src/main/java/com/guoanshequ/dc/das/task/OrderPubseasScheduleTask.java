package com.guoanshequ.dc.das.task;

import com.guoanshequ.dc.das.model.DfOrderPubseas;
import com.guoanshequ.dc.das.service.DfOrderPubseasService;
import com.guoanshequ.dc.das.service.OrderPubseasService;
import com.guoanshequ.dc.das.service.StoreNumberService;
import com.guoanshequ.dc.das.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
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
* @date 2017年11月17日
* @version 1.0
* 说明:
 */
@Component
public class OrderPubseasScheduleTask {
    @Autowired
    StoreNumberService storeNumberService;
    @Autowired
    OrderPubseasService orderPubseasService;
    @Autowired
    DfOrderPubseasService dfOrderPubseasService;
    
    private static final Logger logger = LogManager.getLogger(OrderPubseasScheduleTask.class);
    
    /**
     * 指定人员订单分配调度,数据统计来源：gemini
     * 调度规则：每天凌晨00点20分开始调度
     * 参数：begindate  enddate
     */
//    @Scheduled(cron ="0 20 00 * * ?")
    public void orderPubseasTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    	        	logger.info("**********指定人员公海订单分配调度开始**********");
    	        	//前一天日期所在月份的1号
    	        	String begindate = DateUtils.getPreDateFirstOfMonth(new Date());
    	        	//前一天日期
    	        	String enddate = DateUtils.getPreDate(new Date());
    	        	//给后台接口构建参数
    	        	Map<String, String> paraMap=new HashMap<String, String>();
    	        	paraMap.put("begindate", begindate);
    	        	paraMap.put("enddate", enddate);
    	        	int updatenum = 0;
    	        	int addnum =0;
    	        	//获取1号到前一天的所有order数据
    	    		List<DfOrderPubseas> orderPubseasList = orderPubseasService.queryOrderPubseas(paraMap);
    	    		if(!orderPubseasList.isEmpty()){
    	    			for (DfOrderPubseas orderPubseas : orderPubseasList) {
    	    				String df_order_id = orderPubseas.getDf_order_id();
    	    				paraMap.put("df_order_id", df_order_id);
    	    				//判断是否存在
    	    				String valueflag = dfOrderPubseasService.queryOrderPubseasByOrderId(paraMap);
    	    				//存在，则数据值进行更新
    	    				if(!StringUtils.isBlank(valueflag)){
    	    					int m =dfOrderPubseasService.updateOrderPubseasByOrderId(orderPubseas);
    	    					updatenum += m;
    	    				}else{//不存在，则直接插入新数据
    	    					int n =dfOrderPubseasService.addDfOrderPubseas(orderPubseas);
    	    					addnum += n;
    	    				}
    	    			}
    	    		}
    	    		logger.info("**********指定人员公海订单分配调度结束**********");
    	    		logger.info("共查询数据记录行数："+orderPubseasList.size()+",更新记录条数："+updatenum+",新增记录条数："+addnum);
    	    		} catch (Exception e) {
    	    			logger.error(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }
	/**
	 * 公海订单清单,数据计算来源：df_mass_order
	 * 调度规则：每2分钟调度一次
	 * 参数：begindate  enddate
	 */
	@Scheduled(cron ="0 */2 * * * ? ")
	public void orderPubseasTaskByMassOrder() {
		new Thread(){
			public void run() {
				try {
					logger.info("**********指定人员公海订单分配调度开始**********");
					//取得公海表中最大签收时间
					String maxSignedTime = dfOrderPubseasService.queryMaxSignedTime();
					//给后台接口构建参数
					Map<String, String> paraMap=new HashMap<String, String>();
					paraMap.put("maxSignedTime", maxSignedTime);
					int updatenum  = dfOrderPubseasService.addDfOrderPubseasByMassOrder(paraMap);
					logger.info("**********指定人员公海订单分配调度结束**********");
					logger.info("共插入记录行数："+updatenum);
				} catch (Exception e) {
					logger.error(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}
}
