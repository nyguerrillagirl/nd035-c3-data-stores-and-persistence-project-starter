package com.udacity.jdnd.course3.critter.pet;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.ICustomerService;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class TestPetRepository {
	
	@Autowired
	private ICustomerService customerService;

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private PetRepository petRepository;

	@Test
	void testGetCustomerPets() {
		Long customerId = createCustomer();
		Optional<Customer>  optionalCustomer = customerRepository.findById(customerId);
		Customer savedCustomer = null;
		if (optionalCustomer.isPresent()) {
			savedCustomer = optionalCustomer.get();
		} else {
			fail("Could not find created customer!");
		}
		// Add some pets
		Set<Pet> petSet = new HashSet<>();
		
		Pet purdyCat = new Pet(PetType.CAT, "purdy", LocalDate.now());
		purdyCat.setNotes("An old and friendly cat. Handle with care");
		purdyCat.setCustomer(savedCustomer);
		petRepository.save(purdyCat);
		petSet.add(purdyCat);

		Pet louie = new Pet(PetType.DOG, "louie", LocalDate.now());
		louie.setNotes("An old and not so friendly dog. Needs a bath!");
		louie.setCustomer(savedCustomer);
		petRepository.save(louie);
		petSet.add(louie);
		
		savedCustomer.setOwnedPets(petSet);
		customerRepository.save(savedCustomer);
		
		// Start test here
		List<Pet> lstCustomerPets = petRepository.findByCustomer(savedCustomer);
		assertTrue(lstCustomerPets.size() == 2);
		
		for (Pet aPet:lstCustomerPets) {
			System.out.println("aPet: " + aPet.toString());
		}
	}
	
	private Long createCustomer() {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setName("Nathan Hale");
		customerDTO.setNotes("Nathan is a radical with various pets");
		customerDTO.setPhoneNumber("2025556666");
		customerDTO = customerService.saveCustomer(customerDTO);
		return customerDTO.getId();
	}
}
