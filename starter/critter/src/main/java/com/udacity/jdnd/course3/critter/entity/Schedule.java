package com.udacity.jdnd.course3.critter.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

@Entity
@Table(name = "schedules")
public class Schedule {

	@Id
	@GeneratedValue
	private Long id;
	
	private LocalDate date;
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "schedule_employee", joinColumns = @JoinColumn(name = "schedule_id"), 
	inverseJoinColumns = @JoinColumn(name = "employee_id"))
	private Set<Employee> scheduledEmployees = new HashSet<Employee>();

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "schedule_pet", joinColumns = @JoinColumn(name = "schedule_id"), 
	inverseJoinColumns = @JoinColumn(name = "pet_id"))
	private Set<Pet> scheduledPets = new HashSet<Pet>();
	
	@ElementCollection(targetClass = EmployeeSkill.class)
	@JoinTable(name = "schedule_activity", joinColumns = @JoinColumn(name = "schedule_id"))
	@Column(name = "skill", nullable = false)
	@Enumerated(EnumType.STRING)
	private Set<EmployeeSkill> activities = new HashSet<EmployeeSkill>();

	public Schedule() {}
	
	public Schedule(LocalDate date) {
		this.date = date;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY"); 
		sb.append("Id: " + id + ";Appt. date: " + formatter.format(date) + "\n");
		if (scheduledEmployees != null) {
			for (Employee anEmployee:scheduledEmployees) {
				sb.append("\n\tEmployee: " + anEmployee.toString());
			}
		}
		if (scheduledPets != null) {
			for (Pet aPet:scheduledPets) {
				sb.append("\n\tPet: " + aPet.toString());
			}
		}
		if (activities != null) {
			for (EmployeeSkill aSkill:activities) {
				sb.append("\n\tSkill: " + aSkill);
			}
		}
		
		return sb.toString();
	}
	// Handle Employee
	public void addScheduledEmployee(Employee employee) {
		scheduledEmployees.add(employee);
		employee.getSchedules().add(this);
	}
	
	public void removeScheduledEmployee(Employee employee) {
		scheduledEmployees.remove(employee);
		employee.getSchedules().remove(this);
	}
	
	// Handle Pet
	public void addScheduledPet(Pet pet) {
		scheduledPets.add(pet);
		pet.getSchedules().add(this);
	}
	
	public void removeScheduledPet(Pet pet) {
		scheduledPets.remove(pet);
		pet.getSchedules().remove(this);
	}
	
	// Handle Activities
	public void addActivity(EmployeeSkill activity) {
		activities.add(activity);
	}
	
	public void removeActivity(EmployeeSkill activity) {
		activities.remove(activity);
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

    public Set<Pet> getScheduledPets() {
		return scheduledPets;
	}

	public void setScheduledPets(Set<Pet> scheduledPets) {
		this.scheduledPets = scheduledPets;
	}

	public Set<EmployeeSkill> getActivities() {
		return activities;
	}

	public void setScheduledActivities(Set<EmployeeSkill> activities) {
		this.activities = activities;
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
