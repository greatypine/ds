package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.DfUserMemberMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("DfUserMemberService")
@Transactional(value = "master", rollbackFor = Exception.class)
public class DfUserMemberService {

	@Autowired
	DfUserMemberMapper dfUserMemberDao;
	
	public Integer addDfUserMember(Map<String, Object> paraMap) {
		return dfUserMemberDao.addDfUserMember(paraMap);
	}
	
	public List<Map<String, String>> queryDfUserMember(Map<String, String> paraMap) {
		return dfUserMemberDao.queryDfUserMembers(paraMap);
	}
	
	public Integer updateInviteCodeByCusId (Map<String, String> paraMap) {
		return dfUserMemberDao.updateInviteCodeByCusId(paraMap);
	}
	
	public Integer addDsOpeMemberCancelCityDay(Map<String, String> paraMap) {
		return dfUserMemberDao.addDsOpeMemberCancelCityDay(paraMap);
	}	
	
	public Integer addDfTryUserMember(Map<String, Object> paraMap) {
		return dfUserMemberDao.addDfTryUserMember(paraMap);
	}	
}
