package com.cdpo.techservice.client;

import feign.RetryableException;
import feign.Retryer;

public class MetricFeignRetries implements Retryer {
    private final long period;

    public MetricFeignRetries() {
        this(1000);
    }

    public MetricFeignRetries(long period) {
        this.period = period;
    }

    @Override
    public void continueOrPropagate(RetryableException e) {
        try {
            Thread.sleep(period);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            throw e;
        }
    }

    @Override
    public Retryer clone() {
        return new MetricFeignRetries(period);
    }
}
