package com.cdpo.techservice.client;

import com.cdpo.techservice.dto.BookingMetricRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Logger;
import feign.RetryableException;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Profile("feign")
@FeignClient(value = "metric-service", url = "http://localhost:8007/api/v1/dwh/metric", configuration = MetricFeignClient.Config.class)
public interface MetricFeignClient extends IMetricClient {
    @Override
    @PostMapping
    List<Long> saveCompletedBookings(List<BookingMetricRequestDTO> completedBookings);

    @Configuration
    class Config {
        @Bean
        public Retryer retryer() {
            return new MetricFeignRetries();
        }

        @Bean
        public ErrorDecoder errorDecoder() {
            return (s, response) -> {
                if (response.body() != null) {
                    try {
                        String listAsString = IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8);
                        List<?> responseBody = new ObjectMapper().readValue(listAsString, List.class);
                        if(responseBody == null || responseBody.isEmpty()) {
                            return new RetryableException(response.status(), "Empty array response", response.request().httpMethod(), null, 1L, response.request());
                        }
                    } catch (IOException ignored) {}
                }
                return new ErrorDecoder.Default().decode(s, response);
            };
        }

        @Bean
        Logger.Level feignLoggerLevel() {
            return Logger.Level.FULL;
        }
    }
}
