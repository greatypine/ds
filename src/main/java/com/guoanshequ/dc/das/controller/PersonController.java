package com.guoanshequ.dc.das.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.guoanshequ.dc.das.dto.RestResponse;
import com.guoanshequ.dc.das.model.Person;
import com.guoanshequ.dc.das.service.AuthService;
import com.guoanshequ.dc.das.service.PersonService;

@RestController
public class PersonController {

	@Autowired
	PersonService personService;
	@Autowired
	AuthService authService;
	
	/**
	 * 查询某一个人的数据信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="rest/getPerson/{id}",method=RequestMethod.POST)
	public RestResponse getPersonById(@PathVariable int id){
		Person person = personService.getPersonById(id);
		return new RestResponse("getPerson", "OK",person);
	}
	
	/**
	 * 查询所有人员数据信息
	 * @return
	 */
	@RequestMapping(value="rest/getAllPerson",method=RequestMethod.POST)
	public RestResponse getAllPerson(){
		
		List<Person> list = personService.getAllPerson();
		return new RestResponse("getAllPerson", "OK",list);
	}
	/**
	 * 新增人员数据信息
	 * @return
	 */
	@RequestMapping(value="rest/addPerson",method=RequestMethod.POST)
	public RestResponse addPerson(){
		Person person = new Person();
		person.setPersonid("1012");
		person.setDisplayname("李四");
		person.setSex("男");
		person.setPhone("17890766343");
		person.setAddress("海南省三亚市");
		personService.addPerson(person);
		return new RestResponse("addPerson", "OK",person);
	}
	
	/**
	 * 删除人员数据信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="rest/deletePerson/{id}",method=RequestMethod.POST)
	public RestResponse deletePerson(@PathVariable int id){
		int i = personService.deletePerson(id);		
		return new RestResponse("deletePerson", "OK",i);
	}
	
	@RequestMapping(value="rest/updatePerson/{id}",method=RequestMethod.POST)
	public RestResponse updatePerson(@PathVariable int id){
		Person person = personService.getPersonById(id);
		person.setDisplayname(person.getDisplayname()+1);
		person.setSex("女");
		person.setPhone("1111111111111");
		person.setAddress("小胡同");
		int i = personService.updatePerson(person);
		return new RestResponse("updatePerson","OK",i);
	}
}
