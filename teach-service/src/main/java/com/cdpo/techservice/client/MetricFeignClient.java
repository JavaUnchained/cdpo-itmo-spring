package com.cdpo.techservice.client;

import com.cdpo.techservice.dto.BookingMetricRequestDTO;
import feign.Logger;
import feign.Retryer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;

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
        Logger.Level feignLoggerLevel() {
            return Logger.Level.FULL;
        }
    }
}
