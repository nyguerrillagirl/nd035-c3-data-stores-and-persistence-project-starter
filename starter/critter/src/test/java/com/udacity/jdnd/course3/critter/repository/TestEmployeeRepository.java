package com.udacity.jdnd.course3.critter.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

@DataJpaTest
class TestEmployeeRepository {
	
	@Autowired
	private EmployeeRepository employeeRepository;


	@Test
	public void testSimpleSave() {
		Employee employee = new Employee("Lorraine Figueroa");
		employeeRepository.save(employee);
		
		// Check that our record is in the database
		List<Employee> lstEmployees = employeeRepository.findByName("Lorraine Figueroa");
		Employee savedEmployeeRecord = lstEmployees.get(0);
		assertTrue(employee.getName().equals(savedEmployeeRecord.getName()));
	}
	
	@Test
	public void testEmployeesWithSameName() {
		Employee newEmployee1 = new Employee("Lorraine Figueroa");
		employeeRepository.save(newEmployee1);
		
		Employee newEmployee2 = new Employee("Lorraine Figueroa");
		employeeRepository.save(newEmployee2);
		
		List<Employee> lstEmployees = employeeRepository.findByName("Lorraine Figueroa");
		assertTrue(lstEmployees.size() == 2);
	}
	
	@Test
	public void testAddSkills() {
		Employee newEmployee = new Employee("Lorraine Figueroa");
		newEmployee.addSkill(EmployeeSkill.PETTING);
		newEmployee.addSkill(EmployeeSkill.WALKING);
		employeeRepository.save(newEmployee);
		
		// Now obtain the employee
		Optional<Employee> optionalSavedEmployee = employeeRepository.findById(newEmployee.getId());
		if (optionalSavedEmployee.isPresent()) {
			Employee savedEmployee = optionalSavedEmployee.get();
			// let's confirm we obtained the saved skills
			assertTrue(savedEmployee.getSkills().size() == 2);
		}	
	}

	
	@Test
	public void testAddWorkdays() {
		Employee newEmployee = new Employee("Lorraine Figueroa");
		newEmployee.addWorkday(DayOfWeek.MONDAY);
		newEmployee.addWorkday(DayOfWeek.WEDNESDAY);
		newEmployee.addWorkday(DayOfWeek.FRIDAY);
		
		employeeRepository.save(newEmployee);
		
		// Now obtain the employee
		Optional<Employee> optionalSavedEmployee = employeeRepository.findById(newEmployee.getId());
		if (optionalSavedEmployee.isPresent()) {
			Employee savedEmployee = optionalSavedEmployee.get();
			// let's confirm we obtained the saved skills
			assertTrue(savedEmployee.getDaysAvailable().size() == 3);
		}	
	}
	
	@Test
	public void testEmployeeDeleteAllWorkdaysAndSkillsDeleted() {
		Employee newEmployee = new Employee("Lorraine Figueroa");
		newEmployee.addSkill(EmployeeSkill.PETTING);
		newEmployee.addSkill(EmployeeSkill.WALKING);
		newEmployee.addWorkday(DayOfWeek.MONDAY);
		newEmployee.addWorkday(DayOfWeek.WEDNESDAY);
		newEmployee.addWorkday(DayOfWeek.FRIDAY);
		employeeRepository.save(newEmployee);
		
		// Get the record anew
		Optional<Employee> optionalSavedEmployee = employeeRepository.findById(newEmployee.getId());
		Employee savedEmployee = null;
		if (optionalSavedEmployee.isPresent()) {
			savedEmployee = optionalSavedEmployee.get();
		}	
		
		// Now delete employee record
		employeeRepository.delete(savedEmployee);
		
		// Check the db
		optionalSavedEmployee = employeeRepository.findById(newEmployee.getId());
		assertTrue(optionalSavedEmployee.isEmpty());

	}

	@Test
	public void testEmployeeEquality1() {
		// equals
		Employee employee1 = new Employee("Lorraine Figueroa");
		employee1.setId(1000L);
		
		Employee employee2 = new Employee("Lorraine Figueroa");
		employee2.setId(1000L);
		
		assertTrue(employee1.equals(employee2));
		
	}
	
	@Test
	public void testEmployeeEquality2() {
		// NOT equals
		Employee employee1 = new Employee("Lorraine Figueroa");
		employee1.setId(1000L);
		
		Employee employee2 = new Employee("Lorraine Figueroa");
		employee2.setId(2000L);
		
		assertFalse(employee1.equals(employee2));
	}	
}
