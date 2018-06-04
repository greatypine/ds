package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.DfMemberCountMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("DfMemberCountService")
@Transactional(value = "master", rollbackFor = Exception.class)
public class DfMemberCountService {

	@Autowired
	DfMemberCountMapper dfMemberCountDao;
	/**
	 * 添加社员成交数据
	 * TODO 
	 * @author wuxinxin
	 */
	public Integer addDfMemberCount(Map<String, String> runMap) {
		return dfMemberCountDao.addDfMemberCount(runMap);
	}
	/**
	 * 修改社员成交额  成交量
	 * TODO 
	 * @author wuxinxin
	 */
	public Integer updateMemberCount(Map<String, String> runMap) {
		return dfMemberCountDao.updateDfMemberCount(runMap);
	}
	
	/**
	 * 删除社员最受欢迎、无人问津商品
	 * TODO 
	 * @author wuxinxin
	 */
	public void deletMembers(List<String> fileIdList) {
		dfMemberCountDao.deleteDfMemberCount(fileIdList);
	}
	
}
