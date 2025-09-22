package ru.nexo.ocenka.filmmonster.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    private final Set<Friend> friends = new HashSet<>();

}
