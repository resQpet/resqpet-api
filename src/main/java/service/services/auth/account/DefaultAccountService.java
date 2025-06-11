package service.services.auth.account;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.config.mail.EmailConfig;
import service.domain.entity.auth.AccountRecovery;
import service.domain.entity.user.User;
import service.domain.exceptions.ForbiddenException;
import service.domain.exceptions.InternalErrorException;
import service.domain.models.account.PasswordRecoverRequest;
import service.domain.repository.auth.AccountRecoveryRepository;
import service.services.notification.email.EmailParams;
import service.services.notification.email.EmailService;
import service.services.notification.email.Template;
import service.services.user.UserService;
import service.utils.APIUtils;
import service.utils.Dates;
import service.utils.RequestUtils;

import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class DefaultAccountService implements AccountService {

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final EmailService emailService;
    private final HttpServletRequest request;
    private final AccountRecoveryRepository recoveryRepository;

    /**
     * Sends an account recovery request for the specified user login.
     *
     * @param login The login (username) of the user for whom the account recovery request is being initiated.
     */
    @Override
    @Transactional
    public void sendAccountRecover(String login) {
        // Loads the user from DB
        final User user = this.userService.findByLogin(login);
        // Get remote variables
        final RequestUtils requestUtils = RequestUtils.of(request);
        final String userAgent = requestUtils.getUserAgent();
        final String remoteAddress = requestUtils.getRemoteAddress();
        // Creates the recovery object with expiring in 5 minutes
        final AccountRecovery recovery = AccountRecovery.builder().user(user)
                .agent(userAgent)
                .remoteIp(remoteAddress)
                .consumed(Boolean.FALSE)
                .expiresAt(Dates.addMinutes(5))
                .token(APIUtils.genID())
                .build();
        this.doSendRecoverEmail(recovery);
        // Saves the recovery object into DB
        this.recoveryRepository.save(recovery);
    }

    /**
     * Checks if the recovery token is valid.
     *
     * @param token the recovery token to be checked for validity
     * @return true if the token is valid, false otherwise
     */
    @Override
    public boolean isRecoverTokenValid(String token) {
        return this.recoveryRepository.isTokenValid(token);
    }

    /**
     * Recovers password for a user based on the provided PasswordRecoverRequest.
     *
     * @param request the PasswordRecoverRequest object containing the necessary information for password recovery
     */
    @Override
    @Transactional
    public void recover(PasswordRecoverRequest request) {
        if (!request.isValid()) {
            throw new ForbiddenException("Las contraseñas deben de coincidir.");
        } else {
            this.recoveryRepository.findByTokenValid(request.token()).ifPresentOrElse(recovery -> {
                // Marca el token como consumido.
                recovery.setConsumed(Boolean.TRUE);
                // Asigna nueva contraseña a usuario.
                this.assignUserPassword(request, recovery);
                // Performs recovery update
                this.recoveryRepository.save(recovery);
            }, () -> {
                throw new ForbiddenException("El token ingresado no es válido.");
            });
        }
    }

    /**
     * Assigns a new password to the user associated with the recovery process.
     *
     * @param request  the PasswordRecoverRequest object containing the new password
     * @param recovery the AccountRecovery object for the user to be updated
     */
    private void assignUserPassword(PasswordRecoverRequest request, AccountRecovery recovery) {
        final User user = recovery.getUser();
        user.setPassword(encoder.encode(request.password()));
        this.userService.save(user);
    }

    /**
     * Sends an account recovery email to the specified user.
     *
     * @param recovery The AccountRecovery object containing user information and recovery details.
     */
    private void doSendRecoverEmail(AccountRecovery recovery) {
        final User user = recovery.getUser();
        log.info("Sending account recovery to {}.", user);
        final EmailConfig.EmailTemplate template = this.emailService.get(Template.ACCOUNT_RECOVER);
        final String uri = template.getRedirectUri() + recovery.getToken();
        final Map<String, String> params = Map.of(
                EmailParams.NAME, user.getName(),
                EmailParams.SUBJECT, template.getSubject(),
                EmailParams.URI, uri
        );
        final Boolean result = this.emailService.send(template, user.getEmail(), params);
        if (result) {
            log.info("Successful Account recovery for: {}.", user);
        } else {
            log.error("Failed Account recovery for: {}.", user);
            throw new InternalErrorException("Problemas enviando correo electrónico.");
        }
    }
}
