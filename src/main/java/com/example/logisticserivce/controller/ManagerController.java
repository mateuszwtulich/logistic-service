package com.example.logisticserivce.controller;

import com.example.logisticserivce.business_logic.service.ManagerService;
import com.example.logisticserivce.model.dto.ManagerDto;
import com.example.logisticserivce.model.entity.Manager;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/managers")
public class ManagerController {

    public final ManagerService managerService;

    @GetMapping
    public ResponseEntity<Iterable<Manager>> getManagers() {
        return new ResponseEntity<>(managerService.getManagerList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manager> getManager(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(managerService.getManager(id), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Manager> addManager(@RequestBody @Valid ManagerDto newManager) {
        return new ResponseEntity<>(managerService.addManager(newManager), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Manager> modifyManager(@PathVariable(name = "id") Long id, @RequestBody @Valid ManagerDto modifiedManager) {
        return new ResponseEntity<>(managerService.modifyManager(id, modifiedManager), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable(name = "id") Long id) {
        managerService.deleteManager(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
