package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.StoreTradeService;

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
* @author CaoPs
* @date 2017年3月21日
* @version 1.0
* 说明: 门店交易额接口信息
*/ 
@RestController
@ResponseBody
public class StoreTradeController {

    @Autowired
    StoreTradeService storeTradeService;

    private static final Logger logger = LogManager.getLogger(StoreTradeService.class);
    
    /**
     * pes绩效任务调度生成到daqWeb中ds_storetrade表
     */
    @RequestMapping(value = "rest/queryStoreTrades",method = RequestMethod.POST)
    public RestResponse queryStoreTrades(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		String startdate = paraMap.get("begindate") != null ? paraMap.get("begindate").toString() : null;
 	        String enddate = paraMap.get("enddate") != null ? paraMap.get("enddate").toString() : null;
 	        String storeids =  paraMap.get("storeids") != null ? paraMap.get("storeids").toString() : null;
 	        if(StringUtils.isBlank(startdate)||StringUtils.isBlank(enddate)
 	 	        	||StringUtils.isBlank(storeids)){
 	        	return new RestResponse(EnumRespStatus.DATA_TRADENOCOND);
 	        }
			List<Map<String, String>> list = storeTradeService.queryStoreTrades(paraMap);
			
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
     * 社区动态每日GMV前X名，实时数据
     */
    @RequestMapping(value = "rest/queryStoreTradesOrderByGMV",method = RequestMethod.POST)
    public RestResponse queryStoreTradesOrderByGMV(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		String startdate = paraMap.get("begindate") != null ? paraMap.get("begindate").toString() : null;
 	        String enddate = paraMap.get("enddate") != null ? paraMap.get("enddate").toString() : null;
 	        String storeids =  paraMap.get("storeids") != null ? paraMap.get("storeids").toString() : null;
 	        String limitcond =  paraMap.get("limitcond") != null ? paraMap.get("limitcond").toString() : null;
 	        if(StringUtils.isBlank(startdate)||StringUtils.isBlank(enddate)||StringUtils.isBlank(storeids)||
 	 	       StringUtils.isBlank(limitcond)){
 	        	return new RestResponse(EnumRespStatus.DATA_TRADENOCOND1);
 	        }
			List<Map<String, String>> list = storeTradeService.queryStoreTradesOrderByGMV(paraMap);
			
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
     * 社区动态每日订单数量前X名，实时数据
     */
    @RequestMapping(value = "rest/queryStoreTradesOrderByOrderNum",method = RequestMethod.POST)
    public RestResponse queryStoreTradesOrderByOrderNum(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		String startdate = paraMap.get("begindate") != null ? paraMap.get("begindate").toString() : null;
 	        String enddate = paraMap.get("enddate") != null ? paraMap.get("enddate").toString() : null;
 	        String storeids =  paraMap.get("storeids") != null ? paraMap.get("storeids").toString() : null;
 	        String limitcond =  paraMap.get("limitcond") != null ? paraMap.get("limitcond").toString() : null;
 	        if(StringUtils.isBlank(startdate)||StringUtils.isBlank(enddate)||StringUtils.isBlank(storeids)||
 	 	       StringUtils.isBlank(limitcond)){
 	        	return new RestResponse(EnumRespStatus.DATA_CSNOCOND2);
 	        }
			List<Map<String, String>> list = storeTradeService.queryStoreTradesOrderByOrderNum(paraMap);
			
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
