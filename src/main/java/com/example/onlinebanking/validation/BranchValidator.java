package com.example.onlinebanking.validation;

import com.example.onlinebanking.domain.Address;
import com.example.onlinebanking.domain.Branch;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class BranchValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Branch.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        //For future use as we might need them
        Branch branch = (Branch) target;
        Address address = branch.getAddress();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"branchId","branch.branchId.empty","BRANCHID MUST BE PRESENT");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"branchName","branch.branchName.empty","BRANCHNAME MUST BE PRESENT");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"address.addressLine1","branch.addressLine1.empty","ADDRESSLINE1 MUST BE PRESENT");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"address.addressLine2","branch.addressLine2.empty","ADDRESSLINE2 MUST BE PRESENT");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"address.city","branch.city.empty","CITY MUST BE PRESENT");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"address.state","branch.state.empty","STATE MUST BE PRESENT");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"address.country","branch.country.empty","COUNTRY MUST BE PRESENT");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"address.zipcode","branch.zipcode.empty","ZIPCODE MUST BE PRESENT");






    }
}
