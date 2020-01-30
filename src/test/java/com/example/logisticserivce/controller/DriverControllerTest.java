package com.example.logisticserivce.controller;

import com.example.logisticserivce.business_logic.exception.ResourceAlreadyExistsException;
import com.example.logisticserivce.business_logic.service.DriverService;
import com.example.logisticserivce.model.dto.DriverDto;
import com.example.logisticserivce.model.entity.Driver;
import com.example.logisticserivce.model.entity.Lorry;
import com.example.logisticserivce.model.enumerator.DriverStatus;
import com.example.logisticserivce.model.enumerator.LorryStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DriverController.class)
public class DriverControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DriverService driverService;
    private List<Driver> driverList;
    private List<DriverDto> driverDtoList;
    private List<Lorry> lorryList;

    @Before
    public void prepare() throws Exception {
        DriverDto first = new DriverDto();
        first.setName("Mike").setSurname("Smith").setStatus(DriverStatus.AVAILABLE).setPassword("password")
                .setLogin("smith").setPhoneNumber("123").setLorryId(3L);

        DriverDto second = new DriverDto();
        second.setName("John").setSurname("Kick").setStatus(DriverStatus.AVAILABLE).setPassword("password")
                .setLogin("kick").setPhoneNumber("345").setLorryId(4L);

        driverDtoList = new ArrayList<>();
        driverDtoList.add(first);
        driverDtoList.add(second);

        Lorry firstLorry = new Lorry();
        firstLorry.setLicenceNumber("SCZ2").setModel("MAN").setStatus(LorryStatus.AVAILABLE).setId(3L);

        Lorry secondLorry = new Lorry();
        secondLorry.setLicenceNumber("SC98").setModel("MAN").setStatus(LorryStatus.AVAILABLE).setId(4L);

        lorryList = new ArrayList<>();
        lorryList.add(firstLorry);
        lorryList.add(secondLorry);

        Driver firstDriver = new Driver();
        firstDriver.setId(1L).setName("Mike").setSurname("Smith").setPhoneNumber("123").setLogin("smith")
                .setPassword("password").setStatus(DriverStatus.AVAILABLE).setLorry(firstLorry);

        Driver secondDriver = new Driver();
        secondDriver.setId(2L).setName("John").setSurname("Kick").setPhoneNumber("345").setLogin("kick")
                .setPassword("password").setStatus(DriverStatus.AVAILABLE).setLorry(secondLorry);

        driverList = new ArrayList<>();
        driverList.add(firstDriver);
        driverList.add(secondDriver);
    }

    @Test
    public void getDriversShouldReturnDriver() throws Exception {
        final String expectedJson = "[{\"name\":\"Mike\",\"surname\":\"Smith\",\"id\":1,\"status\":\"AVAILABLE\"," +
                " \"phoneNumber\":\"123\", \"login\":\"smith\",\"lorry\":" +
                "{\"model\":\"MAN\",\"licenceNumber\":\"SCZ2\",\"id\":3,\"status\":\"AVAILABLE\"}}," +
                "{\"name\":\"John\",\"surname\":\"Kick\",\"id\":2,\"status\":\"AVAILABLE\"," +
                " \"phoneNumber\":\"345\", \"login\":\"kick\",\"lorry\":"+
                "{\"model\":\"MAN\",\"licenceNumber\":\"SC98\",\"id\":4,\"status\":\"AVAILABLE\"}}]";

        doAnswer(invocation -> driverList).when(driverService).getDriverList();
        this.mockMvc.perform(get("/drivers")).andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expectedJson));
    }


    @Test
    public void getDriverShouldReturnDriver() throws Exception {
        final Long driverId = 1L;
        final String expectedJson = "{\"name\":\"Mike\",\"surname\":\"Smith\",\"id\":1,\"status\":\"AVAILABLE\"," +
                " \"phoneNumber\":\"123\", \"login\":\"smith\",\"lorry\":" +
                "{\"model\":\"MAN\",\"licenceNumber\":\"SCZ2\",\"id\":3,\"status\":\"AVAILABLE\"}}" ;

        doAnswer(invocation -> driverList.stream().filter(l -> l.getId().equals(driverId)).findFirst().orElseThrow(() -> new Exception())
        ).when(driverService).getDriver(driverId);

        this.mockMvc.perform(get("/drivers/{driverId}", driverId)).andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void addDriverShouldCreateNewDriver() throws Exception {
        final String expectedJson = "{\"name\":\"Mike\",\"surname\":\"Smith\",\"id\":1,\"status\":\"AVAILABLE\"," +
                " \"phoneNumber\":\"123\", \"login\":\"smith\",\"lorry\":" +
                "{\"model\":\"MAN\",\"licenceNumber\":\"SCZ2\",\"id\":3,\"status\":\"AVAILABLE\"}}" ;;
        final ObjectMapper mapper = new ObjectMapper();
        final String driverDtoJson = mapper.writeValueAsString(driverDtoList.get(0));

        doAnswer(invocation -> driverList.get(0)
        ).when(driverService).addDriver(driverDtoList.get(0));
        this.mockMvc.perform(post("/drivers")
                .content(driverDtoJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful()).andExpect(content().json(expectedJson));
    }

    @Test
    public void addDriverShouldReturnAlreadyExists() throws Exception {
        DriverDto third = new DriverDto();
        third.setName("John").setSurname("Flick").setStatus(DriverStatus.AVAILABLE).setPassword("word")
                .setLogin("kick").setPhoneNumber("678").setLorryId(3L);
        final ObjectMapper mapper = new ObjectMapper();
        final String driverDtoJson = mapper.writeValueAsString(third);

        doAnswer(invocation -> {
            throw new ResourceAlreadyExistsException();
        }).when(driverService).addDriver(third);
        this.mockMvc.perform(post("/drivers")
                .content(driverDtoJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
    }
}