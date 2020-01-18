package com.example.logisticserivce.mapper;

import com.example.logisticserivce.model.dto.LoadingDto;
import com.example.logisticserivce.model.entity.Loading;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoadingDtoLoadingMapper {
    Loading loadingDtoToLoading(LoadingDto loadingDto);
}
