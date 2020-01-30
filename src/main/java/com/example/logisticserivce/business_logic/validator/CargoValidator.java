package com.example.logisticserivce.business_logic.validator;

import com.example.logisticserivce.business_logic.exception.ResourceAlreadyExistsException;
import com.example.logisticserivce.business_logic.exception.ResourceNotFoundException;
import com.example.logisticserivce.business_logic.service.PrincipalService;
import com.example.logisticserivce.model.dto.CargoDto;
import com.example.logisticserivce.model.entity.Cargo;
import com.example.logisticserivce.model.entity.Principal;
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
    private final PrincipalService principalService;

    public void validateCargoIfExists(Optional<Cargo> cargo, String identificator) throws ResourceNotFoundException {
        if(!cargo.isPresent()){
            throw new ResourceNotFoundException(MessageFormat.format
                    (CARGO_NOT_EXIST_MSG, identificator), CARGO_NOT_EXIST_KEY, identificator);
        }
    }

    public void validateCargoIfTypeAlreadyExists(CargoDto cargo) throws ResourceAlreadyExistsException {
        if(isCargoAlreadyExists(cargo.getType(), principalService.getPrincipal(cargo.getPrincipalId()))) {
            throwCargoAlreadyExistsExceptionFor(cargo.getType());
        }
    }

    public void validateCargoIfTypeAlreadyExists(CargoDto cargo, Long cargoId) throws ResourceAlreadyExistsException{
        if(isCargoAlreadyExists(cargo.getType(), principalService.getPrincipal(cargo.getPrincipalId()), cargoId)) {
            throwCargoAlreadyExistsExceptionFor(cargo.getType());
        }
    }

    private void throwCargoAlreadyExistsExceptionFor(String cargoType) throws ResourceAlreadyExistsException{
        throw new ResourceAlreadyExistsException(MessageFormat.format(CARGO_WITH_TYPE_ALREADY_EXISTS_MSG, cargoType),
                CARGO_WITH_TYPE_ALREADY_EXISTS_KEY, cargoType);
    }

    private boolean isCargoAlreadyExists(String cargoType, Principal principal){
        return cargoRepository.existsByTypeIgnoreCaseAndPrincipal(cargoType, principal);
    }

    private boolean isCargoAlreadyExists(String cargoType, Principal principal, Long cargoId){
        Optional<Cargo> cargo = cargoRepository.findById(cargoId);
        validateCargoIfExists(cargo, cargoId.toString());
        return (cargoRepository.existsByTypeIgnoreCaseAndPrincipal(cargoType, principal) && !(cargo.get().getType().equals(cargoType)));
    }
}
