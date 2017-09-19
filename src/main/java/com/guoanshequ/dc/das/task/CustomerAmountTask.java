package com.guoanshequ.dc.das.task;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guoanshequ.dc.das.model.DFOrderRealtime;
import com.guoanshequ.dc.das.model.DfCustomerMonth;
import com.guoanshequ.dc.das.model.DfValidCustomer;
import com.guoanshequ.dc.das.service.CustomerAmountService;
import com.guoanshequ.dc.das.service.DfCustomerMonthService;
import com.guoanshequ.dc.das.service.DfCustomerStoreService;
import com.guoanshequ.dc.das.service.DfOrderDailyService;
import com.guoanshequ.dc.das.service.DfValidCustomerService;
import com.guoanshequ.dc.das.utils.DateUtils;

@Component
public class CustomerAmountTask {

	@Autowired
	CustomerAmountService customerAmountService;
	@Autowired
	DfCustomerMonthService dfCustomerMonthService;
	@Autowired
	DfOrderDailyService dfOrderDailyService;
	@Autowired
	DfValidCustomerService dfValidCustomerService;
	@Autowired
	DfCustomerStoreService dfCustomerStoreService;

	private static final Logger logger = LogManager.getLogger(ScheduleTask.class);
	
	/**
	 * 按天进行任务调度，用户分析属于业务数据处理，任务时间应放在daily完成之后，保证前一天所有数据
	 * 全部写入到daily表后，再取出相应数据进行操作；
	 * 每天凌晨的第55分钟，cron ="0 55 0 * * ?"
	 */
	@Scheduled(cron ="0 55 0 * * ?")
	public void dfValidCustomerTask() {
    	new Thread(){
    		public void run() {
    	    	try {
			logger.info("*****************用户分析调度开始****************");
			List<DFOrderRealtime> dfOrderDailyList = dfOrderDailyService.queryOrdersDaily();
			if (dfOrderDailyList != null && dfOrderDailyList.size() > 0) {
				for (int i = 0; i < dfOrderDailyList.size(); i++) {
					BigDecimal dailyTradingPrice =new BigDecimal(0);
					DFOrderRealtime dFOrderDaily = dfOrderDailyList.get(i);
					DfCustomerMonth dfCustomerMonth = new DfCustomerMonth();
					dfCustomerMonth.setCustomer_id(dFOrderDaily.getCustomer_id());
					dfCustomerMonth.setStore_id(dFOrderDaily.getStore_id());
					dfCustomerMonth.setStore_code(dFOrderDaily.getStoreno());
					dfCustomerMonth.setNew_place(dFOrderDaily.getAddr_placename());
					dfCustomerMonth.setYearmonth(DateUtils.getPreYearAndMonth(new Date()));
					if(dFOrderDaily.getTrading_price()!=null){
						dailyTradingPrice = dFOrderDaily.getTrading_price();
					}
					//构建查询/新增的传递参数
					Map<String, String> mapParam = new HashMap<String, String>();
					mapParam.put("customer_id", dFOrderDaily.getCustomer_id());
					mapParam.put("store_id", dFOrderDaily.getStore_id());
					mapParam.put("store_code", dFOrderDaily.getStoreno());
					mapParam.put("customer_phone",dFOrderDaily.getTc_mobilephone());

					DfValidCustomer dfValidCustomer = dfValidCustomerService.queryByCustomerId(dFOrderDaily.getCustomer_id());
					//有效用户中是否存在当天客户id，不存在则直接进行添加
					if (dfValidCustomer != null) {//若存在，则进行总金额、总消费次数的累加

						BigDecimal total_price = dfValidCustomer.getTotal_price().add(dailyTradingPrice);
						// 修改有效用户表中相应信息，进行更新
						dfValidCustomer.setTotal_price(total_price);
						dfValidCustomer.setLast_order_time(dFOrderDaily.getSigned_time());
						dfValidCustomer.setTotal_ordercount(dfValidCustomer.getTotal_ordercount() + 1);
						dfValidCustomerService.updateValidCustomer(dfValidCustomer);
						// 去用户门店表里查询是否存在此用户id和门店id，表明此用户是否在此门店下过订单
						List<Map<String, String>> mapList = dfCustomerStoreService.queryByCustomerIdAndStoreId(mapParam);
						if (mapList != null && mapList.size() > 0) {//若在此门店有过订单，则进行月份表中进行查询
							// 关联查询出的id，按时间倒序，第一条
							DfCustomerMonth dfCustomerMonth_update = dfCustomerMonthService.queryByCustomerIdAndStoreId(mapParam);
							if (dfCustomerMonth_update != null) {
								String yearandmonth = dfCustomerMonth_update.getYearmonth();
								//最新记录为本月订单，则进行金额累加
								if (yearandmonth.equals(DateUtils.getPreYearAndMonth(new Date()))) {
									BigDecimal month_total_price = dfCustomerMonth_update.getTrading_price();
									dfCustomerMonth_update.setTrading_price(dailyTradingPrice.add(month_total_price));
									dfCustomerMonthService.updateCustomerMonth(dfCustomerMonth_update);
								//最新记录为上月订单，则说明为复购客户，进行金额累加，复购标志位
								} else if (yearandmonth.equals(DateUtils.getPreMonthofYear())) {
									dfCustomerMonth.setTrading_price(dailyTradingPrice);
									dfCustomerMonth.setNew_flag(0);
									dfCustomerMonth.setRebuy_flag(1);
									dfCustomerMonthService.addCustomerMonth(dfCustomerMonth);
								//非本月上月订单
								} else {
									dfCustomerMonth.setTrading_price(dailyTradingPrice);
									dfCustomerMonth.setNew_flag(0);
									dfCustomerMonth.setRebuy_flag(0);
									dfCustomerMonthService.addCustomerMonth(dfCustomerMonth);
								}
							} else {
								logger.info("***********出错咯，我的信息怎么不见了，我的customer_id 是：" + dFOrderDaily.getCustomer_id()
										+ "我的store_code 是" + dFOrderDaily.getStoreno() + "!快帮我查查************");
							}
						} else {//若在此门店有过订单，则进行月份表中进行查询，则为新增用户
							// 添加用户门店
							dfCustomerStoreService.addCustomerStore(mapParam);
							// 添加标记位（新增1，复购0）
							dfCustomerMonth.setTrading_price(dailyTradingPrice);
							dfCustomerMonth.setNew_flag(1);
							dfCustomerMonth.setRebuy_flag(0);
							dfCustomerMonthService.addCustomerMonth(dfCustomerMonth);
						}
					} else {
						DfValidCustomer dfValidCustomer_ = new DfValidCustomer();
						
						dfValidCustomer_.setCustomer_id(dFOrderDaily.getCustomer_id());
						dfValidCustomer_.setCustomer_phone(dFOrderDaily.getTc_mobilephone());
						dfValidCustomer_.setFirst_order_time(dFOrderDaily.getSigned_time());
						dfValidCustomer_.setLast_order_time(dFOrderDaily.getSigned_time());
						dfValidCustomer_.setTotal_ordercount(1);
						dfValidCustomer_.setTotal_price(dailyTradingPrice);
						// 添加有效用户
						dfValidCustomerService.addValidCustomer(dfValidCustomer_);
						// 添加用户门店
						dfCustomerStoreService.addCustomerStore(mapParam);
						// 添加标记位（新增1，复购0）
						dfCustomerMonth.setTrading_price(dailyTradingPrice);
						dfCustomerMonth.setNew_flag(1);
						dfCustomerMonth.setRebuy_flag(0);
						dfCustomerMonthService.addCustomerMonth(dfCustomerMonth);
					}
				}
			}
			logger.info("*****************用户分析调度结束****************");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}.start();
}
}
