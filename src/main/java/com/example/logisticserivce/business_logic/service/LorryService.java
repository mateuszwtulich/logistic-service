package com.example.logisticserivce.business_logic.service;

import com.example.logisticserivce.business_logic.exception.ResourceAlreadyExistsException;
import com.example.logisticserivce.business_logic.exception.ResourceNotFoundException;
import com.example.logisticserivce.business_logic.validator.LorryValidator;
import com.example.logisticserivce.mapper.LorryDtoLorryMapper;
import com.example.logisticserivce.model.dto.LorryDto;
import com.example.logisticserivce.model.entity.Lorry;
import com.example.logisticserivce.repository.LorryRepository;
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
public class LorryService {
    private final LorryRepository lorryRepository;
    private final LorryDtoLorryMapper lorryMapper;
    private final LorryValidator validator;

    public List<Lorry> getLorryList() {
        return lorryRepository.findAll();
    }

    public Lorry getLorry(Long id) {
        return getLorryFromRepository(id);
    }

    public Lorry addLorry(LorryDto lorryDto) {
        trimStringFields(lorryDto);
        try {
            validator.validateLorryIfLicenceNumberAlreadyExists(lorryDto.getLicenceNumber());
        } catch (ResourceAlreadyExistsException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
        Lorry lorry = lorryMapper.lorryDtoToLorry(lorryDto);
        return lorryRepository.save(lorry);
    }

    public void deleteLorry(Long id) {
        lorryRepository.deleteById(id);
    }

    public Lorry modifyLorry(Long id, LorryDto modifiedLorry) {
        trimStringFields(modifiedLorry);
        try {
            validator.validateLorryIfLicenceNumberAlreadyExists(modifiedLorry.getLicenceNumber(), id);
        } catch (ResourceAlreadyExistsException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
        Lorry lorry = getLorryFromRepository(id)
                .setStatus(modifiedLorry.getStatus())
                .setLicenceNumber(modifiedLorry.getLicenceNumber())
                .setModel(modifiedLorry.getModel());
        return lorryRepository.save(lorry);
    }

    private Lorry getLorryFromRepository(Long id) {
        final Optional<Lorry> lorry = lorryRepository.findById(id);
        try {
            validator.validateLorryIfExists(lorry, id.toString());
        } catch (ResourceNotFoundException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
        return lorry.get();
    }

    public void trimStringFields(LorryDto lorryDto) {
        final Optional<String> model = Optional.of(lorryDto.getModel());
        if (validator.isStringFieldValid(model)) {
            lorryDto.setModel(model.get().trim());
        }
        final Optional<String> licenceNumber = Optional.of(lorryDto.getLicenceNumber());
        if (validator.isStringFieldValid(licenceNumber)) {
            lorryDto.setLicenceNumber(licenceNumber.get().trim());
        }
    }
}