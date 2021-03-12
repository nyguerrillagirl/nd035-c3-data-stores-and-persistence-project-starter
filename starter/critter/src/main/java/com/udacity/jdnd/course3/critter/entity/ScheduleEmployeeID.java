package com.udacity.jdnd.course3.critter.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ScheduleEmployeeID {

	@Column(name="schedule_id", nullable=false)
	private Long scheduleId;
	
	@Column(name="employee_id", nullable=false)
	private Long employeeId;
}
