package com.udacity.jdnd.course3.critter.schedule;

import java.util.List;

public interface IScheduleService {

	    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO);

	    public List<ScheduleDTO> getAllSchedules();

	    public List<ScheduleDTO> getScheduleForPet(long petId);

	    public List<ScheduleDTO> getScheduleForEmployee(long employeeId);

	    public List<ScheduleDTO> getScheduleForCustomer(long customerId);
	   
}
