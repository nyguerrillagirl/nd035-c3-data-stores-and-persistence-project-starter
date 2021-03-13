package com.udacity.jdnd.course3.critter.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;


@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long>{

	public List<Employee> findByScheduledEmployees(Employee employee);
	
	public List<Pet> findByScheduledPets(Pet pet);
	
	public List<Schedule> findAll();
	

}
