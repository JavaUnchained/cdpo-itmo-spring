package com.cdpo.techservice.service;

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

    @Async("metric-executor")
    @Scheduled(fixedRate = 24, timeUnit = TimeUnit.HOURS)
    public void runBySchedule(){

    }
}
