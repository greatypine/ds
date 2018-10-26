package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.DfTbsdgdsMapper;
import com.guoanshequ.dc.das.model.ImsTbsdgds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("DfTbsgdsService")
@Transactional(value = "master", rollbackFor = Exception.class)
public class DfTbsgdsService {

	@Autowired
	DfTbsdgdsMapper dfTbsgdsDao;
	
	public Integer addDfTbsdgdsDaily(ImsTbsdgds imsTbsdgds) {
		
		return dfTbsgdsDao.addDfTbsdgdsDaily(imsTbsdgds);
	}
	
	public Integer addDfTbsdgdsTotal(ImsTbsdgds imsTbsdgds) {
		
		return dfTbsgdsDao.addDfTbsdgdsTotal(imsTbsdgds);
	}
}
