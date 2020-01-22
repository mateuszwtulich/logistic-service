package com.example.logisticserivce.business_logic.service;

import com.example.logisticserivce.business_logic.exception.ResourceAlreadyExistsException;
import com.example.logisticserivce.business_logic.exception.ResourceNotFoundException;
import com.example.logisticserivce.business_logic.validator.UnloadingValidator;
import com.example.logisticserivce.mapper.UnloadingDtoUnloadingMapper;
import com.example.logisticserivce.model.dto.UnloadingDto;
import com.example.logisticserivce.model.entity.Unloading;
import com.example.logisticserivce.repository.UnloadingRepository;
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
public class UnloadingService {
    private final UnloadingRepository unloadingRepository;
    private final UnloadingDtoUnloadingMapper unloadingMapper;
    private final UnloadingValidator validator;

    public List<Unloading> getUnloadingList() {
        return unloadingRepository.findAll();
    }

    public Unloading getUnloading(Long id){
        return getUnloadingFromRepository(id);
    }

    public Unloading addUnloading(UnloadingDto unloadingDto){
        trimStringFields(unloadingDto);
        try {
            validator.validateUnloadingIfAddressAlreadyExists(unloadingDto.getAddress());
        } catch (ResourceAlreadyExistsException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
        Unloading unloading = unloadingMapper.unloadingDtoToUnloading(unloadingDto);
        return unloadingRepository.save(unloading);
    }

    public void deleteUnloading(Long id){
        unloadingRepository.deleteById(id);
    }

    public Unloading modifyUnloading(Long id, UnloadingDto modifiedUnloading) {
        trimStringFields(modifiedUnloading);
        try {
            validator.validateUnloadingIfAddressAlreadyExists(modifiedUnloading.getAddress(), id);
        } catch (ResourceAlreadyExistsException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
        Unloading unloading = getUnloadingFromRepository(id)
                .setAddress(modifiedUnloading.getAddress());
        return unloadingRepository.save(unloading);
    }

    private Unloading getUnloadingFromRepository(Long id) {
        final Optional<Unloading> unloading = unloadingRepository.findById(id);
        try {
            validator.validateUnloadingIfExists(unloading, id.toString());
        } catch (ResourceNotFoundException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
        return unloading.get();
    }

    public void trimStringFields(UnloadingDto unloadingDto){
        final Optional<String> address = Optional.of(unloadingDto.getAddress());
        if(validator.isStringFieldValid(address)){
            unloadingDto.setAddress(address.get().trim());
        }
    }
}