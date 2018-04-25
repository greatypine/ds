package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.DsOpeGmvStoreChannelMapper;

import java.util.Map;



/**
 * 
* @author CaoPs
* @date 2018年4月23日
* @version 1.0
* 说明:运营数据--门店交易额按频道按月度取数据
 */
@Service("DsOpeGmvStoreChannelService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class DsOpeGmvStoreChannelService {

    @Autowired
    DsOpeGmvStoreChannelMapper dsOpeGmvStoreChannelDao;

    public int deleteByYearMonth(Map<String, String> paraMap){
    	return dsOpeGmvStoreChannelDao.deleteByYearMonth(paraMap);
    }
    
    public int addDsOpeGmvStoreChannelMonthByMassOrder(Map<String, String> paraMap){
        return dsOpeGmvStoreChannelDao.addDsOpeGmvStoreChannelMonthByMassOrder(paraMap);
    }
    
}
