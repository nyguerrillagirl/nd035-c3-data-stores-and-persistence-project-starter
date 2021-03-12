package com.udacity.jdnd.course3.critter.entity;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

//@Entity
public class Schedule {

	@Id
	@GeneratedValue
	private Long id;
	
	@OneToMany
	private Set<Employee> employees;
	private Set<Pet> pets;
	private LocalDate date;
	private Set<EmployeeSkill> activities;
}
