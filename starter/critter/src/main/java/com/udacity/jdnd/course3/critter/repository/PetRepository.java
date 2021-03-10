package com.udacity.jdnd.course3.critter.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;

@Repository
public interface PetRepository extends CrudRepository<Pet, Long> {	

	List<Pet> findAll();
	
	List<Pet> findByCustomer(Customer customer);
}
