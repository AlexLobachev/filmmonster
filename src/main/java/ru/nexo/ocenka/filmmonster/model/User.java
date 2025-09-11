package ru.nexo.ocenka.filmmonster.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class User {

    private final Integer id;
    @Email
    private final String email;
    @NotBlank
    private final String login;
    private final String name;
    @Past
    private final LocalDate birthday;
}
