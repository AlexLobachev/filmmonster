package ru.nexo.ocenka.filmmonster.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.nexo.ocenka.filmmonster.exception.ValidationExceptions;
import ru.nexo.ocenka.filmmonster.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class FilmController {
    private final Map<Integer, Film> filmMap = new HashMap<>();
    private int id = 0;

    @PostMapping("/films")
    public Film createFilm(@Valid @RequestBody Film film) {
        if (!validationFilm(film)) {
            return film;
        }
        film = create(film);
        filmMap.put(film.getId(), film);
        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (!validationFilm(film)) {
            return film;
        }
        film = create(film);
        filmMap.put(film.getId(), film);
        return film;
    }

    @GetMapping("films")
    public List<Film> getAllFilm() {
        return new ArrayList<>(filmMap.values());
    }

    private Film create(Film film) {
        film = Film.builder()
                .name(film.getName())
                .description(film.getDescription())
                .id(generateId())
                .duration(film.getDuration())
                .releaseDate(film.getReleaseDate())
                .build();
        return film;
    }

    private Boolean validationFilm(Film film) {
        try {
            if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))
                throw new ValidationExceptions("Дата релиза фильма не может быть раньше: ", LocalDate.of(1895, 12, 28));

            if (film.getDuration().isNegative()) {
                throw new ValidationExceptions("Продолжительность не может быть отрицательной");
            }
            return true;
        } catch (ValidationException e) {
            log.debug(e.getMessage());
            return false;
        }

    }

    private int generateId() {
        return ++id;
    }
}
