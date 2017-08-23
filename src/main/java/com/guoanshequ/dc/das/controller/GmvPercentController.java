package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.GmvPercentService;
import com.guoanshequ.dc.das.service.SendOrderService;
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

import java.util.List;
import java.util.Map;


/**
 * 核心业务GMV占比，用于社区动态系统中显示和下载文件
 * （微超GMV+养老GMV+家务事GMV）三项之和占门店交易额GMV（去除快周边）的比值
 */
@RestController
@ResponseBody
public class GmvPercentController {

    @Autowired
    GmvPercentService gmvPercentService;
    @Autowired
    StoreNumberService storeNumberService;

    private static final Logger logger = LogManager.getLogger(SendOrderService.class);
    
    @RequestMapping(value = "rest/queryGmvPercentByDate",method = RequestMethod.POST)
    public RestResponse queryGmvPercentByDate(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		String startdate = paraMap.get("begindate") != null ? paraMap.get("begindate").toString() : null;
 	        String enddate = paraMap.get("enddate") != null ? paraMap.get("enddate").toString() : null;
 	        String storeids =  paraMap.get("storeids") != null ? paraMap.get("storeids").toString() : null;
 	        if(StringUtils.isBlank(startdate)||StringUtils.isBlank(enddate)||StringUtils.isBlank(storeids)){
 	        	return new RestResponse(EnumRespStatus.DATA_GMVPERCNOCOND);
 	        }
 	        int totalcount = 1;
 	        totalcount = storeNumberService.queryStoreSum(paraMap);
			List<Map<String, String>> list = gmvPercentService.queryGmvPercentByDate(paraMap);
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
}
