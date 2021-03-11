package com.udacity.jdnd.course3.critter.service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;

@Service
public class EmployeeServiceImpl implements IEmployeeService {
	private static Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
		logger.info("===> EmployeeServiceImpl.saveEmployee invoked");
		Employee employeeEntity = new Employee();
		BeanUtils.copyProperties(employeeDTO, employeeEntity);
		employeeEntity.setId(null); // needs to be cleared!
		employeeRepository.save(employeeEntity);
		logger.info("===> after saving employeeEntity: " + employeeEntity.toString());
		employeeDTO.setId(employeeEntity.getId());
		return employeeDTO;
	}

	@Override
	public EmployeeDTO getEmployee(Long id) {
		EmployeeDTO employleeDTO = new EmployeeDTO();
		Optional<Employee> optionalEmployee = employeeRepository.findById(id);
		if (optionalEmployee.isPresent()) {
			Employee employee = optionalEmployee.get();
			BeanUtils.copyProperties(employee, employleeDTO);
		} else {
			throw new EmployeeNotFoundException("Employee with id: " + id + " not found.");
		}
		return employleeDTO;
	}

	@Override
	public void setAvailability(Set<DayOfWeek> daysAvailable, Long id) {
		// Find employee
		Optional<Employee> optionalEmployee = employeeRepository.findById(id);
		if (optionalEmployee.isPresent()) {
			logger.info("EmployeeService.setAvailabity - found employee with id: " + id);
			Employee employeeEntity = optionalEmployee.get();
			// 2A. Clear out current availability set
			clearEmployeeWorkdays(employeeEntity);
			
			// 2B. Process current work days - daysAvailable
			for (DayOfWeek aDay: daysAvailable) {
				// 3. Add to employee
				employeeEntity.addWorkday(aDay);			
			}
			// 4. save employee
			employeeRepository.save(employeeEntity);
		} else {
			throw new EmployeeNotFoundException("Employee id:" + id + " not found.");
		}	
	}
	
	protected void clearEmployeeWorkdays(Employee employeeEntity) {
		if (employeeEntity.getDaysAvailable() != null && employeeEntity.getDaysAvailable().size() != 0) {
			employeeEntity.setDaysAvailable(null);
			employeeRepository.save(employeeEntity);
		}
	}
	
	@Override
	public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}
