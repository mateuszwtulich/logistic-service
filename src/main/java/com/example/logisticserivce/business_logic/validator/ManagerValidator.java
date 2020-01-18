package com.example.logisticserivce.business_logic.validator;

import com.example.logisticserivce.business_logic.exception.ResourceAlreadyExistsException;
import com.example.logisticserivce.business_logic.exception.ResourceNotFoundException;
import com.example.logisticserivce.model.entity.Manager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Optional;

@AllArgsConstructor
@Component
public class ManagerValidator extends Validator{
    private static final String MANAGER_NOT_EXIST_KEY = "ManagerNotExist";
    private static final String MANAGER_NOT_EXIST_MSG = "Manager ''{0}'' does not exist.";
    private static final String MANAGER_WITH_LOGIN_ALREADY_EXISTS_KEY = "ManagerAlreadyExists";
    private static final String MANAGER_WITH_LOGIN_ALREADY_EXISTS_MSG = "Manager ''{0}'' already exists.";
    private final com.example.logisticserivce.repository.ManagerRepository managerRepository;

    public void validateManagerIfExists(Optional<Manager> manager, String identificator) throws ResourceNotFoundException {
        if(!manager.isPresent()){
            throw new ResourceNotFoundException(MessageFormat.format
                    (MANAGER_NOT_EXIST_MSG, identificator), MANAGER_NOT_EXIST_KEY, identificator);
        }
    }

    public void validateManagerIfLoginAlreadyExists(String managerLogin) throws ResourceAlreadyExistsException {
        if(isManagerAlreadyExists(managerLogin)) {
            throwManagerAlreadyExistsExceptionFor(managerLogin);
        }
    }

    public void validateManagerIfLoginAlreadyExists(String managerLogin, Long managerId) throws ResourceAlreadyExistsException {
        if(isManagerAlreadyExists(managerLogin, managerId)) {
            throwManagerAlreadyExistsExceptionFor(managerLogin);
        }
    }

    private void throwManagerAlreadyExistsExceptionFor(String managerLogin) throws ResourceAlreadyExistsException{
        throw new ResourceAlreadyExistsException(MessageFormat.format(MANAGER_WITH_LOGIN_ALREADY_EXISTS_MSG, managerLogin),
                MANAGER_WITH_LOGIN_ALREADY_EXISTS_KEY, managerLogin);
    }

    private boolean isManagerAlreadyExists(String managerLogin){
        return managerRepository.existsByLoginIgnoreCase(managerLogin);
    }

    private boolean isManagerAlreadyExists(String managerLogin, Long managerId){
        Optional<Manager> manager = managerRepository.findById(managerId);
        validateManagerIfExists(manager, managerId.toString());
        return (managerRepository.existsByLoginIgnoreCase(managerLogin) && !(manager.get().getLogin().equals(managerLogin)));
    }
}