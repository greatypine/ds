package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.CustomerService;
import com.guoanshequ.dc.das.task.AbnormalOrderScheduleTask;
import com.guoanshequ.dc.das.task.AreaScheduleTask;
import com.guoanshequ.dc.das.task.OrderPubseasScheduleTask;
import com.guoanshequ.dc.das.task.ScheduleTask;
import com.guoanshequ.dc.das.task.TopDataScheduleTask;

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
* @version 1.0
* 说明: 手动调度topDataScheduleTask与ScheduleTask；
* 1、人员、店长、拜访记录、单体画像、绩效分数的调度；
* 2、平台上新增用户、复购用户、上门送单量、上门送单量按总数、门店交易额、门店交易额按门店、国安侠好评次数的调度；
* 3、异常订单、异常订单下载基库的调度；
* 4、片区绩效的调度；
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

    private static final Logger logger = LogManager.getLogger(CustomerService.class);
    
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
    		platformScheduleTask.storeTradesTask();
    		return new RestResponse(EnumRespStatus.TASK_STOERTRADEOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    /**
     * 平台数据接口手动调度：上门送单量
     */
    @RequestMapping(value = "rest/sendOrdersTaskRun",method = RequestMethod.POST)
    public RestResponse sendOrdersTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		platformScheduleTask.sendOrdersTask();
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
    		return new RestResponse(EnumRespStatus.TASK_REWARDTIMESOK);
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
    		return new RestResponse(EnumRespStatus.TASK_REWARDTIMESOK);
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
    		return new RestResponse(EnumRespStatus.TASK_REWARDTIMESOK);
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
    		return new RestResponse(EnumRespStatus.TASK_REWARDTIMESOK);
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
    		return new RestResponse(EnumRespStatus.TASK_REWARDTIMESOK);
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
     * 片区新增用户按门店
     */
    @RequestMapping(value = "rest/areaNewAddCusStoreTaskRun",method = RequestMethod.POST)
    public RestResponse areaNewAddCusStoreTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		areaScheduleTask.areaNewAddCusStoreTask();
    		return new RestResponse(EnumRespStatus.TASK_AREANEWADDCUSSTOREOK);
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
     * 片区交易额按门店
     */
    @RequestMapping(value = "rest/areaTradeStoreTaskRun",method = RequestMethod.POST)
    public RestResponse areaTradeStoreTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		areaScheduleTask.areaTradeStoreTask();
    		return new RestResponse(EnumRespStatus.TASK_AREATRADESTOREOK);
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
     * 片区重点GMV按门店
     */
    @RequestMapping(value = "rest/areaZdGmvStoreTaskRun",method = RequestMethod.POST)
    public RestResponse areaZdGmvStoreTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		areaScheduleTask.areaZdGmvStoreTask();
    		return new RestResponse(EnumRespStatus.TASK_AREAZDGMVSTOREOK);
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
     * 指定人员公海订单分配
     */
    @RequestMapping(value = "rest/orderPubseasTaskRun",method = RequestMethod.POST)
    public RestResponse orderPubseasTask(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		orderPubseasScheduleTask.orderPubseasTask();
    		return new RestResponse(EnumRespStatus.TASK_RUNOK);
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }     
}
