package com.seek.test.seek_test.config;

import io.micrometer.cloudwatch2.CloudWatchConfig;
import io.micrometer.cloudwatch2.CloudWatchMeterRegistry;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatch.CloudWatchAsyncClient;

import java.time.Duration;

@Configuration
public class MonitoringConfig {

    @Value("${aws.cloudwatch.namespace:CustomerManagementService}")
    private String cloudWatchNamespace;

    @Value("${aws.region:us-east-1}")
    private String awsRegion;

    /**
     * CloudWatch client for metrics and monitoring
     */
    @Bean
    @Profile("prod")
    public CloudWatchAsyncClient cloudWatchAsyncClient() {
        return CloudWatchAsyncClient.builder()
                .region(Region.of(awsRegion))
                .build();
    }

    /**
     * CloudWatch configuration for metrics
     */
    @Bean
    @Profile("prod")
    public CloudWatchConfig cloudWatchConfig() {
        return new CloudWatchConfig() {
            @Override
            public String get(String key) {
                return null;
            }

            @Override
            public String namespace() {
                return cloudWatchNamespace;
            }

            @Override
            public Duration step() {
                return Duration.ofMinutes(1);
            }
        };
    }

    /**
     * CloudWatch meter registry for sending metrics to AWS
     */
    @Bean
    @Profile("prod")
    public MeterRegistry cloudWatchMeterRegistry(CloudWatchConfig config, CloudWatchAsyncClient client) {
        return new CloudWatchMeterRegistry(config, Clock.SYSTEM, client);
    }
} 