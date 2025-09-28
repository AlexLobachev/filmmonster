package ru.nexo.ocenka.filmmonster.db;

import ru.nexo.ocenka.filmmonster.model.Film;
import ru.nexo.ocenka.filmmonster.model.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> getAllGenre();
    Genre getGenreById(int idGenre);

    List<Genre> getAllGenreByIdFilm (int idFilm);
    void addGenreFilmById(Film film);
    void updateGenreFilmById(Film film);
}
