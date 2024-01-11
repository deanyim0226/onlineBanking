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
import org.springframework.validation.FieldError;
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
        StringBuilder sb = new StringBuilder("");

        if(retrievedCustomer != null || br.hasErrors()){

            if(br.hasErrors()){
                //error while entering account info from user
                List<FieldError> fieldErrors = br.getFieldErrors();
                for(FieldError fieldError: fieldErrors){
                    sb = sb.append("\""+fieldError.getField() +"\":"+fieldError.getDefaultMessage()+"\n");
                }
                System.out.println("sb: " + sb );
                headers.add("errors",sb.toString());

                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).headers(headers).body(retrievedCustomer);
            }else{
                //duplicate Customer
                sb.append("Customer already exist");
                headers.add("errors",sb.toString());
                return ResponseEntity.status(HttpStatus.CONFLICT).headers(headers).body(retrievedCustomer);
            }
        }

        Customer newCustomer = customerService.saveCustomer(customer);
        headers.add("saved Customer", newCustomer.getCustomerName());
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(newCustomer);
    }

    @PutMapping(value = "updateCustomer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer customer, BindingResult br){

        Customer retrievedCustomer = customerService.findById(customer.getCustomerId());
        HttpHeaders headers = new HttpHeaders();
        StringBuilder sb = new StringBuilder("");

        if(retrievedCustomer == null || br.hasErrors()){
            if(br.hasErrors()){
                //error while entering account info from user
                List<FieldError> fieldErrors = br.getFieldErrors();
                for(FieldError fieldError: fieldErrors){
                    sb = sb.append("\""+fieldError.getField() +"\":"+fieldError.getDefaultMessage()+"\n");
                }
                System.out.println("sb: " + sb );
                headers.add("errors",sb.toString());

                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).headers(headers).body(retrievedCustomer);
            }else{
                //duplicate Customer
                sb.append("Customer does not exist");
                headers.add("errors",sb.toString());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(retrievedCustomer);
            }
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
        HttpHeaders headers = new HttpHeaders();
        StringBuilder sb = new StringBuilder("");

        if(retrievedCustomer == null){
            //customer does not exist
            sb.append("Customer does not exist");
            headers.add("errors",sb.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(retrievedCustomer);
        }

        customerService.deleteById(customerId);
        headers.add("deleted Customer", retrievedCustomer.getCustomerName());

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(retrievedCustomer);
    }

}
