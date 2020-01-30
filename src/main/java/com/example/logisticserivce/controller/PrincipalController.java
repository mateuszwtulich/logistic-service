package com.example.logisticserivce.controller;

import com.example.logisticserivce.business_logic.service.PrincipalService;
import com.example.logisticserivce.model.dto.PrincipalDto;
import com.example.logisticserivce.model.entity.Cargo;
import com.example.logisticserivce.model.entity.Loading;
import com.example.logisticserivce.model.entity.Principal;
import com.example.logisticserivce.model.entity.Unloading;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/principals")
public class PrincipalController {

    public final PrincipalService principalService;

    @GetMapping
    public ResponseEntity<Iterable<Principal>> getPrincipals() {
        return new ResponseEntity<>(principalService.getPrincipalList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Principal> getPrincipal(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(principalService.getPrincipal(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/loadings")
    public ResponseEntity<Iterable<Loading>> getLoadings(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(principalService.getLoadingList(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/cargos")
    public ResponseEntity<Iterable<Cargo>> getCargos(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(principalService.getCargoList(id), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Principal> addPrincipal(@RequestBody @Valid PrincipalDto newPrincipal) {
        return new ResponseEntity<>(principalService.addPrincipal(newPrincipal), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Principal> modifyPrincipal(@PathVariable(name = "id") Long id, @RequestBody @Valid PrincipalDto modifiedPrincipal) {
        return new ResponseEntity<>(principalService.modifyPrincipal(id, modifiedPrincipal), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePrincipal(@PathVariable(name = "id") Long id) {
        principalService.deletePrincipal(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}