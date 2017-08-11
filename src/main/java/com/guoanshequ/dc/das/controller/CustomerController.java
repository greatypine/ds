package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.CustomerService;
import com.guoanshequ.dc.das.service.HumanresourceService;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author CaoPs
* @date 2017年3月10日
* @version 1.0
* 说明: 单体画像接口调用处理
*/ 
@RestController
@ResponseBody
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @Autowired
    HumanresourceService humanresourceService;
    @Autowired
    StoreService storeService;
    @Autowired
    TopDataService topDataService;

    private static final Logger logger = LogManager.getLogger(CustomerService.class);
    
    /**
     * 按国安侠统计拜访记录总量
     */
    @RequestMapping(value = "rest/queryCustomers",method = RequestMethod.POST)
    public RestResponse queryCustomers(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
	    	String yearmonth = paraMap.get("yearmonth") != null ? paraMap.get("yearmonth").toString() : null;
	        String grade = paraMap.get("grade") != null ? paraMap.get("grade").toString() :null; 
	        if(StringUtils.isBlank(yearmonth)||StringUtils.isBlank(grade)){
	        	return new RestResponse(EnumRespStatus.DATA_CSNOCOND);
	        }
	    	//得到上月的月初月末日期
	    	Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
	    	//上月月初日期
	    	String begindate = datemap.get("first");
	    	//获取上月形式，年份月份：YYYY-MM
	    	String preYearMonth = begindate.substring(0, 7);
	        List<Map<String, String>> list = null;
	    	if(yearmonth.compareTo(preYearMonth)>0){//请求月份大于绩效月份则取实时数据
		        if("1".equals(grade)){
		        	list = customerService.queryFirst(paraMap);
		        }else if("2".equals(grade)){
		        	list = customerService.querySecond(paraMap);
		        }else if("3".equals(grade)){
		        	list = customerService.queryThird(paraMap);
		        }
	    	}else{//请求月份小于等于绩效月份则取非实时表数据
		        if("1".equals(grade)){
		        	list = topDataService.queryCusgrade1OnTop(paraMap);
		        }else if("2".equals(grade)){
		        	list = topDataService.queryCusgrade2OnTop(paraMap);
		        }else if("3".equals(grade)){
		        	list = topDataService.queryCusgrade3OnTop(paraMap);
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
    
    @RequestMapping(value = "rest/queryCustomersByStore",method = RequestMethod.POST)
    public RestResponse queryCustomersByStore(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
	    	String yearmonth = paraMap.get("yearmonth") != null ? paraMap.get("yearmonth").toString() : null;
	        String grade = paraMap.get("grade") != null ? paraMap.get("grade").toString() :null;
	        if(StringUtils.isBlank(yearmonth)||StringUtils.isBlank(grade)){
	        	return new RestResponse(EnumRespStatus.DATA_CSNOCOND);
	        }
	    	//得到上月的月初月末日期
	    	Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
	    	//上月月初日期
	    	String begindate = datemap.get("first");
	    	//获取上月形式，年份月份：YYYY-MM
	    	String preYearMonth = begindate.substring(0, 7);
	        List<Map<String, String>> list = null;
	        if(yearmonth.compareTo(preYearMonth)>0){//请求月份大于绩效月份则取实时数据
		        if("1".equals(grade)){
		        	list = customerService.queryFirstByStore(paraMap);
		        }else if("2".equals(grade)){
		        	list = customerService.querySecondByStore(paraMap);
		        }else if("3".equals(grade)){
		        	list = customerService.queryThirdByStore(paraMap);
		        }
	        }else{//请求月份小于等于绩效月份则取非实时表数据
		        if("1".equals(grade)){
		        	list = topDataService.queryStoreCusgrade1OnTop(paraMap);
		        }else if("2".equals(grade)){
		        	list = topDataService.queryStoreCusgrade2OnTop(paraMap);
		        }else if("3".equals(grade)){
		        	list = topDataService.queryStoreCusgrade3OnTop(paraMap);
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
     * 社区动态：客户画像二档表格明细，按日期实时查询
     */
    @RequestMapping(value = "rest/queryCustomerSecondStoreByDate",method = RequestMethod.POST)
    public RestResponse queryCustomerSecondStoreByDate(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
       		String startdate = paraMap.get("begindate") != null ? paraMap.get("begindate").toString() : null;
 	        String enddate = paraMap.get("enddate") != null ? paraMap.get("enddate").toString() : null;
 	        String storeids =  paraMap.get("storeids") != null ? paraMap.get("storeids").toString() : null;
	        if(StringUtils.isBlank(startdate)||StringUtils.isBlank(enddate)||StringUtils.isBlank(storeids)){
	        	return new RestResponse(EnumRespStatus.DATA_CSNOCOND3);
	        }
	    	List<Map<String, String>> list = customerService.queryCustomerSecondStoreByDate(paraMap);
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
     * 社区动态：页面圆圈总数
     */
    @RequestMapping(value = "rest/queryCustomerSecondStoreSumByDate",method = RequestMethod.POST)
    public RestResponse queryCustomerSecondStoreSumByDate(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
       		String startdate = paraMap.get("begindate") != null ? paraMap.get("begindate").toString() : null;
 	        String enddate = paraMap.get("enddate") != null ? paraMap.get("enddate").toString() : null;
 	        String storeids =  paraMap.get("storeids") != null ? paraMap.get("storeids").toString() : null;
	        if(StringUtils.isBlank(startdate)||StringUtils.isBlank(enddate)||StringUtils.isBlank(storeids)){
	        	return new RestResponse(EnumRespStatus.DATA_CSNOCOND3);
	        }
	    	List<Map<String, String>> list = customerService.queryCustomerSecondStoreSumByDate(paraMap);
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
