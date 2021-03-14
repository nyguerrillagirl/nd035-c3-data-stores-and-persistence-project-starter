package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Represents the form that schedule request and response data takes. Does not map
 * to the database directly.
 */
public class ScheduleDTO {
    private long id;
    private List<Long> employeeIds;
    private List<Long> petIds;
    private LocalDate date;
    private Set<EmployeeSkill> activities;

    
    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Long> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Long> employeeIds) {
        this.employeeIds = employeeIds;
        // Required for test cases to work as originally designed!
        Collections.sort(this.employeeIds);
   }

    public List<Long> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Long> petIds) {
    	// Since the test cases required that this list be sorted
        this.petIds = petIds;
        // Required for test cases to work as originally designed!
       Collections.sort(this.petIds);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }
    
	public String toString() {
		StringBuilder sb = new StringBuilder();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY"); 
		sb.append("Id: " + id + ";Appt. date: " + formatter.format(date) + "\n");
		if (employeeIds != null) {
			for (Long employeeId:employeeIds) {
				sb.append("\n\tEmployee Id: " + employeeId);
			}
		}
		if (petIds != null) {
			for (Long petId:petIds) {
				sb.append("\n\tPet Id: " + petId);
			}
		}
		if (activities != null) {
			for (EmployeeSkill aSkill:activities) {
				sb.append("\n\tSkill: " + aSkill);
			}
		}
		
		return sb.toString();
	}
}
