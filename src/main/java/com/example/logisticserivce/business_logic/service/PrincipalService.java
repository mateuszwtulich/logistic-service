package com.example.logisticserivce.business_logic.service;

import com.example.logisticserivce.business_logic.exception.ResourceAlreadyExistsException;
import com.example.logisticserivce.business_logic.exception.ResourceNotFoundException;
import com.example.logisticserivce.business_logic.validator.PrincipalValidator;
import com.example.logisticserivce.mapper.PrincipalDtoPrincipalMapper;
import com.example.logisticserivce.model.dto.PrincipalDto;
import com.example.logisticserivce.model.entity.Cargo;
import com.example.logisticserivce.model.entity.Loading;
import com.example.logisticserivce.model.entity.Principal;
import com.example.logisticserivce.model.entity.Unloading;
import com.example.logisticserivce.repository.PrincipalRepository;
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
public class PrincipalService {
    private final PrincipalRepository principalRepository;
    private final PrincipalDtoPrincipalMapper principalMapper;
    private final PrincipalValidator validator;

    public List<Principal> getPrincipalList() {
        return principalRepository.findAll();
    }

    public Principal getPrincipal(Long id){
        return getPrincipalFromRepository(id);
    }

    public Principal addPrincipal(PrincipalDto principalDto){
        trimStringFields(principalDto);
        try {
            validator.validatePrincipalIfNameAlreadyExists(principalDto.getName());
            validator.validatePrincipalIfAddressAlreadyExists(principalDto.getAddress());
        } catch (ResourceAlreadyExistsException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
        return principalRepository.save(principalMapper.principalDtoToPrincipal(principalDto));
    }

    public void deletePrincipal(Long id){
        principalRepository.deleteById(id);
    }

    public Principal modifyPrincipal(Long id, PrincipalDto modifiedPrincipal) {
        trimStringFields(modifiedPrincipal);
        try {
            validator.validatePrincipalIfNameAlreadyExists(modifiedPrincipal.getName(), id);
            validator.validatePrincipalIfAddressAlreadyExists(modifiedPrincipal.getAddress(), id);
        } catch (ResourceAlreadyExistsException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
        Principal principal = getPrincipalFromRepository(id)
                .setName(modifiedPrincipal.getName())
                .setAddress(modifiedPrincipal.getAddress());

        return principalRepository.save(principal);
    }

    private Principal getPrincipalFromRepository(Long id) {
        final Optional<Principal> principal = principalRepository.findById(id);
        try {
            validator.validatePrincipalIfExists(principal, id.toString());
        } catch (ResourceNotFoundException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
        return principal.get();
    }

    public void trimStringFields(PrincipalDto principalDto){
        final Optional<String> name = Optional.of(principalDto.getName());
        if(validator.isStringFieldValid(name)){
            principalDto.setName(name.get().trim());
        }
        final Optional<String> address = Optional.of(principalDto.getAddress());
        if(validator.isStringFieldValid(address)){
            principalDto.setAddress(address.get().trim());
        }
    }

    public List<Loading> getLoadingList(Long id) {
        return getPrincipalFromRepository(id).getLoadingList();
    }

    public List<Cargo> getCargoList(Long id) {
        return getPrincipalFromRepository(id).getCargoList();
    }
}