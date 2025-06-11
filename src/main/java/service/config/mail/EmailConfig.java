package service.config.mail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Optional;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.emails")
@JsonIncludeProperties(value = {"from", "templates"})
public class EmailConfig {

    private String from;
    private String apiKey;
    private String sender;
    private Map<String, EmailTemplate> templates;

    /**
     * Retrieves the email template associated with the provided template name.
     *
     * @param template the name of the template to retrieve
     * @return an Optional containing the EmailTemplate associated with the provided template name,
     * or an empty Optional if no template is found
     */
    @JsonIgnore
    public Optional<EmailTemplate> getTemplate(String template) {
        return Optional.ofNullable(this.templates.get(template));
    }

    @Data
    public static class EmailTemplate {
        private String subject;
        private String template;
        private String redirectUri;
    }
}
