package com.example.logisticserivce.mapper;

import com.example.logisticserivce.model.dto.PrincipalDto;
import com.example.logisticserivce.model.entity.Principal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrincipalDtoPrincipalMapper {
    Principal principalDtoToPrincipal(PrincipalDto principalDto);
}
