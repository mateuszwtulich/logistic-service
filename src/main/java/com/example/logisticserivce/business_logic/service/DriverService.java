package com.example.logisticserivce.business_logic.service;

import com.example.logisticserivce.business_logic.exception.ResourceAlreadyExistsException;
import com.example.logisticserivce.business_logic.exception.ResourceNotFoundException;
import com.example.logisticserivce.business_logic.validator.DriverValidator;
import com.example.logisticserivce.mapper.DriverDtoDriverMapper;
import com.example.logisticserivce.model.dto.DriverDto;
import com.example.logisticserivce.model.dto.JobDto;
import com.example.logisticserivce.model.dto.JobDtoOutput;
import com.example.logisticserivce.model.entity.Driver;
import com.example.logisticserivce.model.enumerator.DriverStatus;
import com.example.logisticserivce.repository.DriverRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@AllArgsConstructor
@Service
@Transactional
public class DriverService {
    private final DriverRepository driverRepository;
    private final DriverDtoDriverMapper driverMapper;
    private final DriverValidator validator;
    private final LorryService lorryService;

    public List<Driver> getDriverList() {
        return driverRepository.findAll();
    }

    public Driver getDriver(Long id){
        return getDriverFromRepository(id);
    }

    public List<JobDtoOutput> getJobList(Long id){
        Driver driver = getDriverFromRepository(id);
        Stream s = driver.getJobList().stream().map(job ->
            new JobDtoOutput().setId(job.getId())
                    .setPlaceOfIssue(job.getPlaceOfIssue())
                    .setComment(job.getComment())
                    .setCommissionedParty(job.getCommissionedParty())
                    .setCargoId(job.getCargo().getId())
                    .setDriverId(job.getDriver().getId())
                    .setLoadingId(job.getLoading().getId())
                    .setManagerId(job.getManager().getId())
                    .setNumber(job.getNumber())
                    .setPayRate(job.getPayRate())
                    .setStatus(job.getStatus())
                    .setPrincipalId(job.getPrincipal().getId())
                    .setDate(job.getDate())
                    .setUnloadingId(job.getUnloading().getId())
                    .setWeight(job.getWeight())
                    .setCargo(job.getCargo().getType())
                    .setLoading(job.getLoading().getAddress())
                    .setDestination(job.getUnloading().getAddress())
        );
        List<JobDtoOutput> list = (List<JobDtoOutput>) s.collect(Collectors.toList());
        return list;
    }

    public Driver addDriver(DriverDto driverDto){
        trimStringFields(driverDto);
        try {
            validator.validateDriverIfLoginAlreadyExists(driverDto.getLogin());
        } catch (ResourceAlreadyExistsException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
        Driver driver = driverMapper.driverDtoToDriver(driverDto);
        if(driverDto.getLorryId() != null){
            driver.setLorry(lorryService.getLorry(driverDto.getLorryId()));
        }
        return driverRepository.save(driver);
    }

    public void deleteDriver(Long id){
        driverRepository.deleteById(id);
    }

    public Driver modifyDriver(Long id, DriverDto modifiedDriver) {
        trimStringFields(modifiedDriver);
        try {
            validator.validateDriverIfLoginAlreadyExists(modifiedDriver.getLogin(), id);
        } catch (ResourceAlreadyExistsException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
        Driver driver = getDriverFromRepository(id)
                .setStatus(modifiedDriver.getStatus())
                .setLogin(modifiedDriver.getLogin())
                .setPassword(modifiedDriver.getPassword())
                .setName(modifiedDriver.getName())
                .setPhoneNumber(modifiedDriver.getPhoneNumber())
                .setSurname(modifiedDriver.getSurname());
        if(modifiedDriver.getLorryId() != null){
            driver.setLorry(lorryService.getLorry(modifiedDriver.getLorryId()));
        }        return driverRepository.save(driver);
    }

    private Driver getDriverFromRepository(Long id) {
        final Optional<Driver> driver = driverRepository.findById(id);
        try {
            validator.validateDriverIfExists(driver, id.toString());
        } catch (ResourceNotFoundException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
        return driver.get();
    }

    public void trimStringFields(DriverDto driverDto){
        final Optional<String> name = Optional.of(driverDto.getName());
        if(validator.isStringFieldValid(name)){
            driverDto.setName(name.get().trim());
        }
        final Optional<String> surname = Optional.of(driverDto.getSurname());
        if(validator.isStringFieldValid(surname)){
            driverDto.setSurname(surname.get().trim());
        }
        final Optional<String> phoneNumber = Optional.of(driverDto.getPhoneNumber());
        if(validator.isStringFieldValid(phoneNumber)){
            driverDto.setPhoneNumber(phoneNumber.get().trim());
        }
        final Optional<String> login = Optional.of(driverDto.getLogin());
        if(validator.isStringFieldValid(login)){
            driverDto.setLogin(login.get().trim());
        }
        final Optional<String> password = Optional.of(driverDto.getPassword());
        if(validator.isStringFieldValid(password)){
            driverDto.setPassword(password.get().trim());
        }
    }
}