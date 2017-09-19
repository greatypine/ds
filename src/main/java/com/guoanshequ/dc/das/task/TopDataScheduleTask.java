package com.guoanshequ.dc.das.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.guoanshequ.dc.das.service.CustomerService;
import com.guoanshequ.dc.das.service.FerryPushService;
import com.guoanshequ.dc.das.service.HumanresourceService;
import com.guoanshequ.dc.das.service.RelationService;
import com.guoanshequ.dc.das.service.StoreKeeperService;
import com.guoanshequ.dc.das.service.TopDataService;
import com.guoanshequ.dc.das.service.WorkRecordService;
import com.guoanshequ.dc.das.utils.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
* @author CaoPs
* @date 2017年6月13日
* @version 1.0
* 说明:
* 1、将异动前人员数据、异动前店长数据、拜访记录、客户画像、摆渡车、员工绩效打分通过任务调度的方式，写入表ds_topdata;
* 2、调度时间为：每月1号3点00分开始调度
* 每月1号3时00分开始调度 (cron ="0 0 03 1 * ?") 
 */
@RestController
@ResponseBody
@Component
public class TopDataScheduleTask {
    @Autowired
    HumanresourceService humanResouceService;
    @Autowired
    StoreKeeperService storeKeeperService;
    @Autowired
    RelationService relationService;
    @Autowired
    CustomerService customerService;
    @Autowired
    WorkRecordService workRecordService;
    @Autowired
    FerryPushService ferryPushService;
    @Autowired
    TopDataService topDataService; 
    
    
    private static final Logger logger = LogManager.getLogger(TopDataScheduleTask.class);
    
