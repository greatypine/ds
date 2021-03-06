package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.Customer;

@Repository
@DataSource("master")
public interface DfUserProfileMapper {

	public Integer addDfUserProfile(Map<String,String> paraMap);
	
	public Integer isExistCusDraw(String mobilephone);
	
	public void addName(Customer paraMap);
	
	public Integer addDfUserStore(Map<String,String> paraMap);
	
	public Integer addUserTag(Map<String,String> paraMap);
	
	public void addYlcUser(Map<String,String> paraMap);

	public List<Map<String, Object>> isExistCustomer(Map<String,String> paraMap);

	public void updateYlcUser(Map<String, String> telMap);

	public String queryYlcCustomer(Map<String, String> paraMap);
	
	public String queryYlcIdcardByPhone(String customer_phone);
	
	public void deleteYlcIdcardByPhone(String customer_phone);
	
}
