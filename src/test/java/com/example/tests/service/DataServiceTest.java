package com.example.tests.service;

import com.example.tests.dto.DataResponseDTO;
import com.example.tests.exception.DataNotFoundException;
import org.hibernate.exception.DataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.tests.dto.DataRequestDTO;
import com.example.tests.entity.Data;
import com.example.tests.exception.DataInvalidException;
import com.example.tests.repository.DataRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest в случае необходимости использовать контекст спринг, позволяет использовать моки без ExtendWith
@ExtendWith(MockitoExtension.class) // при необходимости использовать моки без контекста спринг
class DataServiceTest {

    // Тестируемы класс
    @InjectMocks // зависимости - моки, перечисленные ниже
    DataService dataService;

    @Mock // мок, необходимый DataService классу
    DataRepository dataRepository;


    @BeforeEach
    void setUp() {
    }

    @Test
    void Create_ThrowsDataInvalidException_InvalidDataRequestDTO() {
        // dataRequestDTO == null ||
        // dataRequestDTO.name() == null ||
        // dataRequestDTO.name().isEmpty() ||
        // dataRequestDTO.name().isBlank()

        DataRequestDTO dataNULL = null;
        DataRequestDTO dataNameNULL = new DataRequestDTO(null);
        DataRequestDTO dataNameEmpty = new DataRequestDTO("");
        DataRequestDTO dataNameBlank = new DataRequestDTO(" ");

        assertAll("invalid data",
                () -> assertThrows(DataInvalidException.class, ()-> dataService.create(dataNULL)),
                () -> assertThrows(DataInvalidException.class, ()-> dataService.create(dataNameNULL)),
                () -> assertThrows(DataInvalidException.class, ()-> dataService.create(dataNameEmpty)),
                () -> assertThrows(DataInvalidException.class, ()-> dataService.create(dataNameBlank))
        );
    }

    @Test
    void Create_ReturnId_ValidDataRequestDTO() {

        DataRequestDTO validData = new DataRequestDTO("valid name");

        Data entity = new Data();
        entity.setId(1);
        entity.setName("valid name");

        Mockito.when(dataRepository.save(entity)).thenReturn(entity);

        long id = dataService.create(validData);

        assertEquals(1, id);
    }

    @Test
    void FindByName_ThrowsDataInvalidException_InvalidName() {
        assertAll("invalid data",
                () -> assertThrows(DataInvalidException.class, ()-> dataService.findByName(null)),
                () -> assertThrows(DataInvalidException.class, ()-> dataService.findByName("")),
                () -> assertThrows(DataInvalidException.class, ()-> dataService.findByName("  ")));
    }

    @Test
    void FindByName_ThrowsDataNotFoundException_DataNotFoundByName() {
        String noExistName = "no exist name";
        Mockito.when(dataRepository.findByName(noExistName)).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> dataService.findByName(noExistName));
    }

    @Test
    void FindByName_ReturnData_ValidAndExistsName() {
        Data entity = new Data();
        entity.setId(1);
        entity.setName("valid name");

        Mockito.when(dataRepository.findByName(entity.getName())).thenReturn(Optional.of(entity));

        DataResponseDTO result = dataService.findByName(entity.getName());

        assertEquals(entity.getId(), result.id());
        assertEquals(entity.getName(), result.name());
    }
}