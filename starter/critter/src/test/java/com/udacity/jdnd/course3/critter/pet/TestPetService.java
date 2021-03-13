package com.udacity.jdnd.course3.critter.pet;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.service.ICustomerService;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class TestPetService {
	
	@Autowired
	private IPetService petService;
	
	@Autowired
	private ICustomerService customerService;
	
	@Autowired
	private PetRepository petRepository;

	@Test
	void testSavePet() {
		Long customerId = createCustomer();
		
		PetDTO petDTO = new PetDTO();
		petDTO.setBirthDate(LocalDate.now());
		petDTO.setName("Alice Toklas");
		petDTO.setNotes("Needs to be shaved quite often!");
		petDTO.setType(PetType.BIRD);
		petDTO.setOwnerId(customerId);
		
		petDTO = petService.savePet(petDTO);
		
		// Find pet in db
		PetDTO savedPet = petService.getPet(petDTO.getId());
		assertTrue(savedPet.getName().equals(petDTO.getName()));
	}
	
	@Test
	void testSavePet2() {
		// Save two pets for the same customer
		Long customerId = createCustomer();
		
		PetDTO petDTO = new PetDTO();
		petDTO.setBirthDate(LocalDate.now());
		petDTO.setName("Alice Toklas");
		petDTO.setNotes("Needs to be shaved quite often!");
		petDTO.setType(PetType.BIRD);
		petDTO.setOwnerId(customerId);
		
		petDTO = petService.savePet(petDTO);
		
		// Find pet in db
		PetDTO savedPet = petService.getPet(petDTO.getId());
		assertTrue(savedPet.getName().equals(petDTO.getName()));
		
		PetDTO petDTO2 = new PetDTO();
		petDTO2.setBirthDate(LocalDate.now());
		petDTO2.setName("Gertrude Stein");
		petDTO2.setNotes("Needs to be on a diet");
		petDTO2.setType(PetType.CAT);
		petDTO2.setOwnerId(customerId);
		
		petDTO2 = petService.savePet(petDTO2);
		
		// Check via customer 
		List<PetDTO> lstOwnersPets = petService.getPetsByOwner(customerId);
		
		assertTrue(lstOwnersPets.size() == 2);
	}	
	
	private Long createCustomer() {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setName("Nathan Hale");
		customerDTO.setNotes("Nathan is a radical with various pets");
		customerDTO.setPhoneNumber("2025556666");
		customerDTO = customerService.saveCustomer(customerDTO);
		return customerDTO.getId();
	}
	
	@Test
	public void testObtainPetsFromIdsOK() {
		Long customerId = createCustomer();
		
		// Create pet1 for customer
		PetDTO petDTO = new PetDTO();
		petDTO.setBirthDate(LocalDate.now());
		petDTO.setName("Alice Toklas");
		petDTO.setNotes("Needs to be shaved quite often!");
		petDTO.setType(PetType.BIRD);
		petDTO.setOwnerId(customerId);
		
		petDTO = petService.savePet(petDTO);
		
		// Find pet in db
		Optional<Pet> optionalPet = petRepository.findById(petDTO.getId());
		Pet pet1 = null;
		if (optionalPet.isPresent()) {
			pet1 = optionalPet.get();
		}
		// Create pet 2		
		PetDTO petDTO2 = new PetDTO();
		petDTO2.setBirthDate(LocalDate.now());
		petDTO2.setName("Gertrude Stein");
		petDTO2.setNotes("Needs to be on a diet");
		petDTO2.setType(PetType.CAT);
		petDTO2.setOwnerId(customerId);
		
		petDTO2 = petService.savePet(petDTO2);
		optionalPet = petRepository.findById(petDTO2.getId());
		Pet pet2 = null;
		if (optionalPet.isPresent()) {
			pet2 = optionalPet.get();
		}
		
		// Now create List<long> to test
		List<Long> petIds = new ArrayList<Long>();
		petIds.add(pet1.getId());
		petIds.add(pet2.getId());
		
		List<Pet> foundPets = petService.obtainPetsFromIds(petIds);
		assertTrue(foundPets.size() == 2);
	}
	
	@Test
	public void testObtainPetsFromIdsNotOK() {
		List<Long> petIds = new ArrayList<Long>();
		petIds.add(0L);
		RuntimeException exception = assertThrows(PetNotFoundException.class, () -> {
	        petService.obtainPetsFromIds(petIds);
	    });
	    String expectedMessage = "Could not locate pet with id: 0";
	    String actualMessage = exception.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));	
		
	}

}
