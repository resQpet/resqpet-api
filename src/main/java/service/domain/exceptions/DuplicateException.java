package service.domain.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateException extends RestResponseException {
    public DuplicateException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
