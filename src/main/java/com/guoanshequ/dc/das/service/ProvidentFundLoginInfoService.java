package com.guoanshequ.dc.das.service;

import com.alibaba.druid.support.json.JSONParser;
import com.alibaba.fastjson.JSONArray;
import com.guoanshequ.dc.das.dao.master.ProvidentFundLoginInfoMapper;
import com.guoanshequ.dc.das.model.ProvidentFundLoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.service
 * @Description:
 * @Author: gbl
 * @CreateDate: 2018/12/25 13:53
 */
@Service("ProvidentFundLoginInfoService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class ProvidentFundLoginInfoService {

    @Autowired
    ProvidentFundLoginInfoMapper providentFundLoginInfoMapper;

    /**
     * @Description 保存公积金登录信息
     * @author gbl
     * @date 2018/12/25 13:56
     **/

    public void saveProvidenFundLoginInfo(JSONArray jsonArray){


//        providentFundLoginInfoMapper.saveProvidentFundLoginInfo(providentFundLoginInfo);
    }


    /**
     * @Description 查询公积金登录信息
     * @author gbl
     * @date 2018/12/25 14:03
     **/

    public List<ProvidentFundLoginInfo> queryProvidentFundLoginInfo(Map<String,Object> param){

        return providentFundLoginInfoMapper.queryProvidentFundLoginInfo(param);
    }
}
