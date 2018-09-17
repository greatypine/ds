package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.Store;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@DataSource("master")
public interface StoreMapper {

	List<Map<String, String>> queryStores(Map<String, String> paraMap);
	
	String queryStoreNumbers();

	/**
	 * @author:sunning
	 * 根据城市获取所有有开店时间的门店并根据开店时间正序排序
	 * @param city_name
	 * @return
     */
	List<Store> findStoreToCityNameAndOpenShopTime(String city_name);

	/**
	 * 修改门店
	 * @author:sunning
	 * @param store
     */
	void updateStore(Store store);

	/**
	 * 根据城市获取没有开店时间的所有门店
	 * @param city_name
	 * @return
     */
	List<Store> findStoreToCityNameAndOpenShopTimeIsNull(String city_name);

	/**
	 * 获取有开店时间，但是小于当前时间的门店
	 * @return
     */
	List<Store> findStoreDateEstate();

	//根据门店编号查询门店
	Store findStoreBycode(String storeno);

}
