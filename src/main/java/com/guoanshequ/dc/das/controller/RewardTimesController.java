package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.RewardTimesService;

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
* @date 2017年7月20日
* @version 1.0
* 说明:国安侠好评打赏次数
 */
@RestController
@ResponseBody
public class RewardTimesController {

    @Autowired
    RewardTimesService rewardTimesDao;

    private static final Logger logger = LogManager.getLogger(RewardTimesService.class);
    
    @RequestMapping(value = "rest/queryRewardTimes",method = RequestMethod.POST)
    public RestResponse queryRewardTimes(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		String startdate = paraMap.get("begindate") != null ? paraMap.get("begindate").toString() : null;
 	        String enddate = paraMap.get("enddate") != null ? paraMap.get("enddate").toString() : null;
 	        String storeids =  paraMap.get("storeids") != null ? paraMap.get("storeids").toString() : null;
 	        if(StringUtils.isBlank(startdate)||StringUtils.isBlank(enddate)||StringUtils.isBlank(storeids)){
 	        	return new RestResponse(EnumRespStatus.DATA_REWARDNOCOND);
 	        }
			List<Map<String, String>> list = rewardTimesDao.queryRewardTimes(paraMap);
			List<Map<String, Object>> sumlist = rewardTimesDao.queryRewardTimesSumByDate(paraMap);
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
    
    @RequestMapping(value = "rest/queryRewardTimesSumByDate",method = RequestMethod.POST)
    public RestResponse queryRewardTimesSumByDate(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		String startdate = paraMap.get("begindate") != null ? paraMap.get("begindate").toString() : null;
    		String enddate = paraMap.get("enddate") != null ? paraMap.get("enddate").toString() : null;
    		String storeids =  paraMap.get("storeids") != null ? paraMap.get("storeids").toString() : null;
    		if(StringUtils.isBlank(startdate)||StringUtils.isBlank(enddate)||StringUtils.isBlank(storeids)){
    			return new RestResponse(EnumRespStatus.DATA_REWARDNOCOND);
    		}
    		List<Map<String, Object>> list = rewardTimesDao.queryRewardTimesSumByDate(paraMap);
    		
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
