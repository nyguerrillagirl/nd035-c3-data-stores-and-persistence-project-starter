package com.udacity.jdnd.course3.critter.entity;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

@Entity
@Table(name = "employees")
public class Employee extends Person {

	@ElementCollection(targetClass = EmployeeSkill.class)
	@JoinTable(name = "employee_skill", joinColumns = @JoinColumn(name = "employee_id"))
	@Column(name = "skill", nullable = false)
	@Enumerated(EnumType.STRING)
	private Set<EmployeeSkill> skills;

	@ElementCollection(targetClass = DayOfWeek.class)
	@JoinTable(name = "employee_workday", joinColumns = @JoinColumn(name = "employee_id"))
	@Column(name = "workday", nullable = false)
	@Enumerated(EnumType.STRING)
	private Set<DayOfWeek> daysAvailable;

	@ManyToMany(mappedBy = "scheduledEmployees")
	private Set<Schedule> schedules = new HashSet<Schedule>();
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id: " + getId() + ";name: " + getName());
		if (skills != null) {
			sb.append("\nSkills:\n");
			for (EmployeeSkill aSkill : skills) {
				sb.append("\tskill: " + aSkill.toString() + "\n");
			}
		}

		if (daysAvailable != null) {
			sb.append("\nWorkdays\n");
			for (DayOfWeek aWorkday : daysAvailable) {
				sb.append("\tday: " + aWorkday.toString() + "\n");
			}
		}
		return sb.toString();
	}

	// Empty argument constructor
	public Employee() {
	}

	public Employee(String name) {
		setName(name);
	}

	public Set<EmployeeSkill> getSkills() {
		return skills;
	}

	public void setSkills(Set<EmployeeSkill> skills) {
		this.skills = skills;
	}

	public Set<DayOfWeek> getDaysAvailable() {
		return daysAvailable;
	}

	public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
		this.daysAvailable = daysAvailable;
	}

	public void addSkill(EmployeeSkill skill) {
		if (skills == null) {
			skills = new HashSet<>();
		}
		skills.add(skill);
	}

	public void addWorkday(DayOfWeek workday) {
		if (daysAvailable == null) {
			daysAvailable = new HashSet<>();
		}
		daysAvailable.add(workday);
	}

	public Set<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(Set<Schedule> schedules) {
		this.schedules = schedules;
	}
	
	   @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        Employee employee = (Employee) o;
	        return Objects.equals(getId(), employee.getId());
	    }
	 
	    @Override
	    public int hashCode() {
	        return Objects.hash(name);
	    }	

}
