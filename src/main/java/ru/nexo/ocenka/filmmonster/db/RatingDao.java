package ru.nexo.ocenka.filmmonster.db;

import ru.nexo.ocenka.filmmonster.model.Film;
import ru.nexo.ocenka.filmmonster.model.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingDao {
   List<Rating> getAllRating();
    Rating getRatingById(int idRating);

    Rating getRatingBiIdFilm(int idFilm);
    void addRatingFilmById(Film film);
    void updateRatingFilmById(Film film);
}
