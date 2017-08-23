package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.HumanresourceService;
import com.guoanshequ.dc.das.service.RelationService;
import com.guoanshequ.dc.das.service.StoreService;
import com.guoanshequ.dc.das.service.TopDataService;
import com.guoanshequ.dc.das.utils.DateUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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
    @Autowired
    HumanresourceService humanresourceService;
    @Autowired
    StoreService storeService;
    @Autowired
    TopDataService topDataService;
    
    private static final Logger logger = LogManager.getLogger(RelationService.class);
    /**
     * pes绩效系统按国安侠统计拜访记录总数，实时数据
     */
    @RequestMapping(value = "rest/queryRelations",method = RequestMethod.POST)
    public RestResponse queryRelations(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
	        String yearmonth = paraMap.get("yearmonth") != null ? paraMap.get("yearmonth").toString() : null;
	        if(StringUtils.isBlank(yearmonth)){
	        	return new RestResponse(EnumRespStatus.DATA_RELANOCOND);
	        }
	    	//得到上月的月初月末日期
	    	Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
	    	//上月月初日期
	    	String begindate = datemap.get("first");
	    	//获取上月形式，年份月份：YYYY-MM
	    	String preYearMonth = begindate.substring(0, 7);
	        List<Map<String, String>> list = null;
	    	if(yearmonth.compareTo(preYearMonth)>0){//请求月份大于绩效月份则取实时数据
	    		list = relationService.queryRelations(paraMap);
	    	}else{
	    		list = topDataService.queryRelationnumOnTop(paraMap);
	    		 if(null==list||list.isEmpty()){
	    			 list = relationService.queryRelations(paraMap);
	    		 }
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
    /**
     * pes绩效系统拜访记录（按门店），实时数据
     */
    @RequestMapping(value = "rest/queryRelationsByStore",method = RequestMethod.POST)
    public RestResponse queryRelationsByStore(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
	        String yearmonth = paraMap.get("yearmonth") != null ? paraMap.get("yearmonth").toString() : null;
	        if(StringUtils.isBlank(yearmonth)){
	        	return new RestResponse(EnumRespStatus.DATA_RELANOCOND);
	        }
	    	//得到上月的月初月末日期
	    	Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
	    	//上月月初日期
	    	String begindate = datemap.get("first");
	    	//获取上月形式，年份月份：YYYY-MM
	    	String preYearMonth = begindate.substring(0, 7);
	        List<Map<String, String>> list = null;
	    	if(yearmonth.compareTo(preYearMonth)>0){//请求月份大于绩效月份则取实时数据
	    		list = relationService.queryRelationsByStore(paraMap);
	    	}else{
	    		list = topDataService.queryStoreRelationnumOnTop(paraMap);
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
    
    /**
     * 社区动态系统每日动态门店拜访记录前N名，实时数据表格明细
     */
    @RequestMapping(value = "rest/queryRelationsStoreByDate",method = RequestMethod.POST)
    public RestResponse queryRelationsStoreByDate(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		String startdate = paraMap.get("begindate") != null ? paraMap.get("begindate").toString() : null;
 	        String enddate = paraMap.get("enddate") != null ? paraMap.get("enddate").toString() : null;
 	        String storeids =  paraMap.get("storeids") != null ? paraMap.get("storeids").toString() : null;
 	       String limitcond =  paraMap.get("limitcond") != null ? paraMap.get("limitcond").toString() : null;
 	        if(StringUtils.isBlank(startdate)||StringUtils.isBlank(enddate)||StringUtils.isBlank(storeids)||
 	            StringUtils.isBlank(limitcond)){
 	        		return new RestResponse(EnumRespStatus.DATA_RELANOCOND3);
 	        }
	        List<Map<String, String>> list = relationService.queryRelationsStoreByDate(paraMap);
			List<Map<String, Object>> sumlist = relationService.queryRelationsStoreSumByDate(paraMap);
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
    
    @RequestMapping(value = "rest/queryRelationsEmployeeByDate",method = RequestMethod.POST)
    public RestResponse queryRelationsEmployeeByDate(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
       		String startdate = paraMap.get("begindate") != null ? paraMap.get("begindate").toString() : null;
 	        String enddate = paraMap.get("enddate") != null ? paraMap.get("enddate").toString() : null;
 	        String employeeno =  paraMap.get("employeeno") != null ? paraMap.get("employeeno").toString() : null;
	        if(StringUtils.isBlank(startdate)||StringUtils.isBlank(enddate)||StringUtils.isBlank(employeeno)){
	        	return new RestResponse(EnumRespStatus.DATA_CSNOCOND4);
	        }
	        List<Map<String, String>> list = relationService.queryRelationsEmployeeByDate(paraMap);
			
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
    
    
    
    /**
     * 社区动态：拜访记录总数
     */
    @RequestMapping(value = "rest/queryRelationsStoreSumByDate",method = RequestMethod.POST)
    public RestResponse queryRelationsStoreSumByDate(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		String startdate = paraMap.get("begindate") != null ? paraMap.get("begindate").toString() : null;
 	        String enddate = paraMap.get("enddate") != null ? paraMap.get("enddate").toString() : null;
 	        String storeids =  paraMap.get("storeids") != null ? paraMap.get("storeids").toString() : null;
 	        if(StringUtils.isBlank(startdate)||StringUtils.isBlank(enddate)||StringUtils.isBlank(storeids)){
 	        		return new RestResponse(EnumRespStatus.DATA_RELANOCOND2);
 	        }
	        List<Map<String, Object>> list = relationService.queryRelationsStoreSumByDate(paraMap);
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
