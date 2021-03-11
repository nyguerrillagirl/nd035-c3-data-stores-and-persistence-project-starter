package com.udacity.jdnd.course3.critter.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.CritterApplication;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;


@Transactional
@SpringBootTest(classes = CritterApplication.class)
class TestEmployeeService {
	
	@Autowired
	private EmployeeServiceImpl employeeService;

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Test
	void testClearEmployeeWorkdays() {
		// Create an employee with workdays
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setName("Lorraine Figueroa");
		Set<DayOfWeek> workdays = new HashSet<DayOfWeek>();
		workdays.add(DayOfWeek.MONDAY);
		workdays.add(DayOfWeek.WEDNESDAY);
		employeeDTO.setDaysAvailable(workdays);
		EmployeeDTO newEmployeeDTO = employeeService.saveEmployee(employeeDTO);
		// fetch employee
		Long id = newEmployeeDTO.getId();
		Optional<Employee> optionalEmployee = employeeRepository.findById(id);
		Employee employee = null;
		if (optionalEmployee.isPresent()) {
			employee = optionalEmployee.get();
		} else {
			fail("Employee not found.");
		}
		// clear all workdays
		employeeService.clearEmployeeWorkdays(employee);
		
		// check
		optionalEmployee = employeeRepository.findById(id);
		if (optionalEmployee.isPresent()) {
			employee = optionalEmployee.get();
			assertTrue(employee.getDaysAvailable() == null || employee.getDaysAvailable().size() == 0);
		} else {
			fail("Employee not found.");
		}
	}

}
