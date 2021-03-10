package com.udacity.jdnd.course3.critter.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.udacity.jdnd.course3.critter.entity.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long>{

	public List<Employee> findByName(String name);
	
	public List<Employee> findAll();
}
