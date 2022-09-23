package com.example.ChatAppVaadin1.validation.password;

import com.example.ChatAppVaadin1.model.user.AppUser;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        AppUser user = (AppUser) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}