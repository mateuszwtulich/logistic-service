package com.example.logisticserivce.controller;

import com.example.logisticserivce.business_logic.service.UnloadingService;
import com.example.logisticserivce.model.dto.UnloadingDto;
import com.example.logisticserivce.model.entity.Unloading;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/unloadings")
public class UnloadingController {

    public final UnloadingService unloadingService;

    @GetMapping
    public ResponseEntity<Iterable<Unloading>> getUnloadings() {
        return new ResponseEntity<>(unloadingService.getUnloadingList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Unloading> getUnloading(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(unloadingService.getUnloading(id), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Unloading> addUnloading(@RequestBody @Valid UnloadingDto newUnloading) {
        return new ResponseEntity<>(unloadingService.addUnloading(newUnloading), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Unloading> modifyUnloading(@PathVariable(name = "id") Long id, @RequestBody @Valid UnloadingDto modifiedUnloading) {
        return new ResponseEntity<>(unloadingService.modifyUnloading(id, modifiedUnloading), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUnloading(@PathVariable(name = "id") Long id) {
        unloadingService.deleteUnloading(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}