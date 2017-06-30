package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface TopDataMapper {

	int deleteTopDatas(Map<String, String> paraMap);
	
	void addHumanresources(Map<String, String> paraMap);
	
	void addStoreKeepers(Map<String, String> paraMap);
	
	void updateRelationnum(Map<String, String> paraMap);
	
	void updateCusGrade1(Map<String, String> paraMap);
	
	void updateCusGrade2(Map<String, String> paraMap);
	
	void updateCusGrade3(Map<String, String> paraMap);
	
	void updateWorkRecord(Map<String, String> paraMap);
	
	void updateFerryPushnum(Map<String, String> paraMap);
	
	void updateStoreRelationnum(Map<String, String> paraMap);
	
	void updateStoreCusgrade1(Map<String, String> paraMap);
	
	void updateStoreCusgrade2(Map<String, String> paraMap);
	
	void updateStoreCusgrade3(Map<String, String> paraMap);
	
	List<Map<String, String>> queryHumanResourcesOnTop(Map<String, String> paraMap);
	
	List<Map<String, String>> queryStoreKeeperOnTop(Map<String, String> paraMap);
	
	List<Map<String, String>> queryCusgrade1OnTop(Map<String, String> paraMap);
	
	List<Map<String, String>> queryCusgrade2OnTop(Map<String, String> paraMap);
	
	List<Map<String, String>> queryCusgrade3OnTop(Map<String, String> paraMap);
	
	List<Map<String, String>> queryStoreCusgrade1OnTop(Map<String, String> paraMap);
	
	List<Map<String, String>> queryStoreCusgrade2OnTop(Map<String, String> paraMap);
	
	List<Map<String, String>> queryStoreCusgrade3OnTop(Map<String, String> paraMap);
	
	List<Map<String, String>> queryFirstByStoreOnTop(Map<String, String> paraMap);
	
	List<Map<String, String>> querySecondByStoreOnTop(Map<String, String> paraMap);
	
	List<Map<String, String>> queryThirdByStoreOnTop(Map<String, String> paraMap);
	
	List<Map<String, String>> queryRelationnumOnTop(Map<String, String> paraMap);
	
	List<Map<String, String>> queryStoreRelationnumOnTop(Map<String, String> paraMap);
	
	List<Map<String, String>> queryRelationsByStoreOnTop(Map<String, String> paraMap);
	
	List<Map<String, String>> queryWorkRecordsOnTop(Map<String, String> paraMap);
	
	List<Map<String, String>> queryFerryPushOnTop(Map<String, String> paraMap);
	
}
