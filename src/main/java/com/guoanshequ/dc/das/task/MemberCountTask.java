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
public class MemberCountTask {

	@Autowired
	MemberCountService memberCountService;
	@Autowired
	DfMemberCountService dfMemberCountService;
	private static final Logger logger = LogManager.getLogger(MemberCountTask.class);
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	/** 日期格式yyyy-MM-dd HH:mm:ss字符串常量 */
	private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static SimpleDateFormat sdf_date_format = new SimpleDateFormat(DATE_FORMAT);
	private static Calendar cale = Calendar.getInstance();
	private static SimpleDateFormat sdf_datetime_format = new SimpleDateFormat(DATETIME_FORMAT);

	/**
	 * 
	 * @Title: UserMemberScheduleTask @Description: 社员信息表，每小时一次，采用多线程 @param
	 * 设定文件 @return void 返回类型 @throws
	 */
	@Scheduled(cron ="0 58 2 * * ?")
	public void memberCountTask() {
		new Thread() {
			public void run() {
				try {
					logger.info("************成交额成交量任务开始***********************");
					List<String> fileIdList= new ArrayList<String>();
					fileIdList.add("1");
					fileIdList.add("2");
					fileIdList.add("6");
					fileIdList.add("7");
					fileIdList.add("8");
					fileIdList.add("9");
					dfMemberCountService.deletMembers(fileIdList);
					Map<String, String> paraMap = new HashMap<String, String>();

					// 从gemini中查询成交量
					List<Map<String, Object>> memberCountList = memberCountService.queryMemberCount(paraMap);
					if (!memberCountList.isEmpty()) {
						
						for (Map<String, Object> memberCount : memberCountList) {
							Map<String, String> runMap = new HashMap<String, String>();
							runMap.put("member_type", "1");
							runMap.put("member_count", memberCount.get("cou").toString());
							runMap.put("city_code", memberCount.get("city_code").toString());
							// 更新数据
							dfMemberCountService.addDfMemberCount(runMap);
						}
					}
					// 从gemini中查询成交额
					List<Map<String, Object>> memberSumList = memberCountService.queryMemberSum(paraMap);
					if (!memberSumList.isEmpty()) {
						for (Map<String, Object> memberSum : memberSumList) {
							Map<String, String> memberSumMap = new HashMap<String, String>();
							memberSumMap.put("member_type", "2");
							memberSumMap.put("member_count", memberSum.get("cou").toString());
							memberSumMap.put("city_code", memberSum.get("city_code").toString());
							// 更新数据
							dfMemberCountService.addDfMemberCount(memberSumMap);
						}
					}
					Map<String, String> selMap = new HashMap<String, String>();

					//查询e店订单量、成交额
					List<Map<String, Object>> eShopCountList = memberCountService.queryEshopCount(paraMap);
					if (!eShopCountList.isEmpty()) {
						Map<String, String> eshopCountMap = new HashMap<String, String>();
						eshopCountMap.put("member_type", "6");
						for (Map<String, Object> eshopCount : eShopCountList) {
							eshopCountMap.put("member_name", eshopCount.get("ename").toString());
							eshopCountMap.put("member_count", eshopCount.get("sumprice").toString());
							eshopCountMap.put("sell_duration", eshopCount.get("cou").toString());
							eshopCountMap.put("remark", "订单量、成交额");
							// 入库
							dfMemberCountService.addDfMemberCount(eshopCountMap);
						}
					}
					//查询e店社员、非社员成交额
					List<Map<String, Object>> allEshopSumList = memberCountService.queryAllEshopSum(paraMap);
					if (!allEshopSumList.isEmpty()) {
						for (Map<String, Object> allEshopSum : allEshopSumList) {
							Map<String, String> allEshopSumMap = new HashMap<String, String>();
							allEshopSumMap.put("member_type", "7");
							allEshopSumMap.put("member_name", "allEshopSum");
							allEshopSumMap.put("member_count", allEshopSum.get("cou").toString());
							allEshopSumMap.put("sell_duration", "0");
							allEshopSumMap.put("city_code", allEshopSum.get("city_code").toString());
							// 入库
							dfMemberCountService.addDfMemberCount(allEshopSumMap);
						}
					}
					//查询e店社员成交额
					List<Map<String, Object>> yesEshopSumList = memberCountService.queryYesEshopSum(paraMap);
					if (!yesEshopSumList.isEmpty()) {
						for (Map<String, Object> yesEshopSum : yesEshopSumList) {
							Map<String, String> yesEshopSumMap = new HashMap<String, String>();
							yesEshopSumMap.put("member_type", "8");
							yesEshopSumMap.put("member_name", "yesEshopSum");
							yesEshopSumMap.put("member_count", yesEshopSum.get("cou").toString());
							yesEshopSumMap.put("city_code", yesEshopSum.get("city_code").toString());
							// 入库
							dfMemberCountService.addDfMemberCount(yesEshopSumMap);
						}
					}
					//查询e店非社员成交额
					List<Map<String, Object>> noEshopSumList = memberCountService.queryNoEshopSum(paraMap);
					if (!noEshopSumList.isEmpty()) {
						for (Map<String, Object> noEshopSum : noEshopSumList) {
							Map<String, String> noEshopSumMap = new HashMap<String, String>();
							noEshopSumMap.put("member_type", "9");
							noEshopSumMap.put("member_name", "noEshopSum");
							noEshopSumMap.put("member_count", noEshopSum.get("cou").toString());
							noEshopSumMap.put("city_code", noEshopSum.get("city_code").toString());
							// 入库
							dfMemberCountService.addDfMemberCount(noEshopSumMap);
						}
					}
					logger.info("************社员信息任务结束***********************");
				} catch (Exception e) {
					logger.info("社员信息任务执行失败，请查看！");
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * 
	 * @Title: memberProductTask @Description: 社员最受欢迎商品、无人问津商品，按照每天2点50执行一次 @param
	 * 设定文件 @return void 返回类型 @throws
	 */
	@Scheduled(cron ="0 50 2 * * ?")
	public void memberProductTask() {
		new Thread() {
			public void run() {
				try {
					logger.info("************每天更最受欢迎、无人问津商品任务开始***********************");
					// 删除表中最受欢迎与无人问津商品
					List<String> fileIdList= new ArrayList<String>();
					fileIdList.add("3");
					fileIdList.add("4");
					fileIdList.add("5");
 					dfMemberCountService.deletMembers(fileIdList);
					// 查询最受欢迎商品
					//1.查询e店城市
					String nowDate = getDateTime();
					Map<String, String> paraMap = new HashMap<String, String>();
					
					List<Map<String, Object>> eshopCityList = memberCountService.queryEshopCity(paraMap);
					if (!eshopCityList.isEmpty()) {
						for (Map<String, Object> ecMap : eshopCityList) {
							//2.查询对应的城市的E店商品
							Map<String, String> citycodeMap = new HashMap<String, String>();
							citycodeMap.put("city_code", ecMap.get("citycode").toString());
							List<Map<String, Object>> memberHotProList = memberCountService.queryhotProduct(citycodeMap);
							if (!memberHotProList.isEmpty()) {
								Map<String, String> hotMap = new HashMap<String, String>();
								hotMap.put("member_type", "3");
								for (Map<String, Object> memberhot : memberHotProList) {
									hotMap.put("member_name", memberhot.get("pname").toString());
									hotMap.put("member_count", memberhot.get("pcount").toString());
									String createTime = memberhot.get("createtime").toString();
									int sellDuration = getMargin(nowDate, createTime);
									hotMap.put("sell_duration", sellDuration + "");
									hotMap.put("remark", ecMap.get("citycode").toString());
									// 入库
									dfMemberCountService.addDfMemberCount(hotMap);
								}
							}
							
						}
						
					}
					
					
					
					
					
					
					
					
					
					

					// 查询无人问津商品
					List<Map<String, Object>> membercoolProList = memberCountService.querycoolProduct(paraMap);
					if (!membercoolProList.isEmpty()) {
						Map<String, String> coolMap = new HashMap<String, String>();
						coolMap.put("member_type", "4");
						for (Map<String, Object> membercool : membercoolProList) {
							coolMap.put("member_name", membercool.get("pname").toString());
							coolMap.put("member_count", "0");
							String createTime = membercool.get("createtime").toString();
							int sellDuration = getMargin(nowDate, createTime);
							coolMap.put("sell_duration", sellDuration + "");
							if(membercool.get("citycode")!=null) {
								coolMap.put("city_code", membercool.get("citycode").toString());//城市id
							}
							// 入库
							dfMemberCountService.addDfMemberCount(coolMap);
						}
					}
					
					//查询动销商品量
					List<Map<String, Object>> movingPinList = memberCountService.queryMovingPin(paraMap);
					if (!movingPinList.isEmpty()) {
						for (Map<String, Object> movingPin : movingPinList) {
							Map<String, String> movingPinMap = new HashMap<String, String>();
							movingPinMap.put("member_type", "5");
							movingPinMap.put("member_name", "movingPin");
							movingPinMap.put("member_count", movingPin.get("cou").toString());
							movingPinMap.put("city_code", movingPin.get("city_code").toString());
							// 入库
							dfMemberCountService.addDfMemberCount(movingPinMap);
						}
					}
					
					
					

					
					
					logger.info("************每天更最受欢迎、无人问津商品任务结束***********************");
				} catch (Exception e) {
					logger.info("每天更最受欢迎、无人问津商品任务执行失败，请查看！");
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	
	/**
	 * 2018-06-21
	 * 更新合作社每日商品分类订单数据
	 * TODO 
	 * @author wuxinxin
	 */
	
	@Scheduled(cron ="0 25 3 * * ?")
	public void productCountTask() {
		new Thread() {
			public void run() {
				try {
					logger.info("************更新商品分类订单数据任务开始***********************");

					

					List<String> fileIdList= new ArrayList<String>();
					//商品分类
					fileIdList.add("生鲜果蔬");
					fileIdList.add("幸福厨房");
					fileIdList.add("精致卫浴");
					fileIdList.add("全家健康");
					fileIdList.add("家居优品");
					fileIdList.add("宠物乐园");
					fileIdList.add("好酒好食");
					for (String protype : fileIdList) {
						Map<String, String> paraMap = new HashMap<String, String>();
						paraMap.put("content_tag", "'%"+protype+"%'");
						//查询e店社员成交额
						List<Map<String, Object>> productTypeList = memberCountService.queryProductCount(paraMap);
						if (productTypeList!=null&&!productTypeList.isEmpty()) {
							Map<String, String> protypeSumMap = new HashMap<String, String>();
							for (Map<String, Object> ordercount : productTypeList) {
								protypeSumMap.put("ordertype", protype);
								protypeSumMap.put("ordercount", ordercount.get("cou").toString());
								protypeSumMap.put("data_date", ordercount.get("data_date").toString());
								protypeSumMap.put("city_code", ordercount.get("city_code").toString());
								// 入库
								dfMemberCountService.addProductCount(protypeSumMap);
							}
						}
					}
					logger.info("************更新商品分类订单数据任务结束***********************");
				} catch (Exception e) {
					logger.info("更新商品分类订单数据任务执行失败，请查看！");
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	
	/**
	 * 2018-06-28
	 * 更新合作社每日按小时订单量
	 * TODO 
	 * @author wuxinxin
	 */
	
	@Scheduled(cron ="0 29 3 * * ?")
	public void orderToHourCountTask() {
		new Thread() {
			public void run() {
				try {
					logger.info("************更新按小时统计订单量任务开始***********************");

					
					Map<String, String> paraMap = new HashMap<String, String>();
						//查询当日按小时成交量
						List<Map<String, Object>> orderHourCountList = dfMemberCountService.queryOrderHourCount(paraMap);
						if (orderHourCountList!=null&&!orderHourCountList.isEmpty()) {
							Map<String, String> protypeSumMap = new HashMap<String, String>();
							for (Map<String, Object> ordercount : orderHourCountList) {
								protypeSumMap.put("seltime", ordercount.get("seltime").toString());
								protypeSumMap.put("prisum", ordercount.get("prisum").toString());
								protypeSumMap.put("city_code", ordercount.get("city_code").toString());
								protypeSumMap.put("city_name", ordercount.get("city_name").toString());
								protypeSumMap.put("dorder_date", ordercount.get("dorder_date").toString());
								protypeSumMap.put("remark", ordercount.get("city_name").toString());
								// 入库
								dfMemberCountService.addOrderHourCount(protypeSumMap);
							}
						}
					logger.info("************更新按小时统计订单量任务结束***********************");
				} catch (Exception e) {
					logger.info("更新按小时统计订单量任务执行失败，请查看！");
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * 2018-06-21
	 * 更新安心合作社信息
	 * TODO 
	 * @author wuxinxin
	 */
	
	@Scheduled(cron ="0 25 3 15 * ?")
	public void eshopInfoTask() {
		new Thread() {
			public void run() {
				try {
					logger.info("************更新安心合作社开始***********************");
					Map<String, String> paraMap = new HashMap<String, String>();
						//查询安心合作社
						List<Map<String, Object>> eshopInfoList = memberCountService.queryEshopInfo(paraMap);
						if (eshopInfoList!=null&&!eshopInfoList.isEmpty()) {
							dfMemberCountService.delEshopInfo(null);
							Map<String, String> protypeSumMap = new HashMap<String, String>();
							for (Map<String, Object> eshopInfo : eshopInfoList) {
								protypeSumMap.put("eshop_id", eshopInfo.get("eid").toString());
								protypeSumMap.put("eshop_name", eshopInfo.get("ename").toString());
								protypeSumMap.put("city_code", eshopInfo.get("citycode").toString());
								// 入库
								dfMemberCountService.addEshopInfo(protypeSumMap);
							}
						}
					logger.info("************更新安心合作社结束***********************");
				} catch (Exception e) {
					logger.info("更新安心合作社执行失败，请查看！");
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
