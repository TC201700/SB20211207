package com.example.demo.address;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface AddressRepository extends PagingAndSortingRepository<Address, Long> {

}
