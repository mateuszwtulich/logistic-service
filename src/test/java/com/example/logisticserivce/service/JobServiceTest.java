package com.example.logisticserivce.service;

import com.example.logisticserivce.business_logic.exception.ResourceAlreadyExistsException;
import com.example.logisticserivce.business_logic.exception.ResourceNotFoundException;
import com.example.logisticserivce.business_logic.service.*;
import com.example.logisticserivce.business_logic.validator.DriverValidator;
import com.example.logisticserivce.business_logic.validator.JobValidator;
import com.example.logisticserivce.mapper.JobArchiveJobMapper;
import com.example.logisticserivce.mapper.JobDtoJobMapper;
import com.example.logisticserivce.model.dto.JobDto;
import com.example.logisticserivce.model.entity.*;
import com.example.logisticserivce.model.enumerator.DriverStatus;
import com.example.logisticserivce.model.enumerator.JobStatus;
import com.example.logisticserivce.model.enumerator.LorryStatus;
import com.example.logisticserivce.repository.DriverRepository;
import com.example.logisticserivce.repository.JobArchiveRepository;
import com.example.logisticserivce.repository.JobRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {JobService.class, JobValidator.class})
public class JobServiceTest {
    @MockBean
    private JobRepository jobRepository;
    @MockBean
    private DriverRepository driverRepository;
    @MockBean
    private JobArchiveRepository jobArchiveRepository;
    @MockBean
    private JobArchiveJobMapper jobArchiveJobMapper;
    @MockBean
    private JobDtoJobMapper jobMapper;
    @MockBean
    private DriverValidator driverValidator;
    @Autowired
    private JobValidator validator;
    @Autowired
    private JobService jobService;
    @MockBean
    private DriverService driverService;
    @MockBean
    private CargoService cargoService;
    @MockBean
    private PrincipalService principalService;
    @MockBean
    private LoadingService loadingService;
    @MockBean
    private UnloadingService unloadingService;
    @MockBean
    private ManagerService managerService;

