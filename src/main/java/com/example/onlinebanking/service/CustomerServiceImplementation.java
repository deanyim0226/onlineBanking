package com.example.onlinebanking.service;

import com.example.onlinebanking.dao.CustomerRepository;
import com.example.onlinebanking.domain.Customer;
import com.example.onlinebanking.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImplementation implements CustomerService{

    @Autowired
    CustomerRepository customerRepository;

    public List<User> findAvailableUsers(List<User> userList){

        List<Customer> customerList = customerRepository.findAll();
        List<User> availableUsers = new ArrayList<>();
        return null;
    }

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
    public Customer findByName(String loggedInUser) {
        List<Customer> customerList = customerRepository.findAll();

        for(Customer customer : customerList){

            if(loggedInUser.equals(customer.getCustomerName())){
                return customer;
            }
        }
        return null;
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
