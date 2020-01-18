package com.example.logisticserivce.business_logic.validator;

import com.example.logisticserivce.business_logic.exception.ResourceAlreadyExistsException;
import com.example.logisticserivce.business_logic.exception.ResourceNotFoundException;
import com.example.logisticserivce.model.entity.Job;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Optional;

@AllArgsConstructor
@Component
public class JobValidator extends Validator{
    private static final String JOB_NOT_EXIST_KEY = "JobNotExist";
    private static final String JOB_NOT_EXIST_MSG = "Job ''{0}'' does not exist.";
    private static final String JOB_WITH_NUMBER_ALREADY_EXISTS_KEY = "JobAlreadyExists";
    private static final String JOB_WITH_NUMBER_ALREADY_EXISTS_MSG = "Job ''{0}'' already exists.";
    private final com.example.logisticserivce.repository.JobRepository jobRepository;

    public void validateJobIfExists(Optional<Job> job, String identificator) throws ResourceNotFoundException {
        if(!job.isPresent()){
            throw new ResourceNotFoundException(MessageFormat.format
                    (JOB_NOT_EXIST_MSG, identificator), JOB_NOT_EXIST_KEY, identificator);
        }
    }

    public void validateJobIfNumberAlreadyExists(Long jobNumber) throws ResourceAlreadyExistsException {
        if(isJobAlreadyExists(jobNumber)) {
            throwJobAlreadyExistsExceptionFor(jobNumber);
        }
    }

    public void validateJobIfNumberAlreadyExists(Long jobNumber, Long jobId) throws ResourceAlreadyExistsException {
        if(isJobAlreadyExists(jobNumber, jobId)) {
            throwJobAlreadyExistsExceptionFor(jobNumber);
        }
    }

    private void throwJobAlreadyExistsExceptionFor(Long jobNumber) throws ResourceAlreadyExistsException{
        throw new ResourceAlreadyExistsException(MessageFormat.format(JOB_WITH_NUMBER_ALREADY_EXISTS_MSG, jobNumber.toString()),
                JOB_WITH_NUMBER_ALREADY_EXISTS_KEY, jobNumber.toString());
    }

    private boolean isJobAlreadyExists(Long jobNumber){
        return jobRepository.existsByNumber(jobNumber);
    }

    private boolean isJobAlreadyExists(Long jobNumber, Long jobId){
        Optional<Job> job = jobRepository.findById(jobId);
        validateJobIfExists(job, jobId.toString());
        return (jobRepository.existsByNumber(jobNumber) && !(job.get().getNumber().equals(jobNumber)));
    }
}