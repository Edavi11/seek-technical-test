package com.seek.test.seek_test.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MonitoringService {

    private final MeterRegistry meterRegistry;

    // Counters for different operations
    private final Counter customerCreatedCounter;
    private final Counter customerRetrievedCounter;
    private final Counter metricsRequestedCounter;

    // Timers for performance monitoring
    private final Timer customerCreationTimer;
    private final Timer customerRetrievalTimer;
    private final Timer metricsCalculationTimer;

    public MonitoringService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        
        // Initialize counters
        this.customerCreatedCounter = Counter.builder("customer.operations.created")
                .description("Number of customers created")
                .register(meterRegistry);
        

        
        this.customerRetrievedCounter = Counter.builder("customer.operations.retrieved")
                .description("Number of customers retrieved")
                .register(meterRegistry);
        
        this.metricsRequestedCounter = Counter.builder("customer.metrics.requested")
                .description("Number of metrics requests")
                .register(meterRegistry);

        // Initialize timers
        this.customerCreationTimer = Timer.builder("customer.operations.creation.time")
                .description("Time taken to create a customer")
                .register(meterRegistry);
        
        this.customerRetrievalTimer = Timer.builder("customer.operations.retrieval.time")
                .description("Time taken to retrieve customers")
                .register(meterRegistry);
        
        this.metricsCalculationTimer = Timer.builder("customer.metrics.calculation.time")
                .description("Time taken to calculate metrics")
                .register(meterRegistry);
    }

    /**
     * Records customer creation event
     */
    public void recordCustomerCreated() {
        customerCreatedCounter.increment();
        log.info("Customer creation recorded in metrics");
    }



    /**
     * Records customer retrieval event
     */
    public void recordCustomerRetrieved() {
        customerRetrievedCounter.increment();
        log.info("Customer retrieval recorded in metrics");
    }

    /**
     * Records metrics request event
     */
    public void recordMetricsRequested() {
        metricsRequestedCounter.increment();
        log.info("Metrics request recorded in metrics");
    }

    /**
     * Records execution time for customer creation
     */
    public Timer.Sample startCustomerCreationTimer() {
        return Timer.start(meterRegistry);
    }

    /**
     * Stops customer creation timer and records the time
     */
    public void stopCustomerCreationTimer(Timer.Sample sample) {
        sample.stop(customerCreationTimer);
        log.info("Customer creation time recorded");
    }

    /**
     * Records execution time for customer retrieval
     */
    public Timer.Sample startCustomerRetrievalTimer() {
        return Timer.start(meterRegistry);
    }

    /**
     * Stops customer retrieval timer and records the time
     */
    public void stopCustomerRetrievalTimer(Timer.Sample sample) {
        sample.stop(customerRetrievalTimer);
        log.info("Customer retrieval time recorded");
    }

    /**
     * Records execution time for metrics calculation
     */
    public Timer.Sample startMetricsCalculationTimer() {
        return Timer.start(meterRegistry);
    }

    /**
     * Stops metrics calculation timer and records the time
     */
    public void stopMetricsCalculationTimer(Timer.Sample sample) {
        sample.stop(metricsCalculationTimer);
        log.info("Metrics calculation time recorded");
    }

    /**
     * Records custom business metrics
     */
    public void recordCustomMetric(String metricName, double value) {
        meterRegistry.gauge(metricName, value);
        log.info("Custom metric recorded: {} = {}", metricName, value);
    }

    /**
     * Records error occurrence
     */
    public void recordError(String errorType) {
        Counter.builder("customer.errors")
                .tag("type", errorType)
                .description("Number of errors by type")
                .register(meterRegistry)
                .increment();
        log.warn("Error recorded in metrics: {}", errorType);
    }
} 