    /**
     * 人员店长调度
     * 调度规则：每月1号3点00分开始调度
     */
    @Scheduled(cron ="0 0 03 1 * ?")
    public void addHumanTask() {
    	new Thread(){
    		public void run() {
    	    	try {
    		    	logger.info("**********人员调度开始*************");
    	    		//得到上月的月初月末日期
    	    		Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
    	    		//上月月初日期
    	    		String begindate = datemap.get("first");
    	    		//取得年份
    	    		String year = begindate.substring(0, 4);
    	    		//取得上月月份
    	    		String month = begindate.substring(5, 7);
    	    	    //拼接月份
    	    		String yearmonth = year+"-"+month;

    	    		Map<String, String> paraMap=new HashMap<String, String>();
    	    		paraMap.put("year", year);
    	    		paraMap.put("month", month);
    	    		paraMap.put("yearmonth", yearmonth);
    	    	List<Map<String, String>> humanResourceList = humanResouceService.queryPreHumanresources(paraMap);
    	    	if(!humanResourceList.isEmpty()){
    	    		//若存在本月相应人员存在，则将其删除
    	    		topDataService.deleteTopDatas(paraMap);
    	       	    //先将人员写入到表中
    	    		for (Map<String, String> humanResourcemap : humanResourceList) {
    	    			topDataService.addHumanresources(humanResourcemap);
    				}
    		
    		    	//将店长写入到表中
    		    	logger.info("**********人员列表任务调度结束，共调度数据记录行数："+humanResourceList.size());
    		    	List<Map<String, String>> storeKeeperList = storeKeeperService.queryStoreKeepers(paraMap);
    		    	if(!storeKeeperList.isEmpty()){
    		    		for (Map<String, String> storeKeepermap : storeKeeperList) {
    		    			topDataService.addStoreKeepers(storeKeepermap);
    					}
    		    	}
    		    	logger.info("**********店长列表任务调度结束，共调度数据记录行数："+storeKeeperList.size());
    	    	}
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    		}
    	}.start();
    }
    
    /**
     * 客户画像任务调度
     * 调度规则：每月1号3点30分开始调度
     */
    @Scheduled(cron ="0 30 03 1 * ?")
    public void customerTask(){
    	try {
	    	logger.info("**********客户画像任务调度开始*************");
    		//得到上月的月初月末日期
    		Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
    		//上月月初日期
    		String begindate = datemap.get("first");
    		//取得年份
    		String year = begindate.substring(0, 4);
    		//取得月份
    		String month = begindate.substring(5, 7);
    	    //拼接月份
    		String yearmonth = year+"-"+month;

    		Map<String, String> paraMap=new HashMap<String, String>();
    		paraMap.put("year", year);
    		paraMap.put("month", month);
    		paraMap.put("yearmonth", yearmonth);
	    	//更新客户画像一档
	    	paraMap.put("grade", "1");
	    	List<Map<String, String>> cusgrade1List = customerService.queryFirst(paraMap);
	    	if(!cusgrade1List.isEmpty()){
	    		for (Map<String, String> cusgrade1Map : cusgrade1List) {
	    			topDataService.updateCusGrade1(cusgrade1Map);
				}
	    	}
	    	logger.info("**********客户画像一档任务调度结束，共调度数据记录行数："+cusgrade1List.size());
	    	logger.info("**********客户画像二档任务调度开始*************");
	    	//更新客户画像二档
	    	paraMap.put("grade", "2");
	    	List<Map<String, String>> cusgrade2List = customerService.querySecond(paraMap);
	    	if(!cusgrade2List.isEmpty()){
	    		for (Map<String, String> cusgrade2Map : cusgrade2List) {
	    			topDataService.updateCusGrade2(cusgrade2Map);
				}
	    	}
	    	logger.info("**********客户画像二档任务调度结束，共调度数据记录行数："+cusgrade2List.size());
	    	logger.info("**********客户画像三档任务调度开始*************");
	    	//更新客户画像三档
	    	paraMap.put("grade", "3");
	    	List<Map<String, String>> cusgrade3List = customerService.queryThird(paraMap);
	    	if(!cusgrade3List.isEmpty()){
	    		for (Map<String, String> cusgrade3Map : cusgrade3List) {
	    			topDataService.updateCusGrade3(cusgrade3Map);
				}
	    	}
	    	logger.info("**********客户画像三档任务调度结束，共调度数据记录行数："+cusgrade3List.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 客户画像(按门店)任务调度
     * 调度规则：每月1号4点00分开始调度
     */
    @Scheduled(cron ="0 0 04 1 * ?")
    public void customerByStoreTask(){
    	try {
    		//得到上月的月初月末日期
    		Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
    		//上月月初日期
    		String begindate = datemap.get("first");
    		//取得年份
    		String year = begindate.substring(0, 4);
    		//取得月份
    		String month = begindate.substring(5, 7);
    	    //拼接月份
    		String yearmonth = year+"-"+month;

    		Map<String, String> paraMap=new HashMap<String, String>();
    		paraMap.put("year", year);
    		paraMap.put("month", month);
    		paraMap.put("yearmonth", yearmonth);
    		
		    logger.info("**********客户画像一档(按门店)调度开始*************");
//		    List<Map<String, String>> cusGrade1StoreList = customerService.queryFirstByStore(paraMap);
		    List<Map<String, String>> cusGrade1StoreList = topDataService.queryFirstByStoreOnTop(paraMap);
	    	if(!cusGrade1StoreList.isEmpty()){
	    		for (Map<String, String> cusGrade1StoreMap : cusGrade1StoreList) {
	    			topDataService.updateStoreCusgrade1(cusGrade1StoreMap);
				}
	    	}
		    logger.info("**********客户画像一档(按门店)调度结束，共调度数据记录行数："+cusGrade1StoreList.size());
		    logger.info("**********客户画像二档(按门店)调度开始*************");
//			List<Map<String, String>> cusGrade2StoreList = customerService.querySecondByStore(paraMap);
			List<Map<String, String>> cusGrade2StoreList = topDataService.querySecondByStoreOnTop(paraMap);
	    	if(!cusGrade2StoreList.isEmpty()){
	    		for (Map<String, String> cusGrade2StoreMap : cusGrade2StoreList) {
	    			topDataService.updateStoreCusgrade2(cusGrade2StoreMap);
				}
	    	}
		    logger.info("**********客户画像二档(按门店)调度结束，共调度数据记录行数："+cusGrade2StoreList.size());
		    logger.info("**********客户画像三档(按门店)调度开始*************");
//			List<Map<String, String>> cusGrade3StoreList = customerService.queryThirdByStore(paraMap);
			List<Map<String, String>> cusGrade3StoreList = topDataService.queryThirdByStoreOnTop(paraMap);
	    	if(!cusGrade3StoreList.isEmpty()){
	    		for (Map<String, String> cusGrade3StoreMap : cusGrade3StoreList) {
	    			topDataService.updateStoreCusgrade3(cusGrade3StoreMap);
				}
	    	}
	    	logger.info("**********客户画像三档(按门店)调度结束，共调度数据记录行数："+cusGrade3StoreList.size());
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 拜访记录任务调度
     * 调度规则：每月1号4点30分开始调度
     */
    @Scheduled(cron ="0 30 04 1 * ?")
    public void relationTask(){
    	try {
    		//得到上月的月初月末日期
    		Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
    		//上月月初日期
    		String begindate = datemap.get("first");
    		//取得年份
    		String year = begindate.substring(0, 4);
    		//取得月份
    		String month = begindate.substring(5, 7);
    	    //拼接月份
    		String yearmonth = year+"-"+month;

    		Map<String, String> paraMap=new HashMap<String, String>();
    		paraMap.put("year", year);
    		paraMap.put("month", month);
    		paraMap.put("yearmonth", yearmonth);
	    	logger.info("**********拜访记录任务调度开始*************");
	    	//更新人员拜访记录
	    	List<Map<String, String>> relationsList = relationService.queryRelations(paraMap);
	    	if(!relationsList.isEmpty()){
	    		for (Map<String, String> relationsMap : relationsList) {
	    			topDataService.updateRelationnum(relationsMap);
				}
	    	}
	    	logger.info("**********拜访记录任务调度结束，共调度数据记录行数："+relationsList.size());
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 拜访记录(按门店)任务调度
     * 调度规则：每月1号5点00分开始调度
     */
    @Scheduled(cron ="0 0 05 1 * ?")
    public void relationByStoreTask(){
    	try {
    		//得到上月的月初月末日期
    		Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
    		//上月月初日期
    		String begindate = datemap.get("first");
    		//取得年份
    		String year = begindate.substring(0, 4);
    		//取得月份
    		String month = begindate.substring(5, 7);
    	    //拼接月份
    		String yearmonth = year+"-"+month;
    		Map<String, String> paraMap=new HashMap<String, String>();
    		paraMap.put("year", year);
    		paraMap.put("month", month);
    		paraMap.put("yearmonth", yearmonth);
 	    	logger.info("**********拜访记录(按门店)开始*************");
//   		List<Map<String, String>> relationStoreList = relationService.queryRelationsByStore(paraMap);
	    	List<Map<String, String>> relationStoreList = topDataService.queryRelationsByStoreOnTop(paraMap);
		    	if(!relationStoreList.isEmpty()){
		    		for (Map<String, String> relationStoreMap : relationStoreList) {
		    			topDataService.updateStoreRelationnum(relationStoreMap);
					}
		    }
		    logger.info("**********拜访记录(按门店)调度结束，共调度数据记录行数："+relationStoreList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 员工绩效打分任务调度
     * 调度规则：每月2号1点10分开始调度
     */
    //@Scheduled(cron ="0 10 01 2 * ?")
    public void workRecordTask(){
    	try{
   		//得到上月的月初月末日期
		Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
		//上月月初日期
		String begindate = datemap.get("first");
		//取得年份
		String year = begindate.substring(0, 4);
		//取得月份
		String month = begindate.substring(5, 7);
	    //拼接月份
		String yearmonth = year+"-"+month;

		Map<String, String> paraMap=new HashMap<String, String>();
		paraMap.put("year", year);
		paraMap.put("month", month);
		paraMap.put("yearmonth", yearmonth);
    	logger.info("**********个人绩效打分数调度开始*************");
    	//更新个人绩效打分数
    	paraMap.put("work_month", yearmonth);
    	List<Map<String, String>> workRecordList = workRecordService.queryWorkRecords(paraMap);
    	if(!workRecordList.isEmpty()){
    		for (Map<String, String> workRecordMap : workRecordList) {
    			topDataService.updateWorkRecord(workRecordMap);
			}
    	}
    	logger.info("**********个人绩效打分数调度结束，共调度数据记录行数："+workRecordList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 摆渡车任务调度
     * 调度规则：每月2号1点40分开始调度
     */
    //@Scheduled(cron ="0 40 01 2 * ?")
    public void ferryPushTask(){
    	try {
  		//得到上月的月初月末日期
		Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
		//上月月初日期
		String begindate = datemap.get("first");
		//取得年份
		String year = begindate.substring(0, 4);
		//取得月份
		String month = begindate.substring(5, 7);
	    //拼接月份
		String yearmonth = year+"-"+month;

		Map<String, String> paraMap=new HashMap<String, String>();
		paraMap.put("year", year);
		paraMap.put("month", month);
		paraMap.put("yearmonth", yearmonth);
    	logger.info("**********社区摆渡车数量调度开始*************");
    	//更新社区摆渡车数量
    	List<Map<String, String>> ferryPushList = ferryPushService.queryFerryPushes(paraMap);
    	if(!ferryPushList.isEmpty()){
    		for (Map<String, String> ferrypushMap : ferryPushList) {
    			topDataService.updateFerryPushnum(ferrypushMap);
			}
        }
    	logger.info("**********社区摆渡车数量调度结束，共调度数据记录行数："+ferryPushList.size());
		} catch (Exception e) {
 			e.printStackTrace();
 		}
    }
}
