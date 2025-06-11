package service.domain.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientResponseException;
import service.domain.exceptions.model.ErrorResponse;

import java.nio.charset.StandardCharsets;

@Getter
@EqualsAndHashCode(callSuper = true)
public abstract class RestResponseException extends RestClientResponseException {

    private final HttpStatus status;

    RestResponseException(HttpStatus status, String message) {
        super(message, status, message, null, new byte[0], StandardCharsets.UTF_8);
        this.status = status;
    }

    public ErrorResponse asError() {
        return ErrorResponse.of(super.getMessage(), this.status);
    }

}