package ru.nexo.ocenka.filmmonster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nexo.ocenka.filmmonster.model.Film;
import ru.nexo.ocenka.filmmonster.model.FilmLike;
import ru.nexo.ocenka.filmmonster.storage.FilmStorage;

import java.util.Comparator;
import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {
    FilmStorage filmStorage;

    @Autowired
    public FilmServiceImpl(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film addLikeFilm(int idFilm, int idUser) {
        Film film = filmStorage.getFilm(idFilm);
        film.getUserLike().add(new FilmLike(idFilm, idUser));
        return film;
    }

    public Film deleteLikeFilm(int idFilm, int idUser) {
        Film film = filmStorage.getFilm(idFilm);
        film.getUserLike().remove(new FilmLike(idFilm, idUser));
        return film;
    }

    public List<Film> getPopularFilmTenLike(int count) {
        List<Film> popular = filmStorage.getAllFilm();
        Comparator<Film> popularFilmComparator = new PopularFilmComparator();
        popular.sort(popularFilmComparator);
        return popular.stream().limit(count).toList();
    }
}

class PopularFilmComparator implements Comparator<Film> {
    @Override
    public int compare(Film o1, Film o2) {
        return o2.getUserLike().size() - o1.getUserLike().size();
    }
}