package com.guoanshequ.dc.das.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guoanshequ.dc.das.service.DsCronTaskService;
import com.guoanshequ.dc.das.service.ProductSalesService;
import com.guoanshequ.dc.das.service.TProductSalesService;
import com.guoanshequ.dc.das.utils.DateUtils;

/**
 * @Function：产品销量统计（支持城市、门店、E店、事业群、频道）
 * @author：chenchuang
 * @date:2018年6月29日
 *
 * @version V1.0
 */
@Component
public class ProductSalesTask {

	@Autowired
	DsCronTaskService dsCronTaskService;
	@Autowired
	ProductSalesService productSalesService;
	@Autowired
	TProductSalesService tProductSalesService;
	
	
	private static final Logger logger = LogManager.getLogger(ProductSalesTask.class);
	
	
	/**
	 * 定时用于统计ds_ope_product_store_day表
	 * 调度规则：每天凌晨3点40分
	 */
	@Scheduled(cron ="0 40 3 * * ?")
	public void productSalesTask(){
		new Thread(){
			public void run() {
				try{
					logger.info("**********ds_ope_product_store_day任务调度开始**********");
					Map<String, String> taskMap = dsCronTaskService.queryDsCronTaskById(13);
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
						
						Map<String, String> tempMap=new HashMap<String, String>();
						List<Map<String, String>> list =productSalesService.queryProductDay(paraMap);
						for (Map<String, String> map : list) {
							
							paraMap.put("recdate", map.get("recdate"));
							paraMap.put("store_id", map.get("store_id"));
							paraMap.put("product_id", map.get("product_id"));
							
							Map<String, String> tProductSale = tProductSalesService.queryTProductIsExist(paraMap);
							paraMap.put("product_name", map.get("product_name"));
							paraMap.put("product_type", map.get("product_type"));
							paraMap.put("eshop_id", map.get("eshop_id"));
							paraMap.put("pcount", String.valueOf(map.get("pcount")));
							paraMap.put("pquantity", String.valueOf(map.get("pquantity")));
							paraMap.put("ims_master_id", map.get("ims_master_id"));
							
							if (tProductSale == null) {
								Map<String, String> productStore = productSalesService.queryProductStore(paraMap);
								paraMap.put("store_code", productStore.get("store_code"));
								paraMap.put("store_name", productStore.get("store_name"));
								paraMap.put("city_code", productStore.get("city_code"));
								
								Map<String, String> productMapping = productSalesService.queryProductMapping(paraMap);
								if(productMapping!=null){
									paraMap.put("channel_id", productMapping.get("channel_id"));
									paraMap.put("department_id", productMapping.get("department_id"));
								}
								
								tProductSalesService.addTProductSales(paraMap);
							}else{
								tProductSalesService.updateTProductSalesCount(paraMap);
							}
							
							tProductSale = tProductSalesService.queryTProductIsExist(paraMap);
							
							tempMap.put("eshop_id", tProductSale.get("eshop_id"));
							tempMap.put("city_code", tProductSale.get("city_no"));
							tempMap.put("channel_id", tProductSale.get("channel_id"));
							tempMap.put("department_id", tProductSale.get("department_id"));
							
							Map<String, String> productEshopName = productSalesService.queryProductEshopName(tempMap);
							
							if(productEshopName!=null){
								paraMap.put("eshop_name", productEshopName.get("eshop_name"));
							}
							
							Map<String, String> productCityName = productSalesService.queryProductCityName(tempMap);
							if(productCityName!=null){
								paraMap.put("city_name", productCityName.get("city_name"));
							}
							
							Map<String, String> productDeptChannelName = productSalesService.queryProductDeptChannelName(tempMap);
							if(productDeptChannelName!=null){
								paraMap.put("channel_name", productDeptChannelName.get("channel_name"));
								paraMap.put("dept_name", productDeptChannelName.get("dept_name"));
							}
							
							tProductSalesService.updateTProductSalesNames(paraMap);
						}
					}
					logger.info("**********ds_ope_product_store_day任务调度结束**********");
				} catch (Exception e) {
					logger.info("ds_ope_product_store_day任务调度异常：",e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}
}
