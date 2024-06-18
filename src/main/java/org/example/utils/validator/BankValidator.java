package org.example.utils.validator;

import jakarta.validation.ConstraintViolation;
import org.example.entity.dto.BankDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

@Service
public class BankValidator implements Validator {

    @Autowired
    private jakarta.validation.Validator validator;

    @Override
    public boolean supports(Class<?> clazz) {
        return BankDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Set<ConstraintViolation<Object>> validates = validator.validate(target);

        for (ConstraintViolation<Object> constraintViolation : validates) {
            String propertyPath = constraintViolation.getPropertyPath().toString();
            String message = constraintViolation.getMessage();
            errors.rejectValue(propertyPath, "", message);
        }

    }
}