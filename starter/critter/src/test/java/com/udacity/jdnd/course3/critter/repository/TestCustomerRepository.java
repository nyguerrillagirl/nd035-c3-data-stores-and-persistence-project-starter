package com.udacity.jdnd.course3.critter.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.PetType;

@DataJpaTest
class TestCustomerRepository {

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	void test() {
		Customer customer = new Customer("Lorraine Figueroa", "267-900-9999");
		customerRepository.save(customer);

		// Check that our record is in the database
		List<Customer> lstCustomers = customerRepository.findByName("Lorraine Figueroa");
		Customer savedCustomerRecord = lstCustomers.get(0);
		assertTrue(customer.getName().equals(savedCustomerRecord.getName()));
	}

	@Test
	void testSavedPet() {
		Customer customer = new Customer("Lorraine Figueroa", "267-900-9999");
		Pet pet = new Pet(PetType.BIRD, "tweety", LocalDate.now());
		customer.addPet(pet);
		customerRepository.save(customer);

		// Check out customer
		Optional<Customer> optionalSavedCustomer = customerRepository.findById(customer.getId());
		if (optionalSavedCustomer.isPresent()) {
			Customer savedCustomer = optionalSavedCustomer.get();
			assertTrue(savedCustomer.getOwnedPets().size() == 1);
			Iterator<Pet> iterator = savedCustomer.getOwnedPets().iterator();
			if (iterator.hasNext()) {
				Pet element = iterator.next();
				assertTrue(element.getName().equals("tweety"));
			}
		}
	}

}
