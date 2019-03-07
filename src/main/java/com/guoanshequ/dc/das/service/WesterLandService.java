package com.guoanshequ.dc.das.service;

import com.alibaba.fastjson.JSONArray;
import com.guoanshequ.dc.das.dao.master.WesterLandProductMapper;
import com.guoanshequ.dc.das.dao.master.WesterLandTransactionMapper;
import com.guoanshequ.dc.das.dao.master.WesterLandUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.service
 * @Description:
 * @Author: gbl
 * @CreateDate: 2019/1/29 14:57
 */
@Service("WesterLandService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class WesterLandService {

    @Autowired
    private WesterLandUserMapper westerLandUserMapper;

    @Autowired
    private WesterLandProductMapper westerLandProductMapper;

    @Autowired
    private WesterLandTransactionMapper westerLandTransactionMapper;



    /**
     * @Description 保存保险交易信息
     * @author gbl
     * @date 2019/1/29 15:01
     **/

    public  void addWesterLandTransaction(JSONArray ja){

        westerLandTransactionMapper.deleteTransaction(ja.getJSONObject(0).getString("createDate"));
        for(int i=0; i<ja.size();i++){
            westerLandTransactionMapper.addTransaction(ja.getJSONObject(i));
        }
    }

    /**
     * @Description 删除指定日期的保险交易信息
     * @author gbl
     * @date 2019/1/29 15:04
     **/

    public void deleteWesterLandTransaction(String date){
        westerLandTransactionMapper.deleteTransaction(date);
    }

    /**
     * @Description 保存保险产品
     * @author gbl
     * @date 2019/1/29 15:05
     **/

    public void addProduct(JSONArray ja){
        westerLandProductMapper.deleteProduct(ja.getJSONObject(0).getString("createDate"));
        for(int i=0;i<ja.size();i++){

            westerLandProductMapper.addProduct(ja.getJSONObject(i));
        }
    }

    /**
     * @Description 删除指定日期的保险产品
     * @author gbl
     * @date 2019/1/29 15:05
     **/

    public void deleteProduct(String date){
        westerLandProductMapper.deleteProduct(date);
    }

    /**
     * @Description 保存保险用户信息
     * @author gbl
     * @date 2019/1/29 15:07
     **/

    public void addUser(JSONArray ja){
        westerLandUserMapper.deleteUser(ja.getJSONObject(0).getString("createDate"));
        for(int i=0;i<ja.size();i++){
            westerLandUserMapper.addUser(ja.getJSONObject(i));
        }

    }

    /**
     * @Description 删除指定日期的保险用户
     * @author gbl
     * @date 2019/1/29 15:07
     **/

    public void deleteUser(String date){
        westerLandUserMapper.deleteUser(date);
    }

}
