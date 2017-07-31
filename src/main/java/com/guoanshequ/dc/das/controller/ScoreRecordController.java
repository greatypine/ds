package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.ScoreRecordService;

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
* @date 2017年7月24日
* @version 1.0
* 说明: 绩效打分：拉新客户数、营业额、员工工作行为表现 、店面和库房管理 等
 */
@RestController
@ResponseBody
public class ScoreRecordController {

    @Autowired
    ScoreRecordService scoreRecordService;

    private static final Logger logger = LogManager.getLogger(ScoreRecordService.class);
    
    @RequestMapping(value = "rest/queryScoreRecords",method = RequestMethod.POST)
    public RestResponse queryWorkRecords(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
	    	String yearmonth = paraMap.get("yearmonth") != null ? paraMap.get("yearmonth").toString() : null;
	        if(StringUtils.isBlank(yearmonth)){
	        	return new RestResponse(EnumRespStatus.DATA_WRNOCOND);
            }
	    	
	    	List<Map<String, String>> list = scoreRecordService.queryScoreRecords(paraMap);
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
