package com.guoanshequ.dc.das.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guoanshequ.dc.das.dao.master.AuthMapper;
import com.guoanshequ.dc.das.dao.master.PermissionServiceMapper;
import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.model.Auth;
import com.guoanshequ.dc.das.model.PermissionService;
import com.guoanshequ.dc.das.utils.EncryptUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service("AuthService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class AuthService {

    @Autowired
    AuthMapper authDao;
    @Autowired
	RedisService redisService;
    @Autowired
    PermissionServiceMapper permissionServiceDao;


    private static final Logger logger = LogManager.getLogger(AuthService.class);

    /**
     * 根据sign验证用户是否有效
     */
    public EnumRespStatus verifyAuth(String requestInfo, String requestSign) {
        try {
            if (StringUtils.isBlank(requestInfo)) {
                return EnumRespStatus.REQUEST_ERROR;
            }
            // convert request json string to map object
            ObjectMapper objectMapper = new ObjectMapper();

            Map<String, Object> requestMap = objectMapper.readValue(requestInfo, new TypeReference<Map<String,Object>>(){});
            String appKey = requestMap.get("app_key") != null ? requestMap.get("app_key").toString() : null;
            String reqTime = requestMap.get("stamp") != null ? requestMap.get("stamp").toString() : null;
            String reqNonce = requestMap.get("nonce") != null ? requestMap.get("nonce").toString() : null;

            if (StringUtils.isBlank(appKey) || StringUtils.isBlank(requestSign) || StringUtils.isBlank(reqTime) || StringUtils.isBlank(reqNonce)) {
                return EnumRespStatus.REQUEST_ERROR;
            } else {
                // 验证接入应用合法性
                Auth auth = authDao.findByAppKey(appKey);
                if (auth == null) {
                    return EnumRespStatus.AUTH_ERROR;
                }
                // 验证签名
                String verifySign = EncryptUtils.getMD5(requestInfo + auth.getApp_secret());
                if (!requestSign.equals(verifySign)) {
                    logger.info("**************verifySign:" + verifySign + "**************");
                    return EnumRespStatus.AUTH_ERROR;
                }

                // 验证时间戳
                Long requestTime = Long.parseLong(reqTime);
                Long expireTime = findByAppKey(appKey).getExpiretime();
                if ((System.currentTimeMillis() - requestTime) > expireTime) {
                    logger.info(EnumRespStatus.REQUEST_TIMEOUT.getMessage());
                    return EnumRespStatus.REQUEST_TIMEOUT;
                }

                // 验证nonce是否已经存在
                Boolean isExist = redisService.hasKey(reqNonce);
                if (isExist) {
                    logger.info(EnumRespStatus.REQUEST_REPEATED.getMessage());
                    return EnumRespStatus.REQUEST_REPEATED;
                } else {
                    redisService.setValue(reqNonce, appKey, expireTime);
                }
            }
            
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return EnumRespStatus.SYSTEM_ERROR;
        }
        logger.info(EnumRespStatus.AUTH_OK.getMessage());
        return EnumRespStatus.AUTH_OK;
    }

    /**
     * @Description 验证用户的请求接口是否授权
     * @author gbl
     * @date 2018/11/15 15:48
     **/

    public EnumRespStatus verifyPermissionServices(String requestInfo, String requestSign,String requestURI) {
        try {
            //验证用户有效性
            EnumRespStatus verifyResult = this.verifyAuth(requestInfo, requestSign);
            if (EnumRespStatus.AUTH_OK.equals(verifyResult)) {
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> requestMap = objectMapper.readValue(requestInfo, new TypeReference<Map<String,Object>>(){});
                String appKey = requestMap.get("app_key") != null ? requestMap.get("app_key").toString() : null;
                HashMap<String,Object> param = new HashMap<String,Object>();
                param.put("appkey",appKey);
                param.put("uri",requestURI);
                PermissionService ps = permissionServiceDao.findByAppKeyAndURI(param);
                if (ps == null) {
                    return EnumRespStatus.PERMISSIONSERVICE_ERROR;
                }
            }else{
                return verifyResult;
            }

        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return EnumRespStatus.SYSTEM_ERROR;
        }
        logger.info(EnumRespStatus.AUTH_OK.getMessage());
        return EnumRespStatus.AUTH_OK;
    }
    
    //通过appkey获得对应的访问用户信息
    public Auth findByAppKey(String appKey){
    	return authDao.findByAppKey(appKey);
    }
    
}

