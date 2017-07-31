package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.TargetValueService;

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
* @date 2017年3月9日
* @version 1.0
* 说明: 对pes绩效管理系统的绩效目标值数据进行获取
*/ 
@RestController
@ResponseBody
public class TargetValueController {

    @Autowired
    TargetValueService targetValueService;

    private static final Logger logger = LogManager.getLogger(TargetValueService.class);
    
    
    @RequestMapping(value = "rest/queryTargetValues",method = RequestMethod.POST)
    public RestResponse queryTargetValues(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
			String zw = paraMap.get("zw") != null ? paraMap.get("zw").toString() : null;
			String yearmonth = paraMap.get("yearmonth") !=null ? paraMap.get("yearmonth").toString() : null;
			String storename = paraMap.get("storename") !=null ? paraMap.get("storename").toString() : null;
			if(StringUtils.isBlank(zw)||StringUtils.isBlank(yearmonth)||StringUtils.isBlank(storename)){
				return new RestResponse(EnumRespStatus.PES_TARGET01);
			}
			List<Map<String, String>> targetList = targetValueService.queryTargetValues(paraMap);
			if(targetList.isEmpty()||targetList==null){
				return new RestResponse(EnumRespStatus.DATA_NODATA);
			}else{
				return new RestResponse(EnumRespStatus.DATA_OK,targetList);
			}
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    /**
     * 店长目标值写入到本地表ds_targetvalue
     * @param paraMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "rest/addTargetValues",method = RequestMethod.POST)
    public RestResponse addTargetValues(@RequestBody Map<String, Object> paraMap) throws Exception {
    	try{
    		String assesstime = paraMap.get("assesstime") != null ? paraMap.get("assesstime").toString() : null;
    		if(StringUtils.isBlank(assesstime)){
    			return new RestResponse(EnumRespStatus.PES_TARGET02);
    		}
			List<Map<String,String>> targetDzList = (List<Map<String,String>>) paraMap.get("data");
			if(!targetDzList.isEmpty()){
				for (int i = 0; i < targetDzList.size(); i++) {
					targetValueService.addTargetValues(targetDzList.get(i));
				}
				return new RestResponse(EnumRespStatus.PES_DATAOK);
			}else{
				return new RestResponse(EnumRespStatus.PES_NODATA);
			}
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
}
