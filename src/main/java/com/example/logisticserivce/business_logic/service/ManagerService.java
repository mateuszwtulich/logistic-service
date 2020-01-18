package com.example.logisticserivce.business_logic.service;

import com.example.logisticserivce.business_logic.exception.ResourceAlreadyExistsException;
import com.example.logisticserivce.business_logic.exception.ResourceNotFoundException;
import com.example.logisticserivce.business_logic.validator.ManagerValidator;
import com.example.logisticserivce.mapper.ManagerDtoManagerMapper;
import com.example.logisticserivce.model.dto.ManagerDto;
import com.example.logisticserivce.model.entity.Manager;
import com.example.logisticserivce.repository.ManagerRepository;
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
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final ManagerDtoManagerMapper managerMapper;
    private final ManagerValidator validator;

    public List<Manager> getManagerList() {
        return managerRepository.findAll();
    }

    public Manager getManager(Long id){
        return getManagerFromRepository(id);
    }

    public Manager addManager(ManagerDto managerDto){
        trimStringFields(managerDto);
        try {
            validator.validateManagerIfLoginAlreadyExists(managerDto.getLogin());
        } catch (ResourceAlreadyExistsException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
        return managerRepository.save(managerMapper.managerDtoToManager(managerDto));
    }

    public void deleteManager(Long id){
        managerRepository.deleteById(id);
    }

    public Manager modifyManager(Long id, ManagerDto modifiedManager) {
        trimStringFields(modifiedManager);
        try {
            validator.validateManagerIfLoginAlreadyExists(modifiedManager.getLogin(), id);
        } catch (ResourceAlreadyExistsException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
        Manager manager = getManagerFromRepository(id)
                .setLogin(modifiedManager.getLogin())
                .setPassword(modifiedManager.getPassword())
                .setName(modifiedManager.getName())
                .setSurname(modifiedManager.getSurname());

        return managerRepository.save(manager);
    }

    private Manager getManagerFromRepository(Long id) {
        final Optional<Manager> manager = managerRepository.findById(id);
        try {
            validator.validateManagerIfExists(manager, id.toString());
        } catch (ResourceNotFoundException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
        return manager.get();
    }

    public void trimStringFields(ManagerDto managerDto){
        final Optional<String> name = Optional.of(managerDto.getName());
        if(validator.isStringFieldValid(name)){
            managerDto.setName(name.get().trim());
        }
        final Optional<String> surname = Optional.of(managerDto.getSurname());
        if(validator.isStringFieldValid(surname)){
            managerDto.setSurname(surname.get().trim());
        }
        final Optional<String> login = Optional.of(managerDto.getLogin());
        if(validator.isStringFieldValid(login)){
            managerDto.setLogin(login.get().trim());
        }
        final Optional<String> password = Optional.of(managerDto.getPassword());
        if(validator.isStringFieldValid(password)){
            managerDto.setPassword(password.get().trim());
        }
    }
}