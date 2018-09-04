package ch.olmero.tender.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class ServiceExceptionHandler {

    private final Logger _log = LoggerFactory.getLogger(ServiceExceptionHandler.class);

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<ErrorDTO> validationException(final Exception exception) {

        _log.error("Unexpected error occurred. Error message: {}", exception.getMessage(), exception);

        return ResponseEntity.status(BAD_REQUEST).body(
                new ErrorDTO(exception.getMessage())
        );
    }
}
