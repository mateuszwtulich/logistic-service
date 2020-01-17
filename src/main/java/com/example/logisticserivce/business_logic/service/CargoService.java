package com.example.logisticserivce.business_logic.service;

import com.example.logisticserivce.business_logic.validator.Validator;
//import com.example.logisticserivce.mapper.CargoDtoCargoMapper;
import com.example.logisticserivce.model.dto.CargoDto;
import com.example.logisticserivce.model.entity.Cargo;
import com.example.logisticserivce.repository.CargoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class CargoService {

    private final CargoRepository cargoRepository;
//    private final CargoDtoCargoMapper cargoMapper;
    private final Validator validator;

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
        Optional<String> type = Optional.of(cargoDto.getType());
        if(validator.isStringFieldValid(type)){
            cargoDto.setType(type.get().trim());
        }
        return new Cargo().setType(cargoDto.getType());
//        return cargoMapper.cargoDtoToCargo(cargoDto);
    }

    public void deleteCargo(Long id){
        cargoRepository.deleteById(id);
    }
}