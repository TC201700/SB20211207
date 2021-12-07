package com.example.demo.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PersonService {

	@Autowired
	PersonRepository repository;

	public List<Person> getAllPersons()
	{
		List<Person> result = (List<Person>) repository.findAll();

		if(result.size() > 0) {
			return result;
		} else {
			return new ArrayList<Person>();
		}
	}

	public Person getPersonById(Long id) throws Exception 
	{
		Optional<Person> person = repository.findById(id);

		if (person.isPresent()) {
			return person.get();
		} else {
			throw new Exception("No person record exists for the given id");
		}
	}

	public Person createOrUpdatePerson(Person entity) {

		if (entity.getId() == 0) {
			
			entity = repository.save(entity);
			return entity;
		} else {
			Optional<Person> person = repository.findById(entity.getId());

			if (person.isPresent()) {
				Person newEntity = person.get();
				newEntity.setFirstName(entity.getFirstName());
				newEntity.setLastName(entity.getLastName());

				newEntity = repository.save(newEntity);

				return newEntity;
			} else {
				entity = repository.save(entity);

				return entity;
			}
		}
	} 

	public void deletePersonById(Long id) throws Exception {
		Optional<Person> person = repository.findById(id);

		if (person.isPresent()) {
			repository.deleteById(id);
		} else {
			throw new Exception("No person record exists for the given id");
		}
	} 
}
