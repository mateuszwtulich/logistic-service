package com.example.logisticserivce.business_logic.service;

import com.example.logisticserivce.business_logic.exception.ResourceAlreadyExistsException;
import com.example.logisticserivce.business_logic.exception.ResourceNotFoundException;
import com.example.logisticserivce.business_logic.validator.CargoValidator;
import com.example.logisticserivce.mapper.CargoDtoCargoMapper;
import com.example.logisticserivce.model.dto.CargoDto;
import com.example.logisticserivce.model.entity.Cargo;
import com.example.logisticserivce.repository.CargoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
@Transactional
public class CargoService {

    private final CargoRepository cargoRepository;
    private final CargoDtoCargoMapper cargoMapper;
    private final CargoValidator validator;

    public List<Cargo> getCargoList() {
        return cargoRepository.findAll();
    }

    public Cargo getCargo(Long id){
        return getCargoFromRepository(id);
    }

    public Cargo addCargo(CargoDto cargoDto){
        trimString(cargoDto);
        try {
            validator.validateCargoIfTypeAlreadyExists(cargoDto.getType());
        } catch (ResourceAlreadyExistsException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
        return cargoRepository.save(cargoMapper.cargoDtoToCargo(cargoDto));
    }

    public void deleteCargo(Long id){
        cargoRepository.deleteById(id);
    }

    public Cargo modifyCargo(Long id, CargoDto modifiedCargo) {
        trimString(modifiedCargo);
        try {
            validator.validateCargoIfTypeAlreadyExists(modifiedCargo.getType(), id);
        } catch (ResourceAlreadyExistsException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
        return cargoRepository.save(cargoMapper.cargoDtoToCargo(modifiedCargo).setId(id));
    }

    private Cargo getCargoFromRepository(Long id) {
        final Optional<Cargo> cargo = cargoRepository.findById(id);
        try {
            validator.validateCargoIfExists(cargo, id.toString());
        } catch (ResourceNotFoundException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
        return cargo.get();
    }

    public void trimString(CargoDto cargoDto){
        final Optional<String> type = Optional.of(cargoDto.getType());
        if(validator.isStringFieldValid(type)){
            cargoDto.setType(type.get().trim());
        }
    }
}