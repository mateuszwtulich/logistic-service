package com.example.logisticserivce.business_logic.validator;

import com.example.logisticserivce.business_logic.exception.ResourceAlreadyExistsException;
import com.example.logisticserivce.business_logic.exception.ResourceNotFoundException;
import com.example.logisticserivce.model.entity.Unloading;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Optional;

@AllArgsConstructor
@Component
public class UnloadingValidator extends Validator{
    private static final String UNLOADING_NOT_EXIST_KEY = "UnloadingNotExist";
    private static final String UNLOADING_NOT_EXIST_MSG = "Unloading ''{0}'' does not exist.";
    private static final String UNLOADING_WITH_ADDRESS_ALREADY_EXISTS_KEY = "UnloadingAlreadyExists";
    private static final String UNLOADING_WITH_ADDRESS_ALREADY_EXISTS_MSG = "Unloading with address ''{0}'' already exists.";
    private final com.example.logisticserivce.repository.UnloadingRepository unloadingRepository;

    public void validateUnloadingIfExists(Optional<Unloading> unloading, String identificator) throws ResourceNotFoundException {
        if(!unloading.isPresent()){
            throw new ResourceNotFoundException(MessageFormat.format
                    (UNLOADING_NOT_EXIST_MSG, identificator), UNLOADING_NOT_EXIST_KEY, identificator);
        }
    }

    public void validateUnloadingIfAddressAlreadyExists(String address) throws ResourceAlreadyExistsException {
        if(isUnloadingAddressAlreadyExists(address)) {
            throwUnloadingAddressAlreadyExistsExceptionFor(address);
        }
    }

    public void validateUnloadingIfAddressAlreadyExists(String address, Long unloadingId) throws ResourceAlreadyExistsException {
        if(isUnloadingAddressAlreadyExists(address, unloadingId)) {
            throwUnloadingAddressAlreadyExistsExceptionFor(address);
        }
    }

    private void throwUnloadingAddressAlreadyExistsExceptionFor(String address) throws ResourceAlreadyExistsException{
        throw new ResourceAlreadyExistsException(MessageFormat.format(UNLOADING_WITH_ADDRESS_ALREADY_EXISTS_MSG, address),
                UNLOADING_WITH_ADDRESS_ALREADY_EXISTS_KEY, address);
    }

    private boolean isUnloadingAddressAlreadyExists(String address){
        return unloadingRepository.existsByAddressIgnoreCase(address);
    }

    private boolean isUnloadingAddressAlreadyExists(String address, Long unloadingId){
        Optional<Unloading> unloading = unloadingRepository.findById(unloadingId);
        validateUnloadingIfExists(unloading, unloadingId.toString());
        return (unloadingRepository.existsByAddressIgnoreCase(address) && !(unloading.get().getAddress().equals(address)));
    }
}