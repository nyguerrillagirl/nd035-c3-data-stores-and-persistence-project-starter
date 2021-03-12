package com.udacity.jdnd.course3.critter.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SchedulePetID {
	
	@Column(name="schedule_id", nullable=false)
	private Long scheduleId;
	
	@Column(name="pet_id", nullable=false)
	private Long petId;

}
