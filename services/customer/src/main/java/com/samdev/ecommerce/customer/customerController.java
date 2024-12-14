package com.samdev.ecommerce.customer;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class customerController {
    private final CustomerService service;

    public customerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> createCustomer (@RequestBody @Valid CustomerRequest request){

        return ResponseEntity.ok(service.createCustomer(request));
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(@RequestBody @Valid CustomerRequest request){
        service.updateCustomer(request);

        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll(){

        return ResponseEntity.ok(service.findAllCustomers());
    }

    @GetMapping("exists/{customer-id}")
    public ResponseEntity<Boolean> existsByID(@PathVariable("customer-id") String customerId){
        return ResponseEntity.ok(service.existsByID(customerId));
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> findByID(@PathVariable("customer-id") String customerId){
        return ResponseEntity.ok(service.findByID(customerId));
    }

    @DeleteMapping("/{customer-id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("customer-id") String customerId){
        service.deleteCustomer(customerId);
        return ResponseEntity.ok().build();
    }


}
