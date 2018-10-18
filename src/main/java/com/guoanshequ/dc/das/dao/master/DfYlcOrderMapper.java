package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.Customer;

@Repository
@DataSource("master")
public interface DfYlcOrderMapper {

	public Integer addYlcOrder(Map<String, String> paraMap);

	public Integer addYlcOrderInfo(Map<String, String> paraMap);

	public String queryYlcOrder(Map<String, String> paraMap);

	public String queryYlcOrderInfo(Map<String, String> paraMap);

}
