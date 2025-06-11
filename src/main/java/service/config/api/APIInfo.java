package service.config.api;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@JsonIncludeProperties(value = {"name", "version"})
@ConfigurationProperties(prefix = "spring.application")
public class APIInfo {

    /**
     * Represents the application name.
     */
    private String name;

    /**
     * Represents the application version.
     */
    private String version;

}
