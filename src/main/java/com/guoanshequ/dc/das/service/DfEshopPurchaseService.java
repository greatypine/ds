package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.DfEshopPurchaseMapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 
* @author CaoPs
* @date 2017年10月17日
* @version 1.0
* 说明:
 */
@Service("dfEshopPurchaseService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class DfEshopPurchaseService {

    @Autowired
   DfEshopPurchaseMapper dfEshopPurchaseDao;
    
    /**
     * 平台门店编号storeno字段，用逗号分开 
     */
    public String queryEshopIds(){
    	List<String> eshopPurchaseList = dfEshopPurchaseDao.queryEshopIds();
    	String eshopIdStr = String.join(",", eshopPurchaseList);
    	return eshopIdStr;
    }
}
