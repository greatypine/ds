package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.TNewaddCusService;

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
* @date 2017年4月12日
* @version 1.0
* 说明:新增消费客户按月度获取信息
 */
@RestController
@ResponseBody
public class TNewaddCusController {

    @Autowired
    TNewaddCusService tnewaddCusService;

    private static final Logger logger = LogManager.getLogger(TNewaddCusService.class);
    
    @RequestMapping(value = "rest/queryTNewaddCus",method = RequestMethod.POST)
    public RestResponse queryNewaddCus(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		String year = paraMap.get("year") != null ? paraMap.get("year").toString() : null;
 	        String month = paraMap.get("month") != null ? paraMap.get("month").toString() : null;
 	        if(StringUtils.isBlank(year)||StringUtils.isBlank(month)){
 	        	return new RestResponse(EnumRespStatus.DATA_TNEWADDNOCOND);
 	        }
			List<Map<String, String>> list = tnewaddCusService.queryTNewaddCus(paraMap);
			List<Map<String, Object>> sumlist = tnewaddCusService.queryTNewaddCusSumByMonth(paraMap);
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
    
    @RequestMapping(value = "rest/deleteTNewaddCus",method = RequestMethod.POST)
    public RestResponse deleteTNewaddCus(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		String year = paraMap.get("year") != null ? paraMap.get("year").toString() : null;
 	        String month = paraMap.get("month") != null ? paraMap.get("month").toString() : null;
 	        if(StringUtils.isBlank(year)||StringUtils.isBlank(month)){
 	        	return new RestResponse(EnumRespStatus.DATA_TNEWADDNOCOND);
 	        }
			int resultnum = tnewaddCusService.deleteByYearMonth(paraMap);
	        if(resultnum<=0){
	        	return new RestResponse(EnumRespStatus.DATA_NODATA);
	        }else{
	        	return new RestResponse(EnumRespStatus.DATA_OK,resultnum);
	        }
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
   
    @RequestMapping(value = "rest/queryTNewaddCusSumByMonth",method = RequestMethod.POST)
    public RestResponse queryTNewaddCusSumByMonth(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		String year = paraMap.get("year") != null ? paraMap.get("year").toString() : null;
 	        String month = paraMap.get("month") != null ? paraMap.get("month").toString() : null;
 	        String storeids =  paraMap.get("storeids") != null ? paraMap.get("storeids").toString() : null;
 	        if(StringUtils.isBlank(year)||StringUtils.isBlank(month)||StringUtils.isBlank(storeids)){
 	        	return new RestResponse(EnumRespStatus.DATA_TNEWADDNOCOND);
 	        }
			List<Map<String, Object>> list = tnewaddCusService.queryTNewaddCusSumByMonth(paraMap);
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
