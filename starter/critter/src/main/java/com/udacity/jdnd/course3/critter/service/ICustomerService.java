package com.udacity.jdnd.course3.critter.service;


import java.util.List;


import com.udacity.jdnd.course3.critter.user.CustomerDTO;

public interface ICustomerService {

	public CustomerDTO saveCustomer(CustomerDTO customerDTO);
	public List<CustomerDTO> getAllCustomers();
	public CustomerDTO getOwnerByPet(Long petId);
	
}
