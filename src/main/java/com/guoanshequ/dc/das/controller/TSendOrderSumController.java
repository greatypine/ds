package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.TSendOrderSumService;

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
* @date 2017年8月28日
* @version 1.0
* 说明:
 */
@RestController
@ResponseBody
public class TSendOrderSumController {

    @Autowired
    TSendOrderSumService tsendOrderSumService;

    private static final Logger logger = LogManager.getLogger(TSendOrderSumService.class);
    
    @RequestMapping(value = "rest/queryTSendOrderSum",method = RequestMethod.POST)
    public RestResponse queryTSendOrderSum(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		String year = paraMap.get("year") != null ? paraMap.get("year").toString() : null;
 	        String month = paraMap.get("month") != null ? paraMap.get("month").toString() : null;
 	        if(StringUtils.isBlank(year)||StringUtils.isBlank(month)){
 	        	return new RestResponse(EnumRespStatus.DATA_TSENDNOCOND);
 	        }
			List<Map<String, String>> list = tsendOrderSumService.queryTSendOrderSum(paraMap);
			List<Map<String, Object>> sumlist = tsendOrderSumService.queryTSendOrderSumByMonth(paraMap);
			int totalcount = 1;
			if(!sumlist.isEmpty()){
				for (Map<String, Object> map : sumlist) {
					totalcount =  ((Long)map.get("totalcount")).intValue();
				}
			}			
	        if(null==list||list.isEmpty()){
	        	return new RestResponse(EnumRespStatus.DATA_NODATA);
	        }else{
	        	return new RestResponse(EnumRespStatus.DATA_OK,totalcount,list);
	        }
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    @RequestMapping(value = "rest/queryTSendOrderSumByMonth",method = RequestMethod.POST)
    public RestResponse queryTSendOrderSumByMonth(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		String year = paraMap.get("year") != null ? paraMap.get("year").toString() : null;
 	        String month = paraMap.get("month") != null ? paraMap.get("month").toString() : null;
 	        if(StringUtils.isBlank(year)||StringUtils.isBlank(month)){
 	        	return new RestResponse(EnumRespStatus.DATA_TSENDNOCOND);
 	        }
			List<Map<String, Object>> list = tsendOrderSumService.queryTSendOrderSumByMonth(paraMap);
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
