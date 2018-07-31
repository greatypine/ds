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
* @author CaoPs
* @date 2018年01月23日
* @version 1.0
 */
@Component
public class PesNewScheduleTask {
    @Autowired
    TStoreTradeService tstoreTradeService;
	@Autowired
	TNewaddCusService tnewaddCusService;
	@Autowired
	EmployeeTradeService employeeTradeService;
	@Autowired
	TSendOrderService tSendOrderService;
	@Autowired
	DepartmentBizService departmentBizService; 
	@Autowired
	DsPesCustomerStoreMonthService dsPesCustomerMonthService;
	@Autowired
	DsPesCustomerEmployeeMonthService dsPesCustomerEmployeeMonthService;
	@Autowired
	DsPesGmvActivityGmvService dsPesGmvActivityGmvService;
    
    private static final Logger logger = LogManager.getLogger(PesNewScheduleTask.class);
    
    /**
     * 门店交易额任务调度
     * 参数：begindate  enddate  storename  storeids
     */
    @Scheduled(cron ="0 10 02 * * ?")
    public void storeTradesByMassOrderTask() {
    	new Thread(){
    		public void run(){
    	    	try {
    	        	logger.info("**********门店交易额任务调度开始**********");
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
					tstoreTradeService.deleteByYearMonth(paraMap);
					int num = tstoreTradeService.addTStoreTradesByMassOrder(paraMap);
    	    		logger.info("**********门店交易额任务调度结束**********");
    	    		logger.info("共调度数据记录行数："+num);
    	    		} catch (Exception e) {
    	    			logger.info(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }

	/**
	 * 国安侠gmv
	 * 参数：begindate  enddate  storename  storeids
	 */
	@Scheduled(cron ="0 10 02 * * ?")
	public void empTradesByMassOrderTask() {
		new Thread(){
			public void run(){
				try {
					logger.info("**********国安侠GMV任务调度开始**********");
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
					employeeTradeService.deleteByYearMonth(paraMap);
					int num = employeeTradeService.addEmployeeTradeByMassOrder(paraMap);
					logger.info("**********国安侠GMV任务调度结束**********");
					logger.info("共调度数据记录行数："+num);
				} catch (Exception e) {
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	
	
	
	/**
	 * 由于公海与异常订单都存在流程审批考核，绩效数据于3号进行关闭重新计算 
	 * 上月门店绩效GMV
	 * 参数：begindate  enddate  storename  storeids
	 */
	@Scheduled(cron ="0 0 03 3 * ?")
	public void preStoreTradesByMassOrderTask() {
		new Thread(){
			public void run(){
				try {
					logger.info("**********上月门店GMV任务调度开始**********");
    	    		//得到上月的月初月末日期
    	    		Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
    	    		//上月月初日期
    	    		String begindate = datemap.get("first");
    	    		//上月月末日期
    	    		String enddate = datemap.get("last");
    	    		//取得上月年份
    	    		String year = begindate.substring(0, 4);
    	    		//取得上月月份
    	    		String month = begindate.substring(5, 7);
					//给后台接口构建参数
					Map<String, String> paraMap=new HashMap<String, String>();
					paraMap.put("year", year);
					paraMap.put("month", month);
					paraMap.put("begindate", begindate);
					paraMap.put("enddate", enddate);
					tstoreTradeService.deleteByYearMonth(paraMap);
					int num = tstoreTradeService.addPreTStoreTradesByMassOrder(paraMap);
					logger.info("**********上月门店GMV任务调度结束**********共调度数据记录行数："+num);
				} catch (Exception e) {
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	
	/**
	 * 由于公海与异常订单都存在流程审批考核，绩效数据于3号进行关闭重新计算 
	 * 上月国安侠GMV
	 * 参数：begindate  enddate  storename  storeids
	 */
	@Scheduled(cron ="0 0 03 3 * ?")
	public void preEmpTradesByMassOrderTask() {
		new Thread(){
			public void run(){
				try {
					logger.info("**********上月国安侠GMV任务调度开始**********");
    	    		//得到上月的月初月末日期
    	    		Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
    	    		//上月月初日期
    	    		String begindate = datemap.get("first");
    	    		//上月月末日期
    	    		String enddate = datemap.get("last");
    	    		//取得上月年份
    	    		String year = begindate.substring(0, 4);
    	    		//取得上月月份
    	    		String month = begindate.substring(5, 7);
					//给后台接口构建参数
					Map<String, String> paraMap=new HashMap<String, String>();
					paraMap.put("year", year);
					paraMap.put("month", month);
					paraMap.put("begindate", begindate);
					paraMap.put("enddate", enddate);
					employeeTradeService.deleteByYearMonth(paraMap);
					int num = employeeTradeService.addPreEmployeeTradeByMassOrder(paraMap);
					logger.info("**********上月国安侠GMV任务调度结束**********共调度数据记录行数："+num);
				} catch (Exception e) {
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	/**
	 * 国安侠送单量
	 * 参数：begindate  enddate  storename  storeids
	 */
	@Scheduled(cron ="0 20 02 * * ?")
	public void empSendOrdersByMassOrderTask() {
		new Thread(){
			public void run(){
				try {
					logger.info("**********国安侠送单量（按频道）任务调度开始**********");
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
					tSendOrderService.deleteByYearMonth(paraMap);
					int num = tSendOrderService.addTSendOrdersByMassOrder(paraMap);
					logger.info("**********国安侠送单量（按频道）任务调度结束**********");
					logger.info("共调度数据记录行数："+num);
				} catch (Exception e) {
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}	
	
	/**
	 * 事业群GMV
	 * 参数：begindate  enddate  storename  storeids
	 * 调度时间：
	 */
	@Scheduled(cron ="0 20 02 * * ?")
	public void DeptGmvTask() {
		new Thread(){
			public void run(){
				try {
					logger.info("**********事业群GMV任务调度开始**********");
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
					departmentBizService.deleteDeptGmvByYearMonth(paraMap);
					int num = departmentBizService.addDeptGmvByMassOrder(paraMap);
					logger.info("**********事业群GMV任务调度结束**********");
					logger.info("共调度数据记录行数："+num);
				} catch (Exception e) {
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}	
	
	/**
	 * 事业群Cus
	 * 参数：begindate  enddate  storename  storeids
	 * 调度时间：
	 */
	@Scheduled(cron ="0 20 02 * * ?")
	public void DeptCusTask() {
		new Thread(){
			public void run(){
				try {
					logger.info("**********事业群消费用户数任务调度开始**********");
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
					departmentBizService.deleteDeptCusByYearMonth(paraMap);
					int num = departmentBizService.addDeptCusByMassOrder(paraMap);
					logger.info("**********事业群消费用户数任务调度结束**********");
					logger.info("共调度数据记录行数："+num);
				} catch (Exception e) {
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	
	/**
	 * 
	* @Title: storeCustomerTask  
	* @Description: TODO 门店用户调度任务
	* 2018年4月16日
	* @param       
	* @return void 
	* @throws
	* @author gbl
	 */
	@Scheduled(cron ="0 25 02 * * ?")
	public void storeCustomerTask() {
		new Thread(){
			public void run(){
				try {
					logger.info("**********门店消费用户数任务调度开始**********");
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
					dsPesCustomerMonthService.deleteDsPesCustomer(paraMap);
					int num = dsPesCustomerMonthService.addDsPesCustomer(paraMap);
					logger.info("**********门店消费用户数任务调度结束**********");
					logger.info("共调度数据记录行数："+num);
				} catch (Exception e) {
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	
	/**
	 * 
	* @Title: employeeCustomerTask  
	* @Description: TODO 国安侠用户调度任务
	* 2018年4月16日
	* @param       
	* @return void 
	* @throws
	 */
	@Scheduled(cron ="0 25 02 * * ?")
	public void employeeCustomerTask() {
		new Thread(){
			public void run(){
				try {
					logger.info("**********国安侠消费用户数任务调度开始**********");
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
					dsPesCustomerEmployeeMonthService.deleteDsPesCustomer(paraMap);
					int num = dsPesCustomerEmployeeMonthService.addDsPesCustomer(paraMap);
					logger.info("**********国安侠消费用户数任务调度结束**********");
					logger.info("共调度数据记录行数："+num);
				} catch (Exception e) {
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	
    /**
     * 活动类221门店绩效GMV统计
     * 参数：begindate  enddate  storename  storeids
     */
    @Scheduled(cron ="0 45 03 * * ?")
    public void pesGmvActivityStoreByMassOrderTask() {
    	new Thread(){
    		public void run(){
    	    	try {
    	        	logger.info("**********活动类221门店绩效GMV统计任务调度开始**********");
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
    	        	paraMap.put("tableName", "ds_pes_gmv_activity_store_month");
					dsPesGmvActivityGmvService.deleteByYearMonth(paraMap);
					int num = dsPesGmvActivityGmvService.addDsPesGmvActivityStoreMonthByMassOrder(paraMap);
    	    		logger.info("**********活动类221门店绩效GMV统计任务调度结束**********");
    	    		logger.info("共调度数据记录行数："+num);
    	    		} catch (Exception e) {
    	    			logger.info(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }	
    
    /**
     * 活动类221国安侠绩效GMV统计
     * 参数：begindate  enddate  storename  storeids
     */
    @Scheduled(cron ="0 50 03 * * ?")
    public void pesGmvActivityEmpByMassOrderTask() {
    	new Thread(){
    		public void run(){
    	    	try {
    	        	logger.info("**********活动类221国安侠绩效GMV统计任务调度开始**********");
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
    	        	paraMap.put("tableName", "ds_pes_gmv_activity_emp_month");
					dsPesGmvActivityGmvService.deleteByYearMonth(paraMap);
					int num = dsPesGmvActivityGmvService.addDsPesGmvActivityEmpMonthByMassOrder(paraMap);
    	    		logger.info("**********活动类221国安侠绩效GMV统计任务调度结束**********");
    	    		logger.info("共调度数据记录行数："+num);
    	    		} catch (Exception e) {
    	    			logger.info(e.toString());
    	    			e.printStackTrace();
    	    		}
    		}
    	}.start();
    }	
}
