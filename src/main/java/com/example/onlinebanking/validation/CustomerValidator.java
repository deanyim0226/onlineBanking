package com.example.onlinebanking.validation;

import com.example.onlinebanking.domain.*;
import com.example.onlinebanking.service.UserService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerValidator implements Validator {

    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Customer.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Customer customer = (Customer) target;
        User user = customer.getUser();

        if(user.getUserId() == null){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,"user.userId","customer.userId.empty", "USER ID THAT YOU MADE EARLIER NEEDED");
        }else{

            if(userService.findById(user.getUserId()) == null){
                errors.rejectValue("user.userId","customer.userId.mismatched", "USER ID IS NOT MATCHED TO OUR RECORD");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"customerId","customer.customerId.empty","CUSTOMER ID MUST BE PRESENT");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"customerName","customer.customerName.empty","CUSTOMER NAME MUST BE PRESENT");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"gender","customer.gender.empty","CUSTOMER GENDER MUST BE PRESENT");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"customerDOB","customer.customerDOB.empty","CUSTOMER DOB MUST BE PRESENT");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"customerMobileNo","customer.customerMobileNo.empty","CUSTOMER MOBILE.NO MUST BE PRESENT");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"customerRealId","customer.customerRealId.empty","CUSTOMER REAL-ID MUST BE PRESENT");


        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"customerAddress.addressLine1","customer.addressLine1.empty","ADDRESSLINE1 MUST BE PRESENT");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"customerAddress.addressLine2","customer.addressLine2.empty","ADDRESSLINE2 MUST BE PRESENT");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"customerAddress.city","customer.city.empty","CITY MUST BE PRESENT");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"customerAddress.state","customer.state.empty","STATE MUST BE PRESENT");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"customerAddress.country","customer.country.empty","COUNTRY MUST BE PRESENT");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"customerAddress.zipcode","customer.zipcode.empty","ZIPCODE MUST BE PRESENT");



    }
}
