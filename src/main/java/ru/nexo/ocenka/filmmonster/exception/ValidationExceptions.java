package ru.nexo.ocenka.filmmonster.exception;

import java.time.LocalDate;


public class ValidationExceptions extends RuntimeException {
    private LocalDate localDate;


    public ValidationExceptions(String message, LocalDate localDate) {
        super(message);
        this.localDate = localDate;
    }

    public ValidationExceptions(String message) {
        super(message);
    }


    public String getMessageError() {
        return getMessage() + " " + localDate;
    }

}
