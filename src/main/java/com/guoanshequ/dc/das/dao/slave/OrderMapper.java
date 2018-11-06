package com.guoanshequ.dc.das.dao.slave;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.Contract;
import com.guoanshequ.dc.das.model.OrderItemExtra;

@Repository
@DataSource("slave")
public interface OrderMapper{
	
	String queryIdByOrderSn(Map<String, String> paraMap);
	
	Map<String, Object> queryOrderAddressByOrderSn(Map<String, String> paraMap);
	
	String queryMaxSigntime(Map<String, String> paraMap);
	
	OrderItemExtra queryOrderRebateCouponById(Map<String, String> paraMap);
	
	String queryOrderCostPriceById(Map<String, String> paraMap);
	
	Contract queryOrderContractInfoById(String order_id);
}
