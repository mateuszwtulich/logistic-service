package com.example.logisticserivce.business_logic.validator;

import com.example.logisticserivce.business_logic.exception.ResourceAlreadyExistsException;
import com.example.logisticserivce.business_logic.exception.ResourceNotFoundException;
import com.example.logisticserivce.model.entity.Principal;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Optional;

@AllArgsConstructor
@Component
public class PrincipalValidator extends Validator{
    private static final String PRINCIPAL_NOT_EXIST_KEY = "PrincipalNotExist";
    private static final String PRINCIPAL_NOT_EXIST_MSG = "Principal ''{0}'' does not exist.";
    private static final String PRINCIPAL_WITH_NAME_ALREADY_EXISTS_KEY = "PrincipalAlreadyExists";
    private static final String PRINCIPAL_WITH_NAME_ALREADY_EXISTS_MSG = "Principal ''{0}'' already exists.";
    private static final String PRINCIPAL_WITH_ADDRESS_ALREADY_EXISTS_KEY = "PrincipalAlreadyExists";
    private static final String PRINCIPAL_WITH_ADDRESS_ALREADY_EXISTS_MSG = "Principal with address ''{0}'' already exists.";
    private final com.example.logisticserivce.repository.PrincipalRepository principalRepository;

    public void validatePrincipalIfExists(Optional<Principal> principal, String identificator) throws ResourceNotFoundException {
        if(!principal.isPresent()){
            throw new ResourceNotFoundException(MessageFormat.format
                    (PRINCIPAL_NOT_EXIST_MSG, identificator), PRINCIPAL_NOT_EXIST_KEY, identificator);
        }
    }

    public void validatePrincipalIfNameAlreadyExists(String principalName) throws ResourceAlreadyExistsException {
        if(isPrincipalAlreadyExists(principalName)) {
            throwPrincipalAlreadyExistsExceptionFor(principalName);
        }
    }

    public void validatePrincipalIfNameAlreadyExists(String principalName, Long principalId) throws ResourceAlreadyExistsException {
        if(isPrincipalAlreadyExists(principalName, principalId)) {
            throwPrincipalAlreadyExistsExceptionFor(principalName);
        }
    }

    public void validatePrincipalIfAddressAlreadyExists(String address) throws ResourceAlreadyExistsException {
        if(isPrincipalAddressAlreadyExists(address)) {
            throwPrincipalAddressAlreadyExistsExceptionFor(address);
        }
    }

    public void validatePrincipalIfAddressAlreadyExists(String address, Long principalId) throws ResourceAlreadyExistsException {
        if(isPrincipalAddressAlreadyExists(address, principalId)) {
            throwPrincipalAddressAlreadyExistsExceptionFor(address);
        }
    }

    private void throwPrincipalAddressAlreadyExistsExceptionFor(String address) throws ResourceAlreadyExistsException{
        throw new ResourceAlreadyExistsException(MessageFormat.format(PRINCIPAL_WITH_ADDRESS_ALREADY_EXISTS_MSG, address),
                PRINCIPAL_WITH_ADDRESS_ALREADY_EXISTS_KEY, address);
    }

    private void throwPrincipalAlreadyExistsExceptionFor(String principalName) throws ResourceAlreadyExistsException{
        throw new ResourceAlreadyExistsException(MessageFormat.format(PRINCIPAL_WITH_NAME_ALREADY_EXISTS_MSG, principalName),
                PRINCIPAL_WITH_NAME_ALREADY_EXISTS_KEY, principalName);
    }

    private boolean isPrincipalAddressAlreadyExists(String address){
        return principalRepository.existsByAddressIgnoreCase(address);
    }

    private boolean isPrincipalAddressAlreadyExists(String address, Long principalId){
        Optional<Principal> principal = principalRepository.findById(principalId);
        validatePrincipalIfExists(principal, principalId.toString());
        return (principalRepository.existsByAddressIgnoreCase(address) && !(principal.get().getName().equals(address)));
    }

    private boolean isPrincipalAlreadyExists(String principalName){
        return principalRepository.existsByNameIgnoreCase(principalName);
    }

    private boolean isPrincipalAlreadyExists(String principalName, Long principalId){
        Optional<Principal> principal = principalRepository.findById(principalId);
        validatePrincipalIfExists(principal, principalId.toString());
        return (principalRepository.existsByNameIgnoreCase(principalName) && !(principal.get().getAddress().equals(principalName)));
    }
}
