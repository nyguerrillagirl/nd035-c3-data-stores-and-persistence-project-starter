package com.udacity.jdnd.course3.critter.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public class Person {
	
	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	private String name;
	
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
