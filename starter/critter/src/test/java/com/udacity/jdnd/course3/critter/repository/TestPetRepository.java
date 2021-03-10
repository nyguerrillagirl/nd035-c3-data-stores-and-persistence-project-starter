package com.udacity.jdnd.course3.critter.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.PetType;

@DataJpaTest
class TestPetRepository {
	
	@Autowired
	private PetRepository petRepository;
	
	@Autowired
	private CustomerRepository customerRepository;

	@Test
	void test() {
		// first create a customer
		Customer customer = new Customer("Lorraine Figueroa", "212-555-7777");
		customerRepository.save(customer);
		
		LocalDate today = LocalDate.now();
		Pet newPet = new Pet(PetType.CAT, "purdy", today);
		String notes = "Purdy is having trouble breathing since the weather has gotten cold.";
		newPet.setNotes(notes);
		newPet.setCustomer(customer);
		petRepository.save(newPet);
		
		Optional<Pet> optionalSavedPet = petRepository.findById(newPet.getId());
		Pet savedPet = null;
		if (!optionalSavedPet.isEmpty()) {
			savedPet = optionalSavedPet.get();
		}
		assertNotNull(savedPet);
		System.out.println("===> savedPet:\n" + savedPet.toString());
		
		assertTrue("purdy".equals(savedPet.getName()));
		assertTrue(savedPet.getCustomer().getName().equals("Lorraine Figueroa"));
	}

}
