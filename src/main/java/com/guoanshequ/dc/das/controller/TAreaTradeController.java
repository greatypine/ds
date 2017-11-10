package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.AreaTradeService;
import com.guoanshequ.dc.das.service.TAreaTradeService;
import com.guoanshequ.dc.das.service.TAreaTradeStoreService;

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
* 说明: 片区GMV
 */
@RestController
@ResponseBody
public class TAreaTradeController {

    @Autowired
    TAreaTradeService tareaTradeService;
    @Autowired
    TAreaTradeStoreService tareaTradeStoreService;
    @Autowired
    AreaTradeService areaTradeService;

    private static final Logger logger = LogManager.getLogger(TAreaTradeService.class);
    
    @RequestMapping(value = "rest/queryTAreaTrade",method = RequestMethod.POST)
    public RestResponse queryTAreaTrade(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		String year = paraMap.get("year") != null ? paraMap.get("year").toString() : null;
 	        String month = paraMap.get("month") != null ? paraMap.get("month").toString() : null;
 	        if(StringUtils.isBlank(year)||StringUtils.isBlank(month)){
 	        	return new RestResponse(EnumRespStatus.DATA_NOYEARMONTH);
 	        }
			List<Map<String, String>> list = tareaTradeService.queryTAreaTradesGroupByEmp(paraMap);
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
    
    @RequestMapping(value = "rest/queryTAreaTradeStore",method = RequestMethod.POST)
    public RestResponse queryTAreaTradeStore(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		String year = paraMap.get("year") != null ? paraMap.get("year").toString() : null;
 	        String month = paraMap.get("month") != null ? paraMap.get("month").toString() : null;
 	        if(StringUtils.isBlank(year)||StringUtils.isBlank(month)){
 	        	return new RestResponse(EnumRespStatus.DATA_NOYEARMONTH);
 	        }
			List<Map<String, String>> list = tareaTradeStoreService.queryTAreaTradesStore(paraMap);
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
    @RequestMapping(value = "rest/queryTAreaTradeByMonth",method = RequestMethod.POST)
    public RestResponse queryTAreaTradeByMonth(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		String year = paraMap.get("year") != null ? paraMap.get("year").toString() : null;
 	        String month = paraMap.get("month") != null ? paraMap.get("month").toString() : null;    		
    		String employee_no = paraMap.get("employee_no") != null ? paraMap.get("employee_no").toString() : null;
 	        if(StringUtils.isBlank(year)||StringUtils.isBlank(month)||StringUtils.isBlank(employee_no)){
 	        	return new RestResponse(EnumRespStatus.DATA_NOPARA);
 	        }
 			Double areaTradeAmount =0.0;
 			String areaTradeTrade = tareaTradeService.queryTAreaTradeSumGroupByEmpOnMonth(paraMap);
 			if(!StringUtils.isBlank(areaTradeTrade)){
 				areaTradeAmount = Double.valueOf(areaTradeTrade);
 			}
 			
    		Map<String,Double> resMap = new HashMap<>();
			List<Map<String,Double>> resList = new ArrayList<>();
			resMap.put("areaTradeAmount", areaTradeAmount);
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
