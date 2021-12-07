package com.example.demo.address;

import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.person.Person;
import com.example.demo.person.PersonRepository;


@Controller
@SessionScope // for flash messages
public class AddressController {


		@Autowired 
		PersonRepository personRepository;
		
		@Autowired 
		AddressRepository addressRepository;

		/**
		 * @author example
		 * Generate form to add a new address
		 */
		@Transactional(readOnly = true)
		@RequestMapping(value={"/person/{id}/address/new"}, method=RequestMethod.GET)
		public String create(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttrs) {
			Optional<Person> person = personRepository.findById(id);
			if (person.isPresent()) {
				Address address = new Address();
				address.setPerson(person.get());
				model.addAttribute("verb", "Add");
				model.addAttribute("address", address);
				return "editAddress";
			} else {
				redirectAttrs.addFlashAttribute("message", "Person Not Found");
				return "redirect:/person";
			}
		}

		/**
		 * @author example
		 * Generate form to edit an existing address
		 */
		@Transactional(readOnly = true)
		@RequestMapping(value={"/address/{id}/edit"}, method=RequestMethod.GET)
		public String edit(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttrs) {
			Optional<Address> address = addressRepository.findById(id);
			if (address.isPresent()) {
				model.addAttribute("address", address.get());
				return "editAddress";
			} else {
				redirectAttrs.addFlashAttribute("message", "Not Found");
				return "redirect:/person";
			}
		}

		/**
		 * @author example
		 * Accept POST parameters and create or update an address
		 */
		@Transactional(readOnly = false, rollbackFor = Throwable.class)
		@RequestMapping(value={"/address"}, method=RequestMethod.POST)
		public String update(Model model, @Valid Address address, BindingResult bindingResult, RedirectAttributes redirectAttrs) {

			if (bindingResult.hasErrors()) {
				return "editAddress";
			} else {
				addressRepository.save(address);
				redirectAttrs.addFlashAttribute("message", "Address updated");
				return "redirect:/person/" + address.getPerson().getId();
			}
		}

		/**
		 * @author example
		 * Delete an existing address
		 */
		@Transactional(readOnly = false, rollbackFor = Throwable.class)
		@RequestMapping(value={"/address/{id}/delete"}, method=RequestMethod.POST)
		public String delete(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttrs) {
			Optional<Address> address = addressRepository.findById(id);
			if (address.isPresent()) {
				long personId = address.get().getPerson().getId();
				Person p = personRepository.findById(personId).get();
				p.getAddresses().removeIf(item -> item.getId() == id);
				personRepository.save(p);
				redirectAttrs.addFlashAttribute("message", "Address deleted.");
				return "redirect:/person/" + address.get().getPerson().getId();
			} else {
				redirectAttrs.addFlashAttribute("message", "Not Found");
				return "redirect:/person";
			}
		}


	}
