package service.services.notification.email;

import service.config.mail.EmailConfig;

import java.util.Map;

public interface EmailService {

    EmailConfig.EmailTemplate get(Template template);

    Boolean send(EmailConfig.EmailTemplate template, String recipient, Map<String, String> params);

}
