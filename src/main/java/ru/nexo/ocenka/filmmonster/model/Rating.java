package ru.nexo.ocenka.filmmonster.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Rating {
    private final Integer id;
    private final String name;
}
