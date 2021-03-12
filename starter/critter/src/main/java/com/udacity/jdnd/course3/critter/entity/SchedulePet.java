package com.udacity.jdnd.course3.critter.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "schedule_pets")
public class SchedulePet {
	
	@EmbeddedId
	private SchedulePetID id;
}
