package com.udacity.jdnd.course3.critter.pet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.CustomerNotFoundException;

@Service
@Transactional
public class PetServiceImpl implements IPetService {
	private static Logger logger = LoggerFactory.getLogger(PetServiceImpl.class);
	
	@Autowired
	private PetRepository petRepository;
	
	@Autowired 
	private CustomerRepository customerRepository;

	@Override
	public PetDTO savePet(PetDTO petDTO) {
		Optional<Customer> optionalCustomer = customerRepository.findById(petDTO.getOwnerId());
		Customer savedCustomer = null;
		if (optionalCustomer.isPresent()) {
			savedCustomer = optionalCustomer.get();
		} else {
			throw new CustomerNotFoundException("Cannot locate pet owner with id: " + petDTO.getOwnerId());
		}
		Set<Pet> petSet = savedCustomer.getOwnedPets();
		if (petSet == null) {
			petSet = new HashSet<Pet>();
		}
		// Add cat
		Pet petEntity = new Pet(petDTO.getType(), petDTO.getName(), petDTO.getBirthDate());
		petEntity.setNotes(petDTO.getNotes());
		petEntity.setCustomer(savedCustomer);
		petRepository.save(petEntity);
		petDTO.setId(petEntity.getId());
		logger.info("===> pet details: " + petEntity.toString());
		petSet.add(petEntity);
		
		savedCustomer.setOwnedPets(petSet);
		customerRepository.save(savedCustomer);	
	
		return petDTO;

	}

	protected void copyFromDTOToPet(PetDTO petDTO, Pet pet) {
		BeanUtils.copyProperties(petDTO, pet);
		if (petDTO.getOwnerId() != 0) {
			logger.info("===> Obtaining customer record");
			Optional<Customer> optionalCustomer  = customerRepository.findById(petDTO.getOwnerId());
			if (optionalCustomer.isPresent()) {
				Customer customer = optionalCustomer.get();
				logger.info("===> customer record found: \n" + customer.toString());
				pet.setCustomer(customer);
			} else {
				throw new CustomerNotFoundException("Unable to locate customer with id: " + petDTO.getOwnerId());
			}
		}
	}

	@Override
	public PetDTO getPet(Long petId) {
		Optional<Pet> optionalPet = petRepository.findById(petId);
		PetDTO petDTO = new PetDTO();
		if (optionalPet.isPresent()) {
			Pet pet = optionalPet.get();
			copyFromPetToDTO(pet,petDTO);
			return petDTO;
		} else {
			throw new PetNotFoundException("Pet id: " + petId + " not found.");
		}
	}

	private void copyFromPetToDTO(Pet pet, PetDTO petDTO) {
		BeanUtils.copyProperties(pet, petDTO);
		if (pet.getCustomer() != null) {
			petDTO.setOwnerId(pet.getCustomer().getId());
		}
	}

	@Override
	public List<PetDTO> getPets() {
		List<Pet> lstAllPets = petRepository.findAll();
		List<PetDTO> lstPetDTOs = new ArrayList<PetDTO>();
		if (lstAllPets != null && lstAllPets.size() > 0) {
			for (Pet aPet:lstAllPets) {
				PetDTO petDTO = new PetDTO();
				copyFromPetToDTO(aPet, petDTO);
				lstPetDTOs.add(petDTO);
			}
		}
		
		return lstPetDTOs;
	}

	@Override
	public List<PetDTO> getPetsByOwner(Long ownerId) {
		List<PetDTO> lstPetsDTO = new ArrayList<PetDTO>();
		Optional<Customer> optionalCustomer = customerRepository.findById(ownerId);
		if (optionalCustomer.isPresent()) {
			Customer owner = optionalCustomer.get();
			Set<Pet> customerPetSet = owner.getOwnedPets();
			if (customerPetSet != null) {
				for (Pet aPet : customerPetSet) {
					PetDTO petDTO = new PetDTO();
					copyFromPetToDTO(aPet, petDTO);
					lstPetsDTO.add(petDTO);
				}
			}
		}
		return lstPetsDTO;
	}
	
	public List<Pet> obtainPetsFromIds(List<Long> petIds) {
		List<Pet> lstPets = new ArrayList<Pet>();
		for (Long petId: petIds) {
			Optional<Pet> optionalPet = petRepository.findById(petId);
			if (optionalPet.isPresent()) {
				Pet petEntity = optionalPet.get();
				lstPets.add(petEntity);
			} else {
				throw new PetNotFoundException("Could not locate pet with id: " + petId);
			}
		}
		return lstPets;
	}

}
