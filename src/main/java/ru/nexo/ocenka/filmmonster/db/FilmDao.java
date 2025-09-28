package ru.nexo.ocenka.filmmonster.db;

import ru.nexo.ocenka.filmmonster.model.Film;

import java.util.List;

public interface FilmDao {
    Film createFilm(Film film);

    Film updateFilm(Film film);

    Film getFilm(int id);

    List<Film> getAllFilm();

    void deleteFilm(int idFilm);

    void addLikeFilm(int idFilm, int idUser);

    void deleteLikeFilm(int idFilm,int idUser);

    List<Film> getPopularFilmTenLike(int count);


}
