package ru.nexo.ocenka.filmmonster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.nexo.ocenka.filmmonster.db.FilmDao;
import ru.nexo.ocenka.filmmonster.db.GenreDao;
import ru.nexo.ocenka.filmmonster.db.RatingDao;
import ru.nexo.ocenka.filmmonster.model.Film;
import ru.nexo.ocenka.filmmonster.model.Genre;
import ru.nexo.ocenka.filmmonster.model.Rating;

import java.util.List;

@Service("filmService")
public class FilmServiceImpl implements FilmService {
    @Qualifier("filmDao")
    private final FilmDao filmDao;


    @Autowired
    public FilmServiceImpl(FilmDao filmDao) {
        this.filmDao = filmDao;
    }

    @Override
    public void addLikeFilm(int idFilm, int idUser) {
        filmDao.addLikeFilm(idFilm, idUser);

    }

    @Override
    public void deleteLikeFilm(int idFilm, int idUser) {
        filmDao.deleteLikeFilm(idFilm, idUser);

    }

    @Override
    public List<Film> getPopularFilmTenLike(int count) {
        return filmDao.getPopularFilmTenLike(count);
    }




}

