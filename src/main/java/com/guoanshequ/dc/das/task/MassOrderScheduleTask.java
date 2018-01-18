package com.guoanshequ.dc.das.task;

import com.guoanshequ.dc.das.model.DfMassOrder;
import com.guoanshequ.dc.das.model.TinyDispatch;
import com.guoanshequ.dc.das.service.*;
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
	@Autowired
	DfOrderReturnedService dfOrderReturnedService;

	private static final Logger logger = LogManager.getLogger(MassOrderScheduleTask.class);
	
	/**
	 * 调度规则：根据情况1分钟调度一次
	 * 参数：maxSignTime
	 */
    @Scheduled(cron ="0 */1 * * * ?")
	public void massOrderTask() {
			try {
				long startTime = System.currentTimeMillis();    //获取开始时间
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
				long endTime = System.currentTimeMillis();    //获取结束时间
				logger.info("自动清洗海量订单共调度数据记录行数："+massOrderList.size());
				logger.info("**********自动清洗海量订单任务调度结束:"+(endTime - startTime) + "ms**********");
				} catch (Exception e) {
					logger.info("自动清洗海量订单调度异常：",e);
				}
	}
    
    /**
     *  每天12点40分删除前1天的数据
     */
    @Scheduled(cron ="0 40 12 * * ?")
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
     * 每月1号中午12点40删数据，保留最近2个月
     */
    @Scheduled(cron ="0 40 12 1 * ?")
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
     * 将数据写入到退货order_returned表中
	 *
     */
//    @Scheduled(cron ="0 */30 * * * ?")
    public void OrderReturnedTask(){
    	new Thread(){
    		public void run() {
    			try{
    				logger.info("**********定时插入退货订单任务调度开始**********");
    	        	//获取上次调度时的最大退货时间
    	        	String maxReturnedTime = dfOrderReturnedService.queryMaxReturnedTime()==null?DateUtils.getPreDate(new Date()):dfOrderReturnedService.queryMaxReturnedTime();
    	        	//给后台接口构建参数
    	        	Map<String, String> paraMap=new HashMap<String, String>();
    	        	paraMap.put("maxReturnTime", "2018-01-01");
    	    		List<Map<String, String>> list =massOrderService.queryReturnOrders(paraMap);
					int addnum =0;
					int n =0;
					for (Map<String, String> returnMap:list) {
						n = dfOrderReturnedService.addDfOrderReturned(returnMap);
						addnum +=n;
					}
					logger.info("**********定时插入退货订单任务调度结束,记录行数："+addnum);
    			} catch (Exception e) {
	    			logger.info("定时插入退货订单任务调度异常：",e);
	    		}
    		}
    	}.start();
    }

	/**
	 * 定时查询退货订单并更新状态
	 * 间隔30分钟查询一次，调度范围：当前时间前一天到现在
	 */
	@Scheduled(cron ="0 */30 * * * ?")
	public void returnMassOrderTask(){
		new Thread(){
			public void run() {
				try{
					logger.info("**********定时更新退货订单任务调度开始**********");
					//获取上次调度时的最大退货时间
					String maxReturnTime = massOrderService.queryMaxReturnTime()==null?DateUtils.getPreDate(new Date()):massOrderService.queryMaxReturnTime();
					//给后台接口构建参数
					Map<String, String> paraMap=new HashMap<String, String>();
					paraMap.put("maxReturnTime", "2018-01-01");
					List<Map<String, String>> list =massOrderService.queryReturnOrderByDate(paraMap);

					list.parallelStream().forEach(record ->{
						Map<String, String> params=new HashMap<String, String>();
						params.put("id", record.get("order_id"));
						params.put("returnTime", String.valueOf(record.get("create_time")));
						params.put("returned_amount",record.get("returned_amount"));
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
	 * 调度为一次性每天凌晨4点30分，调度范围：updatetime为当天的订单
     */
    @Scheduled(cron ="0 30 04 * * ?")
    public void abnormalMassOrderTask(){
    	new Thread(){
    		public void run() {
    			try{
    				logger.info("**********定时异常订单任务调度开始**********");
	    			String queryTime = DateUtils.getPreDate(new Date());
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
     * 夜间4:50调度
     */
  @Scheduled(cron ="0 50 04 * * ?")
    public void customerTradeTask(){
    	new Thread(){
    		public void run() {
    			try{
    				logger.info("**********定时新客订单任务调度开始**********");
    	        	String maxQueryTime = DateUtils.getDayTimeZero(new Date());
    	        	//给后台接口构建参数
    	        	Map<String, String> paraMap=new HashMap<String, String>();
    	        	paraMap.put("maxQueryTime", "2018-01-01");
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
						//设置小区号
						record.setInfo_village_code(tinyDispatch.getCode());
						//设置A国安侠号
						record.setInfo_employee_a_no(tinyDispatch.getEmployee_a_no());
						//设置片区号
						record.setArea_code(areaInfoService.queryAreaNoByTinyNo(tinyDispatch.getCode()));
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
