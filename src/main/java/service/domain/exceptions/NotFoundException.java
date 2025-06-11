package service.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RestResponseException {

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
