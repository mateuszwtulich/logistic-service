package com.example.logisticserivce.mapper;

import com.example.logisticserivce.model.dto.UnloadingDto;
import com.example.logisticserivce.model.entity.Unloading;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UnloadingDtoUnloadingMapper {
    Unloading unloadingDtoToUnloading(UnloadingDto loadingDto);
}