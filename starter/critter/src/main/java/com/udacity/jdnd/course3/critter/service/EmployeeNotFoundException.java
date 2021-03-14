package com.udacity.jdnd.course3.critter.service;

public class EmployeeNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public EmployeeNotFoundException(String message) {
		super(message);
	}
}
