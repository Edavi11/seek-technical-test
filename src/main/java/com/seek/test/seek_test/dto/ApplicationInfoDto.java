package com.seek.test.seek_test.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "Application information response")
public class ApplicationInfoDto {

    @Schema(description = "Application details", example = "Customer Service API")
    @JsonProperty("app")
    private AppInfo app;

    @Schema(description = "System information")
    @JsonProperty("system")
    private SystemInfo system;

    @Schema(description = "Memory usage information")
    @JsonProperty("memory")
    private MemoryInfo memory;

    @Schema(description = "Application configuration")
    @JsonProperty("application")
    private ApplicationConfig application;

    @Schema(description = "Available endpoints")
    @JsonProperty("endpoints")
    private Map<String, String> endpoints;

    // Constructor privado para builder
    private ApplicationInfoDto() {}

    // Getters
    public AppInfo getApp() { return app; }
    public SystemInfo getSystem() { return system; }
    public MemoryInfo getMemory() { return memory; }
    public ApplicationConfig getApplication() { return application; }
    public Map<String, String> getEndpoints() { return endpoints; }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ApplicationInfoDto dto = new ApplicationInfoDto();

        public Builder app(AppInfo app) {
            dto.app = app;
            return this;
        }

        public Builder system(SystemInfo system) {
            dto.system = system;
            return this;
        }

        public Builder memory(MemoryInfo memory) {
            dto.memory = memory;
            return this;
        }

        public Builder application(ApplicationConfig application) {
            dto.application = application;
            return this;
        }

        public Builder endpoints(Map<String, String> endpoints) {
            dto.endpoints = endpoints;
            return this;
        }

