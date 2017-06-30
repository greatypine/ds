package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.CustomerService;
import com.guoanshequ.dc.das.task.ScheduleTask;
import com.guoanshequ.dc.das.task.TopDataScheduleTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
* @author CaoPs
* @date 2017年6月16日
* @version 1.0
* 说明: 手动调度topDataScheduleTask与ScheduleTask；
* 1、人员、店长、拜访记录、单体画像、绩效分数的调度；
* 2、平台上新增用户、复购用户、上门送单量、门店交易额4的调度；
*/ 
@RestController
@ResponseBody
public class TaskByHandController {

    @Autowired
    TopDataScheduleTask topDataScheduleTask;
    @Autowired
    ScheduleTask platformScheduleTask;

    private static final Logger logger = LogManager.getLogger(CustomerService.class);
    
    /**
     * top数据接口手动全调度
     */
    @RequestMapping(value = "rest/topDataTaskRun")
    public RestResponse topDataTaskByHand(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		topDataScheduleTask.addHumanTask();
    		topDataScheduleTask.customerTask();
    		topDataScheduleTask.customerByStoreTask();
    		topDataScheduleTask.relationTask();
    		topDataScheduleTask.relationByStoreTask();
    		topDataScheduleTask.workRecordTask();
    		topDataScheduleTask.ferryPushTask();
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
    @RequestMapping(value = "rest/addHumanTaskRun")
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
    @RequestMapping(value = "rest/customerTaskRun")
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
    @RequestMapping(value = "rest/customerByStoreTaskRun")
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
    @RequestMapping(value = "rest/relationTaskRun")
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
    
    @RequestMapping(value = "rest/relationByStoreTaskRun")
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
    @RequestMapping(value = "rest/workRecordTaskRun")
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
    @RequestMapping(value = "rest/ferryPushTaskRun")
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
    @RequestMapping(value = "rest/platformDataTaskRun")
    public RestResponse platformDataTaskByHand(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		platformScheduleTask.storeTradesTask();
    		platformScheduleTask.sendOrdersTask();
    		platformScheduleTask.rebuyCusTask();
    		platformScheduleTask.newAddCusTask();
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
    @RequestMapping(value = "rest/newAddCusTaskRun")
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
    @RequestMapping(value = "rest/rebuyCusTaskRun")
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
    @RequestMapping(value = "rest/storeTradesTaskRun")
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
    @RequestMapping(value = "rest/sendOrdersTaskRun")
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
}
