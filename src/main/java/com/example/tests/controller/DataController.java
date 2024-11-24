package com.example.tests.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.tests.dto.DataRequestDTO;
import com.example.tests.dto.DataResponseDTO;
import com.example.tests.exception.DataInvalidException;
import com.example.tests.exception.DataNotFoundException;
import com.example.tests.service.DataService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/data")
public class DataController {
    private final DataService dataService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody DataRequestDTO dataRequestDTO){
        try {
            return new ResponseEntity<>(dataService.create(dataRequestDTO), HttpStatus.OK);
        } catch (DataInvalidException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/name/{dateName}")
    public ResponseEntity<DataResponseDTO> findByName(@PathVariable String dateName){
        try {
            return new ResponseEntity<>(dataService.findByName(dateName), HttpStatus.OK);
        } catch (DataInvalidException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }  catch (DataNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
