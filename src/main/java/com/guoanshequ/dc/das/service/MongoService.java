package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.model.CustomerInfoRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service("MongoService")
public class MongoService {

    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 根据customer_id 查询mongo中社员信息对象
     * @param orderid
     * @return
     * @throws Exception
     */
    public CustomerInfoRecord queryCusInfoByCusId(String customerid) throws Exception{
        CustomerInfoRecord customerInfoRecord =mongoTemplate.findOne(new Query(new Criteria(
                "customerId").is(customerid)), CustomerInfoRecord.class);
        if(customerInfoRecord!=null) {
        	return customerInfoRecord;
        }
        return null;
    }

}
