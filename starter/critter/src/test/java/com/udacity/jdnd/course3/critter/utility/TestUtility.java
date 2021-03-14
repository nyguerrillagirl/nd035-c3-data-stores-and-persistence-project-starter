package com.udacity.jdnd.course3.critter.utility;

import static org.junit.Assert.assertTrue;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
	
	@Test
	public void testSortingLongs() {
		List<Long> list1 = Arrays.asList(100L, 200L, 50L);
		
		Collections.sort(list1);
		assertTrue(list1.get(0).intValue() == 50L);
		assertTrue(list1.get(1).intValue() == 100L);
		assertTrue(list1.get(2).intValue() == 200L);
	}
    @Test
    public void testCheckLists1() {
    	List<Long> list1 = null;
    	List<Long> list2 = null;
    	checkLongList(list1, list2);
    }
    
    @Test
    public void testCheckLists2() {
    	List<Long> list1 = Arrays.asList(100L, 200L);
    	
    	List<Long> list2 = Arrays.asList(100L, 200L);
    	checkLongList(list1, list2);
    }  
    
    @Test
    public void testCheckLists3() {
    	List<Long> list1 = Arrays.asList(100L, 200L);
    	
    	List<Long> list2 = Arrays.asList(200L, 100L);
    	checkLongList(list1, list2);
    }    
   
    private static void checkLongList(List<Long> list1, List<Long> list2) {
    	if (list1 == null) {
    		assertTrue(list2 == null);
    		return;
    	}
    	
    	assertTrue(list1.size() == list2.size());
    	
    	for (Long aValue:list1) {
    		list2.contains(aValue);
    	}
    }
}
