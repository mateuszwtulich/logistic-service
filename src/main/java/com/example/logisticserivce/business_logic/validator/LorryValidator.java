package com.example.logisticserivce.business_logic.validator;

import com.example.logisticserivce.business_logic.exception.ResourceAlreadyExistsException;
import com.example.logisticserivce.business_logic.exception.ResourceNotFoundException;
import com.example.logisticserivce.model.entity.Lorry;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Optional;

@AllArgsConstructor
@Component
public class LorryValidator extends Validator{
    private static final String LORRY_NOT_EXIST_KEY = "LorryNotExist";
    private static final String LORRY_NOT_EXIST_MSG = "Lorry ''{0}'' does not exist.";
    private static final String LORRY_WITH_LICENCE_NUMBER_ALREADY_EXISTS_KEY = "LorryAlreadyExists";
    private static final String LORRY_WITH_LICENCE_NUMBER_ALREADY_EXISTS_MSG = "Lorry ''{0}'' already exists.";
    private final com.example.logisticserivce.repository.LorryRepository lorryRepository;

    public void validateLorryIfExists(Optional<Lorry> lorry, String identificator) throws ResourceNotFoundException {
        if(!lorry.isPresent()){
            throw new ResourceNotFoundException(MessageFormat.format
                    (LORRY_NOT_EXIST_MSG, identificator), LORRY_NOT_EXIST_KEY, identificator);
        }
    }

    public void validateLorryIfLicenceNumberAlreadyExists(String lorryLicenceNumber) throws ResourceAlreadyExistsException {
        if(isLorryAlreadyExists(lorryLicenceNumber)) {
            throwLorryAlreadyExistsExceptionFor(lorryLicenceNumber);
        }
    }

    public void validateLorryIfLicenceNumberAlreadyExists(String lorryLicenceNumber, Long lorryId) throws ResourceAlreadyExistsException {
        if(isLorryAlreadyExists(lorryLicenceNumber, lorryId)) {
            throwLorryAlreadyExistsExceptionFor(lorryLicenceNumber);
        }
    }

    private void throwLorryAlreadyExistsExceptionFor(String lorryLicenceNumber) throws ResourceAlreadyExistsException{
        throw new ResourceAlreadyExistsException(MessageFormat.format(LORRY_WITH_LICENCE_NUMBER_ALREADY_EXISTS_MSG, lorryLicenceNumber),
                LORRY_WITH_LICENCE_NUMBER_ALREADY_EXISTS_KEY, lorryLicenceNumber);
    }

    private boolean isLorryAlreadyExists(String lorryLicenceNumber){
        return lorryRepository.existsByLicenceNumberIgnoreCase(lorryLicenceNumber);
    }

    private boolean isLorryAlreadyExists(String lorryLicenceNumber, Long lorryId){
        Optional<Lorry> lorry = lorryRepository.findById(lorryId);
        validateLorryIfExists(lorry, lorryId.toString());
        return (lorryRepository.existsByLicenceNumberIgnoreCase(lorryLicenceNumber) && !(lorry.get().getLicenceNumber().equals(lorryLicenceNumber)));
    }
}
