package com.guoanshequ.dc.das.controller;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.service.AreaInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 查询片区相关信息
 */

@RestController
@ResponseBody
public class AreaInfoController {

	@Autowired
	AreaInfoService areaInfoService;

	private static final Logger logger = LogManager.getLogger(AreaInfoService.class);

	@RequestMapping(value = "rest/queryAreaNoByTinyNo/", method = RequestMethod.POST)
	public RestResponse queryAreaNoByTinyNo(@RequestBody Map<String, String> paraMap) throws Exception {
		try {
			String areano = areaInfoService.queryAreaNoByTinyNo("1201040060000000000018");
			if(StringUtils.isBlank(areano)){
				areano="hahah";
				System.out.println("11111111111111111111111");
			}
			return new RestResponse(EnumRespStatus.DATA_OK,areano);
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
		}
	}
}
