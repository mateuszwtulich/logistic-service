package com.example.logisticserivce.mapper;

import com.example.logisticserivce.model.dto.DriverDto;
import com.example.logisticserivce.model.entity.Driver;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DriverDtoDriverMapper {
    Driver driverDtoToDriver(DriverDto driverDto);
}
