package com.guoanshequ.dc.das.task;

import com.guoanshequ.dc.das.model.DfMassOrder;
import com.guoanshequ.dc.das.model.TinyDispatch;
import com.guoanshequ.dc.das.service.AreaInfoService;
import com.guoanshequ.dc.das.service.DfMassOrderService;
import com.guoanshequ.dc.das.service.MassOrderService;
import com.guoanshequ.dc.das.service.TinyDispatchService;
import com.guoanshequ.dc.das.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Function：清洗海量订单任务调度
 * @author：chenchuang
 * @date:2018年1月4日 下午2:07:23
 *
 * @version V1.0
 */
@Component
public class MassOrderScheduleTask {

	@Autowired
	AreaInfoService areaInfoService;
	@Autowired
	MassOrderService massOrderService;
	@Autowired
	DfMassOrderService dfMassOrderService;
	@Autowired
	TinyDispatchService tinyDispatchService;

	private static final Logger logger = LogManager.getLogger(MassOrderScheduleTask.class);
	
	/**
	 * 调度规则：根据情况5分钟调度一次
	 * 参数：maxSignTime
	 */
//    @Scheduled(cron ="*/10 * * * * ?")
	public void massOrderTask() {
//    	new Thread(){
//    		public void run() {
    	    	try {
    	        	logger.info("**********自动清洗海量订单任务调度开始**********");
    	        	//获取上次调度时的最大签收时间
    	        	String maxSignedTime = dfMassOrderService.queryMaxSignedTime()==null?DateUtils.getDateFirstOfMonth(new Date()):dfMassOrderService.queryMaxSignedTime();
    	        	//给后台接口构建参数
    	        	Map<String, String> paraMap=new HashMap<String, String>();
    	        	paraMap.put("maxSignedTime", maxSignedTime);
    	    		List<DfMassOrder> massOrderList =massOrderService.queryMassOrderByDate(paraMap);
    	    		
    	    		if(!massOrderList.isEmpty()){
    	    			paramsPackage(massOrderList);
    	    		}
    	    		
    	    		logger.info("自动清洗海量订单共调度数据记录行数："+massOrderList.size());
    	    		logger.info("**********自动清洗海量订单任务调度结束**********");
    	    		} catch (Exception e) {
    	    			logger.info("自动清洗海量订单调度异常：",e);
    	    		}
//    		}
//    	}.start();
	}
    
    /**
     *  每天12点40分删除前1天的数据
     */
//    @Scheduled(cron ="0 40 12 * * ?")
    public void deleteDailyMassOrderTask(){
    	new Thread(){
    		public void run() {
    			try {
    				String dateTime = DateUtils.getDayTimeZero(new Date());
    				dfMassOrderService.deleteDfMassOrderDaily(dateTime);
    	    		logger.info("**********自动删除Daily海量订单任务调度结束**********");
    			} catch (Exception e) {
	    			logger.info("自动删除Daily海量订单调度异常：",e);
	    		}
    		}
    	}.start();
    }
    
    /**
     * 每月1号删数据，保留最近2个月
     */
//    @Scheduled(cron ="0 40 12 1 * ?")
    public void deleteMonthlyMassOrderTask(){
    	new Thread(){
    		public void run() {
    			try {
    				String dateTime = DateUtils.getPreDateFirstOfMonth(new Date());//日期所在前一天的1号
    				dfMassOrderService.deleteDfMassOrderMonthly(dateTime);
    	    		logger.info("**********自动删除Monthly海量订单任务调度结束**********");
    			} catch (Exception e) {
	    			logger.info("自动删除Monthly海量订单调度异常：",e);
	    		}
    		}
    	}.start();
    }
   
    /**
     * 定时查询退货订单并更新状态,间隔30分钟
     */
//    @Scheduled(cron ="0 0 */30 * * ?")
    public void returnMassOrderTask(){
    	new Thread(){
    		public void run() {
    			try{
    				logger.info("**********定时更新退货订单任务调度开始**********");
    	        	//获取上次调度时的最大退货时间
    	        	String maxReturnTime = massOrderService.queryMaxReturnTime()==null?DateUtils.getPreDate(new Date()):massOrderService.queryMaxReturnTime();
    	        	//给后台接口构建参数
    	        	Map<String, String> paraMap=new HashMap<String, String>();
    	        	paraMap.put("maxReturnTime", maxReturnTime);
    	    		List<Map<String, String>> list =massOrderService.queryReturnOrderByDate(paraMap);
    	    		
    	    		list.parallelStream().forEach(record ->{
    	    			Map<String, String> params=new HashMap<String, String>();
    	    			params.put("id", record.get("order_id"));
    	    			params.put("returnTime", String.valueOf(record.get("create_time")));
    	    			params.put("returnLabel", DfMassOrder.ReturnLabel.RETURN.code);
    	    			dfMassOrderService.updateDfMassOrderDaily(params);
    	    			dfMassOrderService.updateDfMassOrderMonthly(params);
    	    			dfMassOrderService.updateDfMassOrderTotal(params);
    	    		});
    	    		logger.info("**********定时更新退货订单任务调度结束**********");
    			} catch (Exception e) {
	    			logger.info("定时更新退货订单任务调度异常：",e);
	    		}
    		}
    	}.start();
    }
    
