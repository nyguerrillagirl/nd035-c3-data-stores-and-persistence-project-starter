package com.udacity.jdnd.course3.critter.utility;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

class TestUtility {

	@Test
	void testBeanUtilsOnEmployeeDTO() {
		// test from EmployeeDTO -> Employee
		EmployeeDTO employeeDTO = new EmployeeDTO();
		
		Set<DayOfWeek> workdays = new HashSet<DayOfWeek>();
		workdays.add(DayOfWeek.MONDAY);
		workdays.add(DayOfWeek.FRIDAY);
		employeeDTO.setDaysAvailable(workdays);
		
		employeeDTO.setId(1000);
		employeeDTO.setName("Lorraine Figueroa");
		
		Set<EmployeeSkill> skills = new HashSet<EmployeeSkill>();
		skills.add(EmployeeSkill.FEEDING);
		skills.add(EmployeeSkill.MEDICATING);
		employeeDTO.setSkills(skills);
		
		// Now use BeanUtils
		Employee employee = new Employee();
		BeanUtils.copyProperties(employeeDTO, employee);
		// check employee
		assertTrue(employee.getName().equals("Lorraine Figueroa"));
		assertTrue(employee.getDaysAvailable().size() == 2);
		assertTrue(employee.getSkills().size() == 2);
		assertTrue(employee.getId().longValue() == 1000);
	}
	
	@Test
	void testBeanUtilsOnEmployeeDTO2() {
		// test from Employee -> EmployeeDTO
		Employee employee = new Employee();
		
		Set<DayOfWeek> workdays = new HashSet<DayOfWeek>();
		workdays.add(DayOfWeek.MONDAY);
		workdays.add(DayOfWeek.FRIDAY);
		employee.setDaysAvailable(workdays);
		
		employee.setId(1000L);
		employee.setName("Lorraine Figueroa");
		
		Set<EmployeeSkill> skills = new HashSet<EmployeeSkill>();
		skills.add(EmployeeSkill.FEEDING);
		skills.add(EmployeeSkill.MEDICATING);
		employee.setSkills(skills);
		
		// Now use BeanUtils
		EmployeeDTO employeeDTO = new EmployeeDTO();

		BeanUtils.copyProperties(employee, employeeDTO);
		// check employee
		assertTrue(employeeDTO.getName().equals("Lorraine Figueroa"));
		assertTrue(employeeDTO.getDaysAvailable().size() == 2);
		assertTrue(employeeDTO.getSkills().size() == 2);
		assertTrue(employeeDTO.getId() == 1000);
	}

}
