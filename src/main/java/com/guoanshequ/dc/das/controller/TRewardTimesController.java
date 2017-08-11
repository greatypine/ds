package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.TRewardTimesService;

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
* @date 2017年7月25日
* @version 1.0
* 说明:国安侠好评次数按月度取数据（daqWeb库）
 */
@RestController
@ResponseBody
public class TRewardTimesController {

    @Autowired
    TRewardTimesService trewardTimesService;

    private static final Logger logger = LogManager.getLogger(TRewardTimesService.class);
    
    @RequestMapping(value = "rest/queryTRewardTimes",method = RequestMethod.POST)
    public RestResponse queryTRewardTimes(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		String year = paraMap.get("year") != null ? paraMap.get("year").toString() : null;
 	        String month = paraMap.get("month") != null ? paraMap.get("month").toString() : null;
 	        if(StringUtils.isBlank(year)||StringUtils.isBlank(month)){
 	        	return new RestResponse(EnumRespStatus.DATA_TREWARDNOCOND);
 	        }
			List<Map<String, String>> list = trewardTimesService.queryTRewardTimes(paraMap);
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
    
    @RequestMapping(value="rest/deleteTRewardTimes",method = RequestMethod.POST)
    public RestResponse deleteTRewardTimes(@RequestBody Map<String,String> paraMap)throws Exception{
    	try {
			String year = paraMap.get("year")!=null ?paraMap.get("year").toString() :null;
			String month = paraMap.get("month")!=null ?paraMap.get("month").toString():null;
			if(StringUtils.isBlank(year)||StringUtils.isBlank(month)){
				return new RestResponse(EnumRespStatus.DATA_TREWARDNOCOND);
			}
			int resultnum = trewardTimesService.deleteByYearMonth(paraMap);
			if(resultnum<=0){
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
