package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.StoreMapper;

import com.guoanshequ.dc.das.model.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
* @author CaoPs
* @date 2017年3月9日
* @version 1.0
* 说明: 门店表业务处理
*/ 
@Service("StoreService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class StoreService {

    @Autowired
    StoreMapper storeDao;

    public List<Map<String, String>> queryStores(Map<String, String> paraMap) throws Exception{
        return storeDao.queryStores(paraMap);
    }

    public String queryStoreNumbers(){
    	return storeDao.queryStoreNumbers();
    }

    public List<Store> findStoreToCityNameAndOpenShopTime(String city_name){
        return storeDao.findStoreToCityNameAndOpenShopTime(city_name);
    }
    public void updateStore(Store store){
         storeDao.updateStore(store);
    }

    /**
     * 根据城市获取当前有门店开店时间的最大序号
     * @param city_name
     * @return
     */
    public int findMaxStoreOreNumber(String city_name){
        List<Store> openShopTime = storeDao.findStoreToCityNameAndOpenShopTime(city_name);
        if(openShopTime!=null&&openShopTime.size()>0){
            return openShopTime.size();
        }
        return 0;
    }

    /**
     * 根据城市获取当前城市没有开店时间的门店
     * @param city_name
     * @return
     */
    public List<Store> findStoreToCityNameAndOpenShopTimeIsNull(String city_name){
        return storeDao.findStoreToCityNameAndOpenShopTimeIsNull(city_name);
    }
    /**
     * 获取有开店时间，但是小于当前时间的门店
     * @return
     */
    public List<Store> findStoreDateEstate(){
        return storeDao.findStoreDateEstate();
    }

    public Store findStoreBystoreno(String storeno){
       return storeDao.findStoreBycode(storeno);
    }


}
