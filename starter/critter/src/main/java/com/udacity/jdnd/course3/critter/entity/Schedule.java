package com.udacity.jdnd.course3.critter.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

@Entity
public class Schedule {

	@Id
	@GeneratedValue
	private Long id;
	
	private LocalDate date;
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "schedule_employee", joinColumns = @JoinColumn(name = "schedule_id"), 
	inverseJoinColumns = @JoinColumn(name = "employee_id"))
	private Set<Employee> scheduledEmployees = new HashSet<Employee>();
	
	//private Set<Pet> pets;
	//private Set<EmployeeSkill> activities;

	public Schedule() {}
	
	public Schedule(LocalDate date) {
		this.date = date;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY"); 
		sb.append("Appt. date: " + formatter.format(date) + "\n");
		return sb.toString();
	}
	public void addScheduledEmployee(Employee employee) {
		scheduledEmployees.add(employee);
		employee.getSchedules().add(this);
	}
	
	public void removeScheduledEmployee(Employee employee) {
		scheduledEmployees.remove(employee);
		employee.getSchedules().remove(this);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Set<Employee> getScheduledEmployees() {
		return scheduledEmployees;
	}

	public void setScheduledEmployees(Set<Employee> scheduledEmployees) {
		this.scheduledEmployees = scheduledEmployees;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule)) return false;
        return id != null && id.equals(((Schedule) o).getId());
    }
 
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
	
}
