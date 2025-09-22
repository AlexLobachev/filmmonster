package ru.nexo.ocenka.filmmonster.model;

public enum Rating {
    G("У фильма нет возрастных ограничений"),
    PG("Детям рекомендуется смотреть фильм с родителями"),
    PG_13("Детям до 13 лет просмотр не желателен"),
    R("Лицам до 17 лет просматривать фильм можно только в присутствии взрослого"),
    NC("17 лицам до 18 лет просмотр запрещён");

    private final String title;

    Rating(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    }
