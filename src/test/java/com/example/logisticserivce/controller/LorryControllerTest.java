package com.example.logisticserivce.controller;
import com.example.logisticserivce.business_logic.exception.ResourceAlreadyExistsException;
import com.example.logisticserivce.business_logic.service.LorryService;
import com.example.logisticserivce.model.dto.LorryDto;
import com.example.logisticserivce.model.entity.Driver;
import com.example.logisticserivce.model.entity.Lorry;
import com.example.logisticserivce.model.enumerator.DriverStatus;
import com.example.logisticserivce.model.enumerator.LorryStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.Test;

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
@WebMvcTest(LorryController.class)
public class LorryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LorryService lorryService;
    private List<Lorry> lorryList;
    private List<LorryDto> lorryDtos;

    @Before
    public void prepare() throws Exception {
        LorryDto first = new LorryDto();
        first.setLicenceNumber("SCZ2").setModel("MAN").setStatus(LorryStatus.AVAILABLE);

        LorryDto second = new LorryDto();
        first.setLicenceNumber("SC98").setModel("MAN").setStatus(LorryStatus.AVAILABLE);

        lorryDtos = new ArrayList<>();
        lorryDtos.add(first);
        lorryDtos.add(second);

        Driver driver = new Driver();
        driver.setId(1L).setName("Marek").setSurname("John").setPhoneNumber("11111111").setLogin("john")
                .setPassword("xyz").setStatus(DriverStatus.AVAILABLE);

        Lorry firstLorry = new Lorry();
        firstLorry.setId(1L).setLicenceNumber("SCZ2").setModel("MAN").setStatus(LorryStatus.AVAILABLE)
                .setDriver(driver);
        Lorry secondLorry = new Lorry();
        secondLorry.setId(2L).setLicenceNumber("SC98").setModel("MAN").setStatus(LorryStatus.AVAILABLE)
                .setDriver(driver);

        lorryList = new ArrayList<>();
        lorryList.add(firstLorry);
        lorryList.add(secondLorry);
    }

    @Test
    public void getLorriesShouldReturnLorries() throws Exception {
        final String expectedJson = "[{\"model\":\"MAN\",\"licenceNumber\":\"SCZ2\",\"id\":1,\"status\":\"AVAILABLE\"}," +
                "{\"model\":\"MAN\",\"licenceNumber\":\"SC98\",\"id\":2,\"status\":\"AVAILABLE\"}]";

        doAnswer(invocation -> lorryList).when(lorryService).getLorryList();
        this.mockMvc.perform(get("/lorries")).andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expectedJson));
    }


    @Test
    public void getLorryShouldReturnLorry() throws Exception  {
        final Long lorryId = 1L;
        final String expectedJson = "{\"model\":\"MAN\",\"licenceNumber\":\"SCZ2\",\"id\":1,\"status\":\"AVAILABLE\"}";

        doAnswer(invocation -> lorryList.stream().filter(l -> l.getId().equals(lorryId)).findFirst().orElseThrow(() -> new Exception())
        ).when(lorryService).getLorry(lorryId);

        this.mockMvc.perform(get("/lorries/{lorryId}", lorryId)).andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void addLorryShouldCreateNewLorry() throws Exception {
        final String expectedJson = "{\"id\": 3,\"licenceNumber\":\"SC\",\"model\": \"MAN\", \"status\":\"AVAILABLE\"}";
        final LorryDto thirdLorryDto = new LorryDto().setLicenceNumber("SC").setModel("MAN").setStatus(LorryStatus.AVAILABLE);
        final ObjectMapper mapper = new ObjectMapper();
        final String lineDtoJson = mapper.writeValueAsString(thirdLorryDto);

        doAnswer(invocation -> {
                    Lorry thirdLorry = new Lorry().setId(3L).setLicenceNumber("SC").setModel("MAN").setStatus(LorryStatus.AVAILABLE);
                    lorryList.add(thirdLorry);
                    return thirdLorry;
                }
        ).when(lorryService).addLorry(thirdLorryDto);
        this.mockMvc.perform(post("/lorries")
                .content(lineDtoJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful()).andExpect(content().json(expectedJson));
    }

    @Test
    public void addLorryShouldReturnAlreadyExists() throws Exception {
        final LorryDto thirdLorryDto = new LorryDto().setLicenceNumber("SCZ2").setModel("MAN").setStatus(LorryStatus.AVAILABLE);
        final ObjectMapper mapper = new ObjectMapper();
        final String lineDtoJson = mapper.writeValueAsString(thirdLorryDto);

        doAnswer(invocation -> {
            throw new ResourceAlreadyExistsException();
        }).when(lorryService).addLorry(thirdLorryDto);
        this.mockMvc.perform(post("/lines")
                .content(lineDtoJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
    }
}
