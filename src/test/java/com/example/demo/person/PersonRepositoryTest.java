package com.example.demo.person;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.address.Address;
import com.example.demo.address.AddressRepository;


@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    PersonRepository personRepository;
    
    @Autowired 
	AddressRepository addressRepository;

	/**
	 * @author example
	 * create some people
	 */
	@Test
	@Transactional
	public void testCreate() throws Exception {

		addressRepository.deleteAll();
		personRepository.deleteAll();
		personRepository.save(new Person("A1", "B1"));
		personRepository.save(new Person("A2", "B2"));
		personRepository.save(new Person("A3", "B3"));
		assertEquals(3, personRepository.count());
		
	}
	
	/**
	 * @author example
	 *
	 * check addresses are automatically deleted when a person is deleted
	 */
	@Test
	@Transactional
	public void createAndDelete() throws Exception {

		addressRepository.deleteAll();
		personRepository.deleteAll();
		
		Person person = new Person("A1", "B1"); 
		person.getAddresses().add(new Address(person, "11", "22", "33", "44"));
		person.getAddresses().add(new Address(person, "11", "22", "33", "44")); // then their address
		personRepository.save(person); 

		assertTrue(person.getId() > 0);
		
		assertEquals(1, personRepository.count());
		assertEquals(2, addressRepository.count());

		personRepository.delete(person);
		assertEquals(0, addressRepository.count());
		
	}

	/**
	 * @author example
	 *
	 * check addresses are automatically deleted when a person is deleted
	 */
	@Test
	@Transactional
	public void deleteAddress() throws Exception {

		addressRepository.deleteAll();
		personRepository.deleteAll();
		
		Person person = new Person("A1", "B1"); 
		person.getAddresses().add(new Address(person, "11", "22", "33", "44"));
		person.getAddresses().add(new Address(person, "11", "22", "33", "44")); // then their address
		personRepository.save(person); 

		assertTrue(person.getId() > 0);
		
		assertEquals(1, personRepository.count());
		assertEquals(2, addressRepository.count());

		person.getAddresses().remove(0);
		personRepository.save(person);

		assertEquals(1, addressRepository.count());
		
	}

}