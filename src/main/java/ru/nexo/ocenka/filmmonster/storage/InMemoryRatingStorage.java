package ru.nexo.ocenka.filmmonster.storage;

import ru.nexo.ocenka.filmmonster.model.Rating;

import java.util.List;

public interface InMemoryRatingStorage {
   List<Rating> getAllRating();
    Rating getRatingById(int idRating);

}
