package com.example.logisticserivce.business_logic.validator;

import com.example.logisticserivce.business_logic.exception.ResourceAlreadyExistsException;
import com.example.logisticserivce.business_logic.exception.ResourceNotFoundException;
import com.example.logisticserivce.business_logic.service.PrincipalService;
import com.example.logisticserivce.model.dto.LoadingDto;
import com.example.logisticserivce.model.entity.Loading;
import com.example.logisticserivce.model.entity.Principal;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Optional;

@AllArgsConstructor
@Component
public class LoadingValidator extends Validator{
    private static final String LOADING_NOT_EXIST_KEY = "LoadingNotExist";
    private static final String LOADING_NOT_EXIST_MSG = "Loading ''{0}'' does not exist.";
    private static final String LOADING_WITH_ADDRESS_ALREADY_EXISTS_KEY = "LoadingAlreadyExists";
    private static final String LOADING_WITH_ADDRESS_ALREADY_EXISTS_MSG = "Loading with address ''{0}'' already exists.";
    private final com.example.logisticserivce.repository.LoadingRepository loadingRepository;
    private final PrincipalService principalService;

    public void validateLoadingIfExists(Optional<Loading> loading, String identificator) throws ResourceNotFoundException {
        if(!loading.isPresent()){
            throw new ResourceNotFoundException(MessageFormat.format
                    (LOADING_NOT_EXIST_MSG, identificator), LOADING_NOT_EXIST_KEY, identificator);
        }
    }

    public void validateLoadingIfAddressAlreadyExists(LoadingDto loading) throws ResourceAlreadyExistsException {
        if(isLoadingAddressAlreadyExists(loading.getAddress(), principalService.getPrincipal(loading.getPrincipalId()))) {
            throwLoadingAddressAlreadyExistsExceptionFor(loading.getAddress());
        }
    }

    public void validateLoadingIfAddressAlreadyExists(LoadingDto loading, Long loadingId) throws ResourceAlreadyExistsException {
        if(isLoadingAddressAlreadyExists(loading.getAddress(), principalService.getPrincipal(loading.getPrincipalId()), loadingId)) {
            throwLoadingAddressAlreadyExistsExceptionFor(loading.getAddress());
        }
    }

    private void throwLoadingAddressAlreadyExistsExceptionFor(String address) throws ResourceAlreadyExistsException{
        throw new ResourceAlreadyExistsException(MessageFormat.format(LOADING_WITH_ADDRESS_ALREADY_EXISTS_MSG, address),
                LOADING_WITH_ADDRESS_ALREADY_EXISTS_KEY, address);
    }

    private boolean isLoadingAddressAlreadyExists(String address, Principal principal){
        return loadingRepository.existsByAddressIgnoreCaseAndPrincipal(address, principal);
    }

    private boolean isLoadingAddressAlreadyExists(String address, Principal principal, Long loadingId){
        Optional<Loading> loading = loadingRepository.findById(loadingId);
        validateLoadingIfExists(loading, loadingId.toString());
        return (loadingRepository.existsByAddressIgnoreCaseAndPrincipal(address, principal) && !(loading.get().getAddress().equals(address)));
    }
}