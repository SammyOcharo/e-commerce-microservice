package com.samdev.ecommerce.customer;

import ch.qos.logback.core.util.StringUtil;
import com.samdev.ecommerce.Exception.CustomerNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public CustomerService(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public String createCustomer(@Valid CustomerRequest request) {
        var customer = repository.save(mapper.toCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(@Valid CustomerRequest request) {
        var customer = repository.findById(request.id()).orElseThrow(()-> new CustomerNotFoundException(String.format("Cannot update customer:: No customer found with ID:: %s", request.id())));

        mergeCustomer(customer, request);
        repository.save(customer);
    }

    private void mergeCustomer(Customer customer, @Valid CustomerRequest request) {
        if(StringUtils.isNotBlank(request.firstname())){
            customer.setFirstname(request.firstname());
        }
        if(StringUtils.isNotBlank(request.lastname())){
            customer.setLastname(request.lastname());
        }
        if(StringUtils.isNotBlank(request.email())){
            customer.setEmail(request.email());
        }
        if(request.address() != null){
            customer.setAddress(request.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {

        return repository.findAll().stream()
                .map(mapper::fromCustomerToCustomerResponse)
                .collect(Collectors.toList());
    }

    public Boolean existsByID(String customerId) {

        Optional<Customer> customer = repository.findById(customerId);

        customer.ifPresent(value -> log.info("This is the customer First Name, {}" + value.getFirstname()));
        customer.ifPresent(value -> log.info("This is the customer Last Name, {}" + value.getLastname()));

        return repository.findById(customerId).isPresent();
    }

    public CustomerResponse findByID(String customerId) {
        Customer customer = repository.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("Customer not found"));

        return mapper.fromCustomerToCustomerResponse(customer);
    }

    public void deleteCustomer(String customerId) {
        repository.deleteById(customerId);
    }
}
