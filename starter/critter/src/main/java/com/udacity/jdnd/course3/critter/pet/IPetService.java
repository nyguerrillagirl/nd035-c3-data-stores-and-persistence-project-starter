package com.udacity.jdnd.course3.critter.pet;

import java.util.List;

import com.udacity.jdnd.course3.critter.entity.Pet;

public interface IPetService {

	public PetDTO savePet(PetDTO petDTO);
	
	public PetDTO getPet(Long petId);
	
	public List<PetDTO> getPets();
	
	public List<PetDTO> getPetsByOwner(Long ownerId);
	
	public List<Pet> obtainPetsFromIds(List<Long> petIds);
}
