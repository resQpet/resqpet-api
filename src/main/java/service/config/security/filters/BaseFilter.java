package service.config.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import service.domain.exceptions.InternalErrorException;
import service.domain.exceptions.RestResponseException;
import service.domain.exceptions.model.ErrorResponse;

import java.nio.charset.StandardCharsets;

public abstract class BaseFilter extends OncePerRequestFilter {

    /**
     * Handles the generation of an error response to be sent back to the client.
     *
     * @param mapper    the object mapper used to serialize the error response
     * @param response  the HttpServletResponse object to set the error response on
     * @param e         the Throwable exception that triggered the error response
     */
    void handleError(ObjectMapper mapper, HttpServletResponse response, Throwable e) {
        try {
            final HttpStatus status = e instanceof RestResponseException ? ((RestResponseException) e).getStatus() : HttpStatus.BAD_REQUEST;
            // Creates an error response object
            final ErrorResponse error = ErrorResponse.of(status, e.getMessage());
            // Sends error response
            response.setStatus(status.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().write(mapper.writeValueAsString(error));
            response.getWriter().flush();
        } catch (Throwable ex) {
            throw new InternalErrorException(String.format("Problemas con respuesta: %s.", e.getMessage()));
        }
    }

}
