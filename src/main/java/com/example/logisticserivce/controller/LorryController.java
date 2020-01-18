package com.example.logisticserivce.controller;

import com.example.logisticserivce.business_logic.service.LorryService;
import com.example.logisticserivce.model.dto.LorryDto;
import com.example.logisticserivce.model.entity.Lorry;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/lorries")
public class LorryController {

    public final LorryService lorryService;

    @GetMapping
    public ResponseEntity<Iterable<Lorry>> getLorries() {
        return new ResponseEntity<>(lorryService.getLorryList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lorry> getLorry(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(lorryService.getLorry(id), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Lorry> addLorry(@RequestBody @Valid LorryDto newLorry) {
        return new ResponseEntity<>(lorryService.addLorry(newLorry), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Lorry> modifyLorry(@PathVariable(name = "id") Long id, @RequestBody @Valid LorryDto modifiedLorry) {
        return new ResponseEntity<>(lorryService.modifyLorry(id, modifiedLorry), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteLorry(@PathVariable(name = "id") Long id) {
        lorryService.deleteLorry(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}