package com.guoanshequ.dc.das.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.model.Auth;
import com.guoanshequ.dc.das.service.AuthService;
import com.guoanshequ.dc.das.service.RedisService;
import com.guoanshequ.dc.das.utils.PwdGeneratorUtils;

@RestController
@ResponseBody
public class TokenController {
	
	@Autowired
	RedisService redisService;
	@Autowired
	AuthService authService; 
	
	private static final Logger logger = LogManager.getLogger(TokenController.class);

	@RequestMapping(value = "rest/getTokenString",method = RequestMethod.POST)
	public RestResponse getTokenString(@RequestBody Map<String, String> paraMap) throws Exception {
		try {
			String tokenStr = PwdGeneratorUtils.generateRandomPwd(16);
			Map<String, String> tokenMap = new HashMap<String, String>();
			String appKey = paraMap.get("app_key") != null ? paraMap.get("app_key").toString() : null;
			if(!StringUtils.isBlank(appKey)){
				Auth appkeyAuth = authService.findByAppKey(appKey);
				Long expireTime = appkeyAuth.getExpiretime();
				tokenMap.put("token", tokenStr);
				redisService.setValue(appKey, tokenStr, expireTime);
			}
			return new RestResponse(EnumRespStatus.DATA_OK, tokenMap);
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			return new RestResponse(EnumRespStatus.SYSTEM_ERROR);
		}
	}
}
