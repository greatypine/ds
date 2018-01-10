package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.model.TinyDispatch;
import com.guoanshequ.dc.das.service.TinyDispatchService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ResponseBody
public class TinyDispatchController {

    private static final Log logger = LogFactory.getLog(TinyDispatchController.class);

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    MongoOperations mongoOperations;
    @Autowired
    TinyDispatchService tinyDispatchService;

    @RequestMapping(value = "rest/queryTinyDispatchALL",method = RequestMethod.POST)
    public void showAll() {
        List<TinyDispatch> list = mongoTemplate.findAll(TinyDispatch.class);
        for (TinyDispatch p : list) {
            System.out.println(p);
        }
    }

    @RequestMapping(value = "rest/queryTinyDispatchByOrderId",method = RequestMethod.POST)
    public void showAll1() throws Exception{
        TinyDispatch p = tinyDispatchService.queryTinyDispatchByOrderId("47753df937764e8e9b93fbcec5e30e42");
        System.out.println(p.getEmployee_a_no());
        System.out.println(p.getCode());
    }
}
