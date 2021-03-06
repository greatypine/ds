package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.StoreService;
import com.guoanshequ.dc.das.utils.DateUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author CaoPs
* @date 2017年3月9日
* @version 1.0
* 说明: 门店接口调用处理
*/ 
@RestController
@ResponseBody
public class StoreController {

    @Autowired
    StoreService storeService;

    private static final Logger logger = LogManager.getLogger(StoreService.class);
    
    @RequestMapping(value = "rest/queryStores",method = RequestMethod.POST)
    public RestResponse queryStores(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
	        String update_time_start = paraMap.get("update_time_start") != null ? paraMap.get("update_time_start").toString() : null;
	        String update_time_end = paraMap.get("update_time_end") != null ? paraMap.get("update_time_end").toString() : null;
//	        String storename = paraMap.get("storename") != null ? paraMap.get("storename").toString() : null;
//	        if(StringUtils.isBlank(update_time_start)&&StringUtils.isBlank(update_time_end)
//	        	&&StringUtils.isBlank(storename)){
//	        	return new RestResponse(EnumRespStatus.DATA_NOCOND);
//	        }else 
	        if(!StringUtils.isBlank(update_time_start)&&!StringUtils.isBlank(update_time_end)){
	        	if(DateUtils.getIntervalDays(update_time_start, update_time_end)>30){
	        		return new RestResponse(EnumRespStatus.DATA_LIMIT);
	        	}
	    	}
			List<Map<String, String>> list = storeService.queryStores(paraMap);
	        if(null==list||list.isEmpty()){
	        	return new RestResponse(EnumRespStatus.DATA_NODATA);
	        }else{
	        	return new RestResponse(EnumRespStatus.DATA_OK,list.size(),list);
	        }
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
}
