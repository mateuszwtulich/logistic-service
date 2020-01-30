package com.example.logisticserivce.business_logic.service;

import com.example.logisticserivce.business_logic.exception.DriverNotAvailableException;
import com.example.logisticserivce.business_logic.exception.ResourceAlreadyExistsException;
import com.example.logisticserivce.business_logic.exception.ResourceNotFoundException;
import com.example.logisticserivce.business_logic.validator.DriverValidator;
import com.example.logisticserivce.business_logic.validator.JobValidator;
import com.example.logisticserivce.mapper.JobArchiveJobMapper;
import com.example.logisticserivce.mapper.JobDtoJobMapper;
import com.example.logisticserivce.model.dto.JobDto;
import com.example.logisticserivce.model.entity.Driver;
import com.example.logisticserivce.model.entity.Job;
import com.example.logisticserivce.model.entity.JobArchive;
import com.example.logisticserivce.model.enumerator.DriverStatus;
import com.example.logisticserivce.model.enumerator.JobStatus;
import com.example.logisticserivce.model.enumerator.LorryStatus;
import com.example.logisticserivce.repository.JobArchiveRepository;
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
    private final JobArchiveRepository jobArchiveRepository;
    private final JobArchiveJobMapper jobArchiveJobMapper;
    private final JobDtoJobMapper jobMapper;
    private final DriverValidator driverValidator;
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
        return jobRepository.save(checkStatusAndConvertObjects(jobDto, job));
    }

    public void deleteJob(Long id){
        Job job = getJobFromRepository(id);
        if(job.getDriver() != null) {
            job.getDriver().setStatus(DriverStatus.AVAILABLE);
            job.getDriver().getLorry().setStatus(LorryStatus.AVAILABLE);
        }
        jobArchiveRepository.save(jobArchiveJobMapper.JobToJobArchive(job));
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

        return jobRepository.save(checkStatusAndConvertObjects(modifiedJob, job));
    }

    private Job checkStatusAndConvertObjects(JobDto jobDto, Job job){
        if (jobDto.getStatus() == JobStatus.UNASSIGNED)
            setStatusToUnassigned(job, jobDto);
        if(jobDto.getDriverId() != null) {
            if (jobDto.getStatus() == JobStatus.ASSIGNED)
                assignDriverToJob(job, jobDto);
            if (jobDto.getStatus() == JobStatus.FINISHED)
                finishJob(job, jobDto);
            if (jobDto.getStatus() == JobStatus.IN_PROGRESS)
                setStatusToInProgress(job, jobDto);
            if (jobDto.getStatus() == JobStatus.SUSPENDED)
                setEmergencyStatus(job, jobDto);
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
        return job;
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

    private void trimStringFields(JobDto jobDto){
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

    private void assignDriverToJob(Job job, JobDto jobDto){
        Driver driver = driverService.getDriver(jobDto.getDriverId());
        try{
            driverValidator.validateIfDriverHasAssignedLorry(driver);
            job.setDriver(driverService.getDriver(jobDto.getDriverId()));
            job.setStatus(JobStatus.ASSIGNED);
        }catch (DriverNotAvailableException ex){
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    private void finishJob(Job job, JobDto jobDto){
        Driver driver = driverService.getDriver(jobDto.getDriverId());
        job.setDriver(driver);
        job.setStatus(JobStatus.FINISHED);
        driver.setStatus(DriverStatus.AVAILABLE);
        driver.getLorry().setStatus(LorryStatus.AVAILABLE);
    }

    private void setStatusToInProgress(Job job, JobDto jobDto){
        Driver driver = driverService.getDriver(jobDto.getDriverId());
        try {
            driverValidator.validateDriverIfIsAvailable(driver);
            driverValidator.validateIfDriverHasAssignedLorry(driver);
            driver.setStatus(DriverStatus.WORKING);
            driver.getLorry().setStatus(LorryStatus.WORKING);
            job.setStatus(JobStatus.IN_PROGRESS);
        }catch (DriverNotAvailableException ex){
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    private void setEmergencyStatus(Job job, JobDto jobDto){
        Driver driver = driverService.getDriver(jobDto.getDriverId());
        job.setStatus(JobStatus.SUSPENDED);
        driver.setStatus(DriverStatus.WORKING);
        driver.getLorry().setStatus(LorryStatus.EMERGENCY);
    }

    private void setStatusToUnassigned(Job job, JobDto jobDto){
        if(job.getDriver() != null) {
            Driver driver = job.getDriver();
            driver.setStatus(DriverStatus.AVAILABLE);
            driver.getLorry().setStatus(LorryStatus.AVAILABLE);
        }
        job.setStatus(JobStatus.UNASSIGNED);
        job.setDriver(null);
    }
}