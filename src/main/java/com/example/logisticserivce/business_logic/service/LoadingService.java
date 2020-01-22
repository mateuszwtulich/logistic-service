package com.example.logisticserivce.business_logic.service;

import com.example.logisticserivce.business_logic.exception.ResourceAlreadyExistsException;
import com.example.logisticserivce.business_logic.exception.ResourceNotFoundException;
import com.example.logisticserivce.business_logic.validator.LoadingValidator;
import com.example.logisticserivce.mapper.LoadingDtoLoadingMapper;
import com.example.logisticserivce.model.dto.LoadingDto;
import com.example.logisticserivce.model.entity.Driver;
import com.example.logisticserivce.model.entity.Loading;
import com.example.logisticserivce.repository.LoadingRepository;
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
public class LoadingService {
    private final LoadingRepository loadingRepository;
    private final LoadingDtoLoadingMapper loadingMapper;
    private final PrincipalService principalService;
    private final LoadingValidator validator;

    public List<Loading> getLoadingList() {
        return loadingRepository.findAll();
    }

    public Loading getLoading(Long id){
        return getLoadingFromRepository(id);
    }

    public Loading addLoading(LoadingDto loadingDto){
        trimStringFields(loadingDto);
        try {
            validator.validateLoadingIfAddressAlreadyExists(loadingDto.getAddress());
        } catch (ResourceAlreadyExistsException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
        Loading loading = loadingMapper.loadingDtoToLoading(loadingDto);
        if(loadingDto.getPrincipalId() != null){
            loading.setPrincipal(principalService.getPrincipal(loadingDto.getPrincipalId()));
        }
        return loadingRepository.save(loading);
    }

    public void deleteLoading(Long id){
        loadingRepository.deleteById(id);
    }

    public Loading modifyLoading(Long id, LoadingDto modifiedLoading) {
        trimStringFields(modifiedLoading);
        try {
            validator.validateLoadingIfAddressAlreadyExists(modifiedLoading.getAddress(), id);
        } catch (ResourceAlreadyExistsException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
        Loading loading = getLoadingFromRepository(id)
                .setAddress(modifiedLoading.getAddress());
        if(modifiedLoading.getPrincipalId() != null){
            loading.setPrincipal(principalService.getPrincipal(modifiedLoading.getPrincipalId()));
        }
        return loadingRepository.save(loading);
    }

    private Loading getLoadingFromRepository(Long id) {
        final Optional<Loading> loading = loadingRepository.findById(id);
        try {
            validator.validateLoadingIfExists(loading, id.toString());
        } catch (ResourceNotFoundException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
        return loading.get();
    }

    public void trimStringFields(LoadingDto loadingDto){
        final Optional<String> address = Optional.of(loadingDto.getAddress());
        if(validator.isStringFieldValid(address)){
            loadingDto.setAddress(address.get().trim());
        }
    }
}