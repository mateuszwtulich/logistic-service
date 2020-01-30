package com.example.logisticserivce.controller;

import com.example.logisticserivce.business_logic.service.DriverService;
import com.example.logisticserivce.model.dto.DriverDto;
import com.example.logisticserivce.model.dto.JobDto;
import com.example.logisticserivce.model.dto.JobDtoOutput;
import com.example.logisticserivce.model.entity.Driver;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/drivers")
public class DriverController {

    public final DriverService driverService;

    @GetMapping
    public ResponseEntity<Iterable<Driver>> getDrivers() {
        return new ResponseEntity<>(driverService.getDriverList(), HttpStatus.OK);
    }

    @GetMapping("/{id}/jobs")
    public ResponseEntity<Iterable<JobDtoOutput>> getDriverJobs(@PathVariable(name = "id") Long id){
        return new ResponseEntity<>(driverService.getJobList(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriver(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(driverService.getDriver(id), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Driver> addDriver(@RequestBody @Valid DriverDto newDriver) {
        return new ResponseEntity<>(driverService.addDriver(newDriver), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Driver> modifyDriver(@PathVariable(name = "id") Long id, @RequestBody @Valid DriverDto modifiedDriver) {
        return new ResponseEntity<>(driverService.modifyDriver(id, modifiedDriver), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable(name = "id") Long id) {
        driverService.deleteDriver(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
