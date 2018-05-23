package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.slave.UserMemberMapper;

import java.util.List;
import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2018年5月18日
* @version 1.0
* 说明:
 */
@Service("UserMemberService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class UserMemberService {

    @Autowired
    UserMemberMapper userMemberDao;

    public List<Map<String, Object>> queryUserMemberByCreateTime(Map<String, String> paraMap){
    	
    	return userMemberDao.queryUserMemberByCreateTime(paraMap);
    }
    
}
