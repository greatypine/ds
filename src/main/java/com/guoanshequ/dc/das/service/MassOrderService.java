package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.slave.MassOrderMapper;
import com.guoanshequ.dc.das.model.DfMassOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("MassOrderService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class MassOrderService {

	@Autowired
	MassOrderMapper massOrderDao;
	
	public List<DfMassOrder> queryMassOrderByDate(Map<String, String> paraMap){
        return massOrderDao.queryMassOrderByDate(paraMap);
    }
	

	public String queryMaxReturnTime(){
		return massOrderDao.queryMaxReturnTime();
	}
	
	public List<Map<String, String>> queryReturnOrderByDate(Map<String, String> paraMap){
		return massOrderDao.queryReturnOrderByDate(paraMap);
	}
	
	public String queryMaxQueryTime(){
		return massOrderDao.queryMaxQueryTime();
	}
	
	public List<Map<String, String>> queryCustomerTradeTask(Map<String, String> paraMap){
		return massOrderDao.queryCustomerTradeTask(paraMap);
	}

	public List<Map<String, String>> queryReturnOrders(Map<String, String> paraMap){
		return massOrderDao.queryReturnOrders(paraMap);
	}
	
	public List<Map<String, String>> queryKSeshopByName(){
		return massOrderDao.queryKSeshopByName();
	}
}
