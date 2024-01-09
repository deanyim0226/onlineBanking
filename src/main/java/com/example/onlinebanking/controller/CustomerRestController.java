package com.example.onlinebanking.controller;

import com.example.onlinebanking.domain.Customer;
import com.example.onlinebanking.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("r")
public class CustomerRestController {

    @Autowired
    CustomerService customerService;

    @GetMapping(value = "/getCustomers")
    public ResponseEntity<List<Customer>> getCustomers(){

        List<Customer> retrievedCustomers = customerService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(retrievedCustomers);
    }

    @PostMapping(value = "/saveCustomer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> saveCustomer(@Valid @RequestBody Customer customer, BindingResult br){

        Customer retrievedCustomer = customerService.findById(customer.getCustomerId());
        HttpHeaders headers = new HttpHeaders();
        if(retrievedCustomer != null || br.hasErrors()){

            if(br.hasErrors()){
                //error while entering customer info
                return null;
            }

            //duplicate customer
            return null;
        }

        Customer newCustomer = customerService.saveCustomer(customer);
        headers.add("saved Customer", newCustomer.getCustomerName());
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(newCustomer);
    }

    @PutMapping(value = "updateCustomer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer customer, BindingResult br){

        Customer retrievedCustomer = customerService.findById(customer.getCustomerId());
        HttpHeaders headers = new HttpHeaders();

        if(retrievedCustomer == null || br.hasErrors()){
            if (br.hasErrors()){
                //error while entering customer info
                return null;
            }

            //customer does not exist

            return null;
        }

        retrievedCustomer.setCustomerAddress(customer.getCustomerAddress());
        retrievedCustomer.setCustomerDOB(customer.getCustomerDOB());
        retrievedCustomer.setCustomerName(customer.getCustomerName());
        retrievedCustomer.setCustomerMobileNo(customer.getCustomerMobileNo());
        retrievedCustomer.setCustomerRealId(customer.getCustomerRealId());
        retrievedCustomer.setGender(customer.getGender());

        Customer updatedCustomer = customerService.saveCustomer(retrievedCustomer);
        headers.add("updated Customer", updatedCustomer.getCustomerName());

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(updatedCustomer);
    }

    @DeleteMapping(value = "deleteCustomer/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long customerId){
        Customer retrievedCustomer = customerService.findById(customerId);

        if(retrievedCustomer == null){
            //customer does not exist
        }

        customerService.deleteById(customerId);

        return ResponseEntity.status(HttpStatus.OK).body(retrievedCustomer);
    }

}
