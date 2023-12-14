package com.example.onlinebanking.service;

import com.example.onlinebanking.domain.Customer;

import java.util.List;

public interface CustomerService {

    public Customer saveCustomer(Customer customer);

    public Customer findById(Long customerId);

    public List<Customer> findAll();

    public void deleteById(Long customerId);

    public Customer updateCustomer(Long customerId);
}
