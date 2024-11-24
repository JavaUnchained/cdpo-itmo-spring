package com.example.tests.client;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;

import com.example.tests.dto.ClientResponseDTO;

import static org.junit.jupiter.api.Assertions.*;

@Import(ResClientTestConfig.class)
@RestClientTest(ClientService.class) // для тестирования RestClient сервисов
class ClientServiceTest {

    @Autowired
    ClientService clientService; // тестируемый класс


    @Autowired
    MockRestServiceServer restServiceServer; // мок сервера, к которому идет обращение

    @AfterEach
    void setUp() {
        restServiceServer.verify();
    }

    @Test
    void getByUuid() {
        restServiceServer.expect(ExpectedCount.once(),
                MockRestRequestMatchers // строка запроса
                        .requestTo("http://localhost:8081/api/clientdata/123"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET)) // http метод
                .andRespond(MockRestResponseCreators // желаемый ответ
                        .withSuccess(""" 
                                {
                                   "uuid": "123",
                                   "name": "valid"
                                }
                                """, MediaType.APPLICATION_JSON)
                      );

        ResponseEntity<ClientResponseDTO> response = clientService.getByUuid("123");
        // проверка
        assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(200));
        assertEquals(response.getBody(), new ClientResponseDTO("123", "valid"));
    }
}