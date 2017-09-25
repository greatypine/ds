package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.CustomerService;
import com.guoanshequ.dc.das.task.AbnormalOrderScheduleTask;
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
* 2、平台上新增用户、复购用户、上门送单量、门店交易额、国安侠好评次数的调度；
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
}
