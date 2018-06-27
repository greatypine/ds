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
	//@Scheduled(cron = "30 * * * * ?")
	@Scheduled(cron ="0 58 2 * * ?")
	public void memberCountTask() {
		new Thread() {
			public void run() {
				try {
					logger.info("************每小时更新成交额成交量任务开始***********************");

					Map<String, String> paraMap = new HashMap<String, String>();

					// 从gemini中查询成交量
					List<Map<String, Object>> memberCountList = memberCountService.queryMemberCount(paraMap);
					if (!memberCountList.isEmpty()) {
						// 设置任务为运行中状态
						Map<String, String> runMap = new HashMap<String, String>();
						runMap.put("member_type", "1");
						for (Map<String, Object> memberCount : memberCountList) {
							runMap.put("member_count", memberCount.get("cou").toString());
							// 更新数据
							dfMemberCountService.updateMemberCount(runMap);
						}
					}
					// 从gemini中查询成交额
					List<Map<String, Object>> memberSumList = memberCountService.queryMemberSum(paraMap);
					if (!memberSumList.isEmpty()) {
						// 设置任务为运行中状态
						Map<String, String> memberSumMap = new HashMap<String, String>();
						memberSumMap.put("member_type", "2");
						for (Map<String, Object> memberSum : memberSumList) {
							memberSumMap.put("member_count", memberSum.get("cou").toString());
							// 更新数据
							dfMemberCountService.updateMemberCount(memberSumMap);
						}
					}
					Map<String, String> selMap = new HashMap<String, String>();
					List<String> fileIdList= new ArrayList<String>();
					fileIdList.add("6");
					fileIdList.add("7");
					fileIdList.add("8");
					fileIdList.add("9");
					dfMemberCountService.deletMembers(fileIdList);
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
						Map<String, String> allEshopSumMap = new HashMap<String, String>();
						allEshopSumMap.put("member_type", "7");
						for (Map<String, Object> allEshopSum : allEshopSumList) {
							allEshopSumMap.put("member_name", "allEshopSum");
							allEshopSumMap.put("member_count", allEshopSum.get("cou").toString());
							allEshopSumMap.put("sell_duration", "0");
							allEshopSumMap.put("remark", "e店社员、非社员总成交额");
							// 入库
							dfMemberCountService.addDfMemberCount(allEshopSumMap);
						}
					}
					//查询e店社员成交额
					List<Map<String, Object>> yesEshopSumList = memberCountService.queryYesEshopSum(paraMap);
					if (!yesEshopSumList.isEmpty()) {
						Map<String, String> yesEshopSumMap = new HashMap<String, String>();
						yesEshopSumMap.put("member_type", "8");
						for (Map<String, Object> yesEshopSum : yesEshopSumList) {
							yesEshopSumMap.put("member_name", "yesEshopSum");
							yesEshopSumMap.put("member_count", yesEshopSum.get("cou").toString());
							yesEshopSumMap.put("remark", "e店社员成交额");
							// 入库
							dfMemberCountService.addDfMemberCount(yesEshopSumMap);
						}
					}
					//查询e店非社员成交额
					List<Map<String, Object>> noEshopSumList = memberCountService.queryNoEshopSum(paraMap);
					if (!noEshopSumList.isEmpty()) {
						Map<String, String> noEshopSumMap = new HashMap<String, String>();
						noEshopSumMap.put("member_type", "9");
						for (Map<String, Object> noEshopSum : noEshopSumList) {
							noEshopSumMap.put("member_name", "noEshopSum");
							noEshopSumMap.put("member_count", noEshopSum.get("cou").toString());
							noEshopSumMap.put("remark", "e店非社员成交额");
							// 入库
							dfMemberCountService.addDfMemberCount(noEshopSumMap);
						}
					}
					logger.info("************社员信息任务结束***********************");
				} catch (Exception e) {
					logger.info("社员信息任务出现问题，任务执行失败，请查看！");
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * 
	 * @Title: memberProductTask @Description: 社员最受欢迎商品、无人问津商品，按照每天1点15执行一次 @param
	 * 设定文件 @return void 返回类型 @throws
	 */
	//@Scheduled(cron = "0 */1 * * * ?")
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
					String nowDate = getDateTime();
					Map<String, String> paraMap = new HashMap<String, String>();
					// 从gemini中查询最受欢迎商品
					List<Map<String, Object>> memberHotProList = memberCountService.queryhotProduct(paraMap);
					if (!memberHotProList.isEmpty()) {
						Map<String, String> hotMap = new HashMap<String, String>();
						hotMap.put("member_type", "3");
						for (Map<String, Object> memberhot : memberHotProList) {
							hotMap.put("member_name", memberhot.get("pname").toString());
							hotMap.put("member_count", memberhot.get("pcount").toString());
							String createTime = memberhot.get("createtime").toString();
							int sellDuration = getMargin(nowDate, createTime);
							hotMap.put("sell_duration", sellDuration + "");
							hotMap.put("remark", "最受欢迎商品");
							// 入库
							dfMemberCountService.addDfMemberCount(hotMap);
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
							coolMap.put("remark", "无人问津商品");
							// 入库
							dfMemberCountService.addDfMemberCount(coolMap);
						}
					}
					
					//查询动销商品量
					List<Map<String, Object>> movingPinList = memberCountService.queryMovingPin(paraMap);
					if (!movingPinList.isEmpty()) {
						Map<String, String> movingPinMap = new HashMap<String, String>();
						movingPinMap.put("member_type", "5");
						for (Map<String, Object> movingPin : movingPinList) {
							movingPinMap.put("member_name", "movingPin");
							movingPinMap.put("member_count", movingPin.get("cou").toString());
							movingPinMap.put("remark", "动销商品数量");
							// 入库
							dfMemberCountService.addDfMemberCount(movingPinMap);
						}
					}
					
					
					

					
					
					logger.info("************社员信息任务结束***********************");
				} catch (Exception e) {
					logger.info("社员商品统计出现问题，任务执行失败，请查看！");
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