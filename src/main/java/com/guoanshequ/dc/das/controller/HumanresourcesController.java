package com.guoanshequ.dc.das.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.HumanresourceService;
import com.guoanshequ.dc.das.service.TopDataService;
import com.guoanshequ.dc.das.utils.DateUtils;

/**
* @author CaoPs
* @date 2017年3月9日
* @version 1.0
* 说明: 普通员工接口调用处理
*/ 
@RestController
@ResponseBody
public class HumanresourcesController {

    @Autowired
    HumanresourceService humanresourceService;
    @Autowired
    TopDataService topDataService;
    
    private static final Logger logger = LogManager.getLogger(HumanresourceService.class);
    
    @RequestMapping(value = "rest/queryHumanresources",method = RequestMethod.POST,consumes="application/json")
    public RestResponse queryHumanresources(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
	        String update_time_start = paraMap.get("update_time_start") != null ? paraMap.get("update_time_start").toString() : null;
	        String update_time_end = paraMap.get("update_time_end") != null ? paraMap.get("update_time_end").toString() : null;
	        String datatype = paraMap.get("datatype") != null ? paraMap.get("datatype").toString() : null;
	        if(StringUtils.isBlank(datatype)){
	        	return new RestResponse(EnumRespStatus.DATA_HUMANTYPE);
	        }else if(!StringUtils.isBlank(update_time_start)&&!StringUtils.isBlank(update_time_end)){
	        	if(DateUtils.getIntervalDays(update_time_start, update_time_end)>30){
	        		return new RestResponse(EnumRespStatus.DATA_LIMIT);
	        	}
	    	}
			List<Map<String, String>> list = null;
			if("0".equals(datatype)){//实时人员数据
				list = humanresourceService.queryHumanresources(paraMap);
			}else if("1".equals(datatype)){//上月异动人员数据
//				list = humanresourceService.queryPreHumanresources(paraMap);
				list = topDataService.queryHumanResourcesOnTop(paraMap);
			}
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
    
    
    
    
    //查询员工基础数据（根据日期差量查询）
    @RequestMapping(value = "rest/queryemployeelist",method = RequestMethod.POST,consumes="application/json")
    public RestResponse queryHumanresourcesList(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		//获取参数 判断是否合法 
    		String datetime = paraMap.get("datetime") != null ? paraMap.get("datetime").toString() : null;
    		if(StringUtils.isBlank(datetime)){
	        	return new RestResponse(EnumRespStatus.DATA_NODATETIME);
	        }
    		//判断是否合法的日期 格式 
    		if(!isValidDate(datetime)) {
    			return new RestResponse(EnumRespStatus.DATA_ERRORDATETIMEFORMAT);
    		}
			List<Map<String, String>> list = null;
			list = humanresourceService.queryHumanresourcesList(paraMap);
			logger.info("--------------------------------------");
			logger.info("数据结果长度为："+list.size());
			logger.info("--------------------------------------");
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
    
    
    //查询全部人员信息方法 无参数 
    @RequestMapping(value = "rest/queryallemployeelist",method = RequestMethod.POST,consumes="application/json")
    public RestResponse queryAllHumanresourcesList() throws Exception {
    	try{
			List<Map<String, String>> list = null;
			list = humanresourceService.queryAllHumanresourcesList();
			logger.info("--------------------------------------");
			logger.info("数据结果长度为："+list.size());
			logger.info("--------------------------------------");
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
    
    
    
    //验证日期格式 
	public static boolean isValidDate(String str) {
		boolean convertSuccess = true;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			format.setLenient(false);
			format.parse(str);
		} catch (Exception e) {
			convertSuccess = false;
		}
		return convertSuccess;
	}
    
}
