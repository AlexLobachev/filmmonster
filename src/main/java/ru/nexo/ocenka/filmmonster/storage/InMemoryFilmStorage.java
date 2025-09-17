package ru.nexo.ocenka.filmmonster.storage;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.nexo.ocenka.filmmonster.exception.NotFound;
import ru.nexo.ocenka.filmmonster.exception.ValidationExceptions;
import ru.nexo.ocenka.filmmonster.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> filmMap = new HashMap<>();
    private int id = 0;

    public Film createFilm(Film film) {
        if (!validationFilm(film)) {
            return film;
        }
        film = create(film);
        filmMap.put(film.getId(), film);
        return film;
    }

    public Film updateFilm(Film film) {
        if (!validationFilm(film)) {
            return film;
        }
        film = create(film);
        filmMap.put(film.getId(), film);
        return film;
    }

    public Film getFilm(int id) {
        return getAllFilm().stream().filter(x -> x.getId() == id).findFirst().orElseThrow(() -> new NotFound("Фильм не найден"));
    }

    public List<Film> getAllFilm() {
        return new ArrayList<>(filmMap.values());
    }

    private Film create(Film film) {
        if (film.getId() == null) {
            film = Film.builder()
                    .name(film.getName())
                    .description(film.getDescription())
                    .id(generateId())
                    .duration(film.getDuration())
                    .releaseDate(film.getReleaseDate())
                    .build();
        }
        else {
            film = Film.builder()
                    .name(film.getName())
                    .description(film.getDescription())
                    .id(film.getId())
                    .duration(film.getDuration())
                    .releaseDate(film.getReleaseDate())
                    .build();
        }
        return film;
    }

    private Boolean validationFilm(Film film) {
        try {
            if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))
                throw new ValidationException("Дата релиза фильма не может быть раньше: " + LocalDate.of(1895, 12, 28));

            if (film.getDuration() < 0) {
                throw new ValidationException("Продолжительность не может быть отрицательной");
            }
            return true;
        } catch (ValidationExceptions e) {
            log.debug(e.getMessageError());
            return false;
        }

    }

    private int generateId() {
        return ++id;
    }
}
