package service.services.notification.email;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import service.config.mail.EmailConfig;
import service.domain.exceptions.InternalErrorException;
import service.utils.Dates;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class DefaultEmailService implements EmailService {

    private final EmailConfig config;
    private static final String ENDPOINT = "mail/send";

    /**
     * Retrieves the specified EmailTemplate using the provided Template object.
     *
     * @param template The Template object specifying the template to retrieve.
     * @return The EmailTemplate associated with the provided Template object.
     */
    @Override
    public EmailConfig.EmailTemplate get(Template template) {
        return config.getTemplate(template.getTemplate())
                .orElseThrow(() -> new InternalErrorException("Template not configured: " + template));
    }

    /**
     * Sends an email with the provided template, recipient, and parameters.
     *
     * @param template  the Template enum value indicating the type of email template to use
     * @param recipient the email address of the recipient
     * @param params    a map containing dynamic data to be included in the email template
     * @return true if the email was sent successfully, false otherwise
     */
    @Override
    public Boolean send(EmailConfig.EmailTemplate template, String recipient, Map<String, String> params) {
        try {
            // Emails
            final Email from = new Email(config.getFrom(), config.getSender()), to = new Email(recipient);
            // Loads template info
            final String subject = template.getSubject();
            // Creates Mail Object and sets values
            final Mail mail = new Mail();
            mail.setFrom(from);
            mail.setSubject(subject);
            mail.setTemplateId(template.getTemplate());
            // Generates email personalization with params and destination.
            final Personalization personalization = this.getPersonalization(params, to);
            // Instances sendgrid with API Key
            return this.doSend(mail, personalization);
        } catch (Throwable exception) {
            final String message = "Problemas enviando correo: Template: {}, To: {}, Params: {}.";
            log.error(message, template, recipient, params, exception);
            return Boolean.FALSE;
        }
    }

    /**
     * Generates a Personalization object for an email with the provided parameters.
     *
     * @param params A map containing dynamic template data to be filled into the email.
     * @param to     The recipient email address for the personalization.
     * @return A Personalization object with the provided recipient, subject, and dynamic template data.
     */
    private Personalization getPersonalization(Map<String, String> params, Email to) {
        // Registers mail personalization
        final Personalization personalization = new Personalization();
        personalization.addTo(to);
        // Add dynamic template data
        for (Map.Entry<String, String> entry : params.entrySet()) {
            personalization.addDynamicTemplateData(entry.getKey(), entry.getValue());
        }
        personalization.addDynamicTemplateData(EmailParams.YEAR, Dates.currentYear());
        return personalization;
    }

    /**
     * Sends an email using the provided Mail and Personalization objects.
     *
     * @param mail            the Mail object containing email information
     * @param personalization the Personalization object containing recipient information and dynamic template data
     * @return true if the email was sent successfully, false otherwise
     * @throws IOException if an I/O error occurs during the email sending process
     */
    private boolean doSend(Mail mail, Personalization personalization) throws IOException {
        mail.addPersonalization(personalization);
        final SendGrid sendGrid = new SendGrid(config.getApiKey());
        final Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint(ENDPOINT);
        final String built = mail.build();
        request.setBody(built);
        log.info("Sending: {}", built);
        final Response response = sendGrid.api(request);
        final HttpStatus status = HttpStatus.valueOf(response.getStatusCode());
        return status.is2xxSuccessful();
    }
}
