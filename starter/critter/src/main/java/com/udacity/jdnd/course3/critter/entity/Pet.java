package com.udacity.jdnd.course3.critter.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.udacity.jdnd.course3.critter.pet.PetType;

@Entity
@Table(name = "pets")
public class Pet {

	@Id
	@GeneratedValue
	private Long id;

	@Enumerated(EnumType.STRING)
	private PetType type;
	
	private String name;
	
	@Column(name = "birth_date")
	private LocalDate birthDate;
	
	@Column(length=2000)
	private String notes;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="customer_id", nullable=false)
	private Customer customer;
	
	public Pet() {}
	
	public Pet(PetType type, String name, LocalDate birthDate) {
		this.type = type;
		this.name = name;
		this.birthDate = birthDate;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id: " + id);
		sb.append("\nPet type:" + type);
		sb.append("\nname: " + name);
		if (birthDate != null) {
			String formattedDate = birthDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
			sb.append("\nBirthdate: " + formattedDate);
		}
		sb.append("\nNotes:\n");
		sb.append(notes);
		if (customer != null) {
			sb.append("\nCustomer: \n" + customer.toString());
		}
		return sb.toString();
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PetType getType() {
		return type;
	}

	public void setType(PetType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}
