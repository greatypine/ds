package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.TStoreTradeService;

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
* @date 2017年4月12日
* @version 1.0
* 说明:门店交易额按月度获取数据（daqWeb库）
 */
@RestController
@ResponseBody
public class TStoreTradeController {

    @Autowired
    TStoreTradeService tstoreTradeService;

    private static final Logger logger = LogManager.getLogger(TStoreTradeService.class);
    
    @RequestMapping(value = "rest/queryTStoreTrades",method = RequestMethod.POST)
    public RestResponse queryTStoreTrades(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		String year = paraMap.get("year") != null ? paraMap.get("year").toString() : null;
 	        String month = paraMap.get("month") != null ? paraMap.get("month").toString() : null;
 	        if(StringUtils.isBlank(year)||StringUtils.isBlank(month)){
 	        	return new RestResponse(EnumRespStatus.DATA_TTRADENOCOND);
 	        }
			List<Map<String, String>> list = tstoreTradeService.queryTStoreTrades(paraMap);
			
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
    
    @RequestMapping(value ="rest/deleteTStoreTrades",method = RequestMethod.POST)
    public RestResponse deleteTStoreTrades(@RequestBody Map<String,String> paraMap)throws Exception{
    	try {
			String year = paraMap.get("year")!=null ?paraMap.get("year").toString() :null;
			String month = paraMap.get("month")!=null ?paraMap.get("month").toString():null;
			if(StringUtils.isBlank(year)||StringUtils.isBlank(month)){
				return new RestResponse(EnumRespStatus.DATA_TTRADENOCOND);
			}
			int resultnum = tstoreTradeService.deleteByYearMonth(paraMap);
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
