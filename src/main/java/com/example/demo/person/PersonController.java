package com.example.demo.person;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@SessionScope // for flash messages
public class PersonController {


	@Autowired 
	PersonRepository personRepository;

	/**
	 * @author example
	 * Display a list of persons
	 */
	@Transactional(readOnly = true)
	@RequestMapping(value={"/person"}, method=RequestMethod.GET)
	public String listAll(Model model) {
		model.addAttribute("persons", personRepository.findAll());
		return "listPerson";
	}

	/**
	 * @author example
	 * Display details about an existing person
	 */
	@Transactional(readOnly = true)
	@RequestMapping(value={"/person/{id}"}, method=RequestMethod.GET)
	public String getPerson(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttrs) throws Exception {
		Optional<Person> person = personRepository.findById(id);
		
		if (person.isPresent()) {
			model.addAttribute("person", person.get());	
		} else {
			redirectAttrs.addFlashAttribute("message", "Not Found");
			return "redirect:/person";
		}
		return "viewPerson";
	}    

	/**
	 * @author example
	 * Display a form to add a new person
	 */
	@Transactional(readOnly = true)
	@RequestMapping(value={"/person/new"}, method=RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("person", new Person());
		return "editPerson";
	}

	/**
	 * @author example
	 * Display a form to edit an existing person
	 */
	@Transactional(readOnly = true)
	@RequestMapping(value={"/person/{id}/edit"}, method=RequestMethod.GET)
	public String edit(@PathVariable("id") long id, Model model) {
		Optional<Person> person = personRepository.findById(id);
		if (person.isPresent()) {
			model.addAttribute("person", person);
			return "editPerson";
		} else {
			return "editPerson";
		}
	}

	/**
	 * @author example
	 * Accept POST parameters and create or update a person
	 */
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	@RequestMapping(value={"/person"}, method=RequestMethod.POST)
	public String update(Model model, @Valid @ModelAttribute Person person, BindingResult bindingResult, RedirectAttributes redirectAttrs) {

		if (bindingResult.hasErrors()) {
			return "editPerson";
		} else {
			long id = person.getId(); 
			if (id > 0) {
				// The supplied details do not include all the persons addresses, so we must fetch the person 
				// (and all their address) and only update certain information before saving 
				Optional<Person> op = personRepository.findById(id);
				if (op.isPresent()) {
					Person p = op.get();
					p.setFirstName(person.getFirstName());
					p.setLastName(person.getLastName());
					personRepository.save(p);
					redirectAttrs.addFlashAttribute("message", "Person updated");
				} else {
					redirectAttrs.addFlashAttribute("message", "Failed to update Person");
				}
			} else {
				personRepository.save(person);
				id = person.getId();
				redirectAttrs.addFlashAttribute("message", "Person added");
			}
			return "redirect:/person/" + id;
		}
	}

	/**
	 * @author example
	 * Delete an existing person
	 */
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	@RequestMapping(value={"/person/{id}/delete"}, method=RequestMethod.POST)
	public String delete(@PathVariable("id") long id, RedirectAttributes redirectAttrs) {
		Optional<Person> person = personRepository.findById(id);
		if (person.isPresent()) {
			personRepository.delete(person.get());
			redirectAttrs.addFlashAttribute("message", "Person deleted");
		} else {
			redirectAttrs.addFlashAttribute("message", "Failed to deleted Person");
		}
		
		return "redirect:/person/";
	}

}
