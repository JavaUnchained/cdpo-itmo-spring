package com.example.tests.controller;

import com.example.tests.exception.DataNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.tests.dto.DataRequestDTO;
import com.example.tests.dto.DataResponseDTO;
import com.example.tests.exception.DataInvalidException;
import com.example.tests.service.DataService;

import lombok.SneakyThrows;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = DataController.class) // для теста контроллеров
class DataControllerTest {

    @MockBean
    DataService dataService;

    @Autowired
    MockMvc mockMvc;

    @SneakyThrows
    @Test
    void Create_ReturnStatusBadRequest_ServiceThrowsException() {
        DataRequestDTO dataNameNULL = new DataRequestDTO(null);

        Mockito.doThrow(DataInvalidException.class)
                .when(dataService)
                .create(dataNameNULL);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/data")
                        .content("""
                                    {
                                        "name": null
                                    }
                                 """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());
    }

    @SneakyThrows
    @Test
    void Create_Return_StatusOkAndId_ServiceReturnId() {
        DataRequestDTO data = new DataRequestDTO("valid name");

        Mockito.doReturn(1L)
                .when(dataService)
                .create(data);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/data")
                        .content("""
                                    {
                                        "name": "valid name"
                                    }
                                 """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("1"));
    }

    @Test
    void findByName_ReturnStatusBadRequest_ServiceThrowsInvalidException() throws Exception {
        String blank = "      ";
        Mockito.doThrow(DataInvalidException.class).when(dataService).findByName(blank);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/data/name/{dateName}", blank))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void findByName_ReturnStatusNotFound_ServiceThrowsNotFoundException() throws Exception {
        String notExist = "not founded name";
        Mockito.doThrow(DataNotFoundException.class).when(dataService).findByName(notExist);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/data/name/{dateName}", notExist))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void findByName_ReturnStatusOkAndValidDataResponse_ServiceReturnDataResponse() throws Exception {
        String exist = "exist";
        long id = 1L;
        Mockito.doReturn(new DataResponseDTO(id, exist)).when(dataService).findByName(exist);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/data/name/{dateName}", exist))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(exist));
    }
}