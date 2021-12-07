package com.example.demo.person;

import javax.persistence.*;
import javax.validation.constraints.Size;
import com.example.demo.address.Address;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



@Entity
@Table(schema="public", name="person")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(length=10, nullable = false)
	@Size(min=2, max=10)
	private String firstName;

	@Column(length=10, nullable = false)
	@Size(min=2, max=10)
	private String lastName;

	@OneToMany(
		mappedBy = "person", 
		cascade = CascadeType.ALL, 
		orphanRemoval = true, 
		fetch = FetchType.EAGER
	)
	List<Address> addresses = new ArrayList<>();

	public Person() {
	}

	
	public Person(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the addresses
	 */
	public List<Address> getAddresses() {
		return addresses;
	}

	/**
	 * @param addresses the addresses to set
	 */
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}


	@Override
	public String toString() {
		return "Person " + firstName + " " + lastName;
	}

	

}
