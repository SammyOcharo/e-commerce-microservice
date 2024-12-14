package com.samdev.ecommerce.customer;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Customer {

    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private Address address;

    public Customer() {
    }

    public Customer(String firstname, String id, String lastname, String email, Address address) {
        this.firstname = firstname;
        this.id = id;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
