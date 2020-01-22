package com.example.logisticserivce.business_logic.validator;

import com.example.logisticserivce.business_logic.exception.ForbiddenCharException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Component
public abstract class Validator {
    private static final String FORBIDDEN_CHAR_KEY = "forbiddenChar";
    private static final String FORBIDDEN_CHAR_MSG = "Forbidden characters ''{0}'' were used.";
    private static final String REGEX_TO_VALIDATE_FIELD = "[a-zA-Z0-9_\\-`.+, ]+";

    public boolean isStringFieldValid(Optional<String> stringField) throws ForbiddenCharException {
        if (!stringField.isPresent()) {
            return false;
        }
        else {
            try {
                validateStringFieldIfMatchesRegex(stringField.get());
            } catch (Exception ex) {
                //log.error(ex.getMessage(), ex);
                throw ex;
            }
            return true;
        }
    }

    public void validateStringFieldIfMatchesRegex(String stringField) throws ForbiddenCharException{
//        if (!stringField.matches(REGEX_TO_VALIDATE_FIELD)) {
//            throw new ForbiddenCharException(MessageFormat.format(FORBIDDEN_CHAR_MSG, stringField), FORBIDDEN_CHAR_KEY, stringField);
//        }
    }
}
