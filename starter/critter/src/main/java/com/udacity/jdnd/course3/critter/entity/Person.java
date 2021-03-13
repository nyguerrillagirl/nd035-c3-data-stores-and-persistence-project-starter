package com.udacity.jdnd.course3.critter.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NaturalId;

@MappedSuperclass
public class Person {
	
	@Id
	@GeneratedValue
	protected Long id;

	@NaturalId
	@NotNull
	protected String name;
	
	public Person() {}
	
	public Person(String name) {
		this.name = name;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
