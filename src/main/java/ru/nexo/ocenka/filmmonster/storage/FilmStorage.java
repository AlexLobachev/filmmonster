package ru.nexo.ocenka.filmmonster.storage;

import ru.nexo.ocenka.filmmonster.model.Film;

import java.util.List;

public interface FilmStorage {
    Film createFilm(Film film);

    Film updateFilm(Film film);

    List<Film> getAllFilm();

    Film getFilm(int id);

}
