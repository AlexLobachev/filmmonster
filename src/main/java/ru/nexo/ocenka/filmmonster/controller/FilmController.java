package ru.nexo.ocenka.filmmonster.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.nexo.ocenka.filmmonster.model.Film;
import ru.nexo.ocenka.filmmonster.model.Genre;
import ru.nexo.ocenka.filmmonster.model.Rating;
import ru.nexo.ocenka.filmmonster.service.FilmService;
import ru.nexo.ocenka.filmmonster.storage.FilmStorage;
import ru.nexo.ocenka.filmmonster.storage.UserStorage;

import java.util.List;

@RestController
@Slf4j
public class FilmController {
    @Qualifier("filmStorage")
    private final FilmStorage filmStorage;
    @Qualifier("filmService")
    private final FilmService filmService;
    @Qualifier("userStorage")
    private final UserStorage userStorage;

    @Autowired
    FilmController(FilmStorage filmStorage, FilmService filmService, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
        this.userStorage = userStorage;
    }

    @PostMapping("/films")
    public Film createFilm(@Valid @RequestBody Film film) {

        return filmStorage.createFilm(film);
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        filmStorage.getFilm(film.getId());
        return filmStorage.updateFilm(film);
    }

    @GetMapping("films")
    public List<Film> getAllFilm() {
        return filmStorage.getAllFilm();
    }

    @GetMapping("films/{idFilm}")
    public Film getFilm(@PathVariable int idFilm) {
        filmStorage.getFilm(idFilm);
        return filmStorage.getFilm(idFilm);
    }

    @DeleteMapping("films/{idFilm}")
    void deleteFilm(@PathVariable("idFilm") int idFilm) {
        filmStorage.deleteFilm(idFilm);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLikeFilm(@PathVariable("id") int idFilm, @PathVariable("userId") int idUser) {
        userStorage.getUser(idUser);
        filmStorage.getFilm(idFilm);
        filmService.addLikeFilm(idFilm, idUser);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLikeFilm(@PathVariable("id") int idFilm, @PathVariable("userId") int idUser) {
        userStorage.getUser(idUser);
        filmStorage.getFilm(idFilm);
        filmService.deleteLikeFilm(idFilm, idUser);
    }

    @GetMapping("/films/popular")
    public List<Film> getPopularFilmTenLike(@RequestParam(defaultValue = "10") String count) {
        return filmService.getPopularFilmTenLike(Integer.parseInt(count));
    }


    @GetMapping("genres")
    public List<Genre> getAllGenre() {
        return filmStorage.getAllGenre();
    }

    @GetMapping("/genres/{id}")
    public Genre getGenreById(@PathVariable("id") int idGenre) {
        return filmStorage.getGenreById(idGenre);
    }

    @GetMapping("/mpa")
    public List<Rating> getAllRating() {
        return filmStorage.getAllRating();
    }

    @GetMapping("/mpa/{id}")
    public Rating getRatingById(@PathVariable("id") int idRating) {
        return filmStorage.getRatingById(idRating);
    }

}
