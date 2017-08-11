package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.RebuyCusService;

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
* @date 2017年4月11日
* @version 1.0
* 说明:复购客户接口信息
 */
@RestController
@ResponseBody
public class RebuyCusController {

    @Autowired
    RebuyCusService rebuyCusService;

    private static final Logger logger = LogManager.getLogger(RebuyCusService.class);
    
    @RequestMapping(value = "rest/queryRebuyCus",method = RequestMethod.POST)
    public RestResponse queryRebuyCus(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		String year = paraMap.get("year") != null ? paraMap.get("year").toString() : null;
 	        String month = paraMap.get("month") != null ? paraMap.get("month").toString() : null;
// 	        String storename = paraMap.get("rebuyStoreName") != null ? paraMap.get("rebuyStoreName").toString() : null;
 	        String storeids =  paraMap.get("storeids") != null ? paraMap.get("storeids").toString() : null;
 	        if(StringUtils.isBlank(year)||StringUtils.isBlank(month)||StringUtils.isBlank(storeids)){
 	        	return new RestResponse(EnumRespStatus.DATA_REBUYNOCOND);
 	        }
			List<Map<String, String>> list = rebuyCusService.queryRebuyCus(paraMap);
			List<Map<String, Object>> sumlist = rebuyCusService.queryRebuyCusSumByDate(paraMap);
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
    
    @RequestMapping(value = "rest/queryRebuyCusSumByDate",method = RequestMethod.POST)
    public RestResponse queryRebuyCusSumByDate(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		String year = paraMap.get("year") != null ? paraMap.get("year").toString() : null;
    		String month = paraMap.get("month") != null ? paraMap.get("month").toString() : null;
    		String storeids =  paraMap.get("storeids") != null ? paraMap.get("storeids").toString() : null;
    		if(StringUtils.isBlank(year)||StringUtils.isBlank(month)||StringUtils.isBlank(storeids)){
    			return new RestResponse(EnumRespStatus.DATA_REBUYNOCOND);
    		}
    		List<Map<String, Object>> list = rebuyCusService.queryRebuyCusSumByDate(paraMap);
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
