package com.example.onlinebanking.service;

import com.example.onlinebanking.domain.Customer;

import java.util.List;

public interface CustomerService {

    public Customer saveCustomer(Customer branch);

    public Customer findById(Long branchId);

    public List<Customer> findAll();

    public void deleteById(Long branchId);

    public Customer updateCustomer(Long branchId);
}
