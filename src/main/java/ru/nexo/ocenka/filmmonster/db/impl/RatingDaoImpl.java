package ru.nexo.ocenka.filmmonster.db.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.nexo.ocenka.filmmonster.db.RatingDao;
import ru.nexo.ocenka.filmmonster.model.Film;
import ru.nexo.ocenka.filmmonster.model.Rating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component("ratingDao")
public class RatingDaoImpl implements RatingDao {
    private final JdbcTemplate jdbcTemplate;
    private String sqlQuery;

    @Autowired
    RatingDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Rating> getAllRating() {
        sqlQuery = ("SELECT * FROM rating");
        return jdbcTemplate.query(sqlQuery, this::ratingMapper);
    }

    @Override
    public Rating getRatingById(int idRating) {
        sqlQuery = ("SELECT * FROM rating WHERE id_rating = ?");
        return jdbcTemplate.queryForObject(sqlQuery, this::ratingMapper, idRating);
    }

    @Override
    public Rating getRatingBiIdFilm(int idRating) throws EmptyResultDataAccessException {
        sqlQuery = ("SELECT * " +
                "FROM rating r " +
                "JOIN film_rating fr ON r.id_rating = fr.id_rating " +
                "WHERE fr.id_film = ?");
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::ratingMapper, idRating);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public void addRatingFilmById(Film film) {
        sqlQuery = ("INSERT INTO film_rating (id_film, id_rating) " +
                "VALUES (?,?) ");
        //try {
            jdbcTemplate.update(sqlQuery, film.getId(), film.getMpa().getId());
       // }
        //catch (EmptyResultDataAccessException ignored){
            //}
    }

    @Override
    public void updateRatingFilmById(Film film) {
        sqlQuery = ("UPDATE film_rating SET id_rating = ? WHERE id_film = ?");
        //try {
            jdbcTemplate.update(sqlQuery,  film.getMpa().getId(), film.getId());
       // }
       // catch (EmptyResultDataAccessException ignored){
       // }
    }

    private Rating ratingMapper(ResultSet resultSet, int rowNum) throws SQLException {
        return Rating.builder().
                id(resultSet.getInt("id_rating")).
                name(resultSet.getString("name_rating"))
                .build();

    }



}
