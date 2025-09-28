package ru.nexo.ocenka.filmmonster.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Friend {
    private final Integer idFriend;
    private int friendship_status = 0;
}
