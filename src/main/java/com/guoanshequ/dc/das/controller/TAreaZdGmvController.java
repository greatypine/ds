package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.AreaZdGmvService;
import com.guoanshequ.dc.das.service.TAreaZdGmvService;
import com.guoanshequ.dc.das.service.TAreaZdGmvStoreService;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2017年10月23日
* @version 1.0
* 说明: 片区重点产品GMV
 */
@RestController
@ResponseBody
public class TAreaZdGmvController {

    @Autowired
    TAreaZdGmvService tareaZdGmvService;
    @Autowired
    TAreaZdGmvStoreService tareaZdGmvStoreService;
    @Autowired
    AreaZdGmvService areaZdGmvService;
    
    private static final Logger logger = LogManager.getLogger(TAreaZdGmvService.class);
    
    @RequestMapping(value = "rest/queryTAreaZdGmv",method = RequestMethod.POST)
    public RestResponse queryTAreaZdGmv(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		String year = paraMap.get("year") != null ? paraMap.get("year").toString() : null;
 	        String month = paraMap.get("month") != null ? paraMap.get("month").toString() : null;
 	        if(StringUtils.isBlank(year)||StringUtils.isBlank(month)){
 	        	return new RestResponse(EnumRespStatus.DATA_NOYEARMONTH);
 	        }
			List<Map<String, String>> list = tareaZdGmvService.queryTAreaZdGmvGroupByEmp(paraMap);
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
    
    @RequestMapping(value = "rest/queryTAreaZdGmvStore",method = RequestMethod.POST)
    public RestResponse queryTAreaZdGmvStore(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		String year = paraMap.get("year") != null ? paraMap.get("year").toString() : null;
 	        String month = paraMap.get("month") != null ? paraMap.get("month").toString() : null;
 	        if(StringUtils.isBlank(year)||StringUtils.isBlank(month)){
 	        	return new RestResponse(EnumRespStatus.DATA_NOYEARMONTH);
 	        }
			List<Map<String, String>> list = tareaZdGmvStoreService.queryTAreaZdGmvStore(paraMap);
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
    
    /**
     * 国安侠片区按月份统计app使用
     */    
    @RequestMapping(value = "rest/queryTAreaZdGmvByMonth",method = RequestMethod.POST)
    public RestResponse queryTAreaZdGmvByMonth(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		String year = paraMap.get("year") != null ? paraMap.get("year").toString() : null;
 	        String month = paraMap.get("month") != null ? paraMap.get("month").toString() : null;    		
    		String employee_no = paraMap.get("employee_no") != null ? paraMap.get("employee_no").toString() : null;
 	        if(StringUtils.isBlank(year)||StringUtils.isBlank(month)||StringUtils.isBlank(employee_no)){
 	        	return new RestResponse(EnumRespStatus.DATA_NOPARA);
 	        }
 			Double areaZdGmvAmount =0.0;
 			String areaZdGmv = tareaZdGmvService.queryTAreaZdGmvSumGroupByEmpOnMonth(paraMap);
 			if(!StringUtils.isBlank(areaZdGmv)){
 				areaZdGmvAmount = Double.valueOf(areaZdGmv);
 			}
 			
    		Map<String,Double> resMap = new HashMap<>();
			List<Map<String,Double>> resList = new ArrayList<>();
			resMap.put("areaZdGmvAmount", areaZdGmvAmount);
			resList.add(resMap);
	        if(null==resList||resList.isEmpty()){
	        	return new RestResponse(EnumRespStatus.DATA_NODATA);
	        }else{
	        	return new RestResponse(EnumRespStatus.DATA_OK,resList);
	        }
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }     
}
