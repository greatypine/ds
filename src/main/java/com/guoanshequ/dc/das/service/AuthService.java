package com.guoanshequ.dc.das.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guoanshequ.dc.das.dao.master.AuthMapper;
import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.model.Auth;
import com.guoanshequ.dc.das.utils.EncryptUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("AuthService")
@Transactional("slave")
public class AuthService {

    @Autowired
    AuthMapper authDao;

    private static final Logger logger = LogManager.getLogger(AuthService.class);


    public EnumRespStatus verifyAuth(String requestInfo, String requestSign) {
        try {
            if (StringUtils.isBlank(requestInfo)) {
                return EnumRespStatus.REQUEST_ERROR;
            }
            // convert request json string to map object
            ObjectMapper objectMapper = new ObjectMapper();

            Map<String, Object> requestMap = objectMapper.readValue(requestInfo, new TypeReference<Map<String,Object>>(){});
            String appKey = requestMap.get("app_key") != null ? requestMap.get("app_key").toString() : null;

            if (StringUtils.isBlank(appKey) || StringUtils.isBlank(requestSign)) {
                return EnumRespStatus.REQUEST_ERROR;
            } else {
                Auth auth = authDao.findByAppKey(appKey);
                if (auth == null) {
                    return EnumRespStatus.AUTH_ERROR;
                } else {
                    String verifySign = EncryptUtils.getMD5(requestInfo + auth.getApp_secret());
                    if (!requestSign.equals(verifySign)) {
                        return EnumRespStatus.AUTH_ERROR;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return EnumRespStatus.SYSTEM_ERROR;
        }
        return EnumRespStatus.AUTH_OK;
    }
}

