package com.guoanshequ.dc.das.dao.ims;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.ImsTbsdgds;

@Repository
@DataSource("ims")
public interface TbsdgdsMapper{

	List<ImsTbsdgds> queryTbsDGdsByTime(Map<String, String> paraMap);
}
