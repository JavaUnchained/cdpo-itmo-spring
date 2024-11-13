package com.cdpo.techservice.client;

import com.cdpo.techservice.dto.BookingMetricRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@Service
@Profile("rest")
public class MetricRestClient implements IMetricClient{
    private final RestClient metricClient;

    public MetricRestClient() {
        this.metricClient = RestClient.builder().baseUrl("http://localhost:8007/api/v1").build();
    }

    @Override
    public List<Long> saveCompletedBookings(List<BookingMetricRequestDTO> completedBookings) {
        ResponseEntity<List<Long>> entity = metricClient.post()
                .uri("/dwh/metric")
                .contentType(MediaType.APPLICATION_JSON)
                .body(completedBookings)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
        if (isUnsuccessful(entity)) {
            sleep();
            return saveCompletedBookings(completedBookings);
        }
        return entity.getBody();
    }

    private static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    private static boolean isUnsuccessful(ResponseEntity<List<Long>> entity) {
        return !entity.getStatusCode().is2xxSuccessful();
    }
}
