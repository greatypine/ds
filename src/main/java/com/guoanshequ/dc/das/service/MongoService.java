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
     * 根据customer_id 查询mongo中社员信息对象,获取身份证字段，用于对身份证信息进行计算
     * @param customerid
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
    
    /**
     * 根据customer_id 查询mongo中邀请码不为空的社员信息，获取邀请码字段，更新清洗表中邀请码字段
     * @param customerid
     * @return
     * @throws Exception
     */
    public CustomerInfoRecord queryCusInviteCodeByCusId(String customerid) throws Exception{
    	Criteria criteria = new Criteria();
    	criteria.andOperator(Criteria.where("customerId").is(customerid).and("inviteCode").exists(true).ne(""));
    	Query query = new Query(criteria);
        CustomerInfoRecord customerInfoRecord =mongoTemplate.findOne(query, CustomerInfoRecord.class);
        if(customerInfoRecord!=null) {
        	return customerInfoRecord;
        }
        return null;
    }

}
