	package com.example.demo.address;


	import javax.persistence.*;
	import javax.validation.constraints.NotNull;
	import javax.validation.constraints.Size;

	import com.example.demo.person.Person;

	@Entity
	@Table(schema="public", name="address")
	public class Address {

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private long id;

		@ManyToOne(fetch = FetchType.EAGER)
		@NotNull
		private Person person;

		@Column(length=10, nullable = false)
		@Size(min=2, max=10)
		private String street;

		@Column(length=10, nullable = false)
		@Size(min=2, max=10)
		private String city;

		@Column(length=10, nullable = false)
		@Size(min=2, max=10)
		private String state;

		@Column(length=10, nullable = false)
		@Size(min=2, max=10)
		private String postalCode;
		
		public Address() {}
		
		public Address(Person person, String street, String city, String state, String postalCode) {
			super();
			this.person = person;
			this.street = street;
			this.city = city;
			this.state = state;
			this.postalCode = postalCode;
		}
		
		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public Person getPerson() {
			return person;
		}

		public void setPerson(Person person) {
			this.person = person;
		}

		/**
		 * @return the street
		 */
		public String getStreet() {
			return street;
		}
		/**
		 * @param street the street to set
		 */
		public void setStreet(String street) {
			this.street = street;
		}
		/**
		 * @return the city
		 */
		public String getCity() {
			return city;
		}
		/**
		 * @param city the city to set
		 */
		public void setCity(String city) {
			this.city = city;
		}
		/**
		 * @return the state
		 */
		public String getState() {
			return state;
		}
		/**
		 * @param state the state to set
		 */
		public void setState(String state) {
			this.state = state;
		}
		/**
		 * @return the postalCode
		 */
		public String getPostalCode() {
			return postalCode;
		}
		/**
		 * @param postalCode the postalCode to set
		 */
		public void setPostalCode(String postalCode) {
			this.postalCode = postalCode;
		}



	}
