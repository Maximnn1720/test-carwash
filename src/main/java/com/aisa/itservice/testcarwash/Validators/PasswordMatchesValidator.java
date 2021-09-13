package com.aisa.itservice.testcarwash.Validators;

import com.aisa.itservice.testcarwash.Annotations.PasswordMatches;
import com.aisa.itservice.testcarwash.Entites.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    private Pattern pattern;
    private Matcher matcher;
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        UserDto user = (UserDto) obj;
        return user.getPassword().equals(user.getCopyPassword());
    }
}
