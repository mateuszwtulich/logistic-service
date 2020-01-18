package com.example.logisticserivce.mapper;

import com.example.logisticserivce.model.dto.JobDto;
import com.example.logisticserivce.model.entity.Job;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobDtoJobMapper {
    Job jobDtoToJob(JobDto jobDto);
}
