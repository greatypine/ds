package com.guoanshequ.dc.das.intercptor;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.filter.BodyReaderHttpServletRequestWrapper;
import com.guoanshequ.dc.das.service.AuthService;
import com.guoanshequ.dc.das.utils.RequestJsonUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by shuhuadai on 2017/2/20.
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

	 private static final Logger logger = LogManager.getLogger(AuthInterceptor.class);
	 
    @Autowired
    private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        BodyReaderHttpServletRequestWrapper requestWrapper= new BodyReaderHttpServletRequestWrapper(request);

        String requestBodyString = RequestJsonUtils.getRequestJsonString(requestWrapper);
        String requestSignString = request.getParameter("sign");

        logger.debug("requestBodyString:"+requestBodyString);
        logger.debug("requestSignString:"+requestSignString);
        
        EnumRespStatus verifyResult = authService.verifyAuth(requestBodyString, requestSignString);
        if (!EnumRespStatus.AUTH_OK.equals(verifyResult)) {
            RestResponse restResponse = new RestResponse(verifyResult);
            response.setContentType("application/json");
            ObjectMapper objectMapper = new ObjectMapper();
            JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(response.getOutputStream(), JsonEncoding.UTF8);
            jsonGenerator.writeObject(restResponse);
            jsonGenerator.close();
            return false;
        }
        return true;
    }
}
