package com.guoanshequ.dc.das.task;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guoanshequ.dc.das.model.Contract;
import com.guoanshequ.dc.das.model.DfMassOrder;
import com.guoanshequ.dc.das.model.ImsTbsdgds;
import com.guoanshequ.dc.das.model.OrderItem;
import com.guoanshequ.dc.das.model.OrderItemExtra;
import com.guoanshequ.dc.das.model.TinyDispatch;
import com.guoanshequ.dc.das.service.AreaInfoService;
import com.guoanshequ.dc.das.service.DfMassOrderService;
import com.guoanshequ.dc.das.service.DfOrderReturnedService;
import com.guoanshequ.dc.das.service.DsCronTaskService;
import com.guoanshequ.dc.das.service.MassOrderService;
import com.guoanshequ.dc.das.service.OrderService;
import com.guoanshequ.dc.das.service.StoreNumberService;
import com.guoanshequ.dc.das.service.TinyDispatchService;
import com.guoanshequ.dc.das.utils.DateUtils;
import com.guoanshequ.dc.das.utils.ImpalaUtil;

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
	@Autowired
	DsCronTaskService dsCronTaskService;
	@Autowired
	OrderService orderService;
	@Autowired
	StoreNumberService storeNumberService;
	

	private static final Logger logger = LogManager.getLogger(MassOrderScheduleTask.class);
	
	/**
	 * 调度规则：根据情况1分钟调度一次 
	 * 参数：maxSignTime
	 */
	@Scheduled(cron ="59 * 6-23 * * ?")
