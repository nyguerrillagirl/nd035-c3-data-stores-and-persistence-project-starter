package com.udacity.jdnd.course3.critter.schedule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.pet.PetNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.service.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.service.EmployeeNotFoundException;

@Service
@Transactional
public class ScheduleServiceImpl implements IScheduleService {
	private static Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);

	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private PetRepository petRepository;
	
	@Autowired CustomerRepository customerRepository;
	
	@Override
	public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
		Schedule schedule = new Schedule();
		schedule.setDate(scheduleDTO.getDate());
		processEmployeeList(schedule, scheduleDTO.getEmployeeIds());
		processPetList(schedule, scheduleDTO.getPetIds());
		schedule.setScheduledActivities(scheduleDTO.getActivities());
		scheduleRepository.save(schedule);
		scheduleDTO.setId(schedule.getId());
		return scheduleDTO;
	}



	@Override
	public List<ScheduleDTO> getAllSchedules() {
		List<Schedule> lstSchedules = scheduleRepository.findAll();
		return convertFromScheduleListToDTOList(lstSchedules);
	}

	@Override
	public List<ScheduleDTO> getScheduleForPet(long petId) {
		Optional<Pet> optionalPet = petRepository.findById(petId);
		if (optionalPet.isPresent()) {
			Pet pet = optionalPet.get();
			List<Schedule> lstSchedules = scheduleRepository.findByScheduledPets(pet);
			return convertFromScheduleListToDTOList(lstSchedules);
		} else {
			throw new PetNotFoundException("Pet with id: " + petId + " not found.");
		}
	}

	@Override
	public List<ScheduleDTO> getScheduleForEmployee(long employeeId) {
		Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
		if (optionalEmployee.isPresent()) {
			Employee employee = optionalEmployee.get();
			List<Schedule> lstSchedules = scheduleRepository.findByScheduledEmployees(employee);			
			return convertFromScheduleListToDTOList(lstSchedules);
		} else {
			throw new EmployeeNotFoundException("Employee with id: " + employeeId + " not found.");
		}
	}

	@Override
	public List<ScheduleDTO> getScheduleForCustomer(long customerId) {
		logger.info("===> getScheduleForCustomer - customerId: " + customerId);
		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
		if (optionalCustomer.isPresent()) {
			Customer customer = optionalCustomer.get();
			logger.info("===> getScheduleForCustomer - customer: " + customer.toString());
			Set<Pet> lstCustomerPets = customer.getOwnedPets();
			List<ScheduleDTO> lstScheduleDTOs = new ArrayList<ScheduleDTO>();
			Set<Schedule> scheduleSet = new HashSet<Schedule>();
			if (lstCustomerPets != null && lstCustomerPets.size() > 0) {
				for (Pet aPet:lstCustomerPets) {
					logger.info("===> getScheduleForCustomer finding the schedule associated with pet: " + aPet.getId());
					List<Schedule> customerPetSchedules = scheduleRepository.findByScheduledPets(aPet);
					addListToScheduleSet(scheduleSet, customerPetSchedules);
				}
			}
			// Now copy over to lstScheduleDTOs!
			List<Schedule> aList = new ArrayList<Schedule>(scheduleSet); 
			return convertFromScheduleListToDTOList(aList);
		} else {
			throw new CustomerNotFoundException("Customer with id: " + customerId + " not found.");
		}
		
	}
	
	protected void addListToScheduleSet(Set<Schedule> scheduleSet, List<Schedule> customerPetSchedules) {
		if (customerPetSchedules != null && customerPetSchedules.size() > 0) {
			for (Schedule aSchedule:customerPetSchedules) {
				logger.info("addListToScheduleSet - adding schedule: " + aSchedule.getId());
				scheduleSet.add(aSchedule);
			}
		}		
	}



	protected ScheduleDTO convert(Schedule aSchedule) {
		//  Use BeanUtil for what it is good for
		ScheduleDTO scheduleDTO = new ScheduleDTO();
		BeanUtils.copyProperties(aSchedule, scheduleDTO);
		// Process employees
		List<Long> employeeIds = new ArrayList<Long>();
		if (aSchedule.getScheduledEmployees() != null && aSchedule.getScheduledEmployees().size() > 0) {
			for (Employee anEmployee:aSchedule.getScheduledEmployees()) {
				employeeIds.add(anEmployee.getId());
			}
		}
		scheduleDTO.setEmployeeIds(employeeIds);
		// Process pets
		List<Long> petIds = new ArrayList<Long>();
		if (aSchedule.getScheduledPets() != null && aSchedule.getScheduledPets().size() > 0) {
			for (Pet aPet:aSchedule.getScheduledPets() ) {
				petIds.add(aPet.getId());
			}
		}
		scheduleDTO.setPetIds(petIds);
		return scheduleDTO;
	}
	
	protected void processEmployeeList(Schedule schedule, List<Long> employeeIds) {
		if (employeeIds != null && employeeIds.size() > 0) {
			for (Long employeeId : employeeIds) {
				Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
				if (optionalEmployee.isPresent()) {
					schedule.addScheduledEmployee(optionalEmployee.get());
				} else {
					throw new EmployeeNotFoundException("Schedule processing failed because employee id: " + employeeId + " not found.");
				}
			}
		}	
	}
	
	protected List<ScheduleDTO> convertFromScheduleListToDTOList(List<Schedule> lstSchedules) {
		List<ScheduleDTO> lstScheduleDTOs  = new ArrayList<ScheduleDTO>();
		if (lstSchedules != null && lstSchedules.size() > 0) {
			for (Schedule aSchedule : lstSchedules) {
				lstScheduleDTOs.add(convert(aSchedule));
			}
		}	
		return lstScheduleDTOs;
	}
	
	protected void processPetList(Schedule schedule, List<Long> petIds) {
		if (petIds != null && petIds.size() > 0) {
			for (Long petId : petIds) {
				Optional<Pet> optionalPet = petRepository.findById(petId);
				if (optionalPet.isPresent()) {
					schedule.addScheduledPet(optionalPet.get());
				} else {
					throw new PetNotFoundException("Schedule processing failed because pet id: " + petId + " not found.");
				}
			}
		}
	}
	
	protected void removeAllSchedules() {
		scheduleRepository.deleteAll();
	}
}
