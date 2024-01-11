package com.example.onlinebanking.validation;

import com.example.onlinebanking.domain.Role;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RoleValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return Role.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Role role = (Role) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"roleId","role.roleId.empty","Role Id must be present");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"name","role.name.empty","Role Name must be present");

    }
}
