package com.example.tests.client;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.example.tests.dto.ClientResponseDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClientService {
    private final RestClient restClient;

    public ResponseEntity<ClientResponseDTO> getByUuid(String uuid){
        return restClient.get()
                .uri("http://localhost:8081/api/clientdata/{uuid}", uuid)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(ClientResponseDTO.class);

    }
}
