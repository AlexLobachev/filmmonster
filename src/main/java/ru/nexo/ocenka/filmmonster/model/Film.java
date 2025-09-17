package ru.nexo.ocenka.filmmonster.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class Film {
    private final Integer id;
    @NotBlank
    private final String name;
    @Size(max = 200)
    @NotBlank
    private final String description;
    private final LocalDate releaseDate;
    private final int duration;
    private final List<FilmLike> userLike = new ArrayList<>();

}
