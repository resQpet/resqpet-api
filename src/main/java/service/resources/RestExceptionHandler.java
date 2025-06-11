package service.resources;

import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import service.domain.exceptions.RestResponseException;
import service.domain.exceptions.model.ErrorResponse;
import service.domain.exceptions.model.ValidationError;

import java.net.ConnectException;
import java.util.List;

@Log4j2
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {RestResponseException.class})
    public ResponseEntity<ErrorResponse> handler(RestResponseException error) {
        return ResponseEntity.status(error.getStatusCode()).body(error.asError());
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> handler(ConstraintViolationException exception) {
        final ErrorResponse error = ErrorResponse.of(exception.getMessage(), HttpStatus.CONFLICT);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(value = {DataAccessResourceFailureException.class, CannotGetJdbcConnectionException.class, ConnectException.class})
    public ResponseEntity<ErrorResponse> handler(Exception e) {
        final ErrorResponse error = ErrorResponse.of(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<ErrorResponse> handler(NullPointerException e) {
        final ErrorResponse error = ErrorResponse.of(String.format("Null: %s", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(value = {MultipartException.class})
    public ResponseEntity<ErrorResponse> handler(MultipartException e) {
        final ErrorResponse error = ErrorResponse.of(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handler(IllegalArgumentException e) {
        final ErrorResponse error = ErrorResponse.of(e.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handler(MethodArgumentNotValidException e) {
        List<ValidationError> errors = e.getAllErrors().stream().map(ValidationError::of).toList();
        final ErrorResponse error = ErrorResponse.of("Error de validación", HttpStatus.BAD_REQUEST, errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
