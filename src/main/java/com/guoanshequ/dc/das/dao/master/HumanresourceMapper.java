package com.guoanshequ.dc.das.dao.master;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 员工实体类
 * Created by liuxi on 2017/2/20.
 */
@Repository
public interface HumanresourceMapper {

	List<Map<String, String>> queryHumanresources(Map<String, String> paraMap);
	
	List<Map<String, String>> queryPreHumanresources(Map<String, String> paraMap);

	int queryStoreEmpCount(String storeno);
	
	List<Map<String, String>> queryHumanresourcesList(Map<String, String> paraMap);
	
	List<Map<String, String>> queryAllHumanresourcesList();
	
	List<Map<String, String>> queryPostList();
	List<Map<String, String>> queryStoreList();

	String queryIdCardOfonlineByPhone(String mobilephone);
}
