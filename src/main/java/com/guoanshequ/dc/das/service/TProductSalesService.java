package com.guoanshequ.dc.das.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.TProductCityMapper;

@Service("TProductSalesService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TProductSalesService {

	@Autowired
	TProductCityMapper tProductCityDao;
	
	public Map<String, String> queryTProductIsExist(Map<String, String> paraMap){
		return tProductCityDao.queryTProductIsExist(paraMap);
	}
	
	public int addTProductSales(Map<String, String> paraMap){
		return tProductCityDao.addTProductSales(paraMap);
	}
	
	public int updateTProductSalesCount(Map<String, String> paraMap){
		return tProductCityDao.updateTProductSalesCount(paraMap);
	}
	
	public int updateTProductSalesNames(Map<String, String> paraMap){
		return tProductCityDao.updateTProductSalesNames(paraMap);
	}
}
