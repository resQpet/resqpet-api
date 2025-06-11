package service.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import service.domain.entity.user.User;
import service.domain.exceptions.InternalErrorException;
import service.domain.exceptions.NotFoundException;
import service.domain.exceptions.RestResponseException;
import service.domain.repository.user.UserRepository;
import service.utils.TokenUtils;
import software.amazon.awssdk.awscore.exception.AwsServiceException;

import java.lang.reflect.Parameter;

@Slf4j
@Aspect
@Component
@AllArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class CurrentAspect {

    private final JwtDecoder decoder;
    private final UserRepository userRepository;
    private final HttpServletRequest servletRequest;

    @Around("execution(public * *(.., @service.aspect.Current (*), ..))")
    public Object interceptMethodsWithAnnotatedParameters(ProceedingJoinPoint point) {
        try {
            int index = -1;
            MethodSignature signature = (MethodSignature) point.getSignature();
            String methodName = signature.getMethod().getName();
            final Parameter[] parameters = point.getTarget().getClass().getMethod(methodName, signature.getMethod().getParameterTypes()).getParameters();
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i].isAnnotationPresent(Current.class)) {
                    index = i;
                    break;
                }
            }
            Object[] args = point.getArgs();
            args[index] = this.getUserFromAuthorizationHeader();
            return point.proceed(args);
        } catch (RestResponseException | AwsServiceException e) {
            throw e;
        } catch (Throwable e) {
            final String message = "Error Interno: " + e.getMessage();
            log.error(message, e);
            throw new InternalErrorException(message);
        }
    }

    public User getUserFromAuthorizationHeader() {
        final String token = TokenUtils.getToken(this.servletRequest.getHeader(HttpHeaders.AUTHORIZATION));
        return this.getUserFromToken(token);
    }


    public User getUserFromToken(String token) {
        // Decodifica el JWT y extrae el subject
        final Jwt decoded = decoder.decode(token);
        final String subject = decoded.getSubject();
        final Long userId = Long.valueOf(subject);
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("No user found: " + subject));
    }
}