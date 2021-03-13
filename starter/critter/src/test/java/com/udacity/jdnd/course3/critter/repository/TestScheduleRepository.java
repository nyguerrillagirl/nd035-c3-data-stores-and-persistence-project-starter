package com.udacity.jdnd.course3.critter.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Schedule;

@DataJpaTest
class TestScheduleRepository {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ScheduleRepository scheduleRepository;

	@Test
	void testFindingSchedulesByEmployeeId() {
		// First create an employee
		Employee employee = new Employee("Lorraine Figueroa");
		employeeRepository.save(employee);
		Long employeeId = employee.getId();
		
		// Create a schedule and add employee
		Schedule schedule = new Schedule(LocalDate.now());
		schedule.addScheduledEmployee(employee);
		scheduleRepository.save(schedule);
		
		// Using employee record find all schedules
		Optional<Employee> optionalSavedEmployee = employeeRepository.findById(employeeId);
		Employee savedEmployee = null;
		if (optionalSavedEmployee.isPresent()) {
			savedEmployee = optionalSavedEmployee.get();
		} else {
			fail("testFindingSchedulesByEmployeeId - unable to find employee in database");
		}
		// check that employee has a schedule
		Set<Schedule> employeeSchedules = savedEmployee.getSchedules();
		assertTrue(employeeSchedules.size() == 1);
	}
	@Test
	void testFindingSchedulesByEmployeeId2() {
		// Add employee to two schedules
		// First create an employee
		Employee employee = new Employee("Lorraine Figueroa");
		employeeRepository.save(employee);
		Long employeeId = employee.getId();
		
		// Create a schedule and add employee
		Schedule schedule = new Schedule(LocalDate.now());
		schedule.addScheduledEmployee(employee);
		scheduleRepository.save(schedule);
		
		// Create a schedule and add employee
		Schedule schedule2 = new Schedule(LocalDate.now().plusDays(1));
		schedule2.addScheduledEmployee(employee);
		scheduleRepository.save(schedule2);
		
		// Using employee record find all schedules
		Optional<Employee> optionalSavedEmployee = employeeRepository.findById(employeeId);
		Employee savedEmployee = null;
		if (optionalSavedEmployee.isPresent()) {
			savedEmployee = optionalSavedEmployee.get();
		} else {
			fail("testFindingSchedulesByEmployeeId - unable to find employee in database");
		}
		// check that employee has a schedule
		Set<Schedule> employeeSchedules = savedEmployee.getSchedules();
		assertTrue(employeeSchedules.size() == 2);
		System.out.println("Employee: \n" + employee.toString());
	}
}
