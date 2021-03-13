package com.udacity.jdnd.course3.critter.schedule;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.pet.PetType;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestScheduleService {
	
	@Autowired
	private ScheduleServiceImpl scheduleService;

	@Test
	public void testConvertToDTO() {
		Schedule schedule = new Schedule();
		schedule.setId(100L);
		schedule.setDate(LocalDate.now());
		schedule.addActivity(EmployeeSkill.FEEDING);
		Employee employee1 = new Employee("Joe Biden");
		employee1.setId(1000L);
		schedule.addScheduledEmployee(employee1);
		Employee employee2 = new Employee("Kamala Harris");
		employee2.setId(2000L);
		schedule.addScheduledEmployee(employee2);
		
		Pet pet1 = new Pet(PetType.BIRD, "Tweety", LocalDate.now());
		pet1.setId(1L);
		schedule.addScheduledPet(pet1);
		
		Pet pet2 = new Pet(PetType.DOG, "Louie", LocalDate.now());
		pet2.setId(2L);
		schedule.addScheduledPet(pet2);
		
		Pet pet3= new Pet(PetType.FISH, "Ariel", LocalDate.now());
		pet3.setId(3L);
		schedule.addScheduledPet(pet3);
		
		ScheduleDTO scheduleDTO = scheduleService.convert(schedule);
		
		// test
		assertTrue(scheduleDTO.getId() == 100);
		assertTrue(scheduleDTO.getActivities().size() == 1);
		assertTrue(scheduleDTO.getEmployeeIds().size() == 2);
		assertTrue(scheduleDTO.getPetIds().size()  == 3);
		
	}
	
	@Test
	public void testGetAllSchedules() {
		// test no schedules
		List<ScheduleDTO> lstScheduleDTOs = scheduleService.getAllSchedules();
		assertTrue(lstScheduleDTOs.size() == 0);
	}
	
	@Test
	public void testCreateSchedule() {
		// Create empty schedule
		ScheduleDTO scheduleDTO = new ScheduleDTO();
		scheduleDTO.setDate(LocalDate.now());
		
		scheduleDTO = scheduleService.createSchedule(scheduleDTO);
		Long id = scheduleDTO.getId();
		// Now get the Schedule
		List<ScheduleDTO> lstSchedules = scheduleService.getAllSchedules();
		assertTrue(lstSchedules.size() == 1);
		assertTrue(lstSchedules.get(0).getId() == id);		
	}
	
	
	@Test
	public void getAllSchedules2() {
		// Add Schedule 1
		ScheduleDTO schedule1 = new ScheduleDTO();
		schedule1.setDate(LocalDate.now());
		schedule1 = scheduleService.createSchedule(schedule1);
		
		// Add Schedule 2
		ScheduleDTO schedule2 = new ScheduleDTO();
		schedule2.setDate(LocalDate.now().plusMonths(1));
		schedule2 = scheduleService.createSchedule(schedule2);
		
		List<ScheduleDTO> lstScheduleDTOs = scheduleService.getAllSchedules();
		assertTrue(lstScheduleDTOs.size() == 2);
	}
}
