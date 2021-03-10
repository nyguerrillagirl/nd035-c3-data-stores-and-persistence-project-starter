package com.udacity.jdnd.course3.critter.service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;

public interface IEmployeeService {
	
	public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);
	
	public EmployeeDTO getEmployee(Long id);
	
	public void setAvailability(Set<DayOfWeek> daysAvailable, Long id);
	
	public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO);

}
