package com.udacity.jdnd.course3.critter.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.pet.PetType;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

@DataJpaTest
class TestScheduleRepository {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Autowired
	private PetRepository petRepository;
	

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
	}
	
	@Test
	void testFindingSchedulesByPetId() {
		// First create a Pet
		Pet pet = new Pet(PetType.CAT, "Tom", LocalDate.now());
		petRepository.save(pet);
		Long petId = pet.getId();
		
		// Create a schedule and add employee
		Schedule schedule = new Schedule(LocalDate.now());
		schedule.addScheduledPet(pet);
		scheduleRepository.save(schedule);
		
		// Using employee record find all schedules
		Optional<Pet> optionalSavedPet = petRepository.findById(petId);
		Pet savedPet = null;
		if (optionalSavedPet.isPresent()) {
			savedPet = optionalSavedPet.get();
		} else {
			fail("testFindingSchedulesByEmployeeId - unable to find pet in database");
		}
		// check that employee has a schedule
		Set<Schedule> petSchedules = savedPet.getSchedules();
		assertTrue(petSchedules.size() == 1);
	}	
	
	@Test
	void testSettingActivities() {
		// Create a schedule and add activities
		Schedule schedule = new Schedule(LocalDate.now());
		schedule.addActivity(EmployeeSkill.FEEDING);
		schedule.addActivity(EmployeeSkill.PETTING);
		scheduleRepository.save(schedule);
		Long scheduleId = schedule.getId();
		
		// Test the activities have been saved
		Optional<Schedule> optionalSavedSchedule = scheduleRepository.findById(scheduleId);
		Schedule savedSchedule = null;
		if (optionalSavedSchedule.isPresent()) {
			savedSchedule = optionalSavedSchedule.get();
		}
		assertTrue(savedSchedule.getActivities().size() == 2);
	}
}
