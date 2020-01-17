package com.example.logisticserivce.business_logic.service;

import com.example.logisticserivce.business_logic.exception.ResourceAlreadyExistsException;
import com.example.logisticserivce.business_logic.validator.CargoValidator;
import com.example.logisticserivce.business_logic.validator.Validator;
import com.example.logisticserivce.mapper.CargoDtoCargoMappper;
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
    private final CargoDtoCargoMappper cargoMapper;
    private final CargoValidator validator;

    public List<Cargo> getCargoList() {
        return cargoRepository.findAll();
    }

    public Cargo getCargo(Long id){
        Optional<Cargo> cargo = cargoRepository.findById(id);
        try{
        if (!cargo.isPresent())
            throw new Exception();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return cargo.get();
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

    public void trimString(CargoDto cargoDto){
        final Optional<String> type = Optional.of(cargoDto.getType());
        if(validator.isStringFieldValid(type)){
            cargoDto.setType(type.get().trim());
        }
    }
}