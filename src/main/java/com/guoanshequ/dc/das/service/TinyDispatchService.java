package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.model.TinyDispatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("TinyDispatchService")
public class TinyDispatchService {

    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 通过orderid获取小区code
     * @param orderid
     * @return
     * @throws Exception
     */
    public String queryCodeByOrderId(String orderid) throws Exception{
        String code ="";
        TinyDispatch tinyDispatch =mongoTemplate.findOne(new Query(new Criteria(
                "orderId").is(orderid)), TinyDispatch.class);
        if(tinyDispatch!=null) {
            code = tinyDispatch.getCode();
        }
        return code;
    }

    /**
     * 通过orderid获取订单分配
     * @param orderid
     * @return
     * @throws Exception
     */
    public TinyDispatch queryTinyDispatchByOrderId(String orderid) throws Exception{

        TinyDispatch tinyDispatch =mongoTemplate.findOne(new Query(new Criteria(
                "orderId").is(orderid)), TinyDispatch.class);
        return tinyDispatch;
    }

    /**
     * 获取mongo中所有tiny_dispatch数据
     * @param orderid
     * @return
     * @throws Exception
     */
    public List<TinyDispatch> queryTinyDispatchALL(String orderid) throws Exception{

        List<TinyDispatch> list =mongoTemplate.findAll(TinyDispatch.class);
        return list;
    }

}
