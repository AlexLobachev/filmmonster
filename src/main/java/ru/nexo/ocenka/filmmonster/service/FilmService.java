package ru.nexo.ocenka.filmmonster.service;

import ru.nexo.ocenka.filmmonster.model.Film;

import java.util.List;

public interface FilmService {

    Film addLikeFilm(int idFilm,int idUser);

    Film deleteLikeFilm(int idFilm,int idUser);

    List<Film> getPopularFilmTenLike(int count);
}
