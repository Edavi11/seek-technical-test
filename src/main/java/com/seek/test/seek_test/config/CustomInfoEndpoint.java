package com.seek.test.seek_test.config;

import com.seek.test.seek_test.dto.ApplicationInfoDto;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "custom-info")
public class CustomInfoEndpoint {

    @ReadOperation
    public ApplicationInfoDto getInfo() {
        // Información básica de la aplicación
        ApplicationInfoDto.AppInfo appInfo = ApplicationInfoDto.AppInfo.builder()
                .name("Customer Service API")
                .description("Spring Boot REST API for Customer Management")
                .version("1.0.0")
                .author("Erick Avila")
                .email("erickdavila11@gmail.com")
                .technology("Spring Boot 3.3.13")
                .javaVersion("17")
                .database("MySQL/AWS RDS")
                .deployment("AWS ECS Fargate")
                .startupTime(LocalDateTime.now())
                .build();
        
        // Información del sistema
        ApplicationInfoDto.SystemInfo systemInfo = ApplicationInfoDto.SystemInfo.builder()
                .javaVersion(System.getProperty("java.version"))
                .javaVendor(System.getProperty("java.vendor"))
                .osName(System.getProperty("os.name"))
                .osVersion(System.getProperty("os.version"))
                .userTimezone(System.getProperty("user.timezone"))
                .userHome(System.getProperty("user.home"))
                .userDir(System.getProperty("user.dir"))
                .build();
        
        // Información de memoria
        Runtime runtime = Runtime.getRuntime();
        ApplicationInfoDto.MemoryInfo memoryInfo = ApplicationInfoDto.MemoryInfo.builder()
                .totalMemory(formatBytes(runtime.totalMemory()))
                .freeMemory(formatBytes(runtime.freeMemory()))
                .usedMemory(formatBytes(runtime.totalMemory() - runtime.freeMemory()))
                .maxMemory(formatBytes(runtime.maxMemory()))
                .availableProcessors(runtime.availableProcessors())
                .build();
        
        // Información de la aplicación
        ApplicationInfoDto.ApplicationConfig applicationConfig = ApplicationInfoDto.ApplicationConfig.builder()
                .activeProfiles(getActiveProfiles())
                .serverPort("8080")
                .contextPath("/")
                .timestamp(LocalDateTime.now())
                .build();
        
        // Información de endpoints disponibles
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("health", "/actuator/health");
        endpoints.put("metrics", "/actuator/metrics");
        endpoints.put("prometheus", "/actuator/prometheus");
        endpoints.put("swagger", "/swagger-ui.html");
        endpoints.put("apiDocs", "/api-docs");
        endpoints.put("customers", "/api/v1/customers");
        endpoints.put("users", "/api/v1/users");
        endpoints.put("auth", "/api/v1/auth/login");
        
        return ApplicationInfoDto.builder()
                .app(appInfo)
                .system(systemInfo)
                .memory(memoryInfo)
                .application(applicationConfig)
                .endpoints(endpoints)
                .build();
    }
    
    private String formatBytes(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp-1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
    }
    
    private String getActiveProfiles() {
        String profiles = System.getProperty("spring.profiles.active");
        if (profiles == null || profiles.isEmpty()) {
            profiles = "default";
        }
        return profiles;
    }
} 