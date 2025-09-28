package ru.nexo.ocenka.filmmonster.storage;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import ru.nexo.ocenka.filmmonster.db.FilmDao;
import ru.nexo.ocenka.filmmonster.db.GenreDao;
import ru.nexo.ocenka.filmmonster.db.RatingDao;
import ru.nexo.ocenka.filmmonster.exception.NotFound;
import ru.nexo.ocenka.filmmonster.model.Film;
import ru.nexo.ocenka.filmmonster.model.Genre;
import ru.nexo.ocenka.filmmonster.model.Rating;

import java.time.LocalDate;
import java.util.List;

@Component("filmStorage")
@Slf4j
public class InMemoryFilmStorage implements FilmStorage, InMemoryGenreStorage, InMemoryRatingStorage {
    @Qualifier("filmDao")
    private final FilmDao filmDao;
    @Qualifier("ratingDao")
    private final RatingDao ratingDao;
    @Qualifier("genreDao")
    private final GenreDao genreDao;

    @Autowired
    public InMemoryFilmStorage(FilmDao filmDao, RatingDao ratingDao, GenreDao genreDao) {
        this.filmDao = filmDao;
        this.ratingDao = ratingDao;
        this.genreDao = genreDao;
    }


    @Override
    public Film createFilm(Film film) {
        return filmDao.createFilm(validationFilm(film));

    }

    @Override
    public Film updateFilm(Film film) {
        return filmDao.updateFilm(validationFilm(film));
    }

    @Override
    public Film getFilm(int idFilm) {
        try {
            return filmDao.getFilm(idFilm);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFound("Фильм не найден");
        }

    }

    @Override
    public List<Film> getAllFilm() {
        return filmDao.getAllFilm();
    }

    @Override
    public void deleteFilm(int idFilm) {
        filmDao.deleteFilm(idFilm);
    }

    @Override
    public List<Genre> getAllGenre() {
        return genreDao.getAllGenre();
    }

    @Override
    public Genre getGenreById(int idGenre) {
        try {
            return genreDao.getGenreById(idGenre);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFound("Жанр не найден");
        }

    }

    @Override
    public List<Rating> getAllRating() {
        return ratingDao.getAllRating();
    }

    @Override
    public Rating getRatingById(int idRating) {
        try {
            return ratingDao.getRatingById(idRating);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFound("Рейтинг не найден");
        }

    }

    private Film validationFilm(Film film) {

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))
            throw new ValidationException("Дата релиза фильма не может быть раньше: " + LocalDate.of(1895, 12, 28));

        if (film.getDuration() < 0) {
            throw new ValidationException("Продолжительность не может быть отрицательной");
        }
        if (film.getMpa() == null) {
            throw new ValidationException("Не задан рейтинг фильма по возрастному ограничению");
        }
        return film;
    }


}
