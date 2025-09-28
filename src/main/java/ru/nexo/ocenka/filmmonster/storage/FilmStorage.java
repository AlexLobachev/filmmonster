package ru.nexo.ocenka.filmmonster.storage;

import ru.nexo.ocenka.filmmonster.model.Film;

import java.util.List;

public interface FilmStorage extends InMemoryRatingStorage,InMemoryGenreStorage {
    Film createFilm(Film film);

    Film updateFilm(Film film);

    List<Film> getAllFilm();

    Film getFilm(int idFilm);

    void deleteFilm(int idFilm);

}
