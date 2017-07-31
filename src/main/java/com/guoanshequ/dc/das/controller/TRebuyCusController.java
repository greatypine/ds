package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.TRebuyCusService;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.List;
import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2017年4月12日
* @version 1.0
* 说明:复购客户按月度获取信息（daqWeb库）
 */
@RestController
@ResponseBody
public class TRebuyCusController {

    @Autowired
    TRebuyCusService trebuyCusService;

    private static final Logger logger = LogManager.getLogger(TRebuyCusService.class);
    
    @RequestMapping(value = "rest/queryTRebuyCus",method = RequestMethod.POST)
    public RestResponse queryTRebuyCus(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		String year = paraMap.get("year") != null ? paraMap.get("year").toString() : null;
 	        String month = paraMap.get("month") != null ? paraMap.get("month").toString() : null;
 	        if(StringUtils.isBlank(year)||StringUtils.isBlank(month)){
 	        	return new RestResponse(EnumRespStatus.DATA_TREBUYNOCOND);
 	        }
			List<Map<String, String>> list = trebuyCusService.queryTRebuyCus(paraMap);
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
    
    @RequestMapping(value = "rest/deleteTRebuyCus",method = RequestMethod.POST)
    public RestResponse deleteTRebuyCus(@RequestBody Map<String,String> paraMap)throws Exception{
    	try {
			String year = paraMap.get("year")!=null ?paraMap.get("year").toString() :null;
			String month = paraMap.get("month")!=null?paraMap.get("month").toString():null;
			if(StringUtils.isBlank(year)||StringUtils.isBlank(month)){
				return new RestResponse(EnumRespStatus.DATA_TNEWADDNOCOND);
			}
			int resultnum = trebuyCusService.deleteByYearMonth(paraMap);
			if(resultnum <= 0){
				return new RestResponse(EnumRespStatus.DATA_NODATA);
			}else{
				return new RestResponse(EnumRespStatus.DATA_OK,resultnum);
			}
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
		}
    }
}
