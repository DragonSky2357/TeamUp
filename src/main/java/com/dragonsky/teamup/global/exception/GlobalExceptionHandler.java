package com.dragonsky.teamup.global.exception;

import com.dragonsky.teamup.auth.exception.ErrorCode;
import com.dragonsky.teamup.auth.exception.MemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> error = new HashMap<>();

        ex.getBindingResult().getAllErrors()
                .forEach(c -> error.put(((FieldError) c).getField(), c.getDefaultMessage()));

        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", ex.getStatusCode());
        response.put("error", error);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<?> handleMemberException(IllegalAccessException ex) {
        Map<String, Object> response = new HashMap<>();


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleMemberException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<?> handleMemberException(MemberException ex) {
        Map<String, Object> response = new HashMap<>();

        ErrorCode errorCode = ex.getErrorCode();

        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", errorCode.message);
        response.put("error", errorCode);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
