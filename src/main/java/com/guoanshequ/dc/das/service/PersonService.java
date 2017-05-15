package com.guoanshequ.dc.das.service;

import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.PersonMapper;
import com.guoanshequ.dc.das.model.Person;

@Service("PersonService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class PersonService {

	@Autowired
	PersonMapper personDao;
	
    private static final Logger logger = LogManager.getLogger(PersonService.class);
	
	public List<Person> getAllPerson(){
		logger.info("verify begin....");
		return personDao.getAllPerson();
	}
	
	public Person getPersonById(int id){
		return personDao.getPersonById(id);
	}
	
	public void addPerson(Person person){
		 personDao.addPerson(person);
	}
	
	public int updatePerson(Person person){
		return personDao.updatePerson(person);
	}
	
	public int deletePerson(int id){
		return personDao.deletePerson(id);
	}
}
