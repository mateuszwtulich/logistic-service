package com.example.logisticserivce.business_logic.validator;

import com.example.logisticserivce.business_logic.exception.ResourceAlreadyExistsException;
import com.example.logisticserivce.business_logic.exception.ResourceNotFoundException;
import com.example.logisticserivce.model.entity.Driver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Optional;

@AllArgsConstructor
@Component
public class DriverValidator extends Validator{
    private static final String DRIVER_NOT_EXIST_KEY = "DriverNotExist";
    private static final String DRIVER_NOT_EXIST_MSG = "Driver ''{0}'' does not exist.";
    private static final String DRIVER_WITH_LOGIN_ALREADY_EXISTS_KEY = "DriverAlreadyExists";
    private static final String DRIVER_WITH_LOGIN_ALREADY_EXISTS_MSG = "Driver ''{0}'' already exists.";
    private final com.example.logisticserivce.repository.DriverRepository driverRepository;

    public void validateDriverIfExists(Optional<Driver> driver, String identificator) throws ResourceNotFoundException {
        if(!driver.isPresent()){
            throw new ResourceNotFoundException(MessageFormat.format
                    (DRIVER_NOT_EXIST_MSG, identificator), DRIVER_NOT_EXIST_KEY, identificator);
        }
    }

    public void validateDriverIfLoginAlreadyExists(String driverLogin) throws ResourceAlreadyExistsException {
        if(isDriverAlreadyExists(driverLogin)) {
            throwDriverAlreadyExistsExceptionFor(driverLogin);
        }
    }

    public void validateDriverIfLoginAlreadyExists(String driverLogin, Long driverId) throws ResourceAlreadyExistsException {
        if(isDriverAlreadyExists(driverLogin, driverId)) {
            throwDriverAlreadyExistsExceptionFor(driverLogin);
        }
    }

    private void throwDriverAlreadyExistsExceptionFor(String driverLogin) throws ResourceAlreadyExistsException{
        throw new ResourceAlreadyExistsException(MessageFormat.format(DRIVER_WITH_LOGIN_ALREADY_EXISTS_MSG, driverLogin),
                DRIVER_WITH_LOGIN_ALREADY_EXISTS_KEY, driverLogin);
    }

    private boolean isDriverAlreadyExists(String driverLogin){
        return driverRepository.existsByLoginIgnoreCase(driverLogin);
    }

    private boolean isDriverAlreadyExists(String driverLogin, Long driverId){
        Optional<Driver> driver = driverRepository.findById(driverId);
        validateDriverIfExists(driver, driverId.toString());
        return (driverRepository.existsByLoginIgnoreCase(driverLogin) && !(driver.get().getLogin().equals(driverLogin)));
    }
}