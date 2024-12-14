package com.samdev.ecommerce.customer;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {
    public Customer toCustomer(@Valid CustomerRequest request) {
        if(request == null){
            return null;
        }
        Customer customer = new Customer();
        Address address = new Address();
        address.setStreet(request.address().getStreet());
        address.setHouseNumber(request.address().getHouseNumber());
        address.setZipCode(request.address().getZipCode());


        customer.setId(request.id());
        customer.setEmail(request.email());
        customer.setFirstname(request.firstname());
        customer.setLastname(request.lastname());
        customer.setAddress(address);
        return customer;
    }

    public CustomerResponse fromCustomerToCustomerResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstname(),
                customer.getLastname(),
                customer.getEmail(),
                customer.getAddress()
        );
    }
}
