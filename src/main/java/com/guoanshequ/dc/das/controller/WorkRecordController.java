package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.WorkRecordService;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
* @author CaoPs
* @date 2017年3月10日
* @version 1.0
* 说明: 个人绩效员工考勤打分接口调用处理
*/ 
@RestController
@ResponseBody
public class WorkRecordController {

    @Autowired
    WorkRecordService workRecordService;

    private static final Logger logger = LogManager.getLogger(WorkRecordService.class);
    
    @RequestMapping(value = "rest/queryWorkRecords")
    public RestResponse queryWorkRecords(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
	    	String work_month = paraMap.get("work_month") != null ? paraMap.get("work_month").toString() : null;
	        String storename = paraMap.get("storename") != null ? paraMap.get("storename").toString() : null;
	        if(StringUtils.isBlank(work_month)||StringUtils.isBlank(storename)){
	        	return new RestResponse(EnumRespStatus.DATA_WRNOCOND);
	        }
			List<Map<String, String>> list = workRecordService.queryWorkRecords(paraMap);
	        if(null==list||list.isEmpty()){
	        	return new RestResponse(EnumRespStatus.DATA_NODATA);
	        }else{
	        	return new RestResponse(EnumRespStatus.DATA_OK,list);
	        }
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
}