//    @Scheduled(cron ="0 */1 * * * ?")
	public void massOrderTask() {
			try {
				Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(1);
				String isrun = taskMap.get("isrun");
				if("ON".equals(isrun)){
						long taskStartTime = System.currentTimeMillis();    //获取开始时间
						logger.info("**********自动清洗海量订单任务调度开始**********");
						//获取上次调度时的最大签收时间
						String maxSignedTime = dfMassOrderService.queryMaxSignedTime()==null?DateUtils.getDateFirstOfMonth(new Date()):dfMassOrderService.queryMaxSignedTime();
						String endSignedTime = DateUtils.getCurTime(new Date());
						//给后台接口构建参数
						Map<String, String> paraMap=new HashMap<String, String>();
						paraMap.put("maxSignedTime", maxSignedTime);
						paraMap.put("endSignedTime", endSignedTime);
						List<DfMassOrder> massOrderList =massOrderService.queryMassOrderByDate(paraMap);
		
						if(!massOrderList.isEmpty()){
							paramsPackage(massOrderList);
						}
						long taskEndTime = System.currentTimeMillis();    //获取结束时间
						logger.info("自动清洗海量订单共调度数据记录行数："+massOrderList.size()+",maxSignedTime："+maxSignedTime+",endSignedTime:"+endSignedTime);
						logger.info("**********自动清洗海量订单任务调度结束:"+(taskEndTime - taskStartTime) + "ms**********");
					}
				} catch (Exception e) {
					logger.info(e.toString());
					e.printStackTrace();
				}
	}

	/**
	 * 调度规则：订单信息打补丁
	 * 说明：根据开始时间与结束时间补期间所丢失的订单数据
	 * 每天凌晨0点15分前一天数据打补丁
	 */
	@Scheduled(cron ="0 15 0 * * ?")
	public void massOrderPatchTask() {
		try {
			Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(2);
			String isrun = taskMap.get("isrun");
			if("ON".equals(isrun)){
				logger.info("**********订单打补丁任务调度开始**********");
				long taskStartTime = System.currentTimeMillis();    //获取开始时间
				String runtype = taskMap.get("runtype");
				String maxSignedTime = null;
				String endSignedTime = null;
				//获取上次调度时的最大签收时间开始时间与结束时间
				if("MANUAL".equals(runtype)){
					maxSignedTime = taskMap.get("begintime");
					endSignedTime = taskMap.get("endtime");
				}else{
					maxSignedTime = DateUtils.getPreDate(new Date());
					endSignedTime = DateUtils.getCurTime(new Date());
				}
				//给后台接口构建参数
				Map<String, String> paraMap=new HashMap<String, String>();
				paraMap.put("maxSignedTime", maxSignedTime);
				paraMap.put("endSignedTime", endSignedTime);
				List<DfMassOrder> massOrderList =massOrderService.queryMassOrderByDate(paraMap);

				if(!massOrderList.isEmpty()){
		    		//设置任务为运行中状态
		    		Map<String, String> runMap = new HashMap<String,String>();
		    		runMap.put("id", "2");
		    		runMap.put("task_status", "RUNNING");
		    		dsCronTaskService.updateTaskStatusById(runMap);
					//根据结果去清洗表mass_order中查找不存在的订单
					List<DfMassOrder> massOrderPatchList = new ArrayList<DfMassOrder>();
					StringBuilder sb = new StringBuilder();
					String ordersn =null;
					for (DfMassOrder dfMassOrder:massOrderList) {
						paraMap.put("orderid", dfMassOrder.getId());
						ordersn = dfMassOrderService.queryOrersnByOrderId(paraMap);
						if (StringUtils.isBlank(ordersn)){
							massOrderPatchList.add(dfMassOrder);
							sb.append(dfMassOrder.getOrder_sn()+",");
						}
					}
					if(!massOrderPatchList.isEmpty()){
						paramsPackage(massOrderPatchList);
					}
					logger.info("订单打补丁共调度数据记录行数："+massOrderPatchList.size() +" 行,所补订单号为："+sb.toString());
				}
				//插入完毕将状态设置为'DONE'
				Map<String, String> doneMap = new HashMap<String,String>();
				doneMap.put("id", "2");
				doneMap.put("task_status", "DONE");
				dsCronTaskService.updateTaskStatusById(doneMap);
				long taskEndTime = System.currentTimeMillis();    //获取结束时间
				logger.info("**********订单打补丁任务调度结束:"+(taskEndTime - taskStartTime) + "ms**********");
			}

		} catch (Exception e) {
			logger.info(e.toString());
			e.printStackTrace();
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
    				String dateTime = DateUtils.getPreNDays(1);
    				dfMassOrderService.deleteDfMassOrderDaily(dateTime);
    	    		logger.info("**********自动删除Daily海量订单任务调度结束**********");
    			} catch (Exception e) {
					logger.info("自动删除Daily海量订单调度异常：",e);
				}
    		}
    	}.start();
    }
    
    /**
     * 每月1号中午12点40删数据，只保留最近当月与上月数据
     */
    @Scheduled(cron ="0 40 12 1 * ?")
    public void deleteMonthlyMassOrderTask(){
    	new Thread(){
    		public void run() {
    			try {
    				String dateTime = DateUtils.getPreDateFirstOfMonth(new Date());//日期前一天所在月份的1号
    				dfMassOrderService.deleteDfMassOrderMonthly(dateTime);
    	    		logger.info("**********自动删除Monthly海量订单任务调度结束**********");
    			} catch (Exception e) {
	    			logger.info("自动删除Monthly海量订单调度异常：",e);
	    		}
    		}
    	}.start();
    }
   
    /**
     * 将数据写入到退货df_order_returned表中
	 * 调度规则：每天凌晨1点40分
	 * 根据表中最大退货时间顺序增量添加
     */
    @Scheduled(cron ="0 40 1 * * ?")
    public void OrderReturnedTask(){
    	new Thread(){
    		public void run() {
    			try{
    				logger.info("**********定时插入退货订单表order_returned任务调度开始**********");
    	        	//获取上次调度时的最大退货时间
    	        	String maxReturnedTime = dfOrderReturnedService.queryMaxReturnedTime()==null?DateUtils.getPreDate(new Date()):dfOrderReturnedService.queryMaxReturnedTime();
    	        	//给后台接口构建参数
    	        	Map<String, String> paraMap=new HashMap<String, String>();
    	        	paraMap.put("maxReturnTime", maxReturnedTime);
    	    		List<Map<String, String>> list =massOrderService.queryReturnOrders(paraMap);
					int addnum =0;
					int n =0;
					for (Map<String, String> returnMap:list) {
						n = dfOrderReturnedService.addDfOrderReturned(returnMap);
						addnum +=n;
					}
					logger.info("**********定时插入退货订单表order_returned任务调度结束,记录行数："+addnum);
    			} catch (Exception e) {
	    			logger.info("定时插入退货订单表order_returned任务调度异常：",e);
	    		}
    		}
    	}.start();
    }

	/**
	 * 定时查询退货订单并更新状态,用于更新mass_order表中退货标签
	 * 调度规则：每天凌晨1点10分
	 * 根据mass_order表中最大退货时间顺序增量添加
	 */
	@Scheduled(cron ="0 10 1 * * ?")
	public void returnMassOrderTask(){
		new Thread(){
			public void run() {
				try{
					Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(99);
					String runtype = taskMap.get("runtype");
					logger.info("**********massorder打退货标签任务调度开始 **********");
					String maxReturnTime = null;
					if("MANUAL".equals(runtype)) { 
						maxReturnTime = taskMap.get("begintime");
					}else {
						//获取上次调度时的最大退货时间
						maxReturnTime = dfMassOrderService.queryMaxReturnTime()==null?DateUtils.getPreDate(new Date()):dfMassOrderService.queryMaxReturnTime();
					}
					//给后台接口构建参数
					Map<String, String> paraMap=new HashMap<String, String>();
					paraMap.put("maxReturnTime", maxReturnTime);
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
					logger.info("**********massorder打退货标签任务调度结束**********");
				} catch (Exception e) {
					logger.info("massorder打退货标签任务调度异常：",e);
				}
			}
		}.start();
	}
    
    /**
     * massOrder中异常订单任务调度
     * 基于ds_abnormal_order，故调度在异常订单任务时间之后
	 * 调度为一次性每天凌晨1点20分，调度范围：updatetime为当天的订单
     */
    @Scheduled(cron ="0 20 01 * * ?")
    public void abnormalMassOrderTask(){
    	new Thread(){
    		public void run() {
    			try{
    				Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(99);
    				String runtype = taskMap.get("runtype");
    				logger.info("**********massorder打异常订单标签任务调度开始**********");
	    			String queryTime = null ;
	    			if("MANUAL".equals(runtype)) {
	    				queryTime = taskMap.get("begintime");
	    			}else {
	    				queryTime = DateUtils.getPreDate(new Date());
	    			}
	    					
	    			Map<String, String> paraMap=new HashMap<String, String>();
		        	paraMap.put("queryTime", queryTime);
		        	paraMap.put("abnormalLabel", DfMassOrder.AbnormalLabel.ABNORMAL.code);
		        	paraMap.put("abnormalNormalLabel", DfMassOrder.AbnormalLabel.DEFAULT.code);
		        	dfMassOrderService.updateAbnormalOrder(paraMap);
		        	dfMassOrderService.updateAbnormalOrderToNormal(paraMap);
		        	logger.info("**********massorder打异常订单标签任务调度结束**********");
    			} catch (Exception e) {
	    			logger.info("massorder打异常订单标签任务调度异常：",e);
	    		}
    		}
    	}.start();
    }
    
    /**
     * massOrder中新客订单打标签任务调度
     * 夜间1:30调度
     */
  //@Scheduled(cron ="0 30 01 * * ?")
    public void customerTradeTask(){
    	new Thread(){
    		public void run() {
    			try{
    				logger.info("**********massorder打 拉新标签 任务调度开始**********");
    	        	String preDate = DateUtils.getPreDate(new Date());
    	        	String year = preDate.substring(0,4);
    	        	String month = preDate.substring(5,7);
    	        	String order_ym = year+month;
    	        	//给后台接口构建参数
    	        	Map<String, String> paraMap=new HashMap<String, String>();
    	        	paraMap.put("order_ym", order_ym);
    				List<Map<String, String>> list =massOrderService.queryCustomerTradeTask(paraMap);
    				
    				list.parallelStream().forEach(record ->{
    	    			Map<String, String> params=new HashMap<String, String>();
    	    			params.put("order_sn", record.get("order_sn"));
    	    			params.put("customer_isnew_flag", DfMassOrder.checkCustomerIsnew(String.valueOf(record.get("trading_price"))));
    	    			dfMassOrderService.updateCustomerOrderDaily(params);
    	    			dfMassOrderService.updateCustomerOrderMonthly(params);
    	    			dfMassOrderService.updateCustomerOrderTotal(params);
    	    		});
		        	logger.info("**********massorder打 拉新标签 任务调度结束**********");
    			} catch (Exception e) {
	    			logger.info("massorder打 拉新标签 任务调度异常：",e);
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
     * 获取订单信息：根据订单号从mongo中获取小区Code/片区Code/国安A侠No
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
    
	/**
	 * 定时用于更新mass_order表中ordertag1标签
	 * 调度规则：每天凌晨1点50分
	 */
	@Scheduled(cron ="0 50 1 * * ?")
	public void xbOrderTagTask(){
		new Thread(){
			public void run() {
				try{
    				Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(99);
    				String runtype = taskMap.get("runtype");
					logger.info("**********massorder打小B端订单标签任务调度开始**********");
					String begintime =null;
					String endtime = null;
					if("MANUAL".equals(runtype)) {
						begintime = taskMap.get("begintime");
						endtime = taskMap.get("endtime");
					}else {
						//开始时间
						begintime = DateUtils.getPreDateTime(new Date());
						//结束时间
						endtime = DateUtils.getCurDateTime(new Date());
					}
					//给后台接口构建参数
					Map<String, String> paraMap=new HashMap<String, String>();

					paraMap.put("begintime", begintime);
					paraMap.put("endtime", endtime);
					
					List<Map<String, String>> list =dfMassOrderService.queryXBorderBySignTime(paraMap);
					for (Map<String, String> map : list) {
						dfMassOrderService.updateXBorderTagDailyById(map);
						dfMassOrderService.updateXBorderTagMonthlyById(map);
						dfMassOrderService.updateXBorderTagTotalById(map);
					}
					logger.info("**********massorder打小B端订单标签任务调度结束**********");
				} catch (Exception e) {
					logger.info("massorder打小B端订单标签任务调度异常：",e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}
    
	/**
	 * 定时用于更新df_userprofile_tag打用户标签 
	 * 调度规则：每天凌晨2点20分
	 */
	@Scheduled(cron ="0 20 2 * * ?")
	public void xbUserProfileTagTask(){
		new Thread(){
			public void run() {
				try{
    				Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(99);
    				String runtype = taskMap.get("runtype");
					logger.info("**********df_userprofile_tag打小B端订单标签任务调度开始**********");
					String begintime = null;
					String endtime = null;
					if("MANUAL".equals(runtype)) {
						begintime = taskMap.get("begintime");
						endtime = taskMap.get("endtime");
					}else {
						//开始时间
						begintime = DateUtils.getPreDateTime(new Date());
						//结束时间
						endtime = DateUtils.getCurDateTime(new Date());
					}
					//给后台接口构建参数
					Map<String, String> paraMap=new HashMap<String, String>();

					paraMap.put("begintime", begintime);
					paraMap.put("endtime", endtime);
					
					List<Map<String, String>> list =dfMassOrderService.queryXBCustomerBySignTime(paraMap);
					for (Map<String, String> map : list) {
						dfMassOrderService.addXBUserTag(map);
					}
					logger.info("**********df_userprofile_tag打小B端订单标签任务调度结束**********");
				} catch (Exception e) {
					logger.info("df_user_profile打小B端订单标签任务调度异常：",e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	/**
	 * 
	* @Title: tinyAreaPatchByOrderidTask 
	* @Description: 给签收时间段的订单重新从mongo中获取小区code\员工编号employee_a_no\片区code
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void tinyAreaPatchByOrderidTask(){
		new Thread(){
			public void run() {
				try{
					logger.info("**********根据指定签收时间内的订单号刷新对应的小区片区号调度开始**********");
					Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(7);
					String isrun = taskMap.get("isrun");
					if("ON".equals(isrun)){
						logger.info("**********订单打补丁任务调度开始**********");
						String runtype = taskMap.get("runtype");
						String begintime = null;
						String endtime = null;
						//获取上次调度时的最大签收时间开始时间与结束时间
						if("MANUAL".equals(runtype)){
							begintime = taskMap.get("begintime");
							endtime = taskMap.get("endtime");
						}else{
							begintime = DateUtils.getPreDate(new Date());
							endtime = DateUtils.getCurTime(new Date());
						}
						//给后台接口构建参数
						Map<String, String> paraMap=new HashMap<String, String>();
						paraMap.put("begintime", begintime);
						paraMap.put("endtime", endtime);
						List<Map<String, String>> orderList= dfMassOrderService.queryOrderIdBySignTime(paraMap);
						if(!orderList.isEmpty()){
				    		Map<String, String> runMap = new HashMap<String,String>();
				    		runMap.put("id", "7");
				    		runMap.put("task_status", "RUNNING");
				    		dsCronTaskService.updateTaskStatusById(runMap);
							TinyDispatch tinyDispatch;
							String order_id ="";
							for (Map<String, String> orderMap : orderList) {
								order_id = orderMap.get("id");
								tinyDispatch = tinyDispatchService.queryTinyDispatchByOrderId(order_id);
	    						if(tinyDispatch!=null){
	    							Map<String, String> params=new HashMap<String, String>();
	        						params.put("order_id", order_id);
	        						params.put("villagecode", tinyDispatch.getCode());
	        						params.put("employee_a_no", tinyDispatch.getEmployee_a_no());
	        						params.put("areacode", areaInfoService.queryAreaNoByTinyNo(tinyDispatch.getCode()));
	        						dfMassOrderService.updateOrderVillageCodeDaily(params);
	        						dfMassOrderService.updateOrderVillageCodeMonthly(params);
	        						dfMassOrderService.updateOrderVillageCodeTotal(params);
	    						}
							}
							
				    		//设置任务为完成状态
				    		Map<String, String> doneMap = new HashMap<String,String>();
				    		doneMap.put("id", "7");
				    		doneMap.put("task_status", "DONE");
				    		dsCronTaskService.updateTaskStatusById(doneMap);
							
						}
					}
					logger.info("**********根据指定签收时间内的订单号刷新对应的小区片区号调度结束**********");
				} catch (Exception e) {
					logger.info("根据指定签收时间内的订单号刷新对应的小区片区号调度异常：",e.toString());
					e.printStackTrace();
				}
			}
		}.start();
		}
	
	
	/**
	 * 定时用于更新mass_order表中ordertag1标签(K-开卡礼，S-试用礼)
	 * 调度规则：每天凌晨2点40分
	 */
	@Scheduled(cron ="0 40 2 * * ?")
	public void ksOrderTagTask(){
		new Thread(){
			public void run() {
				try{
					logger.info("**********massorder开卡礼试用礼订单标签任务调度开始**********");
					Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(11);
					String isrun = taskMap.get("isrun");
					if("ON".equals(isrun)){
						String runtype = taskMap.get("runtype");
						String begintime = null;
						String endtime = null;
						//获取上次调度时的最大签收时间开始时间与结束时间
						if("MANUAL".equals(runtype)){
							begintime = taskMap.get("begintime");
							endtime = taskMap.get("endtime");
						}else{
							begintime = DateUtils.getPreDate(new Date());
							endtime = DateUtils.getCurDateTime(new Date());
						}
						
						//给后台接口构建参数
						Map<String, String> paraMap=new HashMap<String, String>();
						paraMap.put("begintime", begintime);
						paraMap.put("endtime", endtime);
						
						List<Map<String, String>> list =massOrderService.queryKSeshopByName();
						for (Map<String, String> map : list) {
							paraMap.put("order_tag", map.get("tag"));
							paraMap.put("eshop_id", map.get("eshop_id"));
							dfMassOrderService.updateKSorderTagDailyByEshopId(paraMap);
							dfMassOrderService.updateKSorderTagMonthlyByEshopId(paraMap);
							dfMassOrderService.updateKSorderTagTotalByEshopId(paraMap);
						}
					}
					logger.info("**********massorder开卡礼试用礼订单标签任务调度结束**********");
				} catch (Exception e) {
					logger.info("massorder开卡礼试用礼订单标签任务调度异常：",e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	/**
	 * 定时用于更新df_userprofile_tag打社员标签 
	 * 调度规则：每天凌晨2点55分
	 */
	@Scheduled(cron ="0 55 2 * * ?")
	public void memberUserProfileTagTask(){
		new Thread(){
			public void run() {
				try{
					Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(99);
					String runtype = taskMap.get("runtype");
					logger.info("**********df_userprofile_tag打合作社社员标签任务调度开始**********");
					String begintime = null;
					String endtime = null;
					if("MANUAL".equals(runtype)) {
						begintime = taskMap.get("begintime");
						endtime = taskMap.get("endtime");
					}else {
						//开始时间
						begintime = DateUtils.getPreDateTime(new Date());
						//结束时间
						endtime = DateUtils.getCurDateTime(new Date());
					}
					//给后台接口构建参数
					Map<String, String> paraMap=new HashMap<String, String>();

					paraMap.put("begintime", begintime);
					paraMap.put("endtime", endtime);
					
					List<Map<String, String>> list =dfMassOrderService.queryMemberCustomerBySignTime(paraMap);
					for (Map<String, String> map : list) {
						dfMassOrderService.addXBUserTag(map);
					}
					logger.info("**********df_userprofile_tag打合作社社员标签任务调度结束**********");
				} catch (Exception e) {
					logger.info("df_user_profile打合作社社员标签任务调度异常：",e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	/**
	 * 定时用于更新mass_order表中order_tag会员订单标签
	 * 调度规则：每天凌晨3点05分
	 */
	@Scheduled(cron ="0 05 3 * * ?")
	public void memberOrderTagTask(){
		new Thread(){
			public void run() {
				try{
    				Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(99);
    				String runtype = taskMap.get("runtype");
					logger.info("**********mass_order表中order_tag会员订单标签任务调度开始**********");
					String begintime = null;
					String endtime = null;
					if("MANUAL".equals(runtype)) {
						begintime = taskMap.get("begintime");
						endtime = taskMap.get("endtime");
					}else {
						//开始时间
						begintime = DateUtils.getPreDateTime(new Date());
						//结束时间
						endtime = DateUtils.getCurDateTime(new Date());
					}
					//给后台接口构建参数
					Map<String, String> paraMap=new HashMap<String, String>();

					paraMap.put("begintime", begintime);
					paraMap.put("endtime", endtime);
					
					List<Map<String, String>> list =dfMassOrderService.queryMemberOrderBySignTime(paraMap);
					for (Map<String, String> map : list) {
						dfMassOrderService.updateXBorderTagDailyById(map);
						dfMassOrderService.updateXBorderTagMonthlyById(map);
						dfMassOrderService.updateXBorderTagTotalById(map);
					}
					logger.info("**********mass_order表中order_tag会员订单标签任务调度结束**********");
				} catch (Exception e) {
					logger.info("mass_order表中order_tag会员订单标签任务调度异常：",e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * 定时用于更新mass_order表中order_tag合作社E店订单标签
	 * 调度规则：每天凌晨3点05分
	 */
	@Scheduled(cron ="0 05 3 * * ?")
	public void eshopOrderTagTask(){
		new Thread(){
			public void run() {
				try{
					logger.info("**********mass_order表中order_tag合作社E店订单标签任务调度开始**********");
					
					//给后台接口构建参数
					Map<String, String> paraMap=new HashMap<String, String>();
					//开始时间
					String begintime = DateUtils.getPreDateTime(new Date());
					//结束时间
					String endtime = DateUtils.getCurDateTime(new Date());
					paraMap.put("begintime", begintime);
					paraMap.put("endtime", endtime);
					
					List<Map<String, String>> list =massOrderService.queryCooperativeEshop();
					for (Map<String, String> map : list) {
						paraMap.put("order_tag", map.get("tag"));
						paraMap.put("eshop_id", map.get("eshop_id"));
						dfMassOrderService.updateKSorderTagDailyByEshopId(paraMap);
						dfMassOrderService.updateKSorderTagMonthlyByEshopId(paraMap);
						dfMassOrderService.updateKSorderTagTotalByEshopId(paraMap);
					}
					logger.info("**********mass_order表中order_tag合作社E店订单标签任务调度结束**********");
				} catch (Exception e) {
					logger.info("mass_order表中order_tag合作社E店订单标签任务调度异常：",e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	/**
	 * 每日城市会员统计数据 ds_ope_member_city_day
	 * 调度规则：每天凌晨3点20分
	 */
	@Scheduled(cron ="0 20 3 * * ?")
	public void memberCityDayTask(){
		new Thread(){
			public void run() {
				try{
					logger.info("**********ds_ope_member_city_day每日城市会员统计数据任务调度开始**********");
					
					//给后台接口构建参数
					Map<String, String> paraMap=new HashMap<String, String>();
					//开始时间
					String begintime = DateUtils.getPreDateTime(new Date());
					//结束时间
					String endtime = DateUtils.getCurDateTime(new Date());
					paraMap.put("begintime", begintime);
					paraMap.put("endtime", endtime);
					
					dfMassOrderService.updateMemberCityDay(paraMap);
					logger.info("**********ds_ope_member_city_day每日城市会员统计数据任务调度结束**********");
				} catch (Exception e) {
					logger.info("ds_ope_member_city_day每日城市会员统计数据任务调度异常：",e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	/**
	 * 每日时效分配数据 ds_ope_order_distribution
	 * 调度规则：每天凌晨3点20分 
	 */
	@Scheduled(cron ="0 20 3 * * ?")
	public void orderDistribution(){
		new Thread(){
			public void run() {
				try{
					logger.info("**********ds_ope_order_distribution每日时效分配数据统计任务调度开始**********");
					
					//给后台接口构建参数
					Map<String, String> paraMap=new HashMap<String, String>();
					//开始时间
					String begintime = DateUtils.getPreDateTime(new Date());
					//结束时间
					String endtime = DateUtils.getCurDateTime(new Date());
					paraMap.put("begintime", begintime);
					paraMap.put("endtime", endtime);
					
					dfMassOrderService.updateOrderDistribution(paraMap);
					logger.info("**********ds_ope_order_distribution每日时效分配数据统计任务调度结束**********");
				} catch (Exception e) {
					logger.info("ds_ope_order_distribution每日时效分配数据统计任务调度异常：",e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * 计算每个订单所对应的成本、利润信息
	 * 调度规则：每天凌晨3点30分
	 */
	@Scheduled(cron = "0 30 4 * * ?")
	public void updateOrderProfitTask() {
		new Thread() {
			public void run() {
				try {
					logger.info("**********计算每个订单所对应的成本、利润信息任务调度开始**********");
					Integer updatenum = 0;
					Integer preUpdatenum = 0;
					Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(12);
					String isrun = taskMap.get("isrun");
					String begintime = null;
					String endtime = null;
					String pre_begintime = null;
					String pre_endtime = null;
					if ("ON".equals(isrun)) {
						String runtype = taskMap.get("runtype");
						if ("MANUAL".equals(runtype)) {
							begintime = taskMap.get("begintime");
							endtime = taskMap.get("endtime");
						} else {
							begintime = DateUtils.getPreDateTime(new Date());
							endtime = DateUtils.getCurDateTime(new Date());
						}
						pre_begintime = DateUtils.getPreNDateTimeByAssign(DateUtils.StringToDateTime(begintime), 1);
						pre_endtime = DateUtils.getPreNDateTimeByAssign(DateUtils.StringToDateTime(endtime), 1);

						// 给后台接口构建参数
						Map<String, String> paraMap = new HashMap<String, String>();
						paraMap.put("begintime", begintime);
						paraMap.put("endtime", endtime);
						// 处理前一天利润数据
						List<DfMassOrder> massOrderList = dfMassOrderService.queryMassOrderListByDate(paraMap);

						if (!massOrderList.isEmpty()) {
							Map<String, String> runMap = new HashMap<String, String>();
							runMap.put("id", "12");
							runMap.put("task_status", "RUNNING");
							dsCronTaskService.updateTaskStatusById(runMap);

							for (DfMassOrder dfMassOrder : massOrderList) {
								String order_id = dfMassOrder.getId();
								String eshop_joint_ims = dfMassOrder.getEshop_joint_ims();
								Date sign_time = dfMassOrder.getSign_time();
								String sign_date = DateUtils.getStrDate(sign_time);
								String store_id = dfMassOrder.getStore_id();
								String business_type = dfMassOrder.getBusiness_type();
								String bussiness_group_id = dfMassOrder.getBussiness_group_id();

								paraMap.put("order_id", order_id);
								paraMap.put("sign_date", sign_date);

								// 1、关联进销存，计算订单在进销存中的总成本,自营商品无论是否有合同，一律从价
								BigDecimal sum_cost_price = new BigDecimal("0.00");
								BigDecimal order_profit = new BigDecimal("0.00");
								if ("yes".equals(eshop_joint_ims) && "8ac28b935fed0bc8015fed4c76f60018".equals(bussiness_group_id)) {
									// 获取订单明细
									Integer store_number = storeNumberService.queryStoreNumberById(store_id);
									List<OrderItem> orderItemList = massOrderService.queryOrderItemByOrderId(paraMap);
									for (OrderItem orderItem : orderItemList) {
										ImsTbsdgds imsTbsGds;
										String product_code = orderItem.getProduct_code();

										// 存在商品码，则根据商品码和签收日期，去查找商品销售表中计算相应的总成本价=成本价*数量
										if (product_code != null) {
											paraMap.put("product_code", product_code);
											paraMap.put("store_number", store_number.toString());
											// 根据商品code去进销存日销售表中查找对应的成本
											imsTbsGds = dfMassOrderService.queryCostPriceBySigndateCode(paraMap);
											if (imsTbsGds != null) {
												BigDecimal ims_cost_price = imsTbsGds.getCost_price().multiply(new BigDecimal(orderItem.getQuantity()));
												sum_cost_price = sum_cost_price.add(ims_cost_price);
											} else {
												sum_cost_price = null;
												break;
											}
										}
									}
									if (sum_cost_price != null) {
										dfMassOrder.setCost_price(sum_cost_price);
										order_profit = calProfitByPriceOfIms(dfMassOrder);
										dfMassOrder.setOrder_profit(order_profit);
										dfMassOrder.setOrder_tag3("1");
									} else {
										dfMassOrder.setOrder_tag3("0");
									}

								} else {// 不在进销存系统，计算在国安平台中的总成本
									// 2、根据订单号查询对应的总成本价
									String cost_price = orderService.queryOrderCostPriceById(paraMap);
									if (cost_price != null) {
										sum_cost_price = new BigDecimal(cost_price);
									}
									dfMassOrder.setCost_price(sum_cost_price);

									// 3、计算利润信息:通过订单号查询对应的e店合同，有合同则按合同计算方式，无合同则按自营方式（gmvprice-costprice）
									Contract contractInfo = orderService.queryOrderContractInfoById(dfMassOrder.getId());
									if (contractInfo != null) {// 有合同信息
										dfMassOrder.setContract_id(contractInfo.getContract_id());
										dfMassOrder.setContract_method(contractInfo.getContract_method());
										dfMassOrder.setContract_percent(contractInfo.getContract_percent());
										dfMassOrder.setContract_price(contractInfo.getContract_price());
										// 获取合同结算方式
										String contract_method = contractInfo.getContract_method();
										if ("price".equals(contract_method)) {// 从价
											order_profit = calProfitByPrice(dfMassOrder);
										} else if ("volume".equals(contract_method)) {// 从量
											order_profit = calProfitByVolume(dfMassOrder, contractInfo);
										} else if ("percent".equals(contract_method)) {// 从率
											order_profit = calProfitByPercent(dfMassOrder, contractInfo);
										}
									} else {// 无合同信息
										// 无合同自营为从价
										if ("self".equals(business_type)) {
											order_profit = calProfitByPrice(dfMassOrder);
										} else {// 无合同联营利润为0
											order_profit = new BigDecimal("0.00");
										}
									}
									dfMassOrder.setOrder_profit(order_profit);
									dfMassOrder.setOrder_tag3("1");
								}

								System.out.println("dfMassOrdersn*****" + dfMassOrder.getOrder_sn() + "contract_method:" + dfMassOrder.getContract_method() + ",cost_price:" + dfMassOrder.getCost_price() + ",gmvprice:" + dfMassOrder.getGmv_price() + ",profit:" + dfMassOrder.getOrder_profit());
								dfMassOrderService.updateOrderProfitOfDaily(dfMassOrder);
								updatenum += dfMassOrderService.updateOrderProfitOfMonthly(dfMassOrder);
								dfMassOrderService.updateOrderProfitOfTotal(dfMassOrder);
							}
						}

						// 给后台接口构建参数
						Map<String, String> preParaMap = new HashMap<String, String>();
						preParaMap.put("pre_begintime", pre_begintime);
						preParaMap.put("pre_endtime", pre_endtime);
						// 处理前二天
						List<DfMassOrder> massOrderIsnullList = dfMassOrderService.queryMassOrderIsnullListByPreDate(preParaMap);
						if (!massOrderIsnullList.isEmpty()) {
							for (DfMassOrder preDfMassOrder : massOrderIsnullList) {
								String order_id = preDfMassOrder.getId();
								Date sign_time = preDfMassOrder.getSign_time();
								String sign_date = DateUtils.getNextNDateTimeByAssign(sign_time, 1);
								String store_id = preDfMassOrder.getStore_id();

								preParaMap.put("order_id", order_id);
								preParaMap.put("sign_date", sign_date);

								// 1、关联进销存，计算订单在进销存中的总成本,自营商品无论是否有合同，一律从价
								BigDecimal sum_cost_price = new BigDecimal("0.00");
								BigDecimal order_profit = new BigDecimal("0.00");
								// 获取订单明细
								Integer store_number = storeNumberService.queryStoreNumberById(store_id);
								List<OrderItem> orderItemList = massOrderService.queryOrderItemByOrderId(preParaMap);
								for (OrderItem orderItem : orderItemList) {
									ImsTbsdgds imsTbsGds;
									String product_code = orderItem.getProduct_code();

									// 存在商品码，则根据商品码和签收日期，去查找商品销售表中计算相应的总成本价=成本价*数量
									if (product_code != null) {
										preParaMap.put("product_code", product_code);
										preParaMap.put("store_number", store_number.toString());
										// 根据商品code去进销存日销售表中查找对应的成本
										imsTbsGds = dfMassOrderService.queryCostPriceBySigndateCode(preParaMap);
										if (imsTbsGds != null) {
											BigDecimal ims_cost_price = imsTbsGds.getCost_price().multiply(new BigDecimal(orderItem.getQuantity()));
											sum_cost_price = sum_cost_price.add(ims_cost_price);
										} else {
											sum_cost_price = null;
											break;
										}
									}
								}
								if (sum_cost_price != null) {
									preDfMassOrder.setCost_price(sum_cost_price);
									order_profit = calProfitByPriceOfIms(preDfMassOrder);
									preDfMassOrder.setOrder_profit(order_profit);
									preDfMassOrder.setOrder_tag3("1");
								} else {
									preDfMassOrder.setOrder_tag3("0");
								}
								dfMassOrderService.updateOrderProfitOfDaily(preDfMassOrder);
								preUpdatenum += dfMassOrderService.updateOrderProfitOfMonthly(preDfMassOrder);
								dfMassOrderService.updateOrderProfitOfTotal(preDfMassOrder);
							}
						}

						// 设置任务为完成状态
						Map<String, String> doneMap = new HashMap<String, String>();
						doneMap.put("id", "12");
						doneMap.put("task_status", "DONE");
						dsCronTaskService.updateTaskStatusById(doneMap);
					}
					logger.info("**********计算每个订单所对应的成本、利润信息数据任务调度结束,开始时间：" + begintime + ",结束时间：" + endtime + ",共更新记录数：" + updatenum + "**********,前两天更新数：" + preUpdatenum);
				} catch (Exception e) {
					logger.error("计算每个订单所对应的成本、利润信息数据任务调度异常：", e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * 221订单标签order2_tag：商品类、服务类、团购类订单打标签
	 * 调度规则：每天凌晨2点01分 
	 */
	@Scheduled(cron ="0 01 2 * * ?")
	public void activityOrder2TagTask(){
		new Thread(){
			public void run() {
				try{
					Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(99);
					String runtype = taskMap.get("runtype");
					logger.info("**********221订单标签order2_tag：商品类、服务类、团购类订单打标签任务调度开始**********");
					
					//给后台接口构建参数
					Map<String, String> paraMap=new HashMap<String, String>();
					String begintime = null;
					String endtime = null;
					if("MANUAL".equals(runtype)) {
						begintime = taskMap.get("begintime");
						endtime = taskMap.get("endtime");
					}else {
						//开始时间
						begintime = DateUtils.getPreDateTime(new Date());
						//结束时间
						endtime = DateUtils.getCurDateTime(new Date());
					}

					//打标签统计
					int productNum =0;
					int sericeNum =0;
					int grouponNum =0;
					paraMap.put("begintime", begintime);
					paraMap.put("endtime", endtime);
					
					dfMassOrderService.updateActivityProductForDaily(paraMap);
					productNum += dfMassOrderService.updateActivityProductForMonthly(paraMap);
					dfMassOrderService.updateActivityProductForTotal(paraMap);
					dfMassOrderService.updateActivityServiceForDaily(paraMap);
					sericeNum += dfMassOrderService.updateActivityServiceForMonthly(paraMap);
					dfMassOrderService.updateActivityServiceForTotal(paraMap);
					dfMassOrderService.updateActivityGroupOnForDaily(paraMap);
					grouponNum += dfMassOrderService.updateActivityGroupOnForMonthly(paraMap);
					dfMassOrderService.updateActivityGroupOnForTotal(paraMap);
					
					logger.info("**********221订单标签order2_tag：商品类、服务类、团购类订单打标签任务调度结束,商品类："+productNum+
							",服务类："+sericeNum+",团购类："+grouponNum+"**********");
				} catch (Exception e) {
					logger.info("221订单标签order2_tag：商品类、服务类、团购类订单打标签任务调度异常：",e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}	
	
	
	/**
	 * 订单从价计算方法
	 */
	public BigDecimal calProfitByPrice(DfMassOrder dfMassOrder) {
		BigDecimal order_profit ;
		BigDecimal cost_price = dfMassOrder.getCost_price();
		String eshop_id = dfMassOrder.getEshop_id();
		Integer is_saleCard_eshop = dfMassOrderService.queryIsSaleCardEshop(eshop_id);
		//成本为0
		if(cost_price.compareTo(BigDecimal.ZERO)==0 && is_saleCard_eshop==0) {
			order_profit = new BigDecimal("0.00");
		}else {
			order_profit = dfMassOrder.getGmv_price().subtract(dfMassOrder.getCost_price());
		}
		return order_profit;
	}
	
	/**
	 * 订单从量计算方法
	 */
	public BigDecimal calProfitByVolume(DfMassOrder dfMassOrder,Contract contract) {
		BigDecimal order_profit = new BigDecimal("0.00");
		order_profit = new BigDecimal(dfMassOrder.getTotal_quantity()).multiply(contract.getContract_price());
		return order_profit;
	}
	
	/**
	 * 订单从率计算方法
	 */
	public BigDecimal calProfitByPercent(DfMassOrder dfMassOrder,Contract contract) {
		BigDecimal order_profit = new BigDecimal("0.00");
		order_profit = dfMassOrder.getGmv_price().multiply(contract.getContract_percent());
		return order_profit;
	}	
	
	/**
	 * 订单从价计算方法:进销存自营商品，从价计算
	 */
	public BigDecimal calProfitByPriceOfIms(DfMassOrder dfMassOrder) {
		BigDecimal order_profit ;
		order_profit = dfMassOrder.getGmv_price().subtract(dfMassOrder.getCost_price());
		return order_profit;
	}	
	
	/** 
	 * 计算每个订单所对应的优惠券、返利
	 * 调度规则：每天凌晨5点05分
	 */
	@Scheduled(cron ="0 05 5 * * ?")
	public void updateOrderCouponTask(){
		new Thread(){
			public void run() {
				try{
					logger.info("**********计算每个订单所对应的优惠券、返利信息任务调度开始**********");
					Integer updatenum =0;
					Integer groupCounum =0;
					Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(15);
					String isrun = taskMap.get("isrun");
					String begintime = null;
					String endtime = null;
					if("ON".equals(isrun)){
					String runtype = taskMap.get("runtype");
					//获取上次调度时的最大签收时间开始时间与结束时间
					if("MANUAL".equals(runtype)){
						begintime = taskMap.get("begintime");
						endtime = taskMap.get("endtime");
					}else{
						begintime = DateUtils.getPreDateTime(new Date());
						endtime = DateUtils.getCurDateTime(new Date());
					}	
					//给后台接口构建参数
					Map<String, String> paraMap=new HashMap<String, String>();
					paraMap.put("begintime", begintime);
					paraMap.put("endtime", endtime);
					
					List<DfMassOrder> massOrderList = dfMassOrderService.queryMassOrderListByDate(paraMap);
					if(!massOrderList.isEmpty()) {
			    		Map<String, String> runMap = new HashMap<String,String>();
			    		runMap.put("id", "15");
			    		runMap.put("task_status", "RUNNING");
			    		dsCronTaskService.updateTaskStatusById(runMap);
						OrderItemExtra orderItemExtra;
						
						for (DfMassOrder dfMassOrder : massOrderList) {
							String order_id = dfMassOrder.getId();
							
							paraMap.put("order_id", order_id);
							//1、根据订单号查询对应的优惠券、返利信息
							orderItemExtra = orderService.queryOrderRebateCouponById(paraMap);
							if(orderItemExtra!=null) {
								dfMassOrder.setApportion_rebate(orderItemExtra.getApportion_rebate());
								dfMassOrder.setApportion_coupon(orderItemExtra.getApportion_coupon());
								if(orderItemExtra.getApportion_coupon()!=null && dfMassOrder.getCct_proration_platform()!=null &&dfMassOrder.getCct_proration_seller()!=null) {
									dfMassOrder.setPlatform_price(orderItemExtra.getApportion_coupon().multiply(new BigDecimal(dfMassOrder.getCct_proration_platform())).divide(new BigDecimal("100")));
									dfMassOrder.setSeller_price(orderItemExtra.getApportion_coupon().multiply(new BigDecimal(dfMassOrder.getCct_proration_seller())).divide(new BigDecimal("100")));
								}
							}
							dfMassOrderService.updateOrderCouponOfDaily(dfMassOrder);
							updatenum += dfMassOrderService.updateOrderCouponOfMonthly(dfMassOrder);
							dfMassOrderService.updateOrderCouponOfTotal(dfMassOrder);
						}
						logger.info("**********计算组合优惠券订单逻辑开始***************");
						List<DfMassOrder> groupCouponOrderList = dfMassOrderService.queryGroupCouponOrderByDate(paraMap);
						if(!groupCouponOrderList.isEmpty()) {
							for (DfMassOrder groupCouponOrder : groupCouponOrderList) {
								groupCouponOrder.setApportion_coupon(groupCouponOrder.getApportion_coupon().divide(new BigDecimal(groupCouponOrder.getOrder_quantity().toString()),BigDecimal.ROUND_HALF_UP));
								if(groupCouponOrder.getApportion_coupon()!=null &&groupCouponOrder.getCct_proration_platform()!=null&&groupCouponOrder.getCct_proration_seller()!=null) {
									groupCouponOrder.setPlatform_price(groupCouponOrder.getApportion_coupon().multiply(new BigDecimal(groupCouponOrder.getCct_proration_platform().toString())).divide(new BigDecimal("100")));
									groupCouponOrder.setSeller_price(groupCouponOrder.getApportion_coupon().multiply(new BigDecimal(groupCouponOrder.getCct_proration_seller().toString())).divide(new BigDecimal("100")));
								}
								dfMassOrderService.updateOrderCouponOfDaily(groupCouponOrder);
								groupCounum += dfMassOrderService.updateOrderCouponOfMonthly(groupCouponOrder);
								dfMassOrderService.updateOrderCouponOfTotal(groupCouponOrder);
							}
						}
						
					}
		    		//设置任务为完成状态
		    		Map<String, String> doneMap = new HashMap<String,String>();
		    		doneMap.put("id", "15");
		    		doneMap.put("task_status", "DONE");
		    		dsCronTaskService.updateTaskStatusById(doneMap);
				}
				logger.info("**********计算每个订单所对应的优惠券、返利信息数据任务调度结束,开始时间："+begintime+",结束时间："+endtime+",共更新记录数："+updatenum+"**********,组合优惠券更新记录数："+groupCounum);
				} catch (Exception e) {
					logger.info("计算每个订单所对应的优惠券、返利信息数据任务调度异常：",e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}	
	
	/** 
	 * 当天利润完成后，对特殊订单进行利润重新计算
	 * 1、对过账支付订单进行利润清零
	 * 2、对使用优惠券且合同类型为从率且卖家有占比的订单利润，重新计算
	 * 调度规则：每天凌晨5点40分
	 */
	@Scheduled(cron ="0 40 5 * * ?")
	public void updateSpecialOrderProfitTask(){
		new Thread(){
			public void run() {
				try{
					logger.info("**********当天利润完成后，对特殊订单进行利润重新计算调度开始**********");
					Integer postingnum =0;
					Integer percentSellnum =0;
					Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(18);
					String isrun = taskMap.get("isrun");
					String begintime = null;
					String endtime = null;
					if("ON".equals(isrun)){
					String runtype = taskMap.get("runtype");
					//获取上次调度时的最大签收时间开始时间与结束时间
					if("MANUAL".equals(runtype)){
						begintime = taskMap.get("begintime");
						endtime = taskMap.get("endtime");
					}else{
						begintime = DateUtils.getPreDateTime(new Date());
						endtime = DateUtils.getCurDateTime(new Date());
					}	
		    		Map<String, String> runMap = new HashMap<String,String>();
		    		runMap.put("id", "18");
		    		runMap.put("task_status", "RUNNING");
		    		dsCronTaskService.updateTaskStatusById(runMap);
		    		
		    		//过账支付订单利润清0，对应标签设置2 
		    		String postingSql = "select distinct tor.id from daqweb.df_mass_order_monthly tor " + 
		    				"join gemini.t_order_receipts trec on (tor.group_id = trec.order_group_id) " + 
		    				"where trec.pay_platform ='posting'  and trec.pay_status='payed' " + 
		    				"and tor.sign_time >'"+begintime+"' and tor.sign_time<'"+endtime+"' ";
		    		
		    		List<Map<String,Object>> postingOrderList = ImpalaUtil.execute(postingSql);
		    		if(!postingOrderList.isEmpty()) {
		    			logger.info("**********过账支付订单利润计算开始***************");
			    		String post_id =null;
			    		for (Map<String, Object> postingOrderMap : postingOrderList) {
			    			DfMassOrder postingOrder = new DfMassOrder(); 
			    			post_id = postingOrderMap.get("id").toString();
			    			postingOrder.setId(post_id);
							dfMassOrderService.updatePostingOrderProfitOfDaily(postingOrder);
							postingnum += dfMassOrderService.updatePostingOrderProfitOfMonthly(postingOrder);
							dfMassOrderService.updatePostingOrderProfitOfTotal(postingOrder);
						}
						logger.info("**********过账支付订单利润计算结束***************");
		    		}
		    		
		    		//当优惠券，优惠除法，优惠占比完成后，从率卖家优惠占比的利润重新计算
		    		String percentSellSql = "select tor.id from daqweb.df_mass_order_monthly tor " + 
		    				"where tor.seller_price>0 and tor.contract_method ='percent' " + 
		    				"and tor.sign_time >'"+begintime+"' and tor.sign_time<'"+endtime+"' ";		    		
		    		
		    		List<Map<String,Object>> percentSellOrderList = ImpalaUtil.execute(percentSellSql);
		    		if(!percentSellOrderList.isEmpty()) {
		    			logger.info("**********优惠券商家有占比的从率订单利润计算开始***************");
		    			String percentSell_id =null;
		    			for (Map<String, Object> percentSellMap : percentSellOrderList) {
		    				DfMassOrder percentSellOrder = new DfMassOrder();
		    				percentSell_id = percentSellMap.get("id").toString();
		    				percentSellOrder.setId(percentSell_id);
							dfMassOrderService.updatePercentSellProfitOfDaily(percentSellOrder);
							percentSellnum += dfMassOrderService.updatePercentSellProfitOfMonthly(percentSellOrder);
							dfMassOrderService.updatePercentSellProfitOfTotal(percentSellOrder);		    				
						}
		    			
		    			logger.info("**********优惠券商家有占比的从率订单利润计算结束***************");
		    		}
					
		    		//设置任务为完成状态
		    		Map<String, String> doneMap = new HashMap<String,String>();
		    		doneMap.put("id", "18");
		    		doneMap.put("task_status", "DONE");
		    		dsCronTaskService.updateTaskStatusById(doneMap);
				}
				logger.info("**********当天利润完成后，对特殊订单进行利润重新计算调度结束,开始时间："+begintime+",结束时间："+endtime+",过账支付订单共更新记录数："+postingnum+"**********,商家占比的从率订单共更新记录数："+percentSellnum);
				} catch (Exception e) {
					logger.info("当天利润完成后，对特殊订单进行利润重新计算调度异常：",e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}

	/** 
	 * 对营销类订单打标签order_tag4
	 * 1、营销费用分类标签：A1优品试用A2生日券A3开卡礼
	 * 调度规则：每天凌晨6点0分
	 */
	@Scheduled(cron ="0 00 6 * * ?")
	public void updateMarktingTagTask(){
		new Thread(){
			public void run() {
				try{
					logger.info("**********order_tag4营销类打标签任务调度开始**********");
					Integer aUpdatenum =0;
					Integer a1Updatenum =0;
					Integer a2Updatenum =0;
					Integer a3Updatenum =0;
					Integer sumnum =0;
					Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(19);
					String isrun = taskMap.get("isrun");
					String begintime = null;
					String endtime = null;
					if("ON".equals(isrun)){
					String runtype = taskMap.get("runtype");
					//获取上次调度时的最大签收时间开始时间与结束时间
					if("MANUAL".equals(runtype)){
						begintime = taskMap.get("begintime");
						endtime = taskMap.get("endtime");
					}else{
						begintime = DateUtils.getPreDateTime(new Date());
						endtime = DateUtils.getCurDateTime(new Date());
					}	
		    		Map<String, String> runMap = new HashMap<String,String>();
		    		runMap.put("id", "19");
		    		runMap.put("task_status", "RUNNING");
		    		dsCronTaskService.updateTaskStatusById(runMap);
		    		
		    		//A类营销：运营管理中心有分摊占比产生的优惠券所使用的订单
		    		String aSql ="select tor.id from daqWeb.df_mass_order_total tor " + 
		    				"join gemini.t_order_group tog on (tor.group_id = tog.id) " + 
		    				"join gemini.t_card_coupon tcou on (tog.card_coupon_id = tcou.id)  " + 
		    				"join gemini.t_card_coupontype tcout on (tcout.id = tcou.type_id)  " + 
		    				"join gemini.t_card_coupontype_proration tcoutp on (tcoutp.coupontype_id = tcout.id and tcoutp.department_id = '8ad8fa9465a82aa50165b286c97c2ee9') "+
		    				"where tor.sign_time >'"+begintime+"' and tor.sign_time<'"+endtime+"' ";
		    		
		    		//A类营销：A1优品试用每月25的券
		    		String a1Sql = "select tor.id from daqweb.df_mass_order_total tor " + 
		    				"join gemini.t_order_group tog on (tor.group_id = tog.id) " + 
		    				"join gemini.t_card_coupon tcou on (tog.card_coupon_id = tcou.id and tcou.content_price=25 and tcou.emall_id ='c3edf6c77d483c6b67659f52e1f24990')  " + 
		    				"where tor.sign_time >'"+begintime+"' and tor.sign_time<'"+endtime+"' ";
		    		
		    		//A2生日券
		    		String a2Sql = "select tor.id from daqweb.df_mass_order_total tor " + 
		    				"join gemini.t_order_group tog on (tor.group_id = tog.id) " + 
		    				"join gemini.t_card_coupon tcou on (tog.card_coupon_id = tcou.id and tcou.emall_id ='1908fa85f681b7212c9b6deef8803d63')   " + 
		    				"where tor.sign_time >'"+begintime+"' and tor.sign_time<'"+endtime+"' ";
		    		
		    		
		    		//A3开卡礼199 金额199减199
		    		String a3Sql = "select tor.id from daqweb.df_mass_order_total tor " + 
		    				"join gemini.t_order_group tog on (tor.group_id = tog.id) " + 
		    				"join gemini.t_card_coupon tcou on (tog.card_coupon_id = tcou.id and tcou.emall_id ='edea35c2f1fb334184d53f4ca215f7a1')   " + 
		    				"where tor.sign_time >'"+begintime+"' and tor.sign_time<'"+endtime+"' ";
		    		
		    		List<Map<String,Object>> aOrderList = ImpalaUtil.execute(aSql);
		    		List<Map<String,Object>> a1OrderList = ImpalaUtil.execute(a1Sql);
		    		List<Map<String,Object>> a2OrderList = ImpalaUtil.execute(a2Sql);
		    		List<Map<String,Object>> a3OrderList = ImpalaUtil.execute(a3Sql);
		    		
		    		if(!aOrderList.isEmpty()) {
		    			logger.info("**********A类营销费用，总A标签开始***************");
			    		String a_id =null;
			    		for (Map<String, Object> aOrderMap : aOrderList) {
			    			DfMassOrder aOrder = new DfMassOrder(); 
			    			a_id = aOrderMap.get("id").toString();
			    			aOrder.setId(a_id);
			    			aOrder.setOrder_tag4("A");
							dfMassOrderService.updateMarktingTagOfDaily(aOrder);
							aUpdatenum += dfMassOrderService.updateMarktingTagOfMonthly(aOrder);
							dfMassOrderService.updateMarktingTagOfTotal(aOrder);
						}
						logger.info("**********A类营销费用，总A标签结束,共更新记录条数："+aUpdatenum+"***************");
		    		}
		    		
		    		
		    		if(!a1OrderList.isEmpty()) {
		    			logger.info("**********A类营销费用，A1标签开始***************");
			    		String a1_id =null;
			    		for (Map<String, Object> a1OrderMap : a1OrderList) {
			    			DfMassOrder a1Order = new DfMassOrder(); 
			    			a1_id = a1OrderMap.get("id").toString();
			    			a1Order.setId(a1_id);
			    			a1Order.setOrder_tag4("A1");
							dfMassOrderService.updateMarktingTagOfDaily(a1Order);
							a1Updatenum += dfMassOrderService.updateMarktingTagOfMonthly(a1Order);
							dfMassOrderService.updateMarktingTagOfTotal(a1Order);
						}
						logger.info("**********A类营销费用，A1标签结束,共更新记录条数："+a1Updatenum+"***************");
		    		}
		    		
		    		if(!a2OrderList.isEmpty()) {
		    			logger.info("**********A类营销费用，A2标签开始***************");
			    		String a2_id =null;
			    		for (Map<String, Object> a2OrderMap : a2OrderList) {
			    			DfMassOrder a2Order = new DfMassOrder(); 
			    			a2_id = a2OrderMap.get("id").toString();
			    			a2Order.setId(a2_id);
			    			a2Order.setOrder_tag4("A2");
							dfMassOrderService.updateMarktingTagOfDaily(a2Order);
							a2Updatenum += dfMassOrderService.updateMarktingTagOfMonthly(a2Order);
							dfMassOrderService.updateMarktingTagOfTotal(a2Order);
						}
						logger.info("**********A类营销费用，A2标签结束,共更新记录条数："+a2Updatenum+"***************");		    			
		    		}

		    		if(!a3OrderList.isEmpty()) {
		    			logger.info("**********A类营销费用，A3标签开始***************");
			    		String a3_id =null;
			    		for (Map<String, Object> a3OrderMap : a3OrderList) {
			    			DfMassOrder a3Order = new DfMassOrder(); 
			    			a3_id = a3OrderMap.get("id").toString();
			    			a3Order.setId(a3_id);
			    			a3Order.setOrder_tag4("A3");
							dfMassOrderService.updateMarktingTagOfDaily(a3Order);
							a3Updatenum += dfMassOrderService.updateMarktingTagOfMonthly(a3Order);
							dfMassOrderService.updateMarktingTagOfTotal(a3Order);
						}
						logger.info("**********A类营销费用，A3标签结束,共更新记录条数："+a3Updatenum+"***************");		    			
		    		}		    		
		    		sumnum = a1Updatenum+a2Updatenum+a3Updatenum;
		    		//设置任务为完成状态
		    		Map<String, String> doneMap = new HashMap<String,String>();
		    		doneMap.put("id", "19");
		    		doneMap.put("task_status", "DONE");
		    		dsCronTaskService.updateTaskStatusById(doneMap);
				}
				logger.info("**********order_tag4营销类打标签任务调度结束,开始时间："+begintime+",结束时间："+endtime+"**********,共更新记录条数："+sumnum);
				} catch (Exception e) {
					logger.info("order_tag4营销类打标签任务调度：",e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * 更新订单首单信息、销售毛利、交叉毛利、优易补贴(…)和成功时间
	 * 调度规则：每天凌晨6点30
	 */
	@Scheduled(cron = "0 30 6 * * ?")
	public void updateOrderSaleProfitAndSuccesstimeTask() {
		new Thread() {
			public void run() {
				try {
					System.out.println("开始");
					Integer updatenum = 0;
					Integer successnum = 0;
					String begintime = null;
					String endtime = null;
					Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(20);
					String isrun = taskMap.get("isrun");
					if("ON".equals(isrun)){
						String runtype = taskMap.get("runtype");
						//获取上次调度时的最大签收时间开始时间与结束时间
						if("MANUAL".equals(runtype)){
							begintime = taskMap.get("begintime");
							endtime = taskMap.get("endtime");
						}else{
							begintime = DateUtils.getPreDateTime(new Date());
							endtime = DateUtils.getCurDateTime(new Date());
						}	
			    		Map<String, String> runMap = new HashMap<String,String>();
			    		runMap.put("id", "20");
			    		runMap.put("task_status", "RUNNING");
			    		dsCronTaskService.updateTaskStatusById(runMap);

						Map<String, String> paraMap = new HashMap<String, String>();
						paraMap.put("begintime", begintime);
						paraMap.put("endtime", endtime);
//						List<DfMassOrder> massOrderList = dfMassOrderService.queryMassOrderListByAll(paraMap);


						String sql = "select mom.id, mom.order_profit, mom.platform_price, mom.order_tag4, mom.bussiness_group_id"
							+ ", ufos.channel_id as first_order_channel"
							+ ", case when locate('A', mom.order_tag4) > 0 then mom.order_profit else mom.order_profit - ifnull(mom.platform_price, 0) end"
								+ " as sale_profit"
							+ ", case when locate('A', ifnull(mom.order_tag4, '')) = 0 and mom.bussiness_group_id = '8ac28b935fed0bc8015fed4c76f60018'"
								+ " then if(mom.order_profit - ifnull(mom.platform_price, 0) < 0, - (mom.order_profit - ifnull(mom.platform_price, 0)), null)"
								+ " else null end as gayy_subsidy"
							+ " from daqweb.df_mass_order_monthly as mom"
							+ " left join gabase.b_user_first_order_store as ufos on mom.customer_id = ufos.customer_id and mom.real_store_id = ufos.store_id"
							+ " where (mom.sign_time >= '" + begintime + "' and mom.sign_time <= '" + endtime + "')"
							+ " or mom.first_order_channel is null or (mom.order_profit is not null and mom.sale_profit is null);";

						List<Map<String, Object>> orderList = ImpalaUtil.execute(sql);

//						dfMassOrder.setId(orderMap.get("id").toString());
//						if(!massOrderList.isEmpty()) {
						if (!orderList.isEmpty()) {
							logger.info("**********更新订单首单信息、销售毛利、优易补贴(…)和成功时间开始***************");
							for (Map<String, Object> orderMap : orderList) {
//							for (DfMassOrder dfMassOrder : massOrderList) {
								DfMassOrder dfMassOrder = new DfMassOrder();
								dfMassOrder.setId(orderMap.get("id").toString());
								if (orderMap.get("first_order_channel") != null) {
									dfMassOrder.setFirst_order_channel(orderMap.get("first_order_channel").toString());
								}
								if (orderMap.get("sale_profit") != null) {
									dfMassOrder.setSale_profit(new BigDecimal(orderMap.get("sale_profit").toString()));
								}
								if (orderMap.get("gayy_subsidy") != null) {
									dfMassOrder.setGayy_subsidy(new BigDecimal(orderMap.get("gayy_subsidy").toString()));
								}

								if(dfMassOrder.getSale_profit() != null && dfMassOrder.getSale_profit().compareTo(BigDecimal.ZERO) > 0 && dfMassOrder.getFirst_order_channel() != null && !dfMassOrder.getFirst_order_channel().equals("")) {
									dfMassOrder.setThis_channel_profit(dfMassOrder.getSale_profit().multiply(new BigDecimal(taskMap.get("this_channel_scale"))));
									dfMassOrder.setFirst_channel_profit(dfMassOrder.getSale_profit().multiply(new BigDecimal(taskMap.get("first_channel_scale"))));
								} else {
									dfMassOrder.setThis_channel_profit(dfMassOrder.getSale_profit());
								}
								updatenum += dfMassOrderService.updateSaleProfitOfDaily(dfMassOrder);
								dfMassOrderService.updateSaleProfitOfMonthly(dfMassOrder);
								dfMassOrderService.updateSaleProfitDailyOfTotal(dfMassOrder);
							}
							logger.info("**********更新订单销售毛利、优易补贴(…)和成功时间结束***************" + begintime + ",结束时间：" + endtime + ",销售毛利订单共更新记录数：" + updatenum);
						}

						// 成功时间为空的数据
						sql = "select mom.id, min(of.create_time) as success_time from daqweb.df_mass_order_monthly as mom"
								+ " left join gemini.t_order_flow as of on mom.id = of.order_id and of.order_status = 'success'"
								+ " where (mom.success_time is null or unix_timestamp(mom.sign_time) > unix_timestamp(mom.success_time))"
								+ " and of.create_time is not null group by mom.id";

						List<Map<String, Object>> unSuccessList = ImpalaUtil.execute(sql);
						if (!unSuccessList.isEmpty()) {
							logger.info("**********订单成功时间更新计算开始***************");
							String id = null;
							String success_time = null;
							for (Map<String, Object> unSuccessMap : unSuccessList) {
								DfMassOrder unSuccessOrder = new DfMassOrder();
								id = unSuccessMap.get("id").toString();
								if(unSuccessMap.get("success_time") != null) {
									success_time = unSuccessMap.get("success_time").toString();
								}
								unSuccessOrder.setId(id);
								unSuccessOrder.setSuccess_time(success_time);
								dfMassOrderService.updateUnSuccessOfDaily(unSuccessOrder);
								successnum += dfMassOrderService.updateUnSuccessOfMonthly(unSuccessOrder);
								dfMassOrderService.updateUnSuccessOfTotal(unSuccessOrder);
							}
							logger.info("**********订单成功时间更新计算结束***************"+ begintime + ",结束时间：" + endtime + ",成功订单共更新记录数：" + successnum);
						}

			    		//设置任务为完成状态
			    		Map<String, String> doneMap = new HashMap<String,String>();
			    		doneMap.put("id", "20");
			    		doneMap.put("task_status", "DONE");
			    		dsCronTaskService.updateTaskStatusById(doneMap);
					}
				} catch (Exception e) {
					logger.info("更新订单首单信息、销售毛利、优易补贴(…)和成功时间：", e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}
}
