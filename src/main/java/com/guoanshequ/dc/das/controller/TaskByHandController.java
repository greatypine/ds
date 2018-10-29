package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.CustomerService;
import com.guoanshequ.dc.das.task.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
* @author CaoPs
* @date 2017年6月16日
* @version 2.0
* 说明: 手动调度topDataScheduleTask与ScheduleTask；
* 1、人员、店长、拜访记录、单体画像、绩效分数的调度；
* 2、平台上新增用户、复购用户、上门送单量、上门送单量按总数、门店交易额、门店交易额按门店、国安侠好评次数的调度；
* 3、异常订单、异常订单下载基库的调度；
* 4、片区绩效的调度；
* 5、增加新绩效gmv及用户调度;
*/ 
@RestController
@ResponseBody
public class TaskByHandController {

    @Autowired
    TopDataScheduleTask topDataScheduleTask;
    @Autowired
    ScheduleTask platformScheduleTask;
    @Autowired
    AbnormalOrderScheduleTask abnormalOrderScheduleTask;
    @Autowired
    AreaScheduleTask areaScheduleTask;
    @Autowired
    OrderPubseasScheduleTask orderPubseasScheduleTask;
    @Autowired
    AreaPubseasScheduleTask areaPubseasScheduleTask;
    @Autowired
    PreMonthAreaScheduleTask preMonthAreaScheduleTask;
    @Autowired
    StoreTradeHistoryTask storeTradeHistoryTask;
    @Autowired
    MassOrderScheduleTask massOrderScheduleTask;
    @Autowired
    ProductCityTask productCityTask;
    @Autowired
    PesNewScheduleTask pesNewScheduleTask;
    @Autowired
    CustomerOrderMonthTradeScheduleTask customerOrderMonthTradeScheduleTask;
    @Autowired
    UserProfileScheduleTask userProfileScheduleTask;
    @Autowired
    CustomerSumScheduleTask customerSumScheduleTask;
    @Autowired
    OpeDataScheduleTask opeDataScheduleTask;
    @Autowired
    TestTask testTask;
    @Autowired
    UserMemberScheduleTask userMemberScheduleTask;
    @Autowired
    BigScreenScheduleTask bigScreenScheduleTask;
    @Autowired
    ProductSalesTask productSalesTask;
    @Autowired
    EmployeeMoreInfoTask employeeMoveDistanceTask;
    @Autowired
    MemberCountTask memberCountTask;
    @Autowired
    ImsScheduleTask imsScheduleTask;

    
    private static final Logger logger = LogManager.getLogger(CustomerService.class);
    
