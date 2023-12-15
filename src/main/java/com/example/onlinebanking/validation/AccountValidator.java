package com.example.onlinebanking.validation;

import com.example.onlinebanking.domain.*;
import com.example.onlinebanking.service.BranchService;
import com.example.onlinebanking.service.CustomerService;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class AccountValidator implements Validator {

    @Autowired
    BranchService branchService;

    @Autowired
    CustomerService customerService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Account.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Account account = (Account) target;

        Branch branch = account.getAccountBranch();
        Customer customer = account.getAccountCustomer();

        if(branch.getBranchId() == null){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,"accountBranch","account.branchId.empty", "BRANCH ID IS NEEDED");
        }else{

            if(branchService.findById(branch.getBranchId()) == null){
                errors.rejectValue("accountBranch","account.branchId.mismatch","BRANCH ID IS NOT MATCHED TO OUR RECORD");
            }
        }

        if(customer.getCustomerId() == null){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,"accountCustomer","account.customerId.empty", "CUSTOMER ID IS NEEDED");
        }else{

            if(customerService.findById(customer.getCustomerId()) == null){
                errors.rejectValue("accountCustomer","account.customerId.mismatch","CUSTOMER ID IS NOT MATCHED TO OUR RECORD");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"accountId","account.accountId.empty", "ACCOUNT ID MUST NOT BE EMPTY");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"accountType","account.accountType.empty", "ACCOUNT TYPE MUST BE SELECTED");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"accountDateOpened","account.accountDateOpened.empty", "ACCOUNT DATE OPENED MUST BE PRESENT");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"accountHolder","account.accountHolder.empty", "ACCOUNT HOLDER MUST NOT BE EMPTY");

        if(account.getAccountBalance() < 0){
            errors.rejectValue("accountBalance","account.accountBalance.negative","ACCOUNT BALANCE SHOULD BE POSITIVE");
        }

    }
}
