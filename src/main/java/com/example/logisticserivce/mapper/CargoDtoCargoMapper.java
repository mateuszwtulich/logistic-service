package com.example.logisticserivce.mapper;

import com.example.logisticserivce.model.dto.CargoDto;
import com.example.logisticserivce.model.entity.Cargo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CargoDtoCargoMapper {
    Cargo cargoDtoToCargo(CargoDto cargoDto);
}
