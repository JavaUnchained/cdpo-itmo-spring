package com.example.tests.service;

import com.example.tests.dto.DataRequestDTO;
import com.example.tests.dto.DataResponseDTO;
import com.example.tests.entity.Data;
import com.example.tests.exception.DataInvalidException;
import com.example.tests.exception.DataNotFoundException;
import com.example.tests.repository.DataRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DataService {
    private final DataRepository dataRepository;

    public long create(DataRequestDTO dataRequestDTO){
        if (dataRequestDTO == null || dataRequestDTO.name() == null || dataRequestDTO.name().isEmpty() || dataRequestDTO.name().isBlank()){
            throw new DataInvalidException("data name is not valid");
        }
        Data data = new Data();
        data.setName(dataRequestDTO.name());
        return dataRepository.save(data).getId();
    }

    public DataResponseDTO findByName(String name){
        if (name == null || name.isEmpty() || name.isBlank()){
            throw new DataInvalidException("name is not valid");
        }

        Data data = dataRepository.findByName(name)
                .orElseThrow(()->new DataNotFoundException("data not found"));

        return new DataResponseDTO(data.getId(),data.getName());
    }
}
