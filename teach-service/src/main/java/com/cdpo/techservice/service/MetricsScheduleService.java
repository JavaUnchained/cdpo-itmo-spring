package com.cdpo.techservice.service;

import com.cdpo.techservice.client.IMetricClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class MetricsScheduleService {
    private final IMetricClient metricClient;
    private final IServiceBookingService serviceBookingService;


    @Async("metric-executor")
    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
//    @Scheduled(fixedRate = 24, timeUnit = TimeUnit.HOURS)
    public void runBySchedule(){
        metricClient.saveCompletedBookings(serviceBookingService.getCompletedBookings());
    }
}
