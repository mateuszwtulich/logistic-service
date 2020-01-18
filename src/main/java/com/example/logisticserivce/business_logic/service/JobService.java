package com.example.logisticserivce.business_logic.service;

import com.example.logisticserivce.business_logic.exception.ResourceAlreadyExistsException;
import com.example.logisticserivce.business_logic.exception.ResourceNotFoundException;
import com.example.logisticserivce.business_logic.validator.JobValidator;
import com.example.logisticserivce.mapper.JobDtoJobMapper;
import com.example.logisticserivce.model.dto.JobDto;
import com.example.logisticserivce.model.entity.Job;
import com.example.logisticserivce.repository.JobRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
@Transactional
public class JobService {
    private final JobRepository jobRepository;
    private final JobDtoJobMapper jobMapper;
    private final JobValidator validator;
    private final DriverService driverService;
    private final CargoService cargoService;
    private final PrincipalService principalService;
    private final LoadingService loadingService;
    private final UnloadingService unloadingService;
    private final ManagerService managerService;


    public List<Job> getJobList() {
        return jobRepository.findAll();
    }

    public Job getJob(Long id){
        return getJobFromRepository(id);
    }

    public Job addJob(JobDto jobDto){
        trimStringFields(jobDto);
        try {
            validator.validateJobIfNumberAlreadyExists(jobDto.getNumber());
        } catch (ResourceAlreadyExistsException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
        Job job = jobMapper.jobDtoToJob(jobDto);
        if(jobDto.getDriverId() != null){
            job.setDriver(driverService.getDriver(jobDto.getDriverId()));
        }
        if(jobDto.getCargoId() != null){
            job.setCargo(cargoService.getCargo(jobDto.getCargoId()));
        }
        if(jobDto.getManagerId() != null){
            job.setManager(managerService.getManager(jobDto.getManagerId()));
        }
        if(jobDto.getPrincipalId() != null){
            job.setPrincipal(principalService.getPrincipal(jobDto.getPrincipalId()));
        }
        if(jobDto.getLoadingId() != null){
            job.setLoading(loadingService.getLoading(jobDto.getLoadingId()));
        }
        if(jobDto.getUnloadingId() != null){
            job.setUnloading(unloadingService.getUnloading(jobDto.getUnloadingId()));
        }

        return jobRepository.save(job);
    }

    public void deleteJob(Long id){
        jobRepository.deleteById(id);
    }

    public Job modifyJob(Long id, JobDto modifiedJob) {
        trimStringFields(modifiedJob);
        try {
            validator.validateJobIfNumberAlreadyExists(modifiedJob.getNumber(), id);
        } catch (ResourceAlreadyExistsException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
        Job job = getJobFromRepository(id)
                .setStatus(modifiedJob.getStatus())
                .setComment(modifiedJob.getComment())
                .setCommissionedParty(modifiedJob.getCommissionedParty())
                .setDate(modifiedJob.getDate())
                .setNumber(modifiedJob.getNumber())
                .setPayRate(modifiedJob.getPayRate())
                .setPlaceOfIssue(modifiedJob.getPlaceOfIssue());

        if(modifiedJob.getDriverId() != null){
            job.setDriver(driverService.getDriver(modifiedJob.getDriverId()));
        }
        if(modifiedJob.getCargoId() != null){
            job.setCargo(cargoService.getCargo(modifiedJob.getCargoId()));
        }
        if(modifiedJob.getManagerId() != null){
            job.setManager(managerService.getManager(modifiedJob.getManagerId()));
        }
        if(modifiedJob.getPrincipalId() != null){
            job.setPrincipal(principalService.getPrincipal(modifiedJob.getPrincipalId()));
        }
        if(modifiedJob.getLoadingId() != null){
            job.setLoading(loadingService.getLoading(modifiedJob.getLoadingId()));
        }
        if(modifiedJob.getUnloadingId() != null){
            job.setUnloading(unloadingService.getUnloading(modifiedJob.getUnloadingId()));
        }
        return jobRepository.save(job);
    }

    private Job getJobFromRepository(Long id) {
        final Optional<Job> job = jobRepository.findById(id);
        try {
            validator.validateJobIfExists(job, id.toString());
        } catch (ResourceNotFoundException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
        return job.get();
    }

    public void trimStringFields(JobDto jobDto){
        final Optional<String> commissionedParty = Optional.of(jobDto.getCommissionedParty());
        if(validator.isStringFieldValid(commissionedParty)){
            jobDto.setCommissionedParty(commissionedParty.get().trim());
        }
        final Optional<String> comment = Optional.of(jobDto.getComment());
        if(validator.isStringFieldValid(comment)){
            jobDto.setComment(comment.get().trim());
        }
        final Optional<String> placeOfIssue = Optional.of(jobDto.getPlaceOfIssue());
        if(validator.isStringFieldValid(placeOfIssue)){
            jobDto.setPlaceOfIssue(placeOfIssue.get().trim());
        }
    }
}