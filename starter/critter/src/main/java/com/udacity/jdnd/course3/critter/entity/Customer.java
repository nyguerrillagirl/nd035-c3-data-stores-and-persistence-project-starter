package com.udacity.jdnd.course3.critter.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "customers")
public class Customer extends Person {

	@Column(name="phone_number")
	@NotNull
	private String phoneNumber;
	
	@Column(length=2000)
	@Size(max=2000)
	private String notes;
	
	@OneToMany(mappedBy="customer", fetch = FetchType.EAGER)
	private Set<Pet> ownedPets;
	
	public String toString() {
		return "id: " + getId() + ";name: " + getName() + ";phoneNumber: " + phoneNumber;
	}
	
	// Empty argument constructor
	public Customer() {}
	
	public Customer(String name, String phoneNumber) {
		setName(name);
		setPhoneNumber(phoneNumber);
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Set<Pet> getOwnedPets() {
		return ownedPets;
	}

	public void setOwnedPets(Set<Pet> ownedPets) {
		this.ownedPets = ownedPets;
	}
	
	public void addPet(Pet pet) {
		if (ownedPets == null) {
			ownedPets = new HashSet<Pet>();
		}
		ownedPets.add(pet);
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
}
