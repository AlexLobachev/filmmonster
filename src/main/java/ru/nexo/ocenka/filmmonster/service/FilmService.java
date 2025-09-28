package ru.nexo.ocenka.filmmonster.service;

import ru.nexo.ocenka.filmmonster.model.Film;
import ru.nexo.ocenka.filmmonster.model.Genre;
import ru.nexo.ocenka.filmmonster.model.Rating;

import java.util.List;

public interface FilmService {

    void addLikeFilm(int idFilm,int idUser);

    void deleteLikeFilm(int idFilm,int idUser);

    List<Film> getPopularFilmTenLike(int count);

}
