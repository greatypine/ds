package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.DfImsMapper;
import com.guoanshequ.dc.das.dao.master.DfMassOrderMapper;
import com.guoanshequ.dc.das.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("DfImsService")
@Transactional(value = "master", rollbackFor = Exception.class)
public class DfImsService {

	@Autowired
	DfImsMapper dfImsDao;

	public ImsTbOCount queryImsTbOCountIsExist(String c_id){
		return dfImsDao.queryImsTbOCountIsExist(c_id);
	}

	public ImsTbOl queryImsTbOlIsExist(String c_id){
		return dfImsDao.queryImsTbOlIsExist(c_id);
	}

	public ImsTbStore queryImsTbStoreIsExist(String c_id){
		return dfImsDao.queryImsTbStoreIsExist(c_id);
	}
	
	public Integer addDfImsTbOCount(ImsTbOCount imsTbOCount) {
		return dfImsDao.addDfImsTbOCount(imsTbOCount);
	}

	public Integer addDfImsTbOCountg(List<ImsTbOCountg> list) {
		return dfImsDao.addDfImsTbOCountg(list);
	}

	public Integer addDfImsTbStore(ImsTbStore imsTbStore){
		return dfImsDao.addDfImsTbStore(imsTbStore);
	}

	public Integer addDfImsTbOl(ImsTbOl imsTbOl){
		return dfImsDao.addDfImsTbOl(imsTbOl);
	}

	public Integer deleteDfImsTbOCount(String c_id) {
		return dfImsDao.deleteDfImsTbOCount(c_id);
	}

	public Integer deleteDfImsTbOCountg(String c_id) {
		return dfImsDao.deleteDfImsTbOCountg(c_id);
	}

	public Integer deleteDfImsTbOl(String c_id){
		return dfImsDao.deleteDfImsTbOl(c_id);
	}

	public Integer deleteDfImsTbStore(String c_id) {
		return dfImsDao.deleteDfImsTbStore(c_id);
	}
}
