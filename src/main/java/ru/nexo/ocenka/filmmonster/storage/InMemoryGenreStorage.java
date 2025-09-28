package ru.nexo.ocenka.filmmonster.storage;

import ru.nexo.ocenka.filmmonster.model.Genre;

import java.util.List;

public interface InMemoryGenreStorage {
    List<Genre> getAllGenre();

    Genre getGenreById(int idGenre);

}
