package ru.nexo.ocenka.filmmonster.model;

import lombok.Data;

@Data
public class FilmLike {
    private final int userId;
    private final int filmId;

}
