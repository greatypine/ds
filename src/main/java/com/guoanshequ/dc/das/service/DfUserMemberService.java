package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.DfUserMemberMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("DfUserMemberService")
@Transactional(value = "master", rollbackFor = Exception.class)
public class DfUserMemberService {

	@Autowired
	DfUserMemberMapper dfUserMemberDao;
	
	public Integer addDfUserMember(Map<String, Object> paraMap) {
		return dfUserMemberDao.addDfUserMember(paraMap);
	}
	
}