package com.example.logisticserivce.mapper;

import com.example.logisticserivce.model.dto.LorryDto;
import com.example.logisticserivce.model.entity.Lorry;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LorryDtoLorryMapper {
    Lorry lorryDtoToLorry(LorryDto lorryDto);
}
