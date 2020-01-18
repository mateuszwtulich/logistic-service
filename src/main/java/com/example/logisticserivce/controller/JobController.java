package com.example.logisticserivce.controller;

import com.example.logisticserivce.business_logic.service.JobService;
import com.example.logisticserivce.model.dto.JobDto;
import com.example.logisticserivce.model.entity.Job;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/jobs")
public class JobController {

    public final JobService jobService;

    @GetMapping
    public ResponseEntity<Iterable<Job>> getJobs() {
        return new ResponseEntity<>(jobService.getJobList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJob(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(jobService.getJob(id), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Job> addJob(@RequestBody @Valid JobDto newJob) {
        return new ResponseEntity<>(jobService.addJob(newJob), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Job> modifyJob(@PathVariable(name = "id") Long id, @RequestBody @Valid JobDto modifiedJob) {
        return new ResponseEntity<>(jobService.modifyJob(id, modifiedJob), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable(name = "id") Long id) {
        jobService.deleteJob(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
