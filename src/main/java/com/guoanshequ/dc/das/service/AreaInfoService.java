package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.AreaInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 查询片区相关信息
 */
@Service("AreaInfoService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class AreaInfoService {

    @Autowired
    AreaInfoMapper areaInfoDao;

    public String queryAreaNoByTinyNo(String code) throws Exception{

        return areaInfoDao.queryAreaNoByTinyNo(code);
    }
}
