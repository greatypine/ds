package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface DfUserMemberMapper {

	public Integer addDfUserMember(Map<String,Object> paraMap);
	
	public List<Map<String, String>> queryDfUserMembers(Map<String,String> paraMap);
	
	public Integer updateInviteCodeByCusId(Map<String,String> paraMap);
	
}
