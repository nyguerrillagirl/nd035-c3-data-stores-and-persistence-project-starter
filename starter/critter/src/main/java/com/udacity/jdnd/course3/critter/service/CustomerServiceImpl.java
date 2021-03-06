package com.udacity.jdnd.course3.critter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.PetNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;

@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService {
	private static Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private PetRepository petRepository;
	
	@Override
	public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
		Customer customer = new Customer();
		copyFromDTOToCustomer(customerDTO, customer);
		customerRepository.save(customer);
		logger.info("customer id: " + customer.getId());
		customerDTO.setId(customer.getId());
		return customerDTO;
	}

	@Override
	public List<CustomerDTO> getAllCustomers() {
		List<CustomerDTO> lstCustomerDTOs = new ArrayList<CustomerDTO>();
		List<Customer> lstCustomers = customerRepository.findAll();
		if (lstCustomers != null) {
			for (Customer customer:lstCustomers) {
				CustomerDTO customerDTO = new CustomerDTO();
				copyFromCustomerToDTO(customer, customerDTO);
				lstCustomerDTOs.add(customerDTO);
			}
		}
		
		return lstCustomerDTOs;
	}

	@Override
	public CustomerDTO getOwnerByPet(Long petId) {
		
		CustomerDTO customerDTO = new CustomerDTO();
		Optional<Pet> optionalPet = petRepository.findById(petId);
		if (optionalPet.isPresent()) {
			Pet pet = optionalPet.get();
			Customer customer1 = pet.getCustomer();
						
			copyFromCustomerToDTO(customer1, customerDTO);
		}
		return customerDTO;
	}

	protected void copyFromCustomerToDTO(Customer customer, CustomerDTO customerDTO)  {
		BeanUtils.copyProperties(customer, customerDTO);
		// Process pet data
		List<Long> petIds = new ArrayList<Long>();
		if (customer.getOwnedPets() != null) {
			for (Pet pet:customer.getOwnedPets() ) {
				petIds.add(pet.getId());
			}
		}
		customerDTO.setPetIds(petIds);
		
	}
	protected void copyFromDTOToCustomer(CustomerDTO customerDTO, Customer customer) {
		BeanUtils.copyProperties(customerDTO, customer);
		if (customerDTO.getId() == 0) {
			customer.setId(null);
		}
		List<Long> petIds = customerDTO.getPetIds();
		if (petIds != null && petIds.size() > 0) {
			for (Long petId: petIds) {
				Optional<Pet> optionalPet = petRepository.findById(petId);
				if (optionalPet.isPresent()) {
					customer.addPet(optionalPet.get());
				} else {
					throw new PetNotFoundException("petId: " + petId + " not found.");
				}
			}
		}
		
	}
}
