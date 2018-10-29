package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.ims.ImsMapper;
import com.guoanshequ.dc.das.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("ImsService")
@Transactional(value = "ims",rollbackFor = Exception.class)
public class ImsService {

	@Autowired
	ImsMapper imsDao;
	
	public List<ImsTbOCount> queryImsTbOCountByDate(Map<String, String> paraMap){
        return imsDao.queryImsTbOCountByDate(paraMap);
    }

	public List<ImsTbOCountg> queryImsTbOCountgByCId(String c_id){
		return imsDao.queryImsTbOCountgByCId(c_id);
	}

	public ImsTbStore queryImsTbStoreByStoreId(String c_store_id){
		return imsDao.queryImsTbStoreByStoreId(c_store_id);
	}

	public List<ImsTbOl> queryImsTbOlByDate(Map<String, String> paraMap){
		return imsDao.queryImsTbOlByDate(paraMap);
	}

	public String queryMaxAuDtTime(){
		return imsDao.queryMaxAuDtTime();
	}
	
}
