package com.example.logisticserivce.business_logic.validator;

import com.example.logisticserivce.business_logic.exception.ResourceAlreadyExistsException;
import com.example.logisticserivce.business_logic.exception.ResourceNotFoundException;
import com.example.logisticserivce.model.entity.Cargo;
import com.example.logisticserivce.repository.CargoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Optional;

@AllArgsConstructor
@Component
public class CargoValidator extends Validator{
    private static final String CARGO_NOT_EXIST_KEY = "CargoNotExist";
    private static final String CARGO_NOT_EXIST_MSG = "Cargo ''{0}'' does not exist.";
    private static final String CARGO_WITH_TYPE_ALREADY_EXISTS_KEY = "CargoAlreadyExists";
    private static final String CARGO_WITH_TYPE_ALREADY_EXISTS_MSG = "Cargo ''{0}'' already exists.";
    private final com.example.logisticserivce.repository.CargoRepository CargoRepository;

    public void validateCargoIfExists(Optional<Cargo> Cargo, String identificator) throws ResourceNotFoundException {
        if(!Cargo.isPresent()){
            throw new ResourceNotFoundException(MessageFormat.format
                    (CARGO_NOT_EXIST_MSG, identificator), CARGO_NOT_EXIST_KEY, identificator);
        }
    }

    public void validateCargoIfTypeAlreadyExists(String CargoType) throws ResourceAlreadyExistsException {
        if(isCargoAlreadyExists(CargoType)) {
            throwCargoAlreadyExistsExceptionFor(CargoType);
        }
    }

    public void validateCargoIfTypeAlreadyExists(String CargoType, Long CargoId) throws ResourceAlreadyExistsException{
        if(isCargoAlreadyExists(CargoType, CargoId)) {
            throwCargoAlreadyExistsExceptionFor(CargoType);
        }
    }

    private void throwCargoAlreadyExistsExceptionFor(String CargoType) throws ResourceAlreadyExistsException{
        throw new ResourceAlreadyExistsException(MessageFormat.format(CARGO_WITH_TYPE_ALREADY_EXISTS_MSG, CargoType),
                CARGO_WITH_TYPE_ALREADY_EXISTS_KEY, CargoType);
    }

    private boolean isCargoAlreadyExists(String CargoType){
        return CargoRepository.existsByTypeIgnoreCase(CargoType);
    }

    private boolean isCargoAlreadyExists(String CargoType, Long CargoId){
        Cargo cargo = CargoRepository.findById(CargoId).get();
        return (CargoRepository.existsByTypeIgnoreCase(CargoType) && !(cargo.getType().equals(CargoType)));
    }
}
