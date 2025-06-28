package service.config.http.ia;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "ia.api")
public class IAApiProperties {

    private String key;
    private String url;

}
