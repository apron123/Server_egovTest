package egovframework.cbm.config.database;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Data
@Valid
@ConfigurationProperties(prefix = "db")
@Configuration
public class DatabaseProperties {

    private Base base;

    @Data
    public static class Base{
        @NotBlank(message = "driver for BaseDB not found.")
        private String driver;
        @NotBlank(message = "dialect for BaseDB not found.")
        private String dialect;
        @NotBlank(message = "url for BaseDB not found.")
        private String url;
        @NotBlank(message = "username for BaseDB not found.")
        private String username;
        @NotBlank(message = "password for BaseDB not found.")
        private String password;
        private String schema = "";
        private String showSql = "false";
        private String ddlAuto = "none";
        private String maxPoolSize = "5";
        private String formatSql = "true";
        private String useSecondLevelCache = "true";
        private String useQueryCache = "true";
        private String cacheRegionFactory = "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory";
    }

}
