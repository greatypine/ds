package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.CustomerOrderMonthTradeService;
import com.guoanshequ.dc.das.service.SendOrderService;

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
* @date 2018年2月11日
* @version 1.0
* 说明:
 */
@RestController
@ResponseBody
public class CustomerOrderMonthTradeController {

    @Autowired
    CustomerOrderMonthTradeService customerOrderMonthTradeService;

    private static final Logger logger = LogManager.getLogger(SendOrderService.class);
    
    @RequestMapping(value = "rest/queryCustomerOrderMonthTradeBySignTime",method = RequestMethod.POST)
    public RestResponse querySendOrders(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		String begindate = paraMap.get("begindate") != null ? paraMap.get("begindate").toString() : null;
 	        String enddate = paraMap.get("enddate") != null ? paraMap.get("enddate").toString() : null;
 	        if(StringUtils.isBlank(begindate)||StringUtils.isBlank(enddate)){
 	        	return new RestResponse(EnumRespStatus.DATA_SENDNOCOND);
 	        }
			List<Map<String, String>> list = customerOrderMonthTradeService.queryCustomerOrderMonthTradeBySignTime(paraMap);
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
