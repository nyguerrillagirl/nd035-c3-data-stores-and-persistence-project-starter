package com.udacity.jdnd.course3.critter.schedule;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.pet.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.PetType;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TestScheduleService {
	
	@Autowired
	private ScheduleServiceImpl scheduleService;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired 
	private PetRepository petRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;

	
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
	public void testGetAllSchedules2() {
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
	
	@Test
	public  void testGetScheduleForPet1() {
		Assertions.assertThrows(PetNotFoundException.class,() -> {
			scheduleService.getScheduleForPet(100L);
		});
	}
	
	@Test
	public  void testGetScheduleForPet2() {
		// petid exist but not on any schedule
		Customer customer = new Customer("Lorraine Figueroa", "2223334444");
		customerRepository.save(customer);
		
		Pet pet = new Pet(PetType.LIZARD, "Sneaky", LocalDate.now());
		pet.setCustomer(customer);
		petRepository.save(pet);
		
		List<ScheduleDTO> scheduleDTO = scheduleService.getScheduleForPet(pet.getId());
		assertTrue(scheduleDTO.size() == 0);
	}
	
	@Test
	public  void testGetScheduleForPet3() {
		// petid in one schedule
		Customer customer = new Customer("Joe Biden", "2223334444");
		customerRepository.save(customer);
		
		Pet pet = new Pet(PetType.LIZARD, "Snarky", LocalDate.now());
		pet.setCustomer(customer);
		petRepository.save(pet);
		
		ScheduleDTO scheduleDTO = new ScheduleDTO();
		scheduleDTO.setDate(LocalDate.now());
		List<Long> petIds = new ArrayList<Long>();
		petIds.add(pet.getId());
		scheduleDTO.setPetIds(petIds);
		// save the schedule
		scheduleDTO  = scheduleService.createSchedule(scheduleDTO);
		// Now do the test
		List<ScheduleDTO> lstPetSchedules = scheduleService.getScheduleForPet(pet.getId());
		assertTrue(lstPetSchedules.size() == 1);
		assertTrue(lstPetSchedules.get(0).getId() == scheduleDTO.getId());		
	}	
	
	@Test
	public  void testGetScheduleForPet4() {
		Customer customer = new Customer("Barbara Striesand", "2223334444");
		customerRepository.save(customer);
		
		Pet pet = new Pet(PetType.LIZARD, "Trump", LocalDate.now());
		pet.setCustomer(customer);
		petRepository.save(pet);
		
		ScheduleDTO scheduleDTO = new ScheduleDTO();
		scheduleDTO.setDate(LocalDate.now());
		List<Long> petIds = new ArrayList<Long>();
		petIds.add(pet.getId());
		scheduleDTO.setPetIds(petIds);
		// save the schedule
		scheduleDTO  = scheduleService.createSchedule(scheduleDTO);
		
		ScheduleDTO scheduleDTO2 = new ScheduleDTO();
		scheduleDTO2.setDate(LocalDate.now());
		List<Long> petIds2 = new ArrayList<Long>();
		petIds2.add(pet.getId());
		scheduleDTO2.setPetIds(petIds2);
		// save the schedule
		scheduleDTO2 = scheduleService.createSchedule(scheduleDTO2);
		
		// Now do the test
		List<ScheduleDTO> lstPetSchedules = scheduleService.getScheduleForPet(pet.getId());
		assertTrue(lstPetSchedules.size() == 2);
		boolean foundSchedule1 = false;
		boolean foundSchedule2 = false;
		for (ScheduleDTO aScheduleDTO : lstPetSchedules) {
			if (aScheduleDTO.getId() == scheduleDTO.getId()) {
				foundSchedule1 = true;
			}
			if (aScheduleDTO.getId() == scheduleDTO2.getId()) {
				foundSchedule2 = true;
			}
		}
		assertTrue(foundSchedule1 && foundSchedule2);
	}	
	
	// Employee
	@Test
	public  void testGetScheduleForEmployee1() {
		Assertions.assertThrows(EmployeeNotFoundException.class,() -> {
			scheduleService.getScheduleForEmployee(100L);
		});
	}
	
	@Test
	public  void testGetScheduleForEmployee2() {
		// employeeid exist but not on any schedule
		
		Employee employee = new Employee("Lorraine Figueroa");
		employeeRepository.save(employee);
		List<ScheduleDTO> scheduleDTO = scheduleService.getScheduleForEmployee(employee.getId());
		assertTrue(scheduleDTO.size() == 0);
	}
	
	@Test
	public  void testGetScheduleForEmployee3() {
		// employee in one schedule
		
		Employee employee = new Employee("Yentl");
		employeeRepository.save(employee);
		
		ScheduleDTO scheduleDTO = new ScheduleDTO();
		scheduleDTO.setDate(LocalDate.now());
		List<Long> employeeIds = new ArrayList<Long>();
		employeeIds.add(employee.getId());
		scheduleDTO.setEmployeeIds(employeeIds);
		
		// save the schedule
		scheduleDTO  = scheduleService.createSchedule(scheduleDTO);
		// Now do the test
		List<ScheduleDTO> lstPetSchedules = scheduleService.getScheduleForEmployee(employee.getId());
		assertTrue(lstPetSchedules.size() == 1);
		assertTrue(lstPetSchedules.get(0).getId() == scheduleDTO.getId());
		
	}	
	
	@Test
	public  void testGetScheduleForEmployee4() {
		
		Employee employee = new Employee("Kamala Harris");
		employeeRepository.save(employee);
		
		ScheduleDTO scheduleDTO = new ScheduleDTO();
		scheduleDTO.setDate(LocalDate.now());
		List<Long> employeeIds = new ArrayList<Long>();
		employeeIds.add(employee.getId());
		scheduleDTO.setEmployeeIds(employeeIds);
		// save the schedule
		scheduleDTO  = scheduleService.createSchedule(scheduleDTO);
		
		ScheduleDTO scheduleDTO2 = new ScheduleDTO();
		scheduleDTO2.setDate(LocalDate.now());
		List<Long> employeeIds2 = new ArrayList<Long>();
		employeeIds2.add(employee.getId());
		scheduleDTO2.setEmployeeIds(employeeIds2);
		// save the schedule
		scheduleDTO2 = scheduleService.createSchedule(scheduleDTO2);
		
		// Now do the test
		List<ScheduleDTO> lstEmployeeschedules = scheduleService.getScheduleForEmployee(employee.getId());
		assertTrue(lstEmployeeschedules.size() == 2);
		boolean foundSchedule1 = false;
		boolean foundSchedule2 = false;
		for (ScheduleDTO aScheduleDTO : lstEmployeeschedules) {
			if (aScheduleDTO.getId() == scheduleDTO.getId()) {
				foundSchedule1 = true;
			}
			if (aScheduleDTO.getId() == scheduleDTO2.getId()) {
				foundSchedule2 = true;
			}
		}
		assertTrue(foundSchedule1 && foundSchedule2);
	}		
	
	@Test
	public void testAddToScheduleSet1() {
		// no schedules - null
		Set<Schedule> scheduleSet = new HashSet<Schedule>();
		List<Schedule> customerPetSchedules = null;
		scheduleService.addListToScheduleSet(scheduleSet, customerPetSchedules);
		assertTrue(scheduleSet.size() == 0);
	}
	
	@Test
	public void testAddToScheduleSet2() {
		// no schedules - empty
		Set<Schedule> scheduleSet = new HashSet<Schedule>();
		List<Schedule> customerPetSchedules = new ArrayList<Schedule>();
		scheduleService.addListToScheduleSet(scheduleSet, customerPetSchedules);
		assertTrue(scheduleSet.size() == 0);
	}
	
	@Test
	public void testAddToScheduleSet3() {
		// adding one schedule
		Set<Schedule> scheduleSet = new HashSet<Schedule>();
		List<Schedule> customerPetSchedules = new ArrayList<Schedule>();
		Schedule schedule1 = new Schedule(LocalDate.now());
		schedule1.setId(100L); // dummy id
		customerPetSchedules.add(schedule1);
		scheduleService.addListToScheduleSet(scheduleSet, customerPetSchedules);
		assertTrue(scheduleSet.size() == 1);
	}
	
	@Test
	public void testAddToScheduleSet4() {
		// adding two different schedules
		Set<Schedule> scheduleSet = new HashSet<Schedule>();
		List<Schedule> customerPetSchedules = new ArrayList<Schedule>();
		Schedule schedule1 = new Schedule(LocalDate.now());
		schedule1.setId(100L); // dummy id
		customerPetSchedules.add(schedule1);
		
		Schedule schedule2 = new Schedule(LocalDate.now());
		schedule2.setId(200L); // dummy id
		customerPetSchedules.add(schedule2);
		
		
		scheduleService.addListToScheduleSet(scheduleSet, customerPetSchedules);
		assertTrue(scheduleSet.size() == 2);
		
	}	
	
	@Test
	public void testAddToScheduleSet5() {
		// adding schedules in two runs with the same schedule on second run
		// adding two different schedules
		Set<Schedule> scheduleSet = new HashSet<Schedule>();
		List<Schedule> customerPetSchedules = new ArrayList<Schedule>();
		Schedule schedule1 = new Schedule(LocalDate.now());
		schedule1.setId(100L); // dummy id
		customerPetSchedules.add(schedule1);
		
		Schedule schedule2 = new Schedule(LocalDate.now());
		schedule2.setId(200L); // dummy id
		customerPetSchedules.add(schedule2);
		
		
		scheduleService.addListToScheduleSet(scheduleSet, customerPetSchedules);
		
		// Second run
		List<Schedule> customerPetSchedules2 = new ArrayList<Schedule>();
		customerPetSchedules2.add(schedule1);

		scheduleService.addListToScheduleSet(scheduleSet, customerPetSchedules2);
		
		assertTrue(scheduleSet.size() == 2);

		
	}	
}
