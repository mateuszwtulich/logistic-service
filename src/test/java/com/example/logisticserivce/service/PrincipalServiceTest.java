package com.example.logisticserivce.service;

import com.example.logisticserivce.business_logic.exception.ResourceAlreadyExistsException;
import com.example.logisticserivce.business_logic.exception.ResourceNotFoundException;
import com.example.logisticserivce.business_logic.service.PrincipalService;
import com.example.logisticserivce.business_logic.validator.PrincipalValidator;
import com.example.logisticserivce.mapper.PrincipalDtoPrincipalMapper;
import com.example.logisticserivce.model.dto.PrincipalDto;
import com.example.logisticserivce.model.entity.Principal;
import com.example.logisticserivce.repository.PrincipalRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PrincipalService.class, PrincipalValidator.class})
public class PrincipalServiceTest {
    @MockBean
    private PrincipalRepository principalRepository;
    @MockBean
    private PrincipalDtoPrincipalMapper principalMapper;
    @Autowired
    private PrincipalValidator validator;
    @Autowired
    private PrincipalService principalService;

    @Before
    public void initMock(){
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void testAddPrincipal_when_NameAlreadyExists(){
        //Arrange
        final PrincipalDto principalDto = new PrincipalDto().setAddress("address").setName("firma");

        //Act
        when(principalRepository.existsByNameIgnoreCase(principalDto.getName())).thenReturn(true);
        principalService.addPrincipal(principalDto);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void testAddPrincipal_when_AddressAlreadyExists(){
        //Arrange
        final PrincipalDto principalDto = new PrincipalDto().setAddress("address").setName("firma");

        //Act
        when(principalRepository.existsByAddressIgnoreCase(principalDto.getAddress())).thenReturn(true);
        principalService.addPrincipal(principalDto);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetPrincipal_when_PrincipalWithPrincipalIdDoesntExist(){
        //Arrange
        Long principalId = new Long(2);
        //Act
        when(principalRepository.findById(principalId)).thenReturn(Optional.ofNullable(null));
        principalService.getPrincipal(principalId);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void testModifyPrincipal_when_NameAlreadyExists(){
        //Arrange
        Long principalId = new Long(6);
        final PrincipalDto principalDto = new PrincipalDto().setAddress("address").setName("name");
        Principal principal = new Principal().setAddress("address").setName("firma").setId(principalId);

        //Act
        when(principalRepository.findById(principalId)).thenReturn(Optional.of(principal));
        when(principalRepository.existsByNameIgnoreCase(principalDto.getName())).thenReturn(true);
        principalService.modifyPrincipal(principalId, principalDto);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void testModifyPrincipal_when_AddressAlreadyExists(){
        //Arrange
        Long principalId = new Long(6);
        final PrincipalDto principalDto = new PrincipalDto().setAddress("newAddress").setName("firma");
        Principal principal = new Principal().setAddress("address").setName("frima").setId(principalId);

        //Act
        when(principalRepository.findById(principalId)).thenReturn(Optional.of(principal));
        when(principalRepository.existsByAddressIgnoreCase(principalDto.getAddress())).thenReturn(true);
        principalService.modifyPrincipal(principalId, principalDto);
    }
}
