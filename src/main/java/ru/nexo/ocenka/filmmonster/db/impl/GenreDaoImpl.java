package ru.nexo.ocenka.filmmonster.db.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.nexo.ocenka.filmmonster.db.GenreDao;
import ru.nexo.ocenka.filmmonster.model.Film;
import ru.nexo.ocenka.filmmonster.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component("genreDao")
public class GenreDaoImpl implements GenreDao {

    private final JdbcTemplate jdbcTemplate;
    private String sqlQuery;

    @Autowired
    GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getAllGenre() {
        sqlQuery = ("SELECT * FROM genre");
        return jdbcTemplate.query(sqlQuery, this::genreMapper);
    }

    @Override
    public Genre getGenreById(int idGenre) {
        sqlQuery = ("SELECT * FROM genre WHERE id_genre = ?");
        return jdbcTemplate.queryForObject(sqlQuery, this::genreMapper, idGenre);
    }

    @Override
    public List<Genre> getAllGenreByIdFilm(int idFilm) {
        sqlQuery = ("SELECT * " +
                "FROM genre g " +
                "JOIN film_genre fg on g.id_genre = fg.id_genre " +
                "WHERE fg.id_film = ? ");
        return jdbcTemplate.query(sqlQuery, this::genreMapper, idFilm);

    }

    @Override
    public void addGenreFilmById(Film film) {

        try {
            sqlQuery = ("INSERT INTO film_genre (id_film, id_genre) VALUES (?,?)");
            for (Genre idGenre : film.getGenres())
                jdbcTemplate.update(sqlQuery, film.getId(), idGenre.getId());
        } catch (NullPointerException ignore) {

        }

    }

    @Override
    public void updateGenreFilmById(Film film) {

        try {
            if (film.getGenres() != null) {
                sqlQuery = ("DELETE from film_genre WHERE id_film = ?");
                jdbcTemplate.update(sqlQuery, film.getId());
                if (film.getGenres().size() >= 1) {
                    sqlQuery = ("INSERT INTO film_genre VALUES (?,?)");
                    for (Genre idGenre : film.getGenres())
                        jdbcTemplate.update(sqlQuery, film.getId(), idGenre.getId());
                }
            }


        } catch (NullPointerException ignore) {

        }


    }


    private Genre genreMapper(ResultSet resultSet, int rowNum) throws SQLException {
        return Genre.builder()
                .id(resultSet.getInt("id_genre"))
                .name(resultSet.getString("name_genre"))
                .build();
    }
}
