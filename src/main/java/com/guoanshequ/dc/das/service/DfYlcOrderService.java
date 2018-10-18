package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.DfYlcOrderMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("DfYlcOrderService")
@Transactional(value = "master", rollbackFor = Exception.class)
public class DfYlcOrderService {

	@Autowired
	DfYlcOrderMapper dfYlcOrderDao;
	
	public Integer addYlcOrder(Map<String, String> paraMap) {
		return dfYlcOrderDao.addYlcOrder(paraMap);
	}
	
	public Integer addYlcOrderInfo(Map<String, String> paraMap) {
		return dfYlcOrderDao.addYlcOrderInfo(paraMap);
	}
	

	public String queryYlcOrder(Map<String, String> paraMap) {
		/**
		 * @author wuxinxin
		 * 2018年10月15日
		 */
		return dfYlcOrderDao.queryYlcOrder(paraMap);
		
	}
	
	public String queryYlcOrderInfo(Map<String, String> paraMap) {
		/**
		 * @author wuxinxin
		 * 2018年10月15日
		 */
		return dfYlcOrderDao.queryYlcOrderInfo(paraMap);
		
	}
	
}
