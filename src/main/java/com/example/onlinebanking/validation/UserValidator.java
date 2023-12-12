package com.example.onlinebanking.validation;

import com.example.onlinebanking.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        User user = (User) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"userId", "user.userId.empty", "USERID MUST NOT BE EMPTY");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"username", "user.username.empty", "USER NAME MUST NOT BE EMPTY");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password", "user.password.empty", "PASSWORD MUST NOT BE EMPTY");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"email", "user.email.empty", "EMAIL NAME MUST NOT BE EMPTY");

        if(user.getRoles().isEmpty()){
            errors.rejectValue("roles","user.roles.empty", "ROLE MUST NOT BE EMPTY");
        }
    }
}
