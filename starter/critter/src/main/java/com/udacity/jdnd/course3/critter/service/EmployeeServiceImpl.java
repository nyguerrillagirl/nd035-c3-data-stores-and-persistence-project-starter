package com.udacity.jdnd.course3.critter.service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;

public class EmployeeServiceImpl implements IEmployeeService {

	@Override
	public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
		Employee employeeEntity = new Employee();
		BeanUtils.copyProperties(employeeDTO, employeeEntity);
		return null;
	}

	@Override
	public EmployeeDTO getEmployee(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAvailability(Set<DayOfWeek> daysAvailable, Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}
