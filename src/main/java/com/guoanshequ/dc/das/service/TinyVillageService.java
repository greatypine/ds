package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.TinyVillageMapper;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 查询小区相关信息
 */
@Service("TinyVillageService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TinyVillageService {

    @Autowired
    TinyVillageMapper tinyVillageDao;

    public Map<String, Object> queryTinyidByCode(String code) throws Exception{

        return tinyVillageDao.queryTinyidByCode(code);
    }
}