    @Before
    public void initMock(){
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void testAddJob_when_NumberAlreadyExists(){
        //Arrange
        final JobDto jobDto = new JobDto().setComment("Comment").setCommissionedParty("Party").setPlaceOfIssue("Place").setCargoId(1L)
                .setDriverId(2L).setLoadingId(3L).setManagerId(4L).setPrincipalId(5L).setNumber(2L).setPayRate(123).setStatus(JobStatus.ASSIGNED);
        //Act
        when(jobRepository.existsByNumber(jobDto.getNumber())).thenReturn(true);
        jobService.addJob(jobDto);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetjob_when_JobWithJobIdDoesntExist(){
        //Arrange
        Long jobId = new Long(2);
        //Act
        when(jobRepository.findById(jobId)).thenReturn(Optional.ofNullable(null));
        jobService.getJob(jobId);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void testModifyJob_when_NumberAlreadyExists(){
        //Arrange
        Long jobId = new Long(6);
        final JobDto jobDto = new JobDto().setComment("Comment").setCommissionedParty("Party").setPlaceOfIssue("Place").setCargoId(1L)
                .setDriverId(2L).setLoadingId(3L).setManagerId(4L).setPrincipalId(5L).setNumber(3L).setPayRate(123).setStatus(JobStatus.IN_PROGRESS);
        final Lorry lorry = new Lorry().setId(1L).setModel("MAN").setStatus(LorryStatus.AVAILABLE).setLicenceNumber("SCZ");
        final Driver driver = new Driver().setName("John").setSurname("Smith").setPhoneNumber("123").setPassword("password").setLogin("smith")
                .setStatus(DriverStatus.AVAILABLE).setId(2L).setLorry(lorry);
        final Principal principal = new Principal().setId(3L).setName("Firma").setAddress("ulica");
        final Loading loading = new Loading().setAddress("loading").setPrincipal(principal).setId(4L);
        final Unloading unloading = new Unloading().setAddress("unloading").setId(4L);
        final Cargo cargo = new Cargo().setId(5L).setType("cargo").setPrincipal(principal);
        Job job = new Job().setId(6L).setStatus(JobStatus.ASSIGNED).setDriver(driver).setCargo(cargo).setComment("comment").setNumber(2L).setDate(new Timestamp(3))
                .setCommissionedParty("Party").setComment("Comment").setPlaceOfIssue("Place").setPrincipal(principal).setLoading(loading).setUnloading(unloading);

        //Act
        when(jobRepository.findById(jobId)).thenReturn(Optional.of(job));
        when(jobRepository.existsByNumber(3L)).thenReturn(true);
        jobService.modifyJob(jobId, jobDto);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void testModifyJob_when_statusChangedToIN_PROGRESS(){
        //Arrange
        Long jobId = new Long(6);
        final JobDto jobDto = new JobDto().setComment("Comment").setCommissionedParty("Party").setPlaceOfIssue("Place").setCargoId(1L)
                .setDriverId(2L).setLoadingId(3L).setManagerId(4L).setPrincipalId(5L).setNumber(3L).setPayRate(123).setStatus(JobStatus.IN_PROGRESS);
        final Lorry lorry = new Lorry().setId(1L).setModel("MAN").setStatus(LorryStatus.AVAILABLE).setLicenceNumber("SCZ");
        final Driver driver = new Driver().setName("John").setSurname("Smith").setPhoneNumber("123").setPassword("password").setLogin("smith")
                .setStatus(DriverStatus.AVAILABLE).setId(2L).setLorry(lorry);
        final Principal principal = new Principal().setId(3L).setName("Firma").setAddress("ulica");
        final Loading loading = new Loading().setAddress("loading").setPrincipal(principal).setId(4L);
        final Unloading unloading = new Unloading().setAddress("unloading").setId(4L);
        final Cargo cargo = new Cargo().setId(5L).setType("cargo").setPrincipal(principal);
        Job jobExpected = new Job().setId(6L).setStatus(JobStatus.ASSIGNED).setDriver(driver).setCargo(cargo).setComment("comment").setNumber(2L).setDate(new Timestamp(3))
                .setCommissionedParty("Party").setComment("Comment").setPlaceOfIssue("Place").setPrincipal(principal).setLoading(loading).setUnloading(unloading);

        //Act
        when(jobRepository.findById(jobId)).thenReturn(Optional.of(jobExpected));
        when(jobRepository.existsByNumber(3L)).thenReturn(true);
        jobService.modifyJob(jobId, jobDto);

        //Assert

        Assert.assertEquals(jobExpected.getDriver().getStatus(), DriverStatus.WORKING);
        Assert.assertEquals(jobExpected.getDriver().getLorry().getStatus(), LorryStatus.WORKING);
        Assert.assertEquals(jobExpected.getStatus(), JobStatus.IN_PROGRESS);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void testModifyJob_when_statusChangedToFINISHED(){
        //Arrange
        Long jobId = new Long(6);
        final JobDto jobDto = new JobDto().setComment("Comment").setCommissionedParty("Party").setPlaceOfIssue("Place").setCargoId(1L)
                .setDriverId(2L).setLoadingId(3L).setManagerId(4L).setPrincipalId(5L).setNumber(3L).setPayRate(123).setStatus(JobStatus.FINISHED);
        final Lorry lorry = new Lorry().setId(1L).setModel("MAN").setStatus(LorryStatus.WORKING).setLicenceNumber("SCZ");
        final Driver driver = new Driver().setName("John").setSurname("Smith").setPhoneNumber("123").setPassword("password").setLogin("smith")
                .setStatus(DriverStatus.WORKING).setId(2L).setLorry(lorry);
        final Principal principal = new Principal().setId(3L).setName("Firma").setAddress("ulica");
        final Loading loading = new Loading().setAddress("loading").setPrincipal(principal).setId(4L);
        final Unloading unloading = new Unloading().setAddress("unloading").setId(4L);
        final Cargo cargo = new Cargo().setId(5L).setType("cargo").setPrincipal(principal);
        Job jobExpected = new Job().setId(6L).setStatus(JobStatus.IN_PROGRESS).setDriver(driver).setCargo(cargo).setComment("comment").setNumber(2L).setDate(new Timestamp(3))
                .setCommissionedParty("Party").setComment("Comment").setPlaceOfIssue("Place").setPrincipal(principal).setLoading(loading).setUnloading(unloading);

        //Act
        when(jobRepository.findById(jobId)).thenReturn(Optional.of(jobExpected));
        when(jobRepository.existsByNumber(3L)).thenReturn(true);
        jobService.modifyJob(jobId, jobDto);

        //Assert

        Assert.assertEquals(jobExpected.getDriver().getStatus(), DriverStatus.AVAILABLE);
        Assert.assertEquals(jobExpected.getDriver().getLorry().getStatus(), LorryStatus.AVAILABLE);
        Assert.assertEquals(jobExpected.getStatus(), JobStatus.FINISHED);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void testModifyJob_when_statusChangedToASSIGNED(){
        //Arrange
        Long jobId = new Long(6);
        final JobDto jobDto = new JobDto().setComment("Comment").setCommissionedParty("Party").setPlaceOfIssue("Place").setCargoId(1L)
                .setDriverId(2L).setLoadingId(3L).setManagerId(4L).setPrincipalId(5L).setNumber(3L).setPayRate(123).setStatus(JobStatus.ASSIGNED);
        final Lorry lorry = new Lorry().setId(1L).setModel("MAN").setStatus(LorryStatus.AVAILABLE).setLicenceNumber("SCZ");
        final Driver driver = new Driver().setName("John").setSurname("Smith").setPhoneNumber("123").setPassword("password").setLogin("smith")
                .setStatus(DriverStatus.AVAILABLE).setId(2L).setLorry(lorry);
        final Principal principal = new Principal().setId(3L).setName("Firma").setAddress("ulica");
        final Loading loading = new Loading().setAddress("loading").setPrincipal(principal).setId(4L);
        final Unloading unloading = new Unloading().setAddress("unloading").setId(4L);
        final Cargo cargo = new Cargo().setId(5L).setType("cargo").setPrincipal(principal);
        Job jobExpected = new Job().setId(6L).setStatus(JobStatus.UNASSIGNED).setDriver(driver).setCargo(cargo).setComment("comment").setNumber(2L).setDate(new Timestamp(3))
                .setCommissionedParty("Party").setComment("Comment").setPlaceOfIssue("Place").setPrincipal(principal).setLoading(loading).setUnloading(unloading);

        //Act
        when(jobRepository.findById(jobId)).thenReturn(Optional.of(jobExpected));
        when(jobRepository.existsByNumber(3L)).thenReturn(true);
        jobService.modifyJob(jobId, jobDto);

        //Assert

        Assert.assertEquals(jobExpected.getDriver().getStatus(), DriverStatus.AVAILABLE);
        Assert.assertEquals(jobExpected.getDriver().getLorry().getStatus(), LorryStatus.AVAILABLE);
        Assert.assertEquals(jobExpected.getStatus(), JobStatus.ASSIGNED);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void testModifyJob_when_statusChangedToSUSPENDED(){
        //Arrange
        Long jobId = new Long(6);
        final JobDto jobDto = new JobDto().setComment("Comment").setCommissionedParty("Party").setPlaceOfIssue("Place").setCargoId(1L)
                .setDriverId(2L).setLoadingId(3L).setManagerId(4L).setPrincipalId(5L).setNumber(3L).setPayRate(123).setStatus(JobStatus.SUSPENDED);
        final Lorry lorry = new Lorry().setId(1L).setModel("MAN").setStatus(LorryStatus.WORKING).setLicenceNumber("SCZ");
        final Driver driver = new Driver().setName("John").setSurname("Smith").setPhoneNumber("123").setPassword("password").setLogin("smith")
                .setStatus(DriverStatus.WORKING).setId(2L).setLorry(lorry);
        final Principal principal = new Principal().setId(3L).setName("Firma").setAddress("ulica");
        final Loading loading = new Loading().setAddress("loading").setPrincipal(principal).setId(4L);
        final Unloading unloading = new Unloading().setAddress("unloading").setId(4L);
        final Cargo cargo = new Cargo().setId(5L).setType("cargo").setPrincipal(principal);
        Job jobExpected = new Job().setId(6L).setStatus(JobStatus.IN_PROGRESS).setDriver(driver).setCargo(cargo).setComment("comment").setNumber(2L).setDate(new Timestamp(3))
                .setCommissionedParty("Party").setComment("Comment").setPlaceOfIssue("Place").setPrincipal(principal).setLoading(loading).setUnloading(unloading);

        //Act
        when(jobRepository.findById(jobId)).thenReturn(Optional.of(jobExpected));
        when(jobRepository.existsByNumber(3L)).thenReturn(true);
        jobService.modifyJob(jobId, jobDto);

        //Assert

        Assert.assertEquals(jobExpected.getDriver().getStatus(), DriverStatus.WORKING);
        Assert.assertEquals(jobExpected.getDriver().getLorry().getStatus(), LorryStatus.EMERGENCY);
        Assert.assertEquals(jobExpected.getStatus(), JobStatus.SUSPENDED);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void testModifyJob_when_statusChangedToUNASSIGNED(){
        //Arrange
        Long jobId = new Long(6);
        final JobDto jobDto = new JobDto().setComment("Comment").setCommissionedParty("Party").setPlaceOfIssue("Place").setCargoId(1L)
                .setDriverId(2L).setLoadingId(3L).setManagerId(4L).setPrincipalId(5L).setNumber(3L).setPayRate(123).setStatus(JobStatus.ASSIGNED);
        final Lorry lorry = new Lorry().setId(1L).setModel("MAN").setStatus(LorryStatus.AVAILABLE).setLicenceNumber("SCZ");
        final Driver driver = new Driver().setName("John").setSurname("Smith").setPhoneNumber("123").setPassword("password").setLogin("smith")
                .setStatus(DriverStatus.AVAILABLE).setId(2L).setLorry(lorry);
        final Principal principal = new Principal().setId(3L).setName("Firma").setAddress("ulica");
        final Loading loading = new Loading().setAddress("loading").setPrincipal(principal).setId(4L);
        final Unloading unloading = new Unloading().setAddress("unloading").setId(4L);
        final Cargo cargo = new Cargo().setId(5L).setType("cargo").setPrincipal(principal);
        Job jobExpected = new Job().setId(6L).setStatus(JobStatus.UNASSIGNED).setDriver(driver).setCargo(cargo).setComment("comment").setNumber(2L).setDate(new Timestamp(3))
                .setCommissionedParty("Party").setComment("Comment").setPlaceOfIssue("Place").setPrincipal(principal).setLoading(loading).setUnloading(unloading);

        //Act
        when(jobRepository.findById(jobId)).thenReturn(Optional.of(jobExpected));
        when(jobRepository.existsByNumber(3L)).thenReturn(true);
        jobService.modifyJob(jobId, jobDto);

        //Assert

        Assert.assertEquals(jobExpected.getDriver().getStatus(), DriverStatus.AVAILABLE);
        Assert.assertEquals(jobExpected.getDriver().getLorry().getStatus(), LorryStatus.AVAILABLE);
        Assert.assertEquals(jobExpected.getStatus(), JobStatus.UNASSIGNED);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteJob_when_JobWithJobIdDoesntExist(){
        //Arrange
        Long jobId = new Long(2);
        //Act
        when(jobRepository.findById(jobId)).thenReturn(Optional.ofNullable(null));
        jobService.deleteJob(jobId);
    }
}
