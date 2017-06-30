package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.TopDataService;
import com.guoanshequ.dc.das.service.WorkRecordService;
import com.guoanshequ.dc.das.utils.DateUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author CaoPs
* @date 2017年3月10日
* @version 1.0
* 说明: 个人绩效员工考勤打分接口调用处理
*/ 
@RestController
@ResponseBody
public class WorkRecordController {

    @Autowired
    WorkRecordService workRecordService;
    @Autowired
    TopDataService topDataService;

    private static final Logger logger = LogManager.getLogger(WorkRecordService.class);
    
    @RequestMapping(value = "rest/queryWorkRecords")
    public RestResponse queryWorkRecords(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
	    	String yearmonth = paraMap.get("yearmonth") != null ? paraMap.get("yearmonth").toString() : null;
	        if(StringUtils.isBlank(yearmonth)){
	        	return new RestResponse(EnumRespStatus.DATA_WRNOCOND);
            }
	    	//得到上月的月初月末日期
	    	Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
	    	//上月月初日期
	    	String begindate = datemap.get("first");
	    	//获取年份月份：YYYY-MM
	    	String preYearMonth = begindate.substring(0, 7);
	    	
//	        if(yearmonth.compareTo(preYearMonth)>0){//请求月份大于绩效月份则取实时数据
//	    	   list = workRecordService.queryWorkRecords(paraMap);
//	        }else{//请求月份小于等于绩效月份则取非实时表数据
//	           list = topDataService.queryWorkRecordsOnTop(paraMap);
//	           if(null==list||list.isEmpty()){
//	        	   list = workRecordService.queryWorkRecords(paraMap);
//	           }
//	        }
	    	List<Map<String, String>> list = workRecordService.queryWorkRecords(paraMap);
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
