package com.guoanshequ.dc.das.task;

import com.guoanshequ.dc.das.model.DfMassOrder;
import com.guoanshequ.dc.das.model.TinyDispatch;
import com.guoanshequ.dc.das.service.*;
import com.guoanshequ.dc.das.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

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


	private static final Logger logger = LogManager.getLogger(MassOrderScheduleTask.class);
	
	/**
	 * 调度规则：根据情况1分钟调度一次 
	 * 参数：maxSignTime
	 */
    @Scheduled(cron ="0 */1 * * * ?")
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
					logger.info("**********massorder打退货标签任务调度开始 **********");
					//获取上次调度时的最大退货时间
					String maxReturnTime = dfMassOrderService.queryMaxReturnTime()==null?DateUtils.getPreDate(new Date()):dfMassOrderService.queryMaxReturnTime();
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
    				logger.info("**********massorder打异常订单标签任务调度开始**********");
	    			String queryTime = DateUtils.getPreDate(new Date());
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
					logger.info("**********massorder打小B端订单标签任务调度开始**********");
					
					//给后台接口构建参数
					Map<String, String> paraMap=new HashMap<String, String>();
					//开始时间
					String begintime = DateUtils.getPreDateTime(new Date());
					//结束时间
					String endtime = DateUtils.getCurDateTime(new Date());
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
					logger.info("**********df_userprofile_tag打小B端订单标签任务调度开始**********");
					
					//给后台接口构建参数
					Map<String, String> paraMap=new HashMap<String, String>();
					//开始时间
					String begintime = DateUtils.getPreDateTime(new Date());
					//结束时间
					String endtime = DateUtils.getCurDateTime(new Date());
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
					logger.info("**********df_userprofile_tag打合作社社员标签任务调度开始**********");
					
					//给后台接口构建参数
					Map<String, String> paraMap=new HashMap<String, String>();
					//开始时间
					String begintime = DateUtils.getPreDateTime(new Date());
					//结束时间
					String endtime = DateUtils.getCurDateTime(new Date());
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
					logger.info("**********mass_order表中order_tag会员订单标签任务调度开始**********");
					
					//给后台接口构建参数
					Map<String, String> paraMap=new HashMap<String, String>();
					//开始时间
					String begintime = DateUtils.getPreDateTime(new Date());
					//结束时间
					String endtime = DateUtils.getCurDateTime(new Date());
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
	 * 221订单标签order2_tag：商品类、服务类、团购类订单打标签
	 * 调度规则：每天凌晨2点01分 
	 */
	@Scheduled(cron ="0 01 2 * * ?")
	public void activityOrder2TagTask(){
		new Thread(){
			public void run() {
				try{
					logger.info("**********221订单标签order2_tag：商品类、服务类、团购类订单打标签任务调度开始**********");
					
					//给后台接口构建参数
					Map<String, String> paraMap=new HashMap<String, String>();
					//开始时间
					String begintime = DateUtils.getPreDateTime(new Date());
					//结束时间
					String endtime = DateUtils.getCurDateTime(new Date());
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
	
}
