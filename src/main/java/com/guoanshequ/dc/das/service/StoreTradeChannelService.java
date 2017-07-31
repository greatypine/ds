package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.slave.StoreTradeChannelMapper;

import java.util.List;
import java.util.Map;

/**
 * 
* @author CaoPs
* @date 2017年7月24日
* @version 1.0
* 说明:门店交易额（按频道）
 */
@Service("StoreTradeChannelService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class StoreTradeChannelService {

    @Autowired
    StoreTradeChannelMapper storeTradeChannelDao;

    public List<Map<String, String>> queryStoreTradeChannels(Map<String, String> paraMap) throws Exception{
        
    	return storeTradeChannelDao.queryStoreTradeChannels(paraMap);
    }
}
