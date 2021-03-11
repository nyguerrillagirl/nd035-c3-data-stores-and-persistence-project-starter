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
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;


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
	
	@Test
	public void testDoesEmployeeQuailify1() {
		// positive test
		Set<EmployeeSkill> targetSkills = new HashSet<EmployeeSkill>();
		targetSkills.add(EmployeeSkill.FEEDING);
		targetSkills.add(EmployeeSkill.PETTING);
		DayOfWeek targetWorkday = DayOfWeek.WEDNESDAY;
		
		// Now create an employee that works on WEDNESDAY and has those skills
		Employee employee = new Employee();
		employee.addSkill(EmployeeSkill.WALKING);
		employee.addSkill(EmployeeSkill.PETTING);
		employee.addSkill(EmployeeSkill.FEEDING);
		
		employee.addWorkday(DayOfWeek.MONDAY);
		employee.addWorkday(DayOfWeek.WEDNESDAY);
		employee.addWorkday(DayOfWeek.FRIDAY);
		employee.setName("Lorraine Figueroa");
		
		boolean result = employeeService.doesEmployeeQualify(employee, targetWorkday, targetSkills);
		assertEquals(true, result);
	}
	@Test
	public void testDoesEmployeeQuailify2() {
		// negative test
		
		Set<EmployeeSkill> targetSkills = new HashSet<EmployeeSkill>();
		targetSkills.add(EmployeeSkill.FEEDING);
		targetSkills.add(EmployeeSkill.PETTING);
		DayOfWeek targetWorkday = DayOfWeek.WEDNESDAY;
		
		// Now create an employee that works on WEDNESDAY and has those skills
		Employee employee = new Employee();
		employee.addSkill(EmployeeSkill.WALKING);
		employee.addSkill(EmployeeSkill.PETTING);
		
		employee.addWorkday(DayOfWeek.MONDAY);
		employee.addWorkday(DayOfWeek.WEDNESDAY);
		employee.addWorkday(DayOfWeek.FRIDAY);
		employee.setName("Lorraine Figueroa");
		
		boolean result = employeeService.doesEmployeeQualify(employee, targetWorkday, targetSkills);
		assertEquals(false, result);
		
	}
	
	@Test
	public void testDoesEmployeeQuailify3() {
		// negative test, has skills but does not work on that day
		Set<EmployeeSkill> targetSkills = new HashSet<EmployeeSkill>();
		targetSkills.add(EmployeeSkill.FEEDING);
		targetSkills.add(EmployeeSkill.PETTING);
		DayOfWeek targetWorkday = DayOfWeek.WEDNESDAY;
		
		// Now create an employee that works on WEDNESDAY and has those skills
		Employee employee = new Employee();
		employee.addSkill(EmployeeSkill.WALKING);
		employee.addSkill(EmployeeSkill.PETTING);
		employee.addSkill(EmployeeSkill.FEEDING);
		
		employee.addWorkday(DayOfWeek.MONDAY);
		employee.addWorkday(DayOfWeek.FRIDAY);
		employee.setName("Lorraine Figueroa");
		
		boolean result = employeeService.doesEmployeeQualify(employee, targetWorkday, targetSkills);
		assertEquals(false, result);
	}
}
