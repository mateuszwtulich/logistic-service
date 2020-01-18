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
    private final com.example.logisticserivce.repository.CargoRepository cargoRepository;

    public void validateCargoIfExists(Optional<Cargo> cargo, String identificator) throws ResourceNotFoundException {
        if(!cargo.isPresent()){
            throw new ResourceNotFoundException(MessageFormat.format
                    (CARGO_NOT_EXIST_MSG, identificator), CARGO_NOT_EXIST_KEY, identificator);
        }
    }

    public void validateCargoIfTypeAlreadyExists(String cargoType) throws ResourceAlreadyExistsException {
        if(isCargoAlreadyExists(cargoType)) {
            throwCargoAlreadyExistsExceptionFor(cargoType);
        }
    }

    public void validateCargoIfTypeAlreadyExists(String cargoType, Long cargoId) throws ResourceAlreadyExistsException{
        if(isCargoAlreadyExists(cargoType, cargoId)) {
            throwCargoAlreadyExistsExceptionFor(cargoType);
        }
    }

    private void throwCargoAlreadyExistsExceptionFor(String cargoType) throws ResourceAlreadyExistsException{
        throw new ResourceAlreadyExistsException(MessageFormat.format(CARGO_WITH_TYPE_ALREADY_EXISTS_MSG, cargoType),
                CARGO_WITH_TYPE_ALREADY_EXISTS_KEY, cargoType);
    }

    private boolean isCargoAlreadyExists(String cargoType){
        return cargoRepository.existsByTypeIgnoreCase(cargoType);
    }

    private boolean isCargoAlreadyExists(String cargoType, Long cargoId){
        Optional<Cargo> cargo = cargoRepository.findById(cargoId);
        validateCargoIfExists(cargo, cargoId.toString());
        return (cargoRepository.existsByTypeIgnoreCase(cargoType) && !(cargo.get().getType().equals(cargoType)));
    }
}
