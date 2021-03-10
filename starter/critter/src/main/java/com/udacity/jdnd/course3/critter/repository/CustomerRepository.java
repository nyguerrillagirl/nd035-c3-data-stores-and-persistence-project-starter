package com.udacity.jdnd.course3.critter.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.udacity.jdnd.course3.critter.entity.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long>{
	
	public List<Customer> findByName(String name);
	
	public List<Customer> findAll();

}
