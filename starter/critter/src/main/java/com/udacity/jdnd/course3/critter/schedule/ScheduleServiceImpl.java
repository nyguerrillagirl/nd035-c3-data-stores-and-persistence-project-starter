package com.udacity.jdnd.course3.critter.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.pet.PetNotFoundException;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.service.EmployeeNotFoundException;

@Service
@Transactional
public class ScheduleServiceImpl implements IScheduleService {

	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private PetRepository petRepository;
	
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
		List<ScheduleDTO> lstScheduleDTOs = new ArrayList<ScheduleDTO>();
		if (lstSchedules != null && lstSchedules.size() > 0) {
			for (Schedule aSchedule : lstSchedules) {
				lstScheduleDTOs.add(convert(aSchedule));
			}
		}
		return lstScheduleDTOs;
	}

	@Override
	public List<ScheduleDTO> getScheduleForPet(long petId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ScheduleDTO> getScheduleForEmployee(long employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ScheduleDTO> getScheduleForCustomer(long customerId) {
		// TODO Auto-generated method stub
		return null;
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
}
