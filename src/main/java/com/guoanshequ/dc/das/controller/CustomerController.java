package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.CustomerService;

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
* 说明: 单体画像接口调用处理
*/ 
@RestController
@ResponseBody
public class CustomerController {

    @Autowired
    CustomerService customerService;

    private static final Logger logger = LogManager.getLogger(CustomerService.class);
    
    /**
     * 按国安侠统计拜访记录总量
     */
    @RequestMapping(value = "rest/queryCustomers")
    public RestResponse queryCustomers(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
	    	String yearmonth = paraMap.get("yearmonth") != null ? paraMap.get("yearmonth").toString() : null;
	        String grade = paraMap.get("grade") != null ? paraMap.get("grade").toString() :null; 
	        if(StringUtils.isBlank(yearmonth)||StringUtils.isBlank(grade)){
	        	return new RestResponse(EnumRespStatus.DATA_CSNOCOND);
	        }
	        List<Map<String, String>> list = null;
	        if("1".equals(grade)){
	        	list = customerService.queryFirst(paraMap);
	        }else if("2".equals(grade)){
	        	list = customerService.querySecond(paraMap);
	        }else if("3".equals(grade)){
	        	list = customerService.queryThird(paraMap);
	        }
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
     * 按门店统计拜访记录总量
     */
    @RequestMapping(value = "rest/queryCustomersByStore")
    public RestResponse queryCustomersByStore(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
	    	String yearmonth = paraMap.get("yearmonth") != null ? paraMap.get("yearmonth").toString() : null;
	        String grade = paraMap.get("grade") != null ? paraMap.get("grade").toString() :null; 
	        String storename = paraMap.get("storename") != null ? paraMap.get("storename").toString() :null; 
	        if(StringUtils.isBlank(yearmonth)||StringUtils.isBlank(grade)||StringUtils.isBlank(storename)){
	        	return new RestResponse(EnumRespStatus.DATA_CSNOCOND1);
	        }
	        List<Map<String, String>> Storelist = null;
	        if("1".equals(grade)){
	        	Storelist = customerService.queryFirstByStore(paraMap);
	        }else if("2".equals(grade)){
	        	Storelist = customerService.querySecondByStore(paraMap);
	        }else if("3".equals(grade)){
	        	Storelist = customerService.queryThirdByStore(paraMap);
	        }
	        if(null==Storelist||Storelist.isEmpty()){
	        	return new RestResponse(EnumRespStatus.DATA_NODATA);
	        }else{
	        	return new RestResponse(EnumRespStatus.DATA_OK,Storelist);
	        }
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
}
