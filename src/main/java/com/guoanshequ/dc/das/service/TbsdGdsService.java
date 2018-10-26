package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.ims.TbsdgdsMapper;
import com.guoanshequ.dc.das.model.ImsTbsdgds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 
* @author CaoPs
* @date 2017年9月24日
* @version 1.0
* 说明:
 */
@Service("TbsdGdsService")
@Transactional(value = "ims",rollbackFor = Exception.class)
public class TbsdGdsService {

    @Autowired
    TbsdgdsMapper productCostDao;

    public List<ImsTbsdgds> queryTbsDGdsByTime(Map<String, String> paraMap) throws Exception{
        return productCostDao.queryTbsDGdsByTime(paraMap);
    }
    
}
