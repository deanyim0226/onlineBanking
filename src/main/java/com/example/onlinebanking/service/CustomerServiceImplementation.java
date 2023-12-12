package com.example.onlinebanking.service;

import com.example.onlinebanking.dao.CustomerRepository;
import com.example.onlinebanking.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImplementation implements CustomerService{

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Customer saveCustomer(Customer branch) {

        return customerRepository.save(branch);
    }

    @Override
    public Customer findById(Long branchId) {
        return customerRepository.findById(branchId).orElse(null);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public void deleteById(Long branchId) {
        customerRepository.deleteById(branchId);
    }

    @Override
    public Customer updateCustomer(Long branchId) {
        return customerRepository.findById(branchId).orElse(null);
    }
}
