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
    
    /**
     * 按门店统计拜访记录总量
     
    @RequestMapping(value = "rest/queryCustomersByStore")
    public RestResponse queryCustomersByStore(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
	    	String yearmonth = paraMap.get("yearmonth") != null ? paraMap.get("yearmonth").toString() : null;
	        String grade = paraMap.get("grade") != null ? paraMap.get("grade").toString() :null; 
	        String storename = paraMap.get("storename") != null ? paraMap.get("storename").toString() :null; 
	        String datatype = paraMap.get("datatype") != null ? paraMap.get("datatype").toString() : null;
	        if(StringUtils.isBlank(yearmonth)||StringUtils.isBlank(grade)||StringUtils.isBlank(storename)){
	        	return new RestResponse(EnumRespStatus.DATA_CSNOCOND1);
	        }
	        if(StringUtils.isBlank(datatype)){
	        	return new RestResponse(EnumRespStatus.DATA_CSHUMANTYPE);
	        }
	        List<Map<String, String>> personList = null;
			if("0".equals(datatype)){//实时人员数据
				personList = humanresourceService.queryHumanresources(paraMap);
			}else if("1".equals(datatype)){//上月异动人员数据
				personList = humanresourceService.queryPreHumanresources(paraMap);
			}
			Integer num =0;
	        Integer store_id=1;
	        List<Map<String, String>> storeList = storeService.queryStores(paraMap);
	        if(!storeList.isEmpty()){
	        	for(int k=0;k<storeList.size();k++){
	        		Object storeObj = storeList.get(k).get("store_id");
	        		store_id = Integer.parseInt(storeObj.toString());
	        	}
	        }
	        List<Map<Object, Object>> customerlist = new ArrayList<Map<Object, Object>>();
	        Map<Object, Object> customerMap =new HashMap<Object, Object>(); 
	        if("1".equals(grade)){
	        	//Storelist = customerService.queryFirstByStore(paraMap);
		        if(!personList.isEmpty()){
			        for(int i=0;i<personList.size();i++){
			        	String employee_no = personList.get(i).get("employeeno");
			        	paraMap.put("employee_no", employee_no);
			        	List<Map<String, String>> relationlist = customerService.queryFirst(paraMap);
			        	if(!relationlist.isEmpty()){
			        		for(int j=0;j<relationlist.size();j++){
			        			Object obj = relationlist.get(j).get("datacount");
			        			num = num + Integer.parseInt(obj.toString());
			        		}
			        	}
			        }
		        }
	        }else if("2".equals(grade)){
	        	//Storelist = customerService.querySecondByStore(paraMap);
		        if(!personList.isEmpty()){
			        for(int i=0;i<personList.size();i++){
			        	String employee_no = personList.get(i).get("employeeno");
			        	paraMap.put("employee_no", employee_no);
			        	List<Map<String, String>> relationlist = customerService.querySecond(paraMap);
			        	if(!relationlist.isEmpty()){
			        		for(int j=0;j<relationlist.size();j++){
			        			Object obj = relationlist.get(j).get("datacount");
			        			num = num + Integer.parseInt(obj.toString());
			        		}
			        	}
			        }
		        }
	        }else if("3".equals(grade)){
	        	//Storelist = customerService.queryThirdByStore(paraMap);
		        if(!personList.isEmpty()){
			        for(int i=0;i<personList.size();i++){
			        	String employee_no = personList.get(i).get("employeeno");
			        	paraMap.put("employee_no", employee_no);
			        	List<Map<String, String>> relationlist = customerService.queryThird(paraMap);
			        	if(!relationlist.isEmpty()){
			        		for(int j=0;j<relationlist.size();j++){
			        			Object obj = relationlist.get(j).get("datacount");
			        			num = num + Integer.parseInt(obj.toString());
			        		}
			        	}
			        }
		        }
	        }
	        customerMap.put("store_id", store_id);
	        customerMap.put("storename", storename);
	        customerMap.put("datacount", num);
	        customerlist.add(customerMap);
	        if(null==customerlist||customerlist.isEmpty()){
	        	return new RestResponse(EnumRespStatus.DATA_NODATA);
	        }else{
	        	return new RestResponse(EnumRespStatus.DATA_OK,customerlist);
	        }
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }*/
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
     * 
     * TODO 按月查询门店用户画像 
     * 2017年8月4日
     * @author gaobaolei
     * @param paraMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "rest/getCustomerAmountByStoreOfMonth",method = RequestMethod.POST)
    public RestResponse getCustomerAmountByStoreOfMonth(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		//String queryDate = paraMap.get("queryDate") != null ? paraMap.get("queryDate").toString() : null;
	        String grade = paraMap.get("grade") != null ? paraMap.get("grade").toString() :null;
	        String storeId = paraMap.get("storeId") != null ? paraMap.get("storeId").toString() :null;
	        if(StringUtils.isBlank(grade)||StringUtils.isBlank(storeId)){
	        	return new RestResponse(EnumRespStatus.DATA_CSNOCOND);
	        }
	        
	        Map<String, Object> param = new HashMap<String,Object>();
	        param.put("grade",grade);
	        param.put("storeId",storeId);
	    	Integer  total = customerService.getCustomerAmountByStoreOfMonth(param);
	        if(null==total){
	        	return new RestResponse(EnumRespStatus.DATA_NODATA);
	        }else{
	        	return new RestResponse(EnumRespStatus.DATA_OK,total);
	        }
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    
    /**
     * 
     * TODO 按日查询门店用户画像 
     * 2017年8月4日
     * @author gaobaolei
     * @param paraMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "rest/getCustomerAmountByStoreOfDaily",method = RequestMethod.POST)
    public RestResponse getCustomerAmountByStoreOfDaily(@RequestBody Map<String, String> paraMap) throws Exception {
    	try{
    		//String queryDate = paraMap.get("queryDate") != null ? paraMap.get("queryDate").toString() : null;
	        String grade = paraMap.get("grade") != null ? paraMap.get("grade").toString() :null;
	        String storeId = paraMap.get("storeId") != null ? paraMap.get("storeId").toString() :null;
	        if(StringUtils.isBlank(grade)||StringUtils.isBlank(storeId)){
	        	return new RestResponse(EnumRespStatus.DATA_CSNOCOND);
	        }
	        
	        Map<String, Object> param = new HashMap<String,Object>();
	        param.put("grade",grade);
	        param.put("storeId",storeId);
	    	Integer  total = customerService.getCustomerAmountByStoreOfMonth(param);
	        if(null==total){
	        	return new RestResponse(EnumRespStatus.DATA_NODATA);
	        }else{
	        	return new RestResponse(EnumRespStatus.DATA_OK,total);
	        }
    	}catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
        }
    }
    
    
}
