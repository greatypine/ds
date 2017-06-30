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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
     * 按国安侠统计拜访记录总数
     */
    @RequestMapping(value = "rest/queryRelations")
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
     
    @RequestMapping(value = "rest/queryRelationsByStore")
    public RestResponse queryRelationsByStore(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
	        String yearmonth = paraMap.get("yearmonth") != null ? paraMap.get("yearmonth").toString() : null;
	        String storename = paraMap.get("storename") != null ? paraMap.get("storename").toString() : null;
	        String datatype = paraMap.get("datatype") != null ? paraMap.get("datatype").toString() : null;
	        if(StringUtils.isBlank(yearmonth)||StringUtils.isBlank(storename)){
	        	return new RestResponse(EnumRespStatus.DATA_RELANOCOND1);
	        }
	        if(StringUtils.isBlank(datatype)){
	        	return new RestResponse(EnumRespStatus.DATA_RELAHUMANTYPE);
	        }
	        List<Map<String, String>> personList = null;
			if("0".equals(datatype)){//实时人员数据
				personList = humanresourceService.queryHumanresources(paraMap);
			}else if("1".equals(datatype)){//上月异动人员数据
				personList = humanresourceService.queryPreHumanresources(paraMap);
			}
	        Map<Object, Object> relationMap =new HashMap<Object, Object>(); 
	        Integer num =0;
	        Integer store_id=1;
	        List<Map<String, String>> storeList = storeService.queryStores(paraMap);
	        if(!storeList.isEmpty()){
	        	for(int k=0;k<storeList.size();k++){
	        		Object storeObj = storeList.get(k).get("store_id");
	        		store_id = Integer.parseInt(storeObj.toString());
	        	}
	        }
	        if(!personList.isEmpty()){
		        for(int i=0;i<personList.size();i++){
		        	String employee_no = personList.get(i).get("employeeno");
		        	paraMap.put("employee_no", employee_no);
		        	List<Map<String, String>> relationlist = relationService.queryRelations(paraMap);
		        	if(!relationlist.isEmpty()){
		        		for(int j=0;j<relationlist.size();j++){
		        			Object obj = relationlist.get(j).get("num");
		        			num = num + Integer.parseInt(obj.toString());
		        		}
		        	}
		        }
	        }
	        relationMap.put("store_id", store_id);
	        relationMap.put("storename", storename);
	        relationMap.put("num", num);
	        List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();
	        list.add(relationMap);
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
    */
    
    @RequestMapping(value = "rest/queryRelationsByStore")
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
}
