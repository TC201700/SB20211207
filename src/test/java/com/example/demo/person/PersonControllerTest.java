package com.example.demo.person;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import com.example.demo.address.AddressRepository;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc

public class PersonControllerTest {


	@Autowired
	private MockMvc mockMvc;

    @Autowired
    PersonRepository personRepository;
    
	@Autowired 
	AddressRepository addressRepository;
    
    private final String param1 = "firstName";
    private final String param2 = "lastName";

	public void testAddForm() throws Exception {
		mockMvc.perform(get("/person/add"))
			.andExpect(xpath("//input[@name='" + param1 + "']").exists())
			.andExpect(xpath("//input[@name='" + param2 + "']").exists());
	}

	@Test
	public void testIndex() throws Exception {

		personRepository.deleteAll();

		personRepository.save(new Person("A1", "B1"));
		personRepository.save(new Person("A2", "B2"));
		personRepository.save(new Person("A3", "B3"));

        MvcResult result = mockMvc.perform(get("/person")).
                andExpect(status().isOk()).
                andExpect(view().name("listPerson")).
                andExpect(xpath("//a[contains(@href, '/person/1')]").string("View")).
                andExpect(xpath("//a[contains(@href, '/person/2')]").string("View")).
                andExpect(xpath("//a[contains(@href, '/person/3')]").string("View")).
                andReturn();
	}

	
	@Test
	public void testCreate() throws Exception {
		
		long next = personRepository.count() + 1;
		
		MockHttpServletRequestBuilder request = post("/person")
				.param(param1, "AAAAA")
				.param(param2, "BBBBB");

		mockMvc.perform(request)
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/person/" + next));
		
		Person person = personRepository.findById(next).get();
		assertEquals("AAAAA", person.getFirstName());
		assertEquals("BBBBB", person.getLastName());
	}


	@Test
	public void testEdit() throws Exception {
		
		Person person = new Person("XX", "YY");
		personRepository.save(person);
		long id = person.getId();

		MockHttpServletRequestBuilder request = post("/person")
				.param("id", String.valueOf(id))
				.param(param1, "XXX")
				.param(param2, "YYX");

		mockMvc.perform(request)
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/person/" + id));
		
		Person check = personRepository.findById(id).get();
		assertEquals("XXX", check.getFirstName());
		assertEquals("YYX", check.getLastName());
	}

}