    /**
     * 测试接口是否连接成功
     */
    @RequestMapping(value = "rest/portConnStatusTaskRun",method = RequestMethod.POST)
    public RestResponse portConnStatusTask() throws Exception {
	    try {
	    	return new RestResponse("OK","接口连接成功！！！，版本号：20180402");
		} catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
		}
    }
    
    @RequestMapping(value = "rest/testMongoRun",method = RequestMethod.POST)
    public RestResponse testMongoRun() throws Exception {
	    try {
//	    	testTask.testMongoConn();
	    	userMemberScheduleTask.testMongo1();
	    	return new RestResponse("OK","mongo连接成功!");
		} catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
		}
    }
    
    
    /**
     * top数据接口手动全调度
     */
    @RequestMapping(value = "rest/topDataTaskRun",method = RequestMethod.POST)
    public RestResponse topDataTaskByHand(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		topDataScheduleTask.addHumanTask();
    		topDataScheduleTask.customerTask();
    		topDataScheduleTask.customerByStoreTask();
    		topDataScheduleTask.relationTask();
    		topDataScheduleTask.relationByStoreTask();
    		//topDataScheduleTask.workRecordTask();
    		//topDataScheduleTask.ferryPushTask();
    		return new RestResponse(EnumRespStatus.TASK_TOPDATA);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    /**
     * top数据接口手动调度：人员店长信息
     */
    @RequestMapping(value = "rest/addHumanTaskRun",method = RequestMethod.POST)
    public RestResponse addHumanTaskByHand(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		topDataScheduleTask.addHumanTask();
    		return new RestResponse(EnumRespStatus.TASK_ADDHUMANDATA);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    /**
     * top数据接口手动调度：客户画像
     */
    @RequestMapping(value = "rest/customerTaskRun",method = RequestMethod.POST)
    public RestResponse customerTaskByHand(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		topDataScheduleTask.customerTask();
    		return new RestResponse(EnumRespStatus.TASK_CUSTOMERDATA);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    /**
     * top数据接口手动调度：客户画像按门店
     */
    @RequestMapping(value = "rest/customerByStoreTaskRun",method = RequestMethod.POST)
    public RestResponse customerByStoreTaskByHand(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		topDataScheduleTask.customerByStoreTask();
    		return new RestResponse(EnumRespStatus.TASK_CUSTOMERSTOREDATA);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    /**
     * top数据接口手动调度：拜访记录数据
     */
    @RequestMapping(value = "rest/relationTaskRun",method = RequestMethod.POST)
    public RestResponse relationTaskByHand(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		topDataScheduleTask.relationTask();
    		return new RestResponse(EnumRespStatus.TASK_RELATIONDATA);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    /**
     * top数据接口手动调度：拜访记录按门店数据
     */
    
    @RequestMapping(value = "rest/relationByStoreTaskRun",method = RequestMethod.POST)
    public RestResponse relationByStoreTaskByHand(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		topDataScheduleTask.relationByStoreTask();
    		return new RestResponse(EnumRespStatus.TASK_RELATIONSTOREDATA);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    /**
     * top数据接口手动调度：员工绩效打分
     */
    @RequestMapping(value = "rest/workRecordTaskRun",method = RequestMethod.POST)
    public RestResponse workRecordTaskByHand(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		topDataScheduleTask.workRecordTask();
    		return new RestResponse(EnumRespStatus.TASK_WORKRECORDDATA);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    /**
     * top数据接口手动调度：摆渡车
     */
    @RequestMapping(value = "rest/ferryPushTaskRun",method = RequestMethod.POST)
    public RestResponse ferryPushTaskByHand(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		topDataScheduleTask.ferryPushTask();
    		return new RestResponse(EnumRespStatus.TASK_FERRYPUSHDATA);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    
    
    /**
     * 平台数据接口手动全调度
     */
    @RequestMapping(value = "rest/platformDataTaskRun",method = RequestMethod.POST)
    public RestResponse platformDataTaskByHand(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		platformScheduleTask.storeTradesTask();
    		platformScheduleTask.sendOrdersTask();
    		platformScheduleTask.rebuyCusTask();
    		platformScheduleTask.newAddCusTask();
    		platformScheduleTask.rewardTimesTask();
    		platformScheduleTask.storeTradeChannelTask();
    		platformScheduleTask.sendOrderSumTask();
    		platformScheduleTask.gmvPercentTask();
    		return new RestResponse(EnumRespStatus.TASK_PLATFORMDATA);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    /**
     * 平台数据接口手动调度：新增用户
     */
    @RequestMapping(value = "rest/newAddCusTaskRun",method = RequestMethod.POST)
    public RestResponse newAddCusTaskRun(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		platformScheduleTask.newAddCusTask();
    		return new RestResponse(EnumRespStatus.TASK_NEWADDCUSOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    /**
     * 平台数据接口手动调度：复购用户
     */
    @RequestMapping(value = "rest/rebuyCusTaskRun",method = RequestMethod.POST)
    public RestResponse rebuyCusTaskRun(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		platformScheduleTask.rebuyCusTask();
    		return new RestResponse(EnumRespStatus.TASK_REBUYCUSOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    /**
     * 平台数据接口手动调度：门店交易额
     */
    @RequestMapping(value = "rest/storeTradesTaskRun",method = RequestMethod.POST)
    public RestResponse storeTradesTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
//    		platformScheduleTask.storeTradesTask();
            pesNewScheduleTask.storeTradesByMassOrderTask();
    		return new RestResponse(EnumRespStatus.TASK_STOERTRADEOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }

    /**
     * 平台数据接口手动调度：手动调度国安侠gmv
     */
    @RequestMapping(value = "rest/empTradesTaskRun",method = RequestMethod.POST)
    public RestResponse empTradesTask(@RequestBody Map<String, String> paraMap) throws Exception {
        try{
            pesNewScheduleTask.empTradesByMassOrderTask();
            return new RestResponse(EnumRespStatus.TASK_RUNOK);
        }catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }


    /**
     * 平台数据接口手动调度：上门送单量（按频道）
     */
    @RequestMapping(value = "rest/sendOrdersTaskRun",method = RequestMethod.POST)
    public RestResponse sendOrdersTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
//    		platformScheduleTask.sendOrdersTask();
    		pesNewScheduleTask.empSendOrdersByMassOrderTask();
    		return new RestResponse(EnumRespStatus.TASK_SENDORDERSOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    /**
     * 平台数据接口手动调度：国安侠好评次数
     */
    @RequestMapping(value = "rest/rewardTimesTaskRun",method = RequestMethod.POST)
    public RestResponse rewardTimesTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		platformScheduleTask.rewardTimesTask();
    		return new RestResponse(EnumRespStatus.TASK_REWARDTIMESOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    /**
     * 平台数据接口手动调度：上门送单量按国安侠个人总数
     */
    @RequestMapping(value = "rest/sendOrderSumTaskRun",method = RequestMethod.POST)
    public RestResponse sendOrderSumTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		platformScheduleTask.sendOrderSumTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    /**
     * 平台数据接口手动调度：门店交易额（按频道）
     */
    @RequestMapping(value = "rest/storeTradeChannelTaskRun",method = RequestMethod.POST)
    public RestResponse storeTradeChannelTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		platformScheduleTask.storeTradeChannelTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    /**
     * 平台数据接口手动调度：gmv占比
     */
    @RequestMapping(value = "rest/gmvPercentTaskRun",method = RequestMethod.POST)
    public RestResponse gmvPercentTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		platformScheduleTask.gmvPercentTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    /**
     * 平台数据接口手动调度：异常订单手动调度
     */
    @RequestMapping(value = "rest/abnormalOrderTaskRun",method = RequestMethod.POST)
    public RestResponse abnormalOrderTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		abnormalOrderScheduleTask.abnormalOrderTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    /**
     * 平台数据接口手动调度：异常订单下载库手动调度
     */
    @RequestMapping(value = "rest/abnormalOrderDownTaskRun",method = RequestMethod.POST)
    public RestResponse abnormalOrderDownTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		abnormalOrderScheduleTask.abnormalDownTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    
    /**
     * ========================片区业务任务调度=========================================================
     * 片区新增用户
     */
    @RequestMapping(value = "rest/areaNewAddCusTaskRun",method = RequestMethod.POST)
    public RestResponse areaNewAddCusTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		areaScheduleTask.areaNewAddCusTask();
    		return new RestResponse(EnumRespStatus.TASK_AREANEWADDCUSOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    /**
     * 片区交易额
     */
    @RequestMapping(value = "rest/areaTradeTaskRun",method = RequestMethod.POST)
    public RestResponse areaTradeTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		areaScheduleTask.areaTradeTask();
    		return new RestResponse(EnumRespStatus.TASK_AREATRADEOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    
    /**
     * 片区重点产品GMV
     */
    @RequestMapping(value = "rest/areaZdGmvTaskRun",method = RequestMethod.POST)
    public RestResponse areaZdGmvTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		areaScheduleTask.areaZdGmvTask();
    		return new RestResponse(EnumRespStatus.TASK_AREAZDGMVOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    
    /**
     * 片区新增用户按门店--公海数据更新
     */
    @RequestMapping(value = "rest/areaNewAddCusStorePubseasTaskRun",method = RequestMethod.POST)
    public RestResponse areaNewAddCusStorePubseasTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		areaScheduleTask.areaNewAddCusStorePubseasTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }    
    
    /**
     * 片区交易额按门店--公海数据更新
     */
    @RequestMapping(value = "rest/areaTradeStorePubseasTaskRun",method = RequestMethod.POST)
    public RestResponse areaTradeStorePubseasTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		areaScheduleTask.areaTradeStorePubseasTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }  
    
    /**
     * 片区重点产品GMV按门店--公海数据更新
     */
    @RequestMapping(value = "rest/areaZdGmvStorePubseasTaskRun",method = RequestMethod.POST)
    public RestResponse areaZdGmvStorePubseasTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		areaScheduleTask.areaZdGmvStorePubseasTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }     
    
    
    /**
     * 片区新增用户--公海数据更新
     */
    @RequestMapping(value = "rest/areaNewAddCusPubseasTaskRun",method = RequestMethod.POST)
    public RestResponse areaNewAddCusPubseasTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		areaScheduleTask.areaNewAddCusPubseasTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    } 
    
    /**
     * 片区交易额--公海数据更新
     */
    @RequestMapping(value = "rest/areaTradePubseasTaskRun",method = RequestMethod.POST)
    public RestResponse areaTradePubseasTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		areaScheduleTask.areaTradePubseasTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    /**
     * 片区重点产品GMV--公海数据更新
     */
    @RequestMapping(value = "rest/areaZdGmvPubseasTaskRun",method = RequestMethod.POST)
    public RestResponse areaZdGmvPubseasTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		areaScheduleTask.areaZdGmvPubseasTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }  
    
    
    /**
     * ========================指定人员订单分配==================================
     */
    @RequestMapping(value = "rest/orderPubseasTaskRun",method = RequestMethod.POST)
    public RestResponse orderPubseasTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		orderPubseasScheduleTask.orderPubseasTaskByMassOrder();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }   
    
    /**
     * 指定人员订单拉新用户
     */    
    @RequestMapping(value = "rest/areaPubseasNewAddCusTaskRun",method = RequestMethod.POST)
    public RestResponse areaPubseasNewAddCusTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		areaPubseasScheduleTask.areaPubseasNewAddCusTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    } 
    
    
    /**
     * 指定人员订单交易额
     */    
    @RequestMapping(value = "rest/areaPubseasTradeTaskRun",method = RequestMethod.POST)
    public RestResponse areaPubseasTradeTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		areaPubseasScheduleTask.areaPubseasTradeTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }  
    
    
    /**
     * 指定人员订单重点产品交易额
     */    
    @RequestMapping(value = "rest/areaPubseasZdGmvTaskRun",method = RequestMethod.POST)
    public RestResponse areaPubseasZdGmvTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		areaPubseasScheduleTask.areaPubseasZdGmvTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }  
    
    
    /**
     * =============================每月2号凌晨调度上月月度数据开始===================================
     * 
     * 月度指定公海订单调度，指定订单新增用户、交易额、重点产品调度
     * 
     */
    @RequestMapping(value = "rest/preMonthAssignPubseasTaskRun",method = RequestMethod.POST)
    public RestResponse preMonthAssignPubseasTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		preMonthAreaScheduleTask.areaPubseasNewAddCusTask();
    		preMonthAreaScheduleTask.areaPubseasTradeTask();
    		preMonthAreaScheduleTask.areaPubseasZdGmvTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }  

    /**
     * 月度公海订单(按门店)调度，门店公海
     *      
     */
    @RequestMapping(value = "rest/preMonthAreaStorePubseasTaskRun",method = RequestMethod.POST)
    public RestResponse preMonthAreaStorePubseasTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		preMonthAreaScheduleTask.areaNewAddCusStorePubseasTask();
    		preMonthAreaScheduleTask.areaTradeStorePubseasTask();
    		preMonthAreaScheduleTask.areaZdGmvStorePubseasTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    } 

    /**
     * 月度公海订单调度，人均公海
     */
    @RequestMapping(value = "rest/preMonthAreaPubseasTaskRun",method = RequestMethod.POST)
    public RestResponse preMonthAreaPubseasTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		preMonthAreaScheduleTask.areaNewAddCusPubseasTask();
    		preMonthAreaScheduleTask.areaTradePubseasTask();
    		preMonthAreaScheduleTask.areaZdGmvPubseasTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }     
    
    /**
     * =============================每月2号凌晨调度上月月度数据结束===================================
     */
    
    /**
     * =============================门店历史营业额手动调度开始===================================
     */
    @RequestMapping(value = "rest/storeTradeHistoryTaskRun",method = RequestMethod.POST)
    public RestResponse storeTradeHistoryTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		storeTradeHistoryTask.storeTradeHistoryTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    /**
     * =============================门店历史营业额手动调度结束===================================
     */
    /**
     * 手动调度商品按城市排名任务
     * @param paraMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "rest/productCityTaskRun",method = RequestMethod.POST)
    public RestResponse productCityTask(@RequestBody Map<String, String> paraMap) throws Exception {
        try{
            productCityTask.productCityTask();
            return new RestResponse(EnumRespStatus.TASK_RUNOK);
        }catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    /**
     * 特殊节日商口每天销售量，手动调度商品按城市排名任务
     * @param paraMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "rest/productCityDayTaskRun",method = RequestMethod.POST)
    public RestResponse productCityDayTask(@RequestBody Map<String, String> paraMap) throws Exception {
        try{
            productCityTask.productCityDayTask();
            return new RestResponse(EnumRespStatus.TASK_RUNOK);
        }catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }


    /**
     * =============================新订单任务调度===================================
     */
    /**
     * 手动调度清洗订单定时任务（数据根据最大签收时间signtime同时写入daily/monthly/total表）
     * @param paraMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "rest/massOrderTaskRun",method = RequestMethod.POST)
    public RestResponse massOrderTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		massOrderScheduleTask.massOrderTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    /**
     * 手动调度删除Daily订单定时任务
     * @param paraMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "rest/deleteDailyMassOrderTaskRun",method = RequestMethod.POST)
    public RestResponse deleteDailyMassOrderTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		massOrderScheduleTask.deleteDailyMassOrderTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    /**
     * 手动调度删除Monthly订单定时任务
     * @param paraMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "rest/deleteMonthlyMassOrderTaskRun",method = RequestMethod.POST)
    public RestResponse deleteMonthlyMassOrderTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		massOrderScheduleTask.deleteMonthlyMassOrderTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    /** 退货打标签：
     * 手动调度退货订单更新定时任务，用于更新df_mass_order表中的退货标识
     * @param paraMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "rest/returnMassOrderTaskRun",method = RequestMethod.POST)
    public RestResponse returnMassOrderTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		massOrderScheduleTask.returnMassOrderTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }

    /**退货表任务调度：
     * 手动调度退货订单更新定时任务写入到本地表退货表order_returned表中
     * @param paraMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "rest/OrderReturnedTaskRun",method = RequestMethod.POST)
    public RestResponse OrderReturnedTask(@RequestBody Map<String, String> paraMap) throws Exception {
        try{
            massOrderScheduleTask.OrderReturnedTask();
            return new RestResponse(EnumRespStatus.TASK_RUNOK);
        }catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    /**异常打标签：
     * 手动调度异常订单更新定时任务
     * @param paraMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "rest/abnormalMassOrderTaskRun",method = RequestMethod.POST)
    public RestResponse abnormalMassOrderTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		massOrderScheduleTask.abnormalMassOrderTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }

    /**拉新标签
     * 手动调度新客标识
     * @param paraMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "rest/customerTradeTaskRun",method = RequestMethod.POST)
    public RestResponse customerTradeTask(@RequestBody Map<String, String> paraMap) throws Exception {
        try{
//            massOrderScheduleTask.customerTradeTask();
        	customerOrderMonthTradeScheduleTask.newCustomerTagTask();
            return new RestResponse(EnumRespStatus.TASK_RUNOK);
        }catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }

    /**
     * 手动调度恢复小区Code
     * @param paraMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "rest/recoveryVillageCodeTaskRun",method = RequestMethod.POST)
    public RestResponse recoveryVillageCodeTask(@RequestBody Map<String, String> paraMap) throws Exception {
        try{
        	massOrderScheduleTask.recoveryVillageCodeTask();
            return new RestResponse(EnumRespStatus.TASK_RUNOK);
        }catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }

    /**
     * 手动调度订单补丁
     */
    @RequestMapping(value = "rest/massOrderPatchTaskRun",method = RequestMethod.POST)
    public RestResponse massOrderPatchTask(@RequestBody Map<String, String> paraMap) throws Exception {
        try{
            massOrderScheduleTask.massOrderPatchTask();
            return new RestResponse(EnumRespStatus.TASK_RUNOK);
        }catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    /**
     * 手动调度上月片区绩效GMV
     */
    @RequestMapping(value = "rest/preEmpTradesByMassOrderTaskRun",method = RequestMethod.POST)
    public RestResponse preEmpTradesByMassOrderTask(@RequestBody Map<String, String> paraMap) throws Exception {
        try{
            pesNewScheduleTask.preEmpTradesByMassOrderTask();
            return new RestResponse(EnumRespStatus.TASK_RUNOK);
        }catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    /**
     * 每月3号凌晨调度上月门店GMV与国安侠GMV
     */
    @RequestMapping(value = "rest/prePesNewGmvTaskRun",method = RequestMethod.POST)
    public RestResponse prePesNewGmvTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		pesNewScheduleTask.preStoreTradesByMassOrderTask();
    		pesNewScheduleTask.preEmpTradesByMassOrderTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    } 
    
    
    /**
     * 清洗用户相关逻辑
     */
    @RequestMapping(value = "rest/customerOrderMonthTradeTaskRun",method = RequestMethod.POST)
    public RestResponse customerOrderMonthTradeTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		customerOrderMonthTradeScheduleTask.CustomerOrderMonthTradeTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    } 
    
    /**
     * 清洗用户表：更新指定时间段内的订单的小区信息
     */
    @RequestMapping(value = "rest/updateCustomerOrderMonthTradeTaskRun",method = RequestMethod.POST)
    public RestResponse updateCustomerOrderMonthTradeTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		customerOrderMonthTradeScheduleTask.UpdateCustomerOrderMonthTrade();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    } 
    
    /**
     * 清洗用户数据表打补丁
     */
    @RequestMapping(value = "rest/customerTradePatchTaskRun",method = RequestMethod.POST)
    public RestResponse customerTradePatchTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		customerOrderMonthTradeScheduleTask.customerTradePatchTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    } 
    
    /**
     * 清洗用户档案
     */
    @RequestMapping(value = "rest/userProfileTaskRun",method = RequestMethod.POST)
    public RestResponse userProfileTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		userProfileScheduleTask.userProfileTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    } 
    
    
    /**
     * 给订单打小B端订单
     */
    @RequestMapping(value = "rest/xbOrderTagTaskRun",method = RequestMethod.POST)
    public RestResponse xbOrderTagTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		massOrderScheduleTask.xbOrderTagTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    } 
    
    
    /**
     * 给用户打小b标签
     */
    @RequestMapping(value = "rest/xbUserProfileTagTaskRun",method = RequestMethod.POST)
    public RestResponse xbUserProfileTagTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		massOrderScheduleTask.xbUserProfileTagTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    } 
    
    /**
     * 测试插入用户名是否存在问题
     */
    @RequestMapping(value = "rest/addNameTaskRun",method = RequestMethod.POST)
    public RestResponse addNameTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		userProfileScheduleTask.addCusName();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    } 
    
    /**
     * 历史用户量计算（按门店）
     * 
     */
    @RequestMapping(value = "rest/customerSumMonthTaskRun",method = RequestMethod.POST)
    public RestResponse customerSumMonthTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		customerSumScheduleTask.customerSumMonthTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    } 
    /**
     * 每天用户量统计（按门店）
     * 
     */
    @RequestMapping(value = "rest/customerSumDayTaskRun",method = RequestMethod.POST)
    public RestResponse customerSumDayTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		customerSumScheduleTask.customerSumDayTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    } 
    /**
     * 历史用户量统计（按城市）
     * 
     */
    @RequestMapping(value = "rest/customerSumMonthCityTaskRun",method = RequestMethod.POST)
    public RestResponse customerSumMonthCityTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		customerSumScheduleTask.customerSumMonthCityTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    } 
    
    /**
     * 每天用户量统计(按城市)
     * 
     */
    @RequestMapping(value = "rest/customerSumDayCityTaskRun",method = RequestMethod.POST)
    public RestResponse customerSumDayCityTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		customerSumScheduleTask.customerSumDayCityTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    } 
    
    
    /**
     * 根据签收时间段内的订单，刷新对应的小区片区A侠
     * 
     */
    @RequestMapping(value = "rest/tinyAreaPatchByOrderidTaskRun",method = RequestMethod.POST)
    public RestResponse tinyAreaPatchByOrderidTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		massOrderScheduleTask.tinyAreaPatchByOrderidTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    } 
    
    
    /**
     * 事业群gmv调度
     */
    @RequestMapping(value = "rest/deptGmvTaskRun",method = RequestMethod.POST)
    public RestResponse deptGmvTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		pesNewScheduleTask.DeptGmvTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    } 
    
    
    /**
     * 事业群消费用户数调度
     */
    @RequestMapping(value = "rest/deptCusTaskRun",method = RequestMethod.POST)
    public RestResponse deptCusTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		pesNewScheduleTask.DeptCusTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }  
    
    
    /**
     * 清洗用户门店明细
     */
    @RequestMapping(value = "rest/userStoreTaskRun",method = RequestMethod.POST)
    public RestResponse userStoreTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		userProfileScheduleTask.userStoreTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }  
    
    
    /**
     * 绩效：门店用户统计
     */
    @RequestMapping(value = "rest/storeCustomerTaskRun",method = RequestMethod.POST)
    public RestResponse storeCustomerTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		pesNewScheduleTask.storeCustomerTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }  
    
    /**
     * 绩效：国安侠用户统计
     */
    @RequestMapping(value = "rest/employeeCustomerTaskRun",method = RequestMethod.POST)
    public RestResponse employeeCustomerTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		pesNewScheduleTask.employeeCustomerTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
   
    
     // =========================================运营数据手动调度配置===================================================================
     
    
    /**
     * 运营数据：门店交易额
     */
    @RequestMapping(value = "rest/opeGmvStoreMonthTaskRun",method = RequestMethod.POST)
    public RestResponse opeGmvStoreMonthTaskTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		opeDataScheduleTask.opeGmvStoreMonthByMassOrderTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    /**
     * 运营数据：门店交易额按频道
     */
    @RequestMapping(value = "rest/opeGmvStoreChannelMonthTaskRun",method = RequestMethod.POST)
    public RestResponse opeGmvStoreChannelMonthTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		opeDataScheduleTask.opeGmvStoreChannelMonthByMassOrderTask(); 
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    /**
     * 社员信息调度
     */
    @RequestMapping(value = "rest/userMemberTaskRun",method = RequestMethod.POST)
    public RestResponse userMemberTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		userMemberScheduleTask.userMemberTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    /**
     * 试用社员信息调度
     */
    @RequestMapping(value = "rest/tryUserMemberTaskRun",method = RequestMethod.POST)
    public RestResponse tryUserMemberTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		userMemberScheduleTask.tryUserMemberTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }    
    /**
     * 社员信息调度,刷新人员对应邀请码
     */
    @RequestMapping(value = "rest/updateInviteCodeByCusIdTaskRun",method = RequestMethod.POST)
    public RestResponse updateInviteCodeByCusIdTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		userMemberScheduleTask.updateInviteCodeByCusIdTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    /**
     * 大屏数据：左上角用户总数任务调度
     */
    @RequestMapping(value = "rest/bigScreenCusForHQTaskRun",method = RequestMethod.POST)
    public RestResponse bigScreenCusForHQTaskTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		bigScreenScheduleTask.bigScreenCusForHQTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    @RequestMapping(value = "rest/updateKSorderTaskRun",method = RequestMethod.POST)
    public RestResponse updateKSorderTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		massOrderScheduleTask.ksOrderTagTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    @RequestMapping(value = "rest/updateMemberUserProfileTagTaskRun",method = RequestMethod.POST)
    public RestResponse updateMemberUserProfileTagTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		massOrderScheduleTask.memberUserProfileTagTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    @RequestMapping(value = "rest/updateMemberOrderTagTaskRun",method = RequestMethod.POST)
    public RestResponse updateMemberOrderTagTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		massOrderScheduleTask.memberOrderTagTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    @RequestMapping(value = "rest/updateEshopOrderTagTaskRun",method = RequestMethod.POST)
    public RestResponse updateEshopOrderTagTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		massOrderScheduleTask.eshopOrderTagTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    @RequestMapping(value = "rest/updateMemberCityDayTaskTaskRun",method = RequestMethod.POST)
    public RestResponse updateMemberCityDayTaskTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		massOrderScheduleTask.memberCityDayTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    @RequestMapping(value = "rest/memberCancelOrderCityDayTaskRun",method = RequestMethod.POST)
    public RestResponse memberCancelOrderCityDayTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		userMemberScheduleTask.memberCancelOrderCityDayTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }   
    
    @RequestMapping(value = "rest/productSalesTaskTaskRun",method = RequestMethod.POST)
    public RestResponse productSalesTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		productSalesTask.productSalesTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    } 
    
    @RequestMapping(value = "rest/activityOrder2TagTaskRun",method = RequestMethod.POST)
    public RestResponse activityOrder2TagTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		massOrderScheduleTask.activityOrder2TagTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    		
    @RequestMapping(value = "rest/updateOrderProfitTaskRun",method = RequestMethod.POST)
    public RestResponse updateOrderProfitTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		massOrderScheduleTask.updateOrderProfitTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    @RequestMapping(value = "rest/pesGmvActivityStoreByMassOrderTaskRun",method = RequestMethod.POST)
    public RestResponse pesGmvActivityStoreByMassOrderTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		pesNewScheduleTask.pesGmvActivityStoreByMassOrderTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    } 
    
    @RequestMapping(value = "rest/orderToHourCountTaskRun",method = RequestMethod.POST)
    public RestResponse orderToHourCountTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		memberCountTask.orderToHourCountTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    @RequestMapping(value = "rest/pesGmvActivityEmpByMassOrderTaskRun",method = RequestMethod.POST)
    public RestResponse pesGmvActivityEmpByMassOrderTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		pesNewScheduleTask.pesGmvActivityEmpByMassOrderTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }

    /**
     * @Description 获取国安侠前天之前的移动里程
     * @author gbl
     * @date 2018/9/10 10:03
     **/

    @RequestMapping(value = "rest/employeeHistoryMoveDistanceTaskRun",method = RequestMethod.POST)
    public RestResponse employeeHistoryMoveDistanceTask(@RequestBody Map<String, String> paraMap) throws Exception {
        try{
            employeeMoveDistanceTask.getHistoryMovementDistance();
            return new RestResponse(EnumRespStatus.TASK_RUNOK);
        }catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }

    /**
     * @Description 获取国安侠昨天移动里程
     * @author gbl
     * @date 2018/9/10 10:03
     **/

    @RequestMapping(value = "rest/employeeMoveDistanceTaskRun",method = RequestMethod.POST)
    public RestResponse employeeMoveDistanceTask(@RequestBody Map<String, String> paraMap) throws Exception {
        try{
            employeeMoveDistanceTask.getMovementDistance();
            return new RestResponse(EnumRespStatus.TASK_RUNOK);
        }catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }

    /**
     * @Description 计算员工工龄
     * @author gbl
     * @date 2018/9/10 16:05
     **/

    @RequestMapping(value = "rest/analyzeEmployeeWorkingAgeTaskRun",method = RequestMethod.POST)
    public RestResponse analyzeEmployeeWorkingAgeTask(@RequestBody Map<String, String> paraMap) throws Exception {
        try{
            employeeMoveDistanceTask.analyzeEmployeeWorkingAge();
            return new RestResponse(EnumRespStatus.TASK_RUNOK);
        }catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }

    @RequestMapping(value = "rest/TbsdGdsTaskRun",method = RequestMethod.POST)
    public RestResponse TbsdGdsTask(@RequestBody Map<String, String> paraMap) throws Exception {
        try{
        	imsScheduleTask.TbsdGdsTask();
            return new RestResponse(EnumRespStatus.TASK_RUNOK);
        }catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }

    @RequestMapping(value = "rest/imsTbCountTaskRun",method = RequestMethod.POST)
    public RestResponse imsTbCountTask(@RequestBody Map<String, String> paraMap) throws Exception {
        try{
            imsScheduleTask.imsTbCountTask();
            return new RestResponse(EnumRespStatus.TASK_RUNOK);
        }catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }

    @RequestMapping(value = "rest/imsTblTaskRun",method = RequestMethod.POST)
    public RestResponse imsTblTask(@RequestBody Map<String, String> paraMap) throws Exception {
        try{
            imsScheduleTask.imsTblTask();
            return new RestResponse(EnumRespStatus.TASK_RUNOK);
        }catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
 
//    @RequestMapping(value = "rest/updateOrderCouponTaskRun",method = RequestMethod.POST)
//    public RestResponse updateOrderCouponTask(@RequestBody Map<String, String> paraMap) throws Exception {
//    	try{
//    		massOrderScheduleTask.updateOrderCouponTask();
//    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
//    	}catch (Exception e) {
//            logger.error(e.toString());
//            e.printStackTrace();
//            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
//        }
//    }      
}