    /**
     * massOrder中异常订单任务调度
     * 基于ds_abnormal_order，故调度在异常订单任务时间之后
     */
//    @Scheduled(cron ="0 30 04 * * ?")
    public void abnormalMassOrderTask(){
    	new Thread(){
    		public void run() {
    			try{
    				logger.info("**********定时异常订单任务调度开始**********");
	    			String queryTime = DateUtils.getDayTimeZero(new Date());
	    			Map<String, String> paraMap=new HashMap<String, String>();
		        	paraMap.put("queryTime", queryTime);
		        	paraMap.put("abnormalLabel", DfMassOrder.AbnormalLabel.ABNORMAL.code);
		        	paraMap.put("abnormalNormalLabel", DfMassOrder.AbnormalLabel.DEFAULT.code);
		        	dfMassOrderService.updateAbnormalOrder(paraMap);
		        	dfMassOrderService.updateAbnormalOrderToNormal(paraMap);
		        	logger.info("**********定时异常订单任务调度结束**********");
    			} catch (Exception e) {
	    			logger.info("定时异常订单任务调度异常：",e);
	    		}
    		}
    	}.start();
    }
    
    /**
     * massOrder中新客订单任务调度
     * 夜间4:30调度
     */
//  @Scheduled(cron ="0 30 04 * * ?")
    public void customerTradeTask(){
    	new Thread(){
    		public void run() {
    			try{
    				logger.info("**********定时新客订单任务调度开始**********");
    				//获取上次调度时的最大退货时间
    	        	String maxQueryTime = massOrderService.queryMaxQueryTime()==null?DateUtils.getPreDate(new Date()):massOrderService.queryMaxQueryTime();
    	        	//给后台接口构建参数
    	        	Map<String, String> paraMap=new HashMap<String, String>();
    	        	paraMap.put("maxQueryTime", maxQueryTime);
    				List<Map<String, String>> list =massOrderService.queryCustomerTradeTask(paraMap);
    				
    				list.parallelStream().forEach(record ->{
    	    			Map<String, String> params=new HashMap<String, String>();
    	    			params.put("order_sn", record.get("order_sn"));
    	    			params.put("customer_isnew_flag", DfMassOrder.checkCustomerIsnew(String.valueOf(record.get("trading_price"))));
    	    			dfMassOrderService.updateCustomerOrderDaily(params);
    	    			dfMassOrderService.updateCustomerOrderMonthly(params);
    	    			dfMassOrderService.updateCustomerOrderTotal(params);
    	    		});
		        	logger.info("**********定时新客订单任务调度结束**********");
    			} catch (Exception e) {
	    			logger.info("定时新客订单任务调度异常：",e);
	    		}
    		}
    	}.start();
    }
    
    /**
     * 恢复小区Code
     */
    public void recoveryVillageCodeTask(){
    	new Thread(){
    		public void run() {
    			try{
    				logger.info("**********恢复小区Code任务调度开始**********");
    				Map<String, String> paraMap=new HashMap<String, String>();
    	        	paraMap.put("queryTime", "2018-01-01");
    				List<DfMassOrder> massOrderList = dfMassOrderService.queryMassOrderByDate(paraMap);
    				TinyDispatch tinyDispatch;
    				for(DfMassOrder record:massOrderList){
    						tinyDispatch = tinyDispatchService.queryTinyDispatchByOrderId(record.getId());
    						if(tinyDispatch!=null){
    							Map<String, String> params=new HashMap<String, String>();
        						params.put("order_id", record.getId());
        						params.put("villageCode", tinyDispatch.getCode());
        						dfMassOrderService.updateOrderVillageCodeDaily(params);
        						dfMassOrderService.updateOrderVillageCodeMonthly(params);
        						dfMassOrderService.updateOrderVillageCodeTotal(params);
    						}
    						record = null;
    		    	}
		        	logger.info("**********恢复小区Code任务调度结束**********");
    			} catch (Exception e) {
	    			logger.info("恢复小区Code任务调度异常：",e);
	    		}
    		}
    	}.start();
    }
    
    /**
     * 获取订单信息：小区Code/片区Code/国安A侠No
     * @param list
     * @return
     */
    public void paramsPackage(List<DfMassOrder> list){
    	try{
    		TinyDispatch tinyDispatch;
	    	for(DfMassOrder record:list){
					tinyDispatch = tinyDispatchService.queryTinyDispatchByOrderId(record.getId());
					if(tinyDispatch!=null){
						record.setInfo_village_code(tinyDispatch.getCode());
						record.setInfo_employee_a_no(tinyDispatch.getEmployee_a_no());
						if(StringUtils.isBlank(tinyDispatch.getEmployee_a_no())){
							record.setPubseas_label(DfMassOrder.PubseasLabel.PUBSEAS.code);
						}else{
							record.setPubseas_label(DfMassOrder.PubseasLabel.DEFAULT.code);
						}
						record.setArea_code(areaInfoService.queryAreaNoByTinyNo(tinyDispatch.getCode()));
					}else{
						record.setPubseas_label(DfMassOrder.PubseasLabel.PUBSEAS.code);
					}
					dfMassOrderService.addDfMassOrderDaily(record);
	    			dfMassOrderService.addDfMassOrderMonthly(record);
	    			dfMassOrderService.addDfMassOrderTotal(record);
	    			record = null;
	    	}
	    } catch (Exception e) {
			logger.info("获取订单信息异常：",e);
		}
    }

}
