package com.udacity.jdnd.course3.critter.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.IPetService;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetType;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
class TestCustomerService {
	
	@Autowired
	private CustomerServiceImpl customerService;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private IPetService petService;

	@Test
	public void testCopyFromDTOToCustomer() {
		// create DTO to copy into customer
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setName("Lorraine Figueroa");
		customerDTO.setPhoneNumber("2125559999");;
		customerDTO.setNotes("Owns two pets dog - Louie and cat - Gertrude Stein");
		// Create customer entity
		Customer customer = new Customer();
		customerService.copyFromDTOToCustomer(customerDTO, customer);
		assertTrue(customer.getName().equals(customerDTO.getName()));
		assertTrue(customer.getPhoneNumber().equals(customerDTO.getPhoneNumber()));
		assertTrue(customer.getNotes().equals(customerDTO.getNotes()));
	}
	
	@Test
	public void testSaveCusomer() {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setName("Lorraine Figueroa");
		customerDTO.setPhoneNumber("2125559999");
		customerDTO.setNotes("Owns two pets dog - Louie and cat - Gertrude Stein");
		customerDTO = customerService.saveCustomer(customerDTO);
		
		// Check db
		Optional<Customer> optionalCustomer = customerRepository.findById(customerDTO.getId());
		if (optionalCustomer.isPresent()) {
			Customer customer = optionalCustomer.get();
			assertTrue(customer.getName().equals(customerDTO.getName()));
			assertTrue(customer.getPhoneNumber().equals(customerDTO.getPhoneNumber()));
			assertTrue(customer.getNotes().equals(customerDTO.getNotes()));
		} else {
			fail("Customer record not found in database!");
		}
	}
	
	@Test
	public void testGetAllCustomer() {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setName("Joe Biden");
		customerDTO.setPhoneNumber("2125559999");
		customerDTO.setNotes("Owns two dogs - Champ and Major");
		customerDTO = customerService.saveCustomer(customerDTO);
		
		CustomerDTO customerDTO2 = new CustomerDTO();
		customerDTO2.setName("Kamala Harris");
		customerDTO2.setPhoneNumber("2025558888");
		customerDTO2.setNotes("Owns two pets dog - Louie and cat - Gertrude Stein");
		customerDTO2 = customerService.saveCustomer(customerDTO2);
		
		List<Customer> allCustomers = customerRepository.findAll();
		assertTrue(allCustomers.size() == 2);
	}
	
	
	@Test
	public void testGetOwnerByPet() {
		// Create customer 
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setName("Joe Biden");
		customerDTO.setPhoneNumber("2125559999");
		customerDTO.setNotes("Owns two dogs - Champ and Major");
		customerDTO = customerService.saveCustomer(customerDTO);
		
		// Create Pet
		PetDTO petDTO = new PetDTO();
		petDTO.setBirthDate(LocalDate.now());
		petDTO.setName("tweety");
		petDTO.setNotes("Afraid of cats!");
		petDTO.setType(PetType.BIRD);
		petDTO.setOwnerId(customerDTO.getId());
		
		petService.savePet(petDTO);
		
		// Now get Customer (fresh)
		Optional<Customer> optionalSavedCustomer = customerRepository.findById(customerDTO.getId());
		Customer savedCustomer = null;
		if (optionalSavedCustomer.isPresent()) {
			savedCustomer = optionalSavedCustomer.get();
		} else {
			fail("Unable to find customer for this pet.");
		}
		// Use petId to get customer
		Pet savedPet = null;
		for (Pet aPet:savedCustomer.getOwnedPets()) {
			savedPet = aPet;
		}
		assertTrue(savedPet.getId().longValue() == petDTO.getId());
		assertTrue(savedPet.getName().equals("tweety"));
	}

}
