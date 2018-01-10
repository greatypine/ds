package com.guoanshequ.dc.das.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.DfMassOrderMapper;
import com.guoanshequ.dc.das.model.DfMassOrder;

@Service("DfMassOrderService")
@Transactional(value = "master", rollbackFor = Exception.class)
public class DfMassOrderService {

	@Autowired
	DfMassOrderMapper dfMassOrderDao;
	
	public String queryMaxSignedTime(){
		return dfMassOrderDao.queryMaxSignedTime();
	}

	public Integer addDfMassOrderDaily(List<DfMassOrder> dfMassOrderList) {
		return dfMassOrderDao.addDfMassOrderDaily(dfMassOrderList);
	}
	
	public Integer addDfMassOrderMonthly(List<DfMassOrder> dfMassOrderList) {
		return dfMassOrderDao.addDfMassOrderMonthly(dfMassOrderList);
	}
	
	public Integer addDfMassOrderTotal(List<DfMassOrder> dfMassOrderList) {
		return dfMassOrderDao.addDfMassOrderTotal(dfMassOrderList);
	}
	
	public Integer deleteDfMassOrderDaily(String dateTime){
		return dfMassOrderDao.deleteDfMassOrderDaily(dateTime);
	}
	
	public Integer deleteDfMassOrderMonthly(String dateTime){
		return dfMassOrderDao.deleteDfMassOrderMonthly(dateTime);
	}
	
	public Integer updateDfMassOrderDaily(Map<String, String> paraMap){
		return dfMassOrderDao.updateDfMassOrderDaily(paraMap);
	}
	
	public Integer updateDfMassOrderMonthly(Map<String, String> paraMap){
		return dfMassOrderDao.updateDfMassOrderMonthly(paraMap);	
	}
	
	public Integer updateDfMassOrderTotal(Map<String, String> paraMap){
		return dfMassOrderDao.updateDfMassOrderTotal(paraMap);
	}
	
	public Integer updateAbnormalOrder(Map<String, String> paraMap){
		return dfMassOrderDao.updateAbnormalOrder(paraMap);
	}
	
	public Integer updateAbnormalOrderToNormal(Map<String, String> paraMap){
		return dfMassOrderDao.updateAbnormalOrderToNormal(paraMap);
	}

}
