package com.example.logisticserivce.controller;

import com.example.logisticserivce.business_logic.service.CargoService;
import com.example.logisticserivce.model.dto.CargoDto;
import com.example.logisticserivce.model.entity.Cargo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/cargos")
public class CargoController {

    private final CargoService cargoService;

    @GetMapping
    public ResponseEntity<Iterable<Cargo>> getCargos() {
        return new ResponseEntity<>(cargoService.getCargoList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cargo> getCargo(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(cargoService.getCargo(id), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cargo> addCargo(@RequestBody @Valid CargoDto newCargo) {
        return new ResponseEntity<>(cargoService.addCargo(newCargo), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cargo> modifyCargo(@PathVariable(name = "id") Long id, @RequestBody @Valid CargoDto modifiedCargo) {
        return new ResponseEntity<>(cargoService.modifyCargo(id, modifiedCargo), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCargo(@PathVariable(name = "id") Long id) {
        cargoService.deleteCargo(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
