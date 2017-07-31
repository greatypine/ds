package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.CustomerService;
import com.guoanshequ.dc.das.service.TopDataService;
import com.guoanshequ.dc.das.task.ScheduleTask;
import com.guoanshequ.dc.das.task.TopDataScheduleTask;

import org.apache.commons.lang3.StringUtils;
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
* 说明: 平台数据按月份删除相应的数据记录
*/ 
@RestController
@ResponseBody
public class TopDataController {

    @Autowired
    TopDataService topDataService;

    private static final Logger logger = LogManager.getLogger(CustomerService.class);
    
    @RequestMapping(value = "rest/deleteTopData",method = RequestMethod.POST)
    public RestResponse topDataTaskByHand(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		String year = paraMap.get("year")!=null ?paraMap.get("year").toString() :null;
    		String month = paraMap.get("month")!=null ?paraMap.get("month").toString() :null;
    		if(StringUtils.isBlank(year)||StringUtils.isBlank(month)){
    			return new RestResponse(EnumRespStatus.DATA_TOPDATACOND);
    		}
    		int resultnum = topDataService.deleteTopDatas(paraMap);
    		if(resultnum<=0){
    			return new RestResponse(EnumRespStatus.DATA_NODATA);
    		}else{
    			return new RestResponse(EnumRespStatus.DATA_OK,resultnum);
    		}
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    
    
}
