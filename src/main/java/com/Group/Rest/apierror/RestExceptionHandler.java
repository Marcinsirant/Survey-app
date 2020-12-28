package com.Group.Rest.apierror;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import lombok.Getter;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Getter
    @Setter
    static class ApiError {

        private HttpStatus status;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
        private LocalDateTime timestamp;
        private String message;
        private String debugMessage;

        public ApiError(HttpStatus status, String message, Throwable ex) {
            this.status = status;
            this.message = message;
            this.debugMessage = ex.getLocalizedMessage();
            timestamp = LocalDateTime.now();
        }
    }

    @ExceptionHandler({RuntimeException.class})
    protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "Ups, cos poszlo nie tak", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

}
