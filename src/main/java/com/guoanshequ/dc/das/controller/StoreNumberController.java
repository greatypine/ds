package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.StoreNumberService;

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
 * 
* @author CaoPs
* @date 2017年7月20日
* @version 1.0
* 说明:平台门店相关信息：门店number号、门店storeno
 */
@RestController
@ResponseBody
public class StoreNumberController {

    @Autowired
    StoreNumberService storeNumberService;

    private static final Logger logger = LogManager.getLogger(StoreNumberService.class);
    
    @RequestMapping(value = "rest/queryStoreNumbers",method = RequestMethod.POST)
    public RestResponse queryStoreNumbers(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
			String str = storeNumberService.queryStoreNumbers();
	        if(StringUtils.isBlank(str)){
	        	return new RestResponse(EnumRespStatus.DATA_NODATA);
	        }else{
	        	return new RestResponse(EnumRespStatus.DATA_OK,str);
	        }
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    @RequestMapping(value = "rest/queryStoreNoes",method = RequestMethod.POST)
    public RestResponse queryStoreNoes(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
			String str = storeNumberService.queryStoreNoes();
	        if(StringUtils.isBlank(str)){
	        	return new RestResponse(EnumRespStatus.DATA_NODATA);
	        }else{
	        	return new RestResponse(EnumRespStatus.DATA_OK,str);
	        }
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
}
