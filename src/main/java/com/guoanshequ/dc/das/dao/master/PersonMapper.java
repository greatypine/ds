package com.guoanshequ.dc.das.dao.master;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.Person;
@Repository
@DataSource("master")
public interface PersonMapper {

	//得到所有人员信息
	List<Person> getAllPerson();
	//得到某个人员信息
	Person getPersonById(int id);
	//新增人员信息
	void addPerson(Person person);
	//更新人员信息
	int updatePerson(Person person);
	//删除人员信息
	int deletePerson(int id);
}
