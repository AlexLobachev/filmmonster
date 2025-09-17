package ru.nexo.ocenka.filmmonster.exception;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.nexo.ocenka.filmmonster.controller.FilmController;
import ru.nexo.ocenka.filmmonster.controller.UserController;

import java.util.Map;

@RestControllerAdvice(assignableTypes = {UserController.class, FilmController.class})
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> notFound(NotFound e) {
        return Map.of("error", e.getMessage(), "HttpStatus", HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> badRequest(ValidationException e){
        return Map.of("error",e.getMessage(),"HttpStatus",HttpStatus.BAD_REQUEST.getReasonPhrase());
    }
}
