package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.FerryPushService;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2017年4月17日
* @version 1.0
* 说明:摆渡车接口信息业务处理
 */
@RestController
@ResponseBody
public class FerryPushController {

    @Autowired
    FerryPushService ferryPushService;

    private static final Logger logger = LogManager.getLogger(FerryPushService.class);
    
    @RequestMapping(value = "rest/queryferryPushes",method = RequestMethod.POST)
    public RestResponse queryferryPushes(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
 	        String yearmonth = paraMap.get("yearmonth") != null ? paraMap.get("yearmonth").toString() : null; 
 	        if(StringUtils.isBlank(yearmonth)){
 	        	return new RestResponse(EnumRespStatus.DATA_FERRYNOCOND);
 	        }
			List<Map<String, String>> list = ferryPushService.queryFerryPushes(paraMap);
			
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
