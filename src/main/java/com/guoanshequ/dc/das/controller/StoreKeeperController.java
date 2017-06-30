package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.StoreKeeperService;
import com.guoanshequ.dc.das.service.TopDataService;
import com.guoanshequ.dc.das.utils.DateUtils;

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
* @date 2017年3月9日
* @version 1.0
* 说明: 店长接口调用处理
*/ 
@RestController
@ResponseBody
public class StoreKeeperController {

    @Autowired
    StoreKeeperService storeKeeperService;
    @Autowired
    TopDataService topDataService;

    private static final Logger logger = LogManager.getLogger(StoreKeeperService.class);
    
    @RequestMapping(value = "rest/queryStoreKeepers")
    public RestResponse queryStoreKeepers(@RequestBody Map<String, String> paraMap) throws Exception {
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
			if("0".equals(datatype)){//实时店长数据
				list = storeKeeperService.queryStoreKeepers(paraMap);
			}else if("1".equals(datatype)){//上月异动人员数据
				list = topDataService.queryStoreKeeperOnTop(paraMap);
//				list = storeKeeperService.queryPreStoreKeepers(paraMap);
			}
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
