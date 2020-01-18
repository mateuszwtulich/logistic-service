package com.example.logisticserivce.mapper;

import com.example.logisticserivce.model.dto.ManagerDto;
import com.example.logisticserivce.model.entity.Manager;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ManagerDtoManagerMapper {
    Manager managerDtoToManager(ManagerDto managerDto);
}
