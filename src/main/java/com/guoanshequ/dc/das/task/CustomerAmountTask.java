package com.guoanshequ.dc.das.task;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guoanshequ.dc.das.model.DFOrderRealtime;
import com.guoanshequ.dc.das.model.DfCustomerAmount;
import com.guoanshequ.dc.das.service.CustomerAmountService;
import com.guoanshequ.dc.das.service.DfCustomerAmountService;
import com.guoanshequ.dc.das.service.DfOrderDailyService;
import com.guoanshequ.dc.das.utils.DateUtils;

@Component
public class CustomerAmountTask {
    
    @Autowired
    CustomerAmountService customerAmountService;
    @Autowired
    DfCustomerAmountService dfCustomerAmountService;
    @Autowired
    DfOrderDailyService dfOrderDailyService;
    
    private static final Logger logger = LogManager.getLogger(ScheduleTask.class);
	
   //@Scheduled(cron ="0 35 17 30 * ?")
   public void DfCustomerAmountTask(){
	  try{ 
		  logger.info("**************已存在订单清洗开始************");
		  List<Map<String, String>> mapParamList = customerAmountService.queryCustomerAmount();
		  if(mapParamList!=null){
			  long beginTime = System.currentTimeMillis();
			  for (Map<String, String> customerAmountMap : mapParamList) {
				  dfCustomerAmountService.addCustomerAmount(customerAmountMap);
			  }
			  long endTime = System.currentTimeMillis();
		      System.out.println("插入完成，耗时 " + (endTime - beginTime) + " 毫秒！"); 
		  }
		  logger.info("*************已存在订单清洗结束****************");
	  } catch (Exception e){
		  e.printStackTrace();
	  }
   }
   
   //@Scheduled(cron ="0 11 16 * * ?")
   public void DfCustomerAmountDailyTask(){
	   try{
		  logger.info("**************每日订单清洗开始***************");
		  String yearandmonth = DateUtils.getPreYearAndMonth(new Date());
		  List<DFOrderRealtime> dfOrdersDailyList = dfOrderDailyService.queryOrdersDaily();
		  if(dfOrdersDailyList != null && dfOrdersDailyList.size() > 0){
			  for (int i = 0; i < dfOrdersDailyList.size(); i++) {
				  DFOrderRealtime dfOrderDaily = dfOrdersDailyList.get(i);
				  String customer_id = dfOrderDaily.getCustomer_id();
				  String store_id = dfOrderDaily.getStore_id();
				  String order_id = dfOrderDaily.getOrder_id();
				  BigDecimal trading_price = dfOrderDaily.getTrading_price();
				  DfCustomerAmount dfCustomerAmount = new DfCustomerAmount();
				  dfCustomerAmount.setCustomer_id(customer_id);
				  dfCustomerAmount.setStore_id(store_id);
				  dfCustomerAmount.setYearandmonth(yearandmonth);
				  DfCustomerAmount customerAmount = dfCustomerAmountService.queryByCustomerIdAndStoreIdAndYearAndMonth(dfCustomerAmount);
				  if (customerAmount != null){
					  String order_id_ = customerAmount.getOrder_id();
					  BigDecimal total_price = customerAmount.getTotal_price();
					  StringBuilder sb = new StringBuilder();
					  sb.append(order_id_);
					  sb.append(",");
					  sb.append(order_id);
					  customerAmount.setOrder_id(sb.toString());
					  customerAmount.setTotal_price(total_price.add(trading_price));
					  dfCustomerAmountService.updateCustomerAmount(customerAmount);
				  } else {
					  String city_name = dfOrderDaily.getCity_name();
					  dfCustomerAmount.setCity_name(city_name);
					  dfCustomerAmount.setOrder_id(order_id);
					  dfCustomerAmount.setTotal_price(trading_price);
					  dfCustomerAmountService.addCustomerAmount(dfCustomerAmount);
				  }
				  
			  }
		  }
		  logger.info("**************每日订单清洗结束***************");
	   } catch (Exception e){
		   e.printStackTrace();
	   }
   }

}
