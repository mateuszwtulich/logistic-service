package com.example.logisticserivce.mapper;

import com.example.logisticserivce.model.entity.Job;
import com.example.logisticserivce.model.entity.JobArchive;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobArchiveJobMapper {
    JobArchive JobToJobArchive(Job job);
}
