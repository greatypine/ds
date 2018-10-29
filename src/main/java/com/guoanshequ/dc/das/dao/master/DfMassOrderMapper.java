package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.DfMassOrder;
import com.guoanshequ.dc.das.model.ImsTbsdgds;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@DataSource("master")
public interface DfMassOrderMapper {

	String queryMaxSignedTime();

	String queryMaxReturnTime();

	Integer addDfMassOrderDaily(DfMassOrder dfMassOrder);
	
	Integer addDfMassOrderMonthly(DfMassOrder dfMassOrder);
	
	Integer addDfMassOrderTotal(DfMassOrder dfMassOrder);
	
	Integer deleteDfMassOrderDaily(String dateTime);
	
	Integer deleteDfMassOrderMonthly(String dateTime);
	
	Integer updateDfMassOrderDaily(Map<String, String> paraMap);
	
	Integer updateDfMassOrderMonthly(Map<String, String> paraMap);
	
	Integer updateDfMassOrderTotal(Map<String, String> paraMap);
	
	Integer updateAbnormalOrder(Map<String, String> paraMap);
	
	Integer updateAbnormalOrderToNormal(Map<String, String> paraMap);
	
	Integer updateCustomerOrderDaily(Map<String, String> paraMap);
	
	Integer updateCustomerOrderMonthly(Map<String, String> paraMap);
	
	Integer updateCustomerOrderTotal(Map<String, String> paraMap);
	
	List<DfMassOrder> queryMassOrderByDate(Map<String, String> paraMap);
	
	Integer updateOrderVillageCodeDaily(Map<String, String> paraMap);
	
	Integer updateOrderVillageCodeMonthly(Map<String, String> paraMap);
	
	Integer updateOrderVillageCodeTotal(Map<String, String> paraMap);

	String queryOrersnByOrderId (Map<String, String> paraMap);
	
	Map<String, String> queryVillageAreaCodeByOrdersn (String orderSn);
	
	List<Map<String, String>> queryXBorderBySignTime (Map<String, String> paraMap);
	
	Integer updateXBorderTagDailyById(Map<String, String> paraMap);
	
	Integer updateXBorderTagMonthlyById(Map<String, String> paraMap);
	
	Integer updateXBorderTagTotalById(Map<String, String> paraMap);
	
	List<Map<String, String>> queryXBCustomerBySignTime (Map<String, String> paraMap);
	
	Integer addXBUserTag(Map<String, String> paraMap);
	
	List<Map<String, String>> queryOrderIdBySignTime (Map<String, String> paraMap);
	
	DfMassOrder queryMassOrder (Map<String, String> paraMap);
	
	Integer updateKSorderTagDailyByEshopId(Map<String, String> paraMap);
	
	Integer updateKSorderTagMonthlyByEshopId(Map<String, String> paraMap);
	
	Integer updateKSorderTagTotalByEshopId(Map<String, String> paraMap);
	
	List<Map<String, String>> queryMemberCustomerBySignTime (Map<String, String> paraMap);
	
	List<Map<String, String>> queryMemberOrderBySignTime (Map<String, String> paraMap);
	
	Integer updateMemberCityDay(Map<String, String> paraMap);
	
	Integer updateOrderDistribution(Map<String, String> paraMap);

	List<DfMassOrder> queryMassOrderListByDate(Map<String, String> paraMap);
	
	List<DfMassOrder> queryMassOrderIsnullListByPreDate(Map<String, String> paraMap);
	
	Integer updateOrderProfitOfDaily(DfMassOrder dfMassOrder);
	
	Integer updateOrderProfitOfMonthly(DfMassOrder dfMassOrder);
	
	Integer updateOrderProfitOfTotal(DfMassOrder dfMassOrder);
	
	Integer updateActivityProductForDaily(Map<String, String> paraMap);
	
	Integer updateActivityProductForMonthly(Map<String, String> paraMap);
	
	Integer updateActivityProductForTotal(Map<String, String> paraMap);
	
	Integer updateActivityServiceForDaily(Map<String, String> paraMap);
	
	Integer updateActivityServiceForMonthly(Map<String, String> paraMap);
	
	Integer updateActivityServiceForTotal(Map<String, String> paraMap);
	
	Integer updateActivityGroupOnForDaily(Map<String, String> paraMap);
	
	Integer updateActivityGroupOnForMonthly(Map<String, String> paraMap);
	
	Integer updateActivityGroupOnForTotal(Map<String, String> paraMap);
	
	ImsTbsdgds queryCostPriceBySigndateCode(Map<String, String> paraMap);
	
	Integer queryIsSaleCardEshop(String eshop_id);
	
	Integer updateOrderCouponOfDaily(DfMassOrder dfMassOrder);
	
	Integer updateOrderCouponOfMonthly(DfMassOrder dfMassOrder);
	
	Integer updateOrderCouponOfTotal(DfMassOrder dfMassOrder);
	
}
