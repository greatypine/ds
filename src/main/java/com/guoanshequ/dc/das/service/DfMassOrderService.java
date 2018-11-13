package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.DfMassOrderMapper;
import com.guoanshequ.dc.das.model.DfMassOrder;
import com.guoanshequ.dc.das.model.ImsTbsdgds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("DfMassOrderService")
@Transactional(value = "master", rollbackFor = Exception.class)
public class DfMassOrderService {

	@Autowired
	DfMassOrderMapper dfMassOrderDao;
	
	public String queryMaxSignedTime(){
		return dfMassOrderDao.queryMaxSignedTime();
	}

	public String queryMaxReturnTime(){
		return dfMassOrderDao.queryMaxReturnTime();
	}

	public Integer addDfMassOrderDaily(DfMassOrder dfMassOrder) {
		return dfMassOrderDao.addDfMassOrderDaily(dfMassOrder);
	}
	
	public Integer addDfMassOrderMonthly(DfMassOrder dfMassOrder) {
		return dfMassOrderDao.addDfMassOrderMonthly(dfMassOrder);
	}
	
	public Integer addDfMassOrderTotal(DfMassOrder dfMassOrder) {
		return dfMassOrderDao.addDfMassOrderTotal(dfMassOrder);
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
	
	public Integer updateCustomerOrderDaily(Map<String, String> paraMap){
		return dfMassOrderDao.updateCustomerOrderDaily(paraMap);
	}
	
	public Integer updateCustomerOrderMonthly(Map<String, String> paraMap){
		return dfMassOrderDao.updateCustomerOrderMonthly(paraMap);
	}
	
	public Integer updateCustomerOrderTotal(Map<String, String> paraMap){
		return dfMassOrderDao.updateCustomerOrderTotal(paraMap);
	}
	
	public List<DfMassOrder> queryMassOrderByDate(Map<String, String> paraMap){
        return dfMassOrderDao.queryMassOrderByDate(paraMap);
    }
	
	public Integer updateOrderVillageCodeDaily(Map<String, String> paraMap){
		return dfMassOrderDao.updateOrderVillageCodeDaily(paraMap);
	}
	
	public Integer updateOrderVillageCodeMonthly(Map<String, String> paraMap){
		return dfMassOrderDao.updateOrderVillageCodeMonthly(paraMap);
	}
	
	public Integer updateOrderVillageCodeTotal(Map<String, String> paraMap){
		return dfMassOrderDao.updateOrderVillageCodeTotal(paraMap);
	}

	public String queryOrersnByOrderId(Map<String, String> paraMap){
		return dfMassOrderDao.queryOrersnByOrderId(paraMap);
	}
	
	public Map<String, String> queryVillageAreaCodeByOrdersn(String orderSn){
		return dfMassOrderDao.queryVillageAreaCodeByOrdersn(orderSn);
	}

	public List<Map<String, String>> queryXBorderBySignTime (Map<String, String> paraMap){
		return dfMassOrderDao.queryXBorderBySignTime(paraMap);
	}
	
	public Integer updateXBorderTagDailyById(Map<String, String> paraMap){
		return dfMassOrderDao.updateXBorderTagDailyById(paraMap);
	}
	
	public Integer updateXBorderTagMonthlyById(Map<String, String> paraMap){
		return dfMassOrderDao.updateXBorderTagMonthlyById(paraMap);
	}
	
	public Integer updateXBorderTagTotalById(Map<String, String> paraMap){
		return dfMassOrderDao.updateXBorderTagTotalById(paraMap);
	}
	
	public List<Map<String, String>> queryXBCustomerBySignTime (Map<String, String> paraMap){
		return dfMassOrderDao.queryXBCustomerBySignTime(paraMap);
	}
	
	public 	Integer addXBUserTag(Map<String, String> paraMap){
		return dfMassOrderDao.addXBUserTag(paraMap);
	}
	
	public List<Map<String, String>> queryOrderIdBySignTime (Map<String, String> paraMap){
		return dfMassOrderDao.queryOrderIdBySignTime(paraMap);
	}

	public DfMassOrder queryMassOrder (Map<String, String> paraMap) {
		return dfMassOrderDao.queryMassOrder(paraMap);
	}
	
	public Integer updateKSorderTagDailyByEshopId(Map<String, String> paraMap){
		return dfMassOrderDao.updateKSorderTagDailyByEshopId(paraMap);
	}
	
	public Integer updateKSorderTagMonthlyByEshopId(Map<String, String> paraMap){
		return dfMassOrderDao.updateKSorderTagMonthlyByEshopId(paraMap);
	}
	
	public Integer updateKSorderTagTotalByEshopId(Map<String, String> paraMap){
		return dfMassOrderDao.updateKSorderTagTotalByEshopId(paraMap);
	}
	
	public List<Map<String, String>> queryMemberCustomerBySignTime (Map<String, String> paraMap){
		return dfMassOrderDao.queryMemberCustomerBySignTime(paraMap);
	}
	
	public List<Map<String, String>> queryMemberOrderBySignTime (Map<String, String> paraMap){
		return dfMassOrderDao.queryMemberOrderBySignTime(paraMap);
	}
	
	public Integer updateMemberCityDay(Map<String, String> paraMap){
		return dfMassOrderDao.updateMemberCityDay(paraMap);
	}
	
	public Integer updateOrderDistribution(Map<String, String> paraMap){
		return dfMassOrderDao.updateOrderDistribution(paraMap);
	}
	
	public Integer updateActivityProductForDaily(Map<String, String> paraMap) {
		return dfMassOrderDao.updateActivityProductForDaily(paraMap);
	}
	
	public Integer updateActivityProductForMonthly(Map<String, String> paraMap) {
		return dfMassOrderDao.updateActivityProductForMonthly(paraMap);
	}
	
	public Integer updateActivityProductForTotal(Map<String, String> paraMap) {
		return dfMassOrderDao.updateActivityProductForTotal(paraMap);
	}
	
	public Integer updateActivityServiceForDaily(Map<String, String> paraMap) {
		return dfMassOrderDao.updateActivityServiceForDaily(paraMap);
	}
	
	public Integer updateActivityServiceForMonthly(Map<String, String> paraMap) {
		return dfMassOrderDao.updateActivityServiceForMonthly(paraMap);
	}
	
	public Integer updateActivityServiceForTotal(Map<String, String> paraMap) {
		return dfMassOrderDao.updateActivityServiceForTotal(paraMap);
	}
	
	public Integer updateActivityGroupOnForDaily(Map<String, String> paraMap) {
		return dfMassOrderDao.updateActivityGroupOnForDaily(paraMap);
	}
	
	public Integer updateActivityGroupOnForMonthly(Map<String, String> paraMap) {
		return dfMassOrderDao.updateActivityGroupOnForMonthly(paraMap);
	}
	
	public Integer updateActivityGroupOnForTotal(Map<String, String> paraMap) {
		return dfMassOrderDao.updateActivityGroupOnForTotal(paraMap);
	}

	public List<DfMassOrder> queryMassOrderListByDate(Map<String, String> paraMap) {
		return dfMassOrderDao.queryMassOrderListByDate(paraMap);
	}
	
	public List<DfMassOrder> queryMassOrderIsnullListByPreDate(Map<String, String> paraMap) {
		return dfMassOrderDao.queryMassOrderIsnullListByPreDate(paraMap);
	}
	
	public Integer updateOrderProfitOfDaily(DfMassOrder dfMassOrder){
		return dfMassOrderDao.updateOrderProfitOfDaily(dfMassOrder);
	}
	
	public Integer updateOrderProfitOfMonthly(DfMassOrder dfMassOrder){
		return dfMassOrderDao.updateOrderProfitOfMonthly(dfMassOrder);
	}
	
	public Integer updateOrderProfitOfTotal(DfMassOrder dfMassOrder){
		return dfMassOrderDao.updateOrderProfitOfTotal(dfMassOrder);
	}
	
	public ImsTbsdgds queryCostPriceBySigndateCode(Map<String, String> paraMap) {
		return dfMassOrderDao.queryCostPriceBySigndateCode(paraMap);
	}
	
	public Integer queryIsSaleCardEshop(String eshop_id) {
		return dfMassOrderDao.queryIsSaleCardEshop(eshop_id);
	}
	
	public Integer updateOrderCouponOfDaily(DfMassOrder dfMassOrder){
		return dfMassOrderDao.updateOrderCouponOfDaily(dfMassOrder);
	}
	
	public Integer updateOrderCouponOfMonthly(DfMassOrder dfMassOrder){
		return dfMassOrderDao.updateOrderCouponOfMonthly(dfMassOrder);
	}
	
	public Integer updateOrderCouponOfTotal(DfMassOrder dfMassOrder){
		return dfMassOrderDao.updateOrderCouponOfTotal(dfMassOrder);
	}
	
	public List<DfMassOrder> queryGroupCouponOrderByDate(Map<String, String> paraMap){
		return dfMassOrderDao.queryGroupCouponOrderByDate(paraMap);
	}
	
	public Integer updatePostingOrderProfitOfDaily(DfMassOrder dfMassOrder){
		return dfMassOrderDao.updatePostingOrderProfitOfDaily(dfMassOrder);
	}

	public Integer updatePostingOrderProfitOfMonthly(DfMassOrder dfMassOrder){
		return dfMassOrderDao.updatePostingOrderProfitOfMonthly(dfMassOrder);
	}
	
	public Integer updatePostingOrderProfitOfTotal(DfMassOrder dfMassOrder){
		return dfMassOrderDao.updatePostingOrderProfitOfTotal(dfMassOrder);
	}
	
	public Integer updatePercentSellProfitOfDaily(DfMassOrder dfMassOrder){
		return dfMassOrderDao.updatePercentSellProfitOfDaily(dfMassOrder);
	}

	public Integer updatePercentSellProfitOfMonthly(DfMassOrder dfMassOrder){
		return dfMassOrderDao.updatePercentSellProfitOfMonthly(dfMassOrder);
	}
	
	public Integer updatePercentSellProfitOfTotal(DfMassOrder dfMassOrder){
		return dfMassOrderDao.updatePercentSellProfitOfTotal(dfMassOrder);
	}	
	
	public Integer updateMarktingTagOfDaily(DfMassOrder dfMassOrder){
		return dfMassOrderDao.updateMarktingTagOfDaily(dfMassOrder);
	}

	public Integer updateMarktingTagOfMonthly(DfMassOrder dfMassOrder){
		return dfMassOrderDao.updateMarktingTagOfMonthly(dfMassOrder);
	}
	
	public Integer updateMarktingTagOfTotal(DfMassOrder dfMassOrder){
		return dfMassOrderDao.updateMarktingTagOfTotal(dfMassOrder);
	}	
	
}
