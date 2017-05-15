package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by shuhuadai on 2017/2/14.
 */
@RestController
@RequestMapping("/rest/hello")
public class SpringRestController {
//    @RequestMapping(value = "/{name}", method = RequestMethod.GET, headers = "Accept=application/json")
    @RequestMapping(value = "/{name}", method = RequestMethod.POST)
    public RestResponse hello(@PathVariable String name,  @RequestBody Map<String, String> paraMap) {

        paraMap.put("name", name);
        return new RestResponse(EnumRespStatus.DATA_OK, paraMap);
    }
}
