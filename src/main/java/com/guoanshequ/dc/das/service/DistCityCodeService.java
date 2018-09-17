package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.DistCityCodeMapper;
import com.guoanshequ.dc.das.model.DistCityCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sunning on 2018/9/6.
 */
@Service("DistCityCodeService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class DistCityCodeService {
    @Autowired
    private DistCityCodeMapper distCityCodeDao;

    /**
     * 获取所有的城市信息
     * @return
     */
    public List<DistCityCode> getAllDistCityCode(){
        return distCityCodeDao.getAllDistCityCode();
    }


}
