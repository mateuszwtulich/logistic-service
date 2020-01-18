package com.example.logisticserivce.controller;

import com.example.logisticserivce.business_logic.service.LoadingService;
import com.example.logisticserivce.model.dto.LoadingDto;
import com.example.logisticserivce.model.entity.Loading;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/loadings")
public class LoadingController {

    public final LoadingService loadingService;

    @GetMapping
    public ResponseEntity<Iterable<Loading>> getLoadings() {
        return new ResponseEntity<>(loadingService.getLoadingList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Loading> getLoading(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(loadingService.getLoading(id), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Loading> addLoading(@RequestBody @Valid LoadingDto newLoading) {
        return new ResponseEntity<>(loadingService.addLoading(newLoading), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Loading> modifyLoading(@PathVariable(name = "id") Long id, @RequestBody @Valid LoadingDto modifiedLoading) {
        return new ResponseEntity<>(loadingService.modifyLoading(id, modifiedLoading), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteLoading(@PathVariable(name = "id") Long id) {
        loadingService.deleteLoading(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}