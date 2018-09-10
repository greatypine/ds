package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.model.CustomerInfoRecord;
import com.guoanshequ.dc.das.model.EmployeePosition;
import com.guoanshequ.dc.das.utils.DateUtils;
import com.mongodb.BasicDBObject;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    /**
     * @Description 获取员工总数
     * @author gbl
     * @date 2018/9/6 14:41
     **/

    public Long getEmployeePositonCount(){
        Query query = new Query();
        Long count = mongoTemplate.count(query,EmployeePosition.class);
        return count;
    }

    /**
     * @Description 获取员工里程里程
     * @author gbl
     * @date 2018/9/10 9:44
     **/

    public List<EmployeePosition> queryEmployeeHistoryPosition(Long skipcount){

        Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.add(Calendar.DATE, -1); // 设置为前一天
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String curDate = sdf.format(calendar.getTime());

        Aggregation agg =Aggregation.newAggregation(

                Aggregation.project("locations").and("employeeId").as("_id"),
                Aggregation.unwind("locations"),
                Aggregation.match(Criteria.where("locations.createTime").lte(new Date(curDate+" 00:00:00"))),
                Aggregation.group("_id").push("locations.location").as("locations"),
                Aggregation.skip(skipcount),
                Aggregation.limit(50)

                ).withOptions(AggregationOptions.builder().allowDiskUse(true).build());

        AggregationResults<EmployeePosition> results = mongoTemplate.aggregate(agg, "employee_position", EmployeePosition.class);
        List<EmployeePosition> list = results.getMappedResults();



        return list;
    }


    public List<EmployeePosition> queryEmployeePosition(){

        Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.add(Calendar.DATE, -1); // 设置为前一天
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String curDate = sdf.format(calendar.getTime());

        Aggregation agg =Aggregation.newAggregation(

                Aggregation.project("locations").and("employeeId").as("_id"),
                Aggregation.unwind("locations"),
                Aggregation.match(Criteria.where("locations.createTime").gt(new Date(curDate+" 00:00:00")).lte(new Date(curDate+" 23:59:59"))),
                Aggregation.group("_id").push("locations.location").as("locations")
        ).withOptions(AggregationOptions.builder().allowDiskUse(true).build());

        AggregationResults<EmployeePosition> results = mongoTemplate.aggregate(agg, "employee_position", EmployeePosition.class);
        List<EmployeePosition> list = results.getMappedResults();
        return list;
    }

}
