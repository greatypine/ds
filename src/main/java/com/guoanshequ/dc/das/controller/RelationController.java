package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.RelationService;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
* @author CaoPs
* @date 2017年3月16日
* @version 1.0
* 说明: 拜访记录接口控制层
*/ 
@RestController
@ResponseBody
public class RelationController {

    @Autowired
    RelationService relationService;

    private static final Logger logger = LogManager.getLogger(RelationService.class);
    /**
     * 按国安侠统计拜访记录总数
     */
    @RequestMapping(value = "rest/queryRelations")
    public RestResponse queryRelations(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
	        String yearmonth = paraMap.get("yearmonth") != null ? paraMap.get("yearmonth").toString() : null;
	        if(StringUtils.isBlank(yearmonth)){
	        	return new RestResponse(EnumRespStatus.DATA_RELANOCOND);
	        }
			List<Map<String, String>> list = relationService.queryRelations(paraMap);
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
     * 按门店统计拜访记录总数
     */
    @RequestMapping(value = "rest/queryRelationsByStore")
    public RestResponse queryRelationsByStore(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
	        String yearmonth = paraMap.get("yearmonth") != null ? paraMap.get("yearmonth").toString() : null;
	        String storename = paraMap.get("storename") != null ? paraMap.get("storename").toString() : null;
	        if(StringUtils.isBlank(yearmonth)||StringUtils.isBlank(storename)){
	        	return new RestResponse(EnumRespStatus.DATA_RELANOCOND1);
	        }
			List<Map<String, String>> list = relationService.queryRelationsByStore(paraMap);
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
