package com.udacity.jdnd.course3.critter.entity;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

@Entity
@Table(name = "employees")
public class Employee extends Person {
	
	@ElementCollection(targetClass=EmployeeSkill.class)
	@JoinTable(
			name = "employee_skill",
			joinColumns = @JoinColumn(name = "employee_id"))
	@Column(name="skill", nullable=false)
	@Enumerated(EnumType.STRING)
	private Set<EmployeeSkill> employeeSkills;
	
	@ElementCollection(targetClass=DayOfWeek.class)
	@JoinTable(
			name = "employee_workday",
			joinColumns = @JoinColumn(name = "employee_id"))
	@Column(name="workday", nullable=false)
	@Enumerated(EnumType.STRING)
	private Set<DayOfWeek> employeeWorkdays;
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id: " + getId() + ";name: " + getName());
		if (employeeSkills != null) {
			sb.append("\nSkills:\n");
			for (EmployeeSkill aSkill:employeeSkills) {
				sb.append("\tskill: " + aSkill.toString() + "\n");
			}
		}
		
		if (employeeWorkdays != null) {
			sb.append("\nWorkdays\n");
			for (DayOfWeek aWorkday: employeeWorkdays) {
				sb.append("\tday: " + aWorkday.toString() + "\n");
			}
		}
		return sb.toString();
	}
	
	// Empty argument constructor
	public Employee() {}
	
	public Employee(String name) {
		setName(name);
	}


	
	public Set<EmployeeSkill> getEmployeeSkills() {
		return employeeSkills;
	}

	public void setEmployeeSkills(Set<EmployeeSkill> employeeSkills) {
		this.employeeSkills = employeeSkills;
	}

	public Set<DayOfWeek> getEmployeeWorkdays() {
		return employeeWorkdays;
	}

	public void setEmployeeWorkdays(Set<DayOfWeek> employeeWorkdays) {
		this.employeeWorkdays = employeeWorkdays;
	}

	public void addSkill(EmployeeSkill skill) {
		if (employeeSkills == null) {
			employeeSkills = new HashSet<>();
		}
		employeeSkills.add(skill);
	}
	
	public void addWorkday(DayOfWeek workday) {
		if (employeeWorkdays == null) {
			employeeWorkdays = new HashSet<>();
		}
		employeeWorkdays.add(workday);
	}


	
	
}
