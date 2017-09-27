package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.RedisService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by shuhuadai on 2017/2/14.
 */
@RestController
@RequestMapping("/rest/hello")
public class SpringRestController {

    @Autowired
    RedisService redisService;

    @RequestMapping(value = "/{name}", method = RequestMethod.POST)
    public RestResponse hello(@PathVariable String name,  @RequestBody Map<String, String> paraMap) {

       // redisService.setValue("app1", "afrawefaewfaewfaew", 60L);

        //paraMap.put("name", name);
    	if(redisService.getValue("app1")==null){
    		return new RestResponse(EnumRespStatus.DATA_OK, "为空");
    		//paraMap.put("name", redisService.getValue("app1").toString());
        }else{
        	return new RestResponse(EnumRespStatus.DATA_OK, paraMap);
        }
    }
}
