package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.HumanresourceService;
import com.guoanshequ.dc.das.utils.DateUtils;

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
* @date 2017年3月9日
* @version 1.0
* 说明: 普通员工接口调用处理
*/ 
@RestController
@ResponseBody
public class HumanresourcesController {

    @Autowired
    HumanresourceService humanresourceService;

    private static final Logger logger = LogManager.getLogger(HumanresourceService.class);
    
    @RequestMapping(value = "rest/queryHumanresources", consumes="application/json")
    public RestResponse queryHumanresources(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
	    	String username = paraMap.get("username") != null ? paraMap.get("username").toString() : null;
	        String employeeno = paraMap.get("employeeno") != null ? paraMap.get("employeeno").toString() : null;
	        String storename = paraMap.get("storename") != null ? paraMap.get("storename").toString() : null;
	        String zw = paraMap.get("zw") != null ? paraMap.get("zw").toString() : null;
	        String update_time_start = paraMap.get("update_time_start") != null ? paraMap.get("update_time_start").toString() : null;
	        String update_time_end = paraMap.get("update_time_end") != null ? paraMap.get("update_time_end").toString() : null;
	        if(StringUtils.isBlank(username)&&StringUtils.isBlank(employeeno)
	        	&&StringUtils.isBlank(update_time_start)&&StringUtils.isBlank(update_time_end)
	        	&&StringUtils.isBlank(storename)&&StringUtils.isBlank(zw)){
	        	return new RestResponse(EnumRespStatus.DATA_NOCOND);
	        }else if(!StringUtils.isBlank(update_time_start)&&!StringUtils.isBlank(update_time_end)){
	        	if(DateUtils.getIntervalDays(update_time_start, update_time_end)>30){
	        		return new RestResponse(EnumRespStatus.DATA_LIMIT);
	        	}
	    	}
			List<Map<String, String>> list = humanresourceService.queryHumanresources(paraMap);
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
