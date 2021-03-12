package com.udacity.jdnd.course3.critter.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

@Embeddable
public class ScheduleActivityID {
	
	@Column(name="schedule_id", nullable=false)
	private Long scheduleId;
	
	@Column(name="activity_id", nullable=false)
	@Enumerated(EnumType.ORDINAL)
	private EmployeeSkill activity;

}