        public ApplicationInfoDto build() {
            return dto;
        }
    }

    // Inner classes
    @Schema(description = "Application details")
    public static class AppInfo {
        @Schema(description = "Application name", example = "Customer Service API")
        private String name;

        @Schema(description = "Application description", example = "Spring Boot REST API for Customer Management")
        private String description;

        @Schema(description = "Application version", example = "1.0.0")
        private String version;

        @Schema(description = "Application author", example = "Erick Avila")
        private String author;

        @Schema(description = "Author email", example = "erickdavila11@gmail.com")
        private String email;

        @Schema(description = "Technology stack", example = "Spring Boot 3.3.13")
        private String technology;

        @Schema(description = "Java version", example = "17")
        private String javaVersion;

        @Schema(description = "Database technology", example = "MySQL/AWS RDS")
        private String database;

        @Schema(description = "Deployment platform", example = "AWS ECS Fargate")
        private String deployment;

        @Schema(description = "Application startup time")
        private LocalDateTime startupTime;

        // Constructor
        public AppInfo() {}

        // Builder
        public static AppInfoBuilder builder() {
            return new AppInfoBuilder();
        }

        // Getters
        public String getName() { return name; }
        public String getDescription() { return description; }
        public String getVersion() { return version; }
        public String getAuthor() { return author; }
        public String getEmail() { return email; }
        public String getTechnology() { return technology; }
        public String getJavaVersion() { return javaVersion; }
        public String getDatabase() { return database; }
        public String getDeployment() { return deployment; }
        public LocalDateTime getStartupTime() { return startupTime; }

        public static class AppInfoBuilder {
            private AppInfo appInfo = new AppInfo();

            public AppInfoBuilder name(String name) {
                appInfo.name = name;
                return this;
            }

            public AppInfoBuilder description(String description) {
                appInfo.description = description;
                return this;
            }

            public AppInfoBuilder version(String version) {
                appInfo.version = version;
                return this;
            }

            public AppInfoBuilder author(String author) {
                appInfo.author = author;
                return this;
            }

            public AppInfoBuilder email(String email) {
                appInfo.email = email;
                return this;
            }

            public AppInfoBuilder technology(String technology) {
                appInfo.technology = technology;
                return this;
            }

            public AppInfoBuilder javaVersion(String javaVersion) {
                appInfo.javaVersion = javaVersion;
                return this;
            }

            public AppInfoBuilder database(String database) {
                appInfo.database = database;
                return this;
            }

            public AppInfoBuilder deployment(String deployment) {
                appInfo.deployment = deployment;
                return this;
            }

            public AppInfoBuilder startupTime(LocalDateTime startupTime) {
                appInfo.startupTime = startupTime;
                return this;
            }

            public AppInfo build() {
                return appInfo;
            }
        }
    }

    @Schema(description = "System information")
    public static class SystemInfo {
        @Schema(description = "Java version")
        private String javaVersion;

        @Schema(description = "Java vendor")
        private String javaVendor;

        @Schema(description = "Operating system name")
        private String osName;

        @Schema(description = "Operating system version")
        private String osVersion;

        @Schema(description = "User timezone")
        private String userTimezone;

        @Schema(description = "User home directory")
        private String userHome;

        @Schema(description = "Current working directory")
        private String userDir;

        // Constructor
        public SystemInfo() {}

        // Builder
        public static SystemInfoBuilder builder() {
            return new SystemInfoBuilder();
        }

        // Getters
        public String getJavaVersion() { return javaVersion; }
        public String getJavaVendor() { return javaVendor; }
        public String getOsName() { return osName; }
        public String getOsVersion() { return osVersion; }
        public String getUserTimezone() { return userTimezone; }
        public String getUserHome() { return userHome; }
        public String getUserDir() { return userDir; }

        public static class SystemInfoBuilder {
            private SystemInfo systemInfo = new SystemInfo();

            public SystemInfoBuilder javaVersion(String javaVersion) {
                systemInfo.javaVersion = javaVersion;
                return this;
            }

            public SystemInfoBuilder javaVendor(String javaVendor) {
                systemInfo.javaVendor = javaVendor;
                return this;
            }

            public SystemInfoBuilder osName(String osName) {
                systemInfo.osName = osName;
                return this;
            }

            public SystemInfoBuilder osVersion(String osVersion) {
                systemInfo.osVersion = osVersion;
                return this;
            }

            public SystemInfoBuilder userTimezone(String userTimezone) {
                systemInfo.userTimezone = userTimezone;
                return this;
            }

            public SystemInfoBuilder userHome(String userHome) {
                systemInfo.userHome = userHome;
                return this;
            }

            public SystemInfoBuilder userDir(String userDir) {
                systemInfo.userDir = userDir;
                return this;
            }

            public SystemInfo build() {
                return systemInfo;
            }
        }
    }

    @Schema(description = "Memory usage information")
    public static class MemoryInfo {
        @Schema(description = "Total memory allocated")
        private String totalMemory;

        @Schema(description = "Free memory available")
        private String freeMemory;

        @Schema(description = "Used memory")
        private String usedMemory;

        @Schema(description = "Maximum memory available")
        private String maxMemory;

        @Schema(description = "Number of available processors")
        private Integer availableProcessors;

        // Constructor
        public MemoryInfo() {}

        // Builder
        public static MemoryInfoBuilder builder() {
            return new MemoryInfoBuilder();
        }

        // Getters
        public String getTotalMemory() { return totalMemory; }
        public String getFreeMemory() { return freeMemory; }
        public String getUsedMemory() { return usedMemory; }
        public String getMaxMemory() { return maxMemory; }
        public Integer getAvailableProcessors() { return availableProcessors; }

        public static class MemoryInfoBuilder {
            private MemoryInfo memoryInfo = new MemoryInfo();

            public MemoryInfoBuilder totalMemory(String totalMemory) {
                memoryInfo.totalMemory = totalMemory;
                return this;
            }

            public MemoryInfoBuilder freeMemory(String freeMemory) {
                memoryInfo.freeMemory = freeMemory;
                return this;
            }

            public MemoryInfoBuilder usedMemory(String usedMemory) {
                memoryInfo.usedMemory = usedMemory;
                return this;
            }

            public MemoryInfoBuilder maxMemory(String maxMemory) {
                memoryInfo.maxMemory = maxMemory;
                return this;
            }

            public MemoryInfoBuilder availableProcessors(Integer availableProcessors) {
                memoryInfo.availableProcessors = availableProcessors;
                return this;
            }

            public MemoryInfo build() {
                return memoryInfo;
            }
        }
    }

    @Schema(description = "Application configuration")
    public static class ApplicationConfig {
        @Schema(description = "Active Spring profiles")
        private String activeProfiles;

        @Schema(description = "Server port")
        private String serverPort;

        @Schema(description = "Application context path")
        private String contextPath;

        @Schema(description = "Current timestamp")
        private LocalDateTime timestamp;

        // Constructor
        public ApplicationConfig() {}

        // Builder
        public static ApplicationConfigBuilder builder() {
            return new ApplicationConfigBuilder();
        }

        // Getters
        public String getActiveProfiles() { return activeProfiles; }
        public String getServerPort() { return serverPort; }
        public String getContextPath() { return contextPath; }
        public LocalDateTime getTimestamp() { return timestamp; }

        public static class ApplicationConfigBuilder {
            private ApplicationConfig config = new ApplicationConfig();

            public ApplicationConfigBuilder activeProfiles(String activeProfiles) {
                config.activeProfiles = activeProfiles;
                return this;
            }

            public ApplicationConfigBuilder serverPort(String serverPort) {
                config.serverPort = serverPort;
                return this;
            }

            public ApplicationConfigBuilder contextPath(String contextPath) {
                config.contextPath = contextPath;
                return this;
            }

            public ApplicationConfigBuilder timestamp(LocalDateTime timestamp) {
                config.timestamp = timestamp;
                return this;
            }

            public ApplicationConfig build() {
                return config;
            }
        }
    }
} 