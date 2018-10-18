package com.guoanshequ.dc.das.task;

import com.guoanshequ.dc.das.service.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * @author wuxinxin
 * @date 2018年5月28日
 * @version 1.0 说明:
 */
@Component
public class YlcInfoTask {

	@Autowired
	YlcInfoService ylcInfoService;
	@Autowired
	DfUserProfileService dfUserProfileService;
	@Autowired
	DfYlcOrderService dfYlcOrderService;
	private static final Logger logger = LogManager.getLogger(YlcInfoTask.class);
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	/** 日期格式yyyy-MM-dd HH:mm:ss字符串常量 */
	private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static SimpleDateFormat sdf_date_format = new SimpleDateFormat(DATE_FORMAT);
	private static Calendar cale = Calendar.getInstance();
	private static SimpleDateFormat sdf_datetime_format = new SimpleDateFormat(DATETIME_FORMAT);

	//@Scheduled(cron ="0 58 2 * * ?")
	@Scheduled(cron ="0 23 09 16 * ?")
	public void userYlcTask() {
		new Thread() {
			public void run() {
				try {
					logger.info("************查询养老餐用户任务开始***********************");
					Map<String, String> paraMap = new HashMap<String, String>();
					//查询养老餐入库最大注册时间
					String maxTime = dfUserProfileService.queryYlcCustomer(paraMap);
					// 从guoanyanglaocan中查询养老餐用户
					paraMap.put("maxTime", maxTime);
					List<Map<String, Object>> ylcInfoList = ylcInfoService.queryYlcUser(paraMap);
					if (!ylcInfoList.isEmpty()) {
						
						for (Map<String, Object> ylcInfo : ylcInfoList) {
							
							Map<String, String> telMap = new HashMap<String, String>();
							telMap.put("customer_phone", ylcInfo.get("userphone").toString());
							List<Map<String, Object>> daqUserList = dfUserProfileService.isExistCustomer(telMap);
							//查询是否有该用户,有：跳过,没有   更新数据
							if(daqUserList!=null&&daqUserList.size()>0) {
								//更新身份证信息
								System.out.println(ylcInfo.get("userphone").toString());
								if(daqUserList.get(0)!=null) {
									if(daqUserList.get(0).get("idcard")==null||daqUserList.get(0).get("idcard").toString()=="") {
										telMap.put("idcard",ylcInfo.get("usercard").toString());
										dfUserProfileService.updateYlcUser(telMap);
									}
								}
							}else {
								//向daqWeb中插入用户
								Map<String, String> runMap = new HashMap<String, String>();
								//runMap.put("customer_id", 1+"");
								runMap.put("customer_id", ylcInfo.get("userid").toString());
								runMap.put("customer_name", ylcInfo.get("username").toString());
								runMap.put("customer_phone", ylcInfo.get("userphone").toString());
								runMap.put("idcard", ylcInfo.get("usercard").toString());
								runMap.put("regist_time", ylcInfo.get("createtime").toString());
								runMap.put("storeno", ylcInfo.get("storeid").toString());
								//runMap.put("area_code", ylcInfo.get("citycode").toString());
								runMap.put("channel_name", "养老餐");
								runMap.put("system_flag", "SYS002");
								//runMap.put("member_count", ylcInfo.get("storename").toString());
								//runMap.put("city_code", ylcInfo.get("provincecode").toString());
								// 更新数据
								dfUserProfileService.addYlcUser(runMap);
							}
						}
					}
					
					logger.info("************养老餐用户任务结束***********************");
				} catch (Exception e) {
					logger.info("养老餐用户任务执行失败，请查看！");
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}

	@Scheduled(cron ="0 29 09 16 * ?")
	public void orderYlcTask() {
		new Thread() {
			public void run() {
				try {
					logger.info("************查询养老餐订单任务开始***********************");
					Map<String, String> paraMap = new HashMap<String, String>();
					//查询养老餐订单入库最大时间
					String maxOrderTime = dfYlcOrderService.queryYlcOrder(paraMap);
					String maxOrderInfoTime = dfYlcOrderService.queryYlcOrderInfo(paraMap);
					// 从guoanyanglaocan中查询养老餐用户
					paraMap.put("maxOrderTime", maxOrderTime);
					paraMap.put("maxOrderInfoTime", maxOrderInfoTime);
					List<Map<String, Object>> ylcOrderList = ylcInfoService.queryYlcOrder(paraMap);
					if (!ylcOrderList.isEmpty()) {
						
						for (Map<String, Object> ylcOrder : ylcOrderList) {
							Map<String, String> orderMap = new HashMap<String, String>();
								orderMap.put("id", ylcOrder.get("id")==null?"":ylcOrder.get("id").toString());
								orderMap.put("store_id", ylcOrder.get("store_id")==null?"":ylcOrder.get("store_id").toString());
								orderMap.put("customer_id", ylcOrder.get("customer_id")==null?"":ylcOrder.get("customer_id").toString());
								orderMap.put("booking_time", ylcOrder.get("booking_time")==null?"":ylcOrder.get("booking_time").toString());
								orderMap.put("zzw", ylcOrder.get("zzw")==null?"":ylcOrder.get("zzw").toString());
								orderMap.put("delivery", ylcOrder.get("delivery")==null?"":ylcOrder.get("delivery").toString());
								orderMap.put("state", ylcOrder.get("state")==null?"":ylcOrder.get("state").toString());
								orderMap.put("create_time", ylcOrder.get("create_time")==null?"":ylcOrder.get("create_time").toString());
								orderMap.put("status", ylcOrder.get("status")==null?"":ylcOrder.get("status").toString());
								orderMap.put("serial_number", ylcOrder.get("serial_number")==null?"":ylcOrder.get("serial_number").toString());
								// 更新数据
								dfYlcOrderService.addYlcOrder(orderMap);
						}
					}
					List<Map<String, Object>> ylcOrderInfoList = ylcInfoService.queryYlcOrderInfo(paraMap);
					
					if (!ylcOrderInfoList.isEmpty()) {
						
						for (Map<String, Object> ylcOrderInfo : ylcOrderInfoList) {
								Map<String, String> orderInfoMap = new HashMap<String, String>();
								orderInfoMap.put("id", ylcOrderInfo.get("id")==null?"":ylcOrderInfo.get("id").toString());
								orderInfoMap.put("order_id", ylcOrderInfo.get("order_id")==null?"":ylcOrderInfo.get("order_id").toString());
								orderInfoMap.put("product_id", ylcOrderInfo.get("product_id")==null?"":ylcOrderInfo.get("product_id").toString());
								orderInfoMap.put("product_name", ylcOrderInfo.get("product_name")==null?"":ylcOrderInfo.get("product_name").toString());
								orderInfoMap.put("product_price", ylcOrderInfo.get("product_price")==null?"":ylcOrderInfo.get("product_price").toString());
								orderInfoMap.put("num", ylcOrderInfo.get("num")==null?"":ylcOrderInfo.get("num").toString());
								orderInfoMap.put("comment", ylcOrderInfo.get("comment")==null?"":ylcOrderInfo.get("comment").toString());
								orderInfoMap.put("create_time", ylcOrderInfo.get("create_time")==null?"":ylcOrderInfo.get("create_time").toString());
								orderInfoMap.put("status", ylcOrderInfo.get("status")==null?"":ylcOrderInfo.get("status").toString());
								// 更新数据
								dfYlcOrderService.addYlcOrder(orderInfoMap);
						}
					}
					
					logger.info("************养老餐订单任务结束***********************");
				} catch (Exception e) {
					logger.info("养老餐订单任务执行失败，请查看！");
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	/**
	 * 比较两个日期相差的天数
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getMargin(String date1, String date2) {
		int margin;
		try {
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos1 = new ParsePosition(0);
			Date dt1 = sdf_date_format.parse(date1, pos);
			Date dt2 = sdf_date_format.parse(date2, pos1);
			long l = dt1.getTime() - dt2.getTime();
			margin = (int) (l / (24 * 60 * 60 * 1000));
			return margin;
		} catch (Exception e) {
			logger.debug("DateUtil.getMargin():" + e.toString());
			return 0;
		}
	}
	/**
	 * 比较两个日期相差的月数
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getMonthMargin(String date1, String date2) {
		int margin;
		try {
			margin = (Integer.parseInt(date2.substring(0, 4)) - Integer.parseInt(date1.substring(0, 4))) * 12;
			margin += (Integer.parseInt(date2.substring(4, 7).replaceAll("-0", "-"))
					- Integer.parseInt(date1.substring(4, 7).replaceAll("-0", "-")));
			return margin;
		} catch (Exception e) {
			logger.debug("比较两个日期相差的月数:" + e.toString());
			return 0;
		}
	}

	/**
	 * 获得服务器当前日期及时间，以格式为：yyyy-MM-dd HH:mm:ss的日期字符串形式返回
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @return
	 */
	public static String getDateTime() {
		try {
			return sdf_datetime_format.format(cale.getTime());
		} catch (Exception e) {
			logger.debug("DateUtil.getDateTime():" + e.getMessage());
			return "";
		}
	}
}
