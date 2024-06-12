package com.dragonsky.teamup.global.exception;

import com.dragonsky.teamup.auth.exception.AuthException;
import com.dragonsky.teamup.game.exception.GameException;
import com.dragonsky.teamup.member.exception.MemberException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
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

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleMemberException(BadRequestException ex) {
        Map<String, Object> response = new HashMap<>();


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

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> handleMemberException(AuthException authException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(commonExceptionResponse(authException.getErrorCode()));
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<?> handleMemberException(MemberException memberException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(commonExceptionResponse(memberException.getErrorCode()));
    }

    @ExceptionHandler(GameException.class)
    public ResponseEntity<?> handleGameException(GameException gameException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(commonExceptionResponse(gameException.getErrorCode()));
    }

    private Map<String, Object> commonExceptionResponse(CommonException commonException) {
        Map<String, Object> response = new HashMap<>();

        response.put("status", commonException.getStatus());
        response.put("message", commonException.getMessage());
        response.put("error", commonException.getError());

        return response;
    }
}